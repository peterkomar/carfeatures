package com.peterkomar.carfeatures.SmartDeviceLink;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.smartdevicelink.transport.SdlBroadcastReceiver;
import com.smartdevicelink.transport.SdlRouterService;

public class SdlReceiver  extends SdlBroadcastReceiver {

	@Override
	public void onSdlEnabled(Context context, Intent intent) {
		intent.setClass(context, SdlService.class);

		// SdlService needs to be foregrounded in Android O and above
		// This will prevent apps in the background from crashing when they try to start SdlService
		// Because Android O doesn't allow background apps to start background services
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			context.startForegroundService(intent);
		} else {
			context.startService(intent);
		}
	}

	@Override
	public Class<? extends SdlRouterService> defineLocalSdlRouterClass() {
		return com.peterkomar.carfeatures.SmartDeviceLink.SdlRouterService.class;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
	}
}