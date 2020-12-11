package com.peterkomar.carfeatures.SmartDeviceLink;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;

import com.peterkomar.carfeatures.Activity.MainActivity;
import com.peterkomar.carfeatures.Activity.Messages;
import com.peterkomar.carfeatures.BuildConfig;
import com.peterkomar.carfeatures.Car.CarManager;
import com.peterkomar.carfeatures.Car.Permissions;
import com.smartdevicelink.managers.SdlManager;
import com.smartdevicelink.managers.SdlManagerListener;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.managers.lifecycle.LifecycleConfigurationUpdate;
import com.smartdevicelink.managers.lockscreen.LockScreenConfig;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.PredefinedWindows;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.MultiplexTransportConfig;
import com.smartdevicelink.transport.TCPTransportConfig;
import com.smartdevicelink.util.DebugTool;
import com.smartdevicelink.util.Version;

import java.util.Vector;

public class SdlService extends Service {

	private static final String ICON_FILENAME = "app_features_icon.png";

	private static final int FOREGROUND_SERVICE_ID = 550;

	// Manticore
	private static final int TCP_PORT = 14393;
	private static final String DEV_MACHINE_IP_ADDRESS = "m.sdl.tools";

	// Sync Emulator
	//private static final int TCP_PORT = 12345;
	//private static final String DEV_MACHINE_IP_ADDRESS =  "192.168.0.107";

	// variable to create and call functions of the SyncProxy
	private SdlManager sdlManager = null;

	private CarManager carManager = null;

	private BroadcastReceiver commandReceiver;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			enterForeground();
		}
	}

	// Helper method to let the service enter foreground mode
	@SuppressLint("NewApi")
	public void enterForeground() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel channel = new NotificationChannel(BuildConfig.APP_ID,
					"SdlService", NotificationManager.IMPORTANCE_DEFAULT);
			NotificationManager notificationManager = (NotificationManager)
					getSystemService(Context.NOTIFICATION_SERVICE);
			if (notificationManager != null) {
				notificationManager.createNotificationChannel(channel);
				Notification serviceNotification = new Notification.Builder(this,
						channel.getId())
						.setContentTitle("Connected through SDL")
						.setSmallIcon(com.peterkomar.carfeatures.R.mipmap.ic_launcher)
						.build();

				startForeground(FOREGROUND_SERVICE_ID, serviceNotification);
				Messages.info("Connected to SDL core");
			}
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		startProxy();
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(commandReceiver);
		Messages.info("Disconnected from SDL core");
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			stopForeground(true);
		}

		if (sdlManager != null) {
			sdlManager.dispose();
		}

		super.onDestroy();
	}

	private void startProxy() {
		// This logic is to select the correct transport and security levels defined
		// in the selected build flavor
		// Build flavors are selected by the "build variants" tab typically located
		// in the bottom left of Android Studio
		// Typically in your app, you will only set one of these.
		if (sdlManager == null) {
			// Enable DebugTool for debug build type
			if (BuildConfig.DEBUG) {
				DebugTool.enableDebugTool();
			}
			BaseTransportConfig transport = null;
			if (BuildConfig.TRANSPORT.equals("MULTI")) {
				int securityLevel;
				if (BuildConfig.SECURITY.equals("HIGH")) {
					securityLevel = MultiplexTransportConfig.FLAG_MULTI_SECURITY_HIGH;
				} else if (BuildConfig.SECURITY.equals("MED")) {
					securityLevel = MultiplexTransportConfig.FLAG_MULTI_SECURITY_MED;
				} else if (BuildConfig.SECURITY.equals("LOW")) {
					securityLevel = MultiplexTransportConfig.FLAG_MULTI_SECURITY_LOW;
				} else {
					securityLevel = MultiplexTransportConfig.FLAG_MULTI_SECURITY_OFF;
				}
				transport = new MultiplexTransportConfig(this, BuildConfig.APP_ID, securityLevel);
			} else if (BuildConfig.TRANSPORT.equals("TCP")) {
				transport = new TCPTransportConfig(TCP_PORT, DEV_MACHINE_IP_ADDRESS,
						true);
			} else if (BuildConfig.TRANSPORT.equals("MULTI_HB")) {
				MultiplexTransportConfig mtc = new MultiplexTransportConfig(this, BuildConfig.APP_ID,
						MultiplexTransportConfig.FLAG_MULTI_SECURITY_OFF);
				mtc.setRequiresHighBandwidth(true);
				transport = mtc;
			}

			// The app type to be used
			Vector<AppHMIType> appType = new Vector<>();
			appType.add(AppHMIType.DEFAULT);
			final com.peterkomar.carfeatures.SmartDeviceLink.SdlService service = this;

			// The manager listener helps you know when certain events that pertain
			// to the SDL Manager happen
			// Here we will listen for ON_HMI_STATUS and ON_COMMAND notifications
			SdlManagerListener listener = new SdlManagerListener() {
				@Override
				public void onStart() {
					// HMI Status Listener
					sdlManager.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS,
							new OnRPCNotificationListener() {
						@Override
						public void onNotified(RPCNotification notification) {
							OnHMIStatus onHMIStatus = (OnHMIStatus) notification;
							if (onHMIStatus.getWindowID() != null
									&& onHMIStatus.getWindowID()
									!= PredefinedWindows.DEFAULT_WINDOW.getValue()) {
								return;
							}
							if (onHMIStatus.getHmiLevel() == HMILevel.HMI_FULL
									&& onHMIStatus.getFirstRun()) {
								Permissions.identifyPermissions(sdlManager, service);
								carManager = new CarManager(sdlManager, service);
								carManager.bootstrap();
								setupCommandsChannel();
							}
						}
					});
				}

				@Override
				public void onDestroy() {
					com.peterkomar.carfeatures.SmartDeviceLink.SdlService.this.stopSelf();

				}

				@Override
				public void onError(String info, Exception e) {
					Messages.error("SDL Error:" + info);
					updateMessagesLog();
				}

				@Override
				public LifecycleConfigurationUpdate managerShouldUpdateLifecycle(
						Language language, Language hmiLanguage) {
					return null;
				}
			};

			// Create App Icon, this is set in the SdlManager builder
			SdlArtwork appIcon = new SdlArtwork(ICON_FILENAME, FileType.GRAPHIC_PNG,
					com.peterkomar.carfeatures.R.mipmap.ic_launcher, true);

			// The manager builder sets options for your session
			SdlManager.Builder builder = new SdlManager.Builder(this,
					BuildConfig.APP_ID,
					BuildConfig.APP_NAME,
					listener);
			builder.setAppTypes(appType);
			builder.setTransportType(transport);
			builder.setAppIcon(appIcon);
			builder.setLockScreenConfig(getLockScreenConfig());
			builder.setMinimumProtocolVersion(new Version("2.0.0"));

			builder.setLanguage(Language.EN_US);

			sdlManager = builder.build();
			sdlManager.start();
		}
	}

	private LockScreenConfig getLockScreenConfig() {
		LockScreenConfig lockScreenConfig = new LockScreenConfig();
		lockScreenConfig.setAppIcon(com.peterkomar.carfeatures.R.mipmap.ic_launcher);
		return lockScreenConfig;
	}

	public void updateMessagesLog() {
		Intent intent = new Intent(MainActivity.LOG_TEXT_ACTION);
		sendBroadcast(intent);
	}

	private void setupCommandsChannel() {
		commandReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				int command = intent.getIntExtra(MainActivity.COMMAND_ACTION_NAME, 0);
				carManager.runCommand(command);
			}
		};
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(MainActivity.COMMAND_ACTION);
		registerReceiver(commandReceiver, intentFilter);
	}
}

