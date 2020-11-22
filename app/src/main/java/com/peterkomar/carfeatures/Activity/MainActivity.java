package com.peterkomar.carfeatures.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.peterkomar.carfeatures.BuildConfig;
import com.peterkomar.carfeatures.Car.Commands;
import com.peterkomar.carfeatures.R;

import com.peterkomar.carfeatures.SmartDeviceLink.SdlReceiver;
import com.peterkomar.carfeatures.SmartDeviceLink.SdlService;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver logReceiver;

    public static final String LOG_TEXT_ACTION = "LOG_TEXT_ACTION";

    public static final String COMMAND_ACTION = "COMMAND_ACTION";
    public static final String COMMAND_ACTION_NAME = "COMMAND_ACTION_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupMessagesChannel();
        connectToSDL();
    }

    private void connectToSDL() {
        if(BuildConfig.TRANSPORT.equals("MULTI") || BuildConfig.TRANSPORT.equals("MULTI_HB")) {
            SdlReceiver.queryForConnectedService(this);
        } else if(BuildConfig.TRANSPORT.equals("TCP")) {
            Intent proxyIntent = new Intent(this, SdlService.class);
            startService(proxyIntent);
        }
    }

    private void setupMessagesChannel() {
        logReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                TextView view = findViewById(R.id.textView);
                view.setText(Messages.getStrLogs());
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LOG_TEXT_ACTION);
        registerReceiver(logReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        unregisterReceiver(logReceiver);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dev_menu, menu);

        // return true so that the menu pop up is opened
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(COMMAND_ACTION);
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.default_l:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.DEFAULT_TEMPLATE);
                sendBroadcast(intent);
                break;
            case R.id.default_none:
                intent.putExtra(COMMAND_ACTION_NAME, 0);
                sendBroadcast(intent);
                break;
            case R.id.media:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.MEDIA);
                sendBroadcast(intent);
                break;
            case R.id.non_media:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.NON_MEDIA);
                sendBroadcast(intent);
                break;
            case R.id.onscreen_presets:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.ONSCREEN_PRESETS);
                sendBroadcast(intent);
                break;
            case R.id.nav_fullscreen_map:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.NAV_FULLSCREEN_MAP);
                sendBroadcast(intent);
                break;
            case R.id.nav_list:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.NAV_LIST);
                sendBroadcast(intent);
                break;
            case R.id.graphic_with_text:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.GRAPHIC_WITH_TEXT);
                sendBroadcast(intent);
                break;
            case R.id.text_with_graphic:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.TEXT_WITH_GRAPHIC);
                sendBroadcast(intent);
                break;
            case R.id.tiles_only:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.TILES_ONLY);
                sendBroadcast(intent);
                break;
            case R.id.textbuttons_only:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.TEXTBUTTONS_ONLY);
                sendBroadcast(intent);
                break;
            case R.id.graphic_with_tiles:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.GRAPHIC_WITH_TILES);
                sendBroadcast(intent);
                break;
            case R.id.tiles_with_graphic:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.TILES_WITH_GRAPHIC);
                sendBroadcast(intent);
                break;
            case R.id.graphic_with_text_and_softbuttons:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.GRAPHIC_WITH_TEXT_AND_SOFTBUTTONS);
                sendBroadcast(intent);
                break;
            case R.id.text_and_softbuttons_with_graphic:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.TEXT_AND_SOFTBUTTONS_WITH_GRAPHIC);
                sendBroadcast(intent);
                break;
            case R.id.graphic_with_textbuttons:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.GRAPHIC_WITH_TEXTBUTTONS);
                sendBroadcast(intent);
                break;
            case R.id.textbuttons_with_graphic:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.TEXTBUTTONS_WITH_GRAPHIC);
                sendBroadcast(intent);
                break;
            case R.id.large_graphic_with_softbuttons:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.LARGE_GRAPHIC_WITH_SOFTBUTTONS);
                sendBroadcast(intent);
                break;
            case R.id.double_graphic_with_softbuttons:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.DOUBLE_GRAPHIC_WITH_SOFTBUTTONS);
                sendBroadcast(intent);
                break;
            case R.id.large_graphic_only:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.LARGE_GRAPHIC_ONLY);
                sendBroadcast(intent);
                break;
            case R.id.web_view:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.WEB_VIEW);
                sendBroadcast(intent);
                break;

            case R.id.primary_grapics:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.GRAPHICS_UPLOAD);
                sendBroadcast(intent);
                break;
            case R.id.text_fields:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.TEXT_FIELDS);
                sendBroadcast(intent);
                break;
            case R.id.graphic_secondary:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.GRAPHIC_SECONDARY);
                sendBroadcast(intent);
                break;
            case R.id.graphic_static:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.GRAPHIC_STATIC);
                sendBroadcast(intent);
                break;
            case R.id.upload_file:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.UPLOAD_FILE);
                sendBroadcast(intent);
                break;
            case R.id.alert:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.ALERT);
                sendBroadcast(intent);
                break;
            case R.id.alert_static:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.ALERT_STATIC);
                sendBroadcast(intent);
                break;
            case R.id.scroll_message:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.SCROLLABLE_MESSAGE);
                sendBroadcast(intent);
                break;
            case R.id.scroll_message_ua:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.SCROLLABLE_MESSAGE_UA);
                sendBroadcast(intent);
                break;
            case R.id.remote_files:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.REMOTE_FILES);
                sendBroadcast(intent);
                break;

            case R.id.fuel:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.FUEL);
                sendBroadcast(intent);
                break;
            case R.id.fuelrange:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.FUEL_RANGE);
                sendBroadcast(intent);
                break;
            case R.id.pressure:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.PRESSURE);
                sendBroadcast(intent);
                break;
            case R.id.oil:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.OIL);
                sendBroadcast(intent);
                break;
            case R.id.vin:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.VIN);
                sendBroadcast(intent);
                break;
            case R.id.gps:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.GPS);
                sendBroadcast(intent);
                break;
            case R.id.temperature:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.CAR_EXTERNAL_TEMPERATURE);
                sendBroadcast(intent);
                break;
            case R.id.odometer:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.CAR_ODOMETER);
                sendBroadcast(intent);
                break;
            case R.id.gear_status:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.CAR_GEAR_STATUS);
                sendBroadcast(intent);
                break;
            case R.id.prndl:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.CAR_PRNDL);
                sendBroadcast(intent);
                break;

            case R.id.image_fields:
                intent.putExtra(COMMAND_ACTION_NAME, Commands.COMPATIBILITY_IMAGE_FIELDS);
                sendBroadcast(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}