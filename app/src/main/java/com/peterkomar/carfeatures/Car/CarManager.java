package com.peterkomar.carfeatures.Car;

import com.peterkomar.carfeatures.Activity.Messages;
import com.peterkomar.carfeatures.SmartDeviceLink.SdlService;
import com.smartdevicelink.managers.SdlManager;

public class CarManager {

    protected final SdlManager sdlManager;
    protected final SdlService sdlService;

    private Templates tempate;
    private Vehicle vehicle;
    private Display display;
    private Compatibilities compatibility;

    public CarManager(final SdlManager sdl, SdlService service) {
        sdlManager = sdl;
        sdlService = service;
    }

    public void bootstrap() {
        tempate = new Templates(sdlManager, sdlService);
        vehicle = new Vehicle(sdlManager, sdlService);
        display = new Display(sdlManager, sdlService);
        compatibility = new Compatibilities(sdlManager, sdlService);
    }

    public void runCommand(int command) {
        switch (command) {
            case Commands.DEFAULT_TEMPLATE: tempate.setDefaultTemplate(); break;
            case Commands.DEFAULT_WITH_TEXT: tempate.setDefaultTextTemplateWithButtons(); break;
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

            case Commands.GRAPHICS_UPLOAD: display.setUploadGraphics(); break;
            case Commands.TEXT_FIELDS: display.setTextsFields(); break;
            case Commands.GRAPHIC_SECONDARY: display.setUploadGraphicsSecondary(); break;
            case Commands.GRAPHIC_STATIC: display.setUploadGraphicsStatic(); break;
            case Commands.UPLOAD_FILE: display.uploadFile(); break;
            case Commands.ALERT: display.setAlert(); break;
            case Commands.ALERT_STATIC: display.setAlertStaticImage(); break;
            case Commands.SCROLLABLE_MESSAGE: display.showScrollableMessage(); break;
            case Commands.SCROLLABLE_MESSAGE_UA: display.showScrollableMessageUA(); break;
            case Commands.REMOTE_FILES: display.getRemoveFiles(); break;
            case Commands.TEST_CHARACTERS: display.testSupportedCharacters(); break;

            case Commands.FUEL: vehicle.getFuel(); break;
            case Commands.FUEL_RANGE: vehicle.getFuelRange(); break;
            case Commands.PRESSURE: vehicle.getPressure(); break;
            case Commands.OIL: vehicle.getOilLife(); break;
            case Commands.VIN: vehicle.getVinCode(); break;
            case Commands.GPS: vehicle.getGps(); break;
            case Commands.CAR_EXTERNAL_TEMPERATURE: vehicle.getExternalTemperature(); break;
            case Commands.CAR_ODOMETER: vehicle.getOdometer(); break;
            case Commands.CAR_GEAR_STATUS: vehicle.getGearGearStatus(); break;
            case Commands.CAR_PRNDL: vehicle.getPrndl(); break;
            case Commands.CAR_BODY: vehicle.getBodyInfo(); break;
            case Commands.CAR_INSTANT_FUEL_CONSUMPTION: vehicle.getInstantFuelConsumption(); break;
            case Commands.CAR_FUEL_LEVEL_STATE: vehicle.getFuelLevelState(); break;
            case Commands.CAR_BELT_STATUS: vehicle.getBeltStatus(); break;
            case Commands.CAR_DRIVER_BRAKING: vehicle.getDriverBraking(); break;
            case Commands.CAR_WIPER_STATUS: vehicle.getWiperStatus(); break;
            case Commands.CAR_HEAD_LAMP_STATUS: vehicle.getHeadLampStatus(); break;
            case Commands.CAR_ENGINE_TORQUE: vehicle.getEngineTorque(); break;
            case Commands.CAR_ACC_PEDAL_POSITION: vehicle.getACCPedalPosition(); break;
            case Commands.CAR_STEERING_WHEEL_ANGLE: vehicle.getSteeringWheelAngle(); break;
            case Commands.CAR_AIRBAG_STATUS: vehicle.getAirbagStatus(); break;
            case Commands.CAR_CLUSTER_MODE_STATUS: vehicle.getClusterModeStatus(); break;
            case Commands.CAR_MYKEY: vehicle.getMykey(); break;
            case Commands.CAR_TURN_SIGNAL: vehicle.getTurnSignal(); break;
            case Commands.CAR_ELECTRONIC_PARKBRAKE_STATUS: vehicle.getElectronicParkBrakeStatus(); break;
            case Commands.CAR_KEY_RPM: vehicle.getRpm(); break;
            case Commands.CAR_KEY_SPEED: vehicle.getSpeed(); break;
            case Commands.CAR_DEVICE_STATUS: vehicle.getDeviceStatus(); break;

            case Commands.COMPATIBILITY_IMAGE_FIELDS: compatibility.getSupportedImageFields(); break;
            case Commands.COMPATIBILITY_TEXT_FIELDS: compatibility.getSupportedTextFields(); break;
            case Commands.SYSTEM_DATA: compatibility.getSystemData(); break;

            default:
                setDefaultText();
        }
    }

    private void setDefaultText() {
        sdlManager.getScreenManager().beginTransaction();
        sdlManager.getScreenManager().setTextField1("Hello text one");
        sdlManager.getScreenManager().setTextField2("Hello text two");
        sdlManager.getScreenManager().setTextField3("- ok <||==||> ok");
        sdlManager.getScreenManager().setTextField4("- ok <||==||> ok");
        sdlManager.getScreenManager().setMediaTrackTextField("Wheels statuses");
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
