package com.peterkomar.carfeatures.Car;

import android.annotation.SuppressLint;

import com.peterkomar.carfeatures.Activity.Messages;
import com.peterkomar.carfeatures.SmartDeviceLink.SdlService;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.SdlManager;
import com.smartdevicelink.managers.screen.SoftButtonObject;
import com.smartdevicelink.managers.screen.SoftButtonState;
import com.smartdevicelink.managers.screen.menu.MenuCell;
import com.smartdevicelink.managers.screen.menu.MenuSelectionListener;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.GPSData;
import com.smartdevicelink.proxy.rpc.GetVehicleData;
import com.smartdevicelink.proxy.rpc.GetVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.OnButtonEvent;
import com.smartdevicelink.proxy.rpc.OnButtonPress;
import com.smartdevicelink.proxy.rpc.TemplateConfiguration;
import com.smartdevicelink.proxy.rpc.enums.PredefinedLayout;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

import java.util.Arrays;
import java.util.Collections;

public class CarManager {

    protected final SdlManager sdlManager;
    protected final SdlService sdlService;

    private Templates tempate;
    private Vehicle vehicle;

    public CarManager(final SdlManager sdl, SdlService service) {
        sdlManager = sdl;
        sdlService = service;
    }

    public void bootstrap() {
        tempate = new Templates(sdlManager, sdlService);
        vehicle = new Vehicle(sdlManager, sdlService);
    }

    public void runCommand(int command) {
        switch (command) {
            case Commands.DEFAULT_TEMPLATE: tempate.setDefaultTemplate(); break;
            case Commands.MEDIA: tempate.setMedia(); break;
            case Commands.NON_MEDIA: tempate.setNonMedia(); break;
            case Commands.ONSCREEN_PRESETS: tempate.setOnscreenPresets(); break;
            case Commands.NAV_FULLSCREEN_MAP: tempate.setNavFullscreenMap(); break;
            case Commands.NAV_LIST: tempate.setNavList(); break;
            case Commands.GRAPHIC_WITH_TEXT: tempate.setGraphicWithText(); break;
            case Commands.TEXT_WITH_GRAPHIC: tempate.setTextWithGraphic(); break;
            case Commands.TILES_ONLY: tempate.setTilesOnly(); break;
            case Commands.TEXTBUTTONS_ONLY: tempate.setTextButtonsOnly(); break;
            case Commands.GRAPHIC_WITH_TILES: tempate.setGraphicWithTiles(); break;
            case Commands.TILES_WITH_GRAPHIC: tempate.setTilesWithGraphic(); break;
            case Commands.GRAPHIC_WITH_TEXT_AND_SOFTBUTTONS: tempate.setGraphicWithTextAndSoftbuttons(); break;
            case Commands.TEXT_AND_SOFTBUTTONS_WITH_GRAPHIC: tempate.setTextAndSoftbuttonsWithGraphic(); break;
            case Commands.GRAPHIC_WITH_TEXTBUTTONS: tempate.setGraphicWithTextbuttons(); break;
            case Commands.TEXTBUTTONS_WITH_GRAPHIC: tempate.setTextbuttonsWithGraphic(); break;
            case Commands.LARGE_GRAPHIC_WITH_SOFTBUTTONS: tempate.setLargeGraphicWithSoftbuttons(); break;
            case Commands.DOUBLE_GRAPHIC_WITH_SOFTBUTTONS: tempate.setDoubleGraphicWithSoftbuttons(); break;
            case Commands.LARGE_GRAPHIC_ONLY: tempate.setLargeGraphicOnly(); break;
            case Commands.WEB_VIEW: tempate.setWebView(); break;

            case Commands.FUEL: vehicle.getFuel(); break;
            case Commands.FUEL_RANGE:vehicle.getFuelRange(); break;
            case Commands.PRESSURE: vehicle.getPressure(); break;
            case Commands.OIL: vehicle.getOilLife(); break;
            case Commands.VIN: vehicle.getVinCode(); break;
            case Commands.GPS: vehicle.getGps(); break;

            default:
                setDefaultText();
        }
    }

    private void setDefaultText() {
        sdlManager.getScreenManager().beginTransaction();
        sdlManager.getScreenManager().setTextField1("Hello text one");
        sdlManager.getScreenManager().setTextField2("Hello text two");
        sdlManager.getScreenManager().setTextField3("Hello text three");
        sdlManager.getScreenManager().setTextField4("Hello text four");
        sdlManager.getScreenManager().setMediaTrackTextField("Media Track Field");
        sdlManager.getScreenManager().commit(success -> {
            if (success) {
                Messages.info("setDefaultText ok ");
            } else {
                Messages.info("setDefaultText fail");
            }
            sdlService.updateMessagesLog();
        });
    }
}
