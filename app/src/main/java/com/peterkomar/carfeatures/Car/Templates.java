package com.peterkomar.carfeatures.Car;

import com.peterkomar.carfeatures.Activity.Messages;
import com.peterkomar.carfeatures.SmartDeviceLink.SdlService;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.SdlManager;
import com.smartdevicelink.managers.screen.SoftButtonObject;
import com.smartdevicelink.managers.screen.SoftButtonState;
import com.smartdevicelink.proxy.rpc.OnButtonEvent;
import com.smartdevicelink.proxy.rpc.OnButtonPress;
import com.smartdevicelink.proxy.rpc.TemplateConfiguration;
import com.smartdevicelink.proxy.rpc.enums.PredefinedLayout;

import java.util.Arrays;
import java.util.Collections;

public class Templates extends CarManager {

    public Templates(final SdlManager sdl, SdlService service) {
        super(sdl, service);
    }

    public void setDefaultTemplate() {
        sdlManager.getScreenManager().beginTransaction();
        sdlManager.getScreenManager().setTextField1("Text Field 1");
        sdlManager.getScreenManager().setTextField2("Text Field 2");
        sdlManager.getScreenManager().setMediaTrackTextField("Media Track Field");
        TemplateConfiguration templateConfiguration = new TemplateConfiguration().setTemplate(PredefinedLayout.DEFAULT.toString());
        sdlManager.getScreenManager().changeLayout(templateConfiguration, success -> {
            if (success) {
                Messages.info("Default OK");
            } else {
                Messages.info("Default NON ok");
            }
            sdlService.updateMessagesLog();
        });
        Picture carPicture = new Picture();
        sdlManager.getScreenManager().setPrimaryGraphic(carPicture.renderSmall());

        sdlManager.getScreenManager().commit(success -> {
            if (success) {
                Messages.info("DEFAULT OK");
            } else {
                Messages.info("DEFAULT Fail");
            }
            sdlService.updateMessagesLog();
        });
    }

    public void setMedia() {
        sdlManager.getScreenManager().beginTransaction();
        sdlManager.getScreenManager().setTextField1("Text Field 1");
        sdlManager.getScreenManager().setTextField2("Text Field 2");
        sdlManager.getScreenManager().setMediaTrackTextField("Media Track Field");
        TemplateConfiguration templateConfiguration = new TemplateConfiguration().setTemplate(PredefinedLayout.MEDIA.toString());
        sdlManager.getScreenManager().changeLayout(templateConfiguration, success -> {
            if (success) {
                Messages.info("MEDIA OK");
            } else {
                Messages.info("MEDIA FAIL");
            }
            sdlService.updateMessagesLog();
        });
        Picture carPicture = new Picture();
        sdlManager.getScreenManager().setPrimaryGraphic(carPicture.renderSmall());

        sdlManager.getScreenManager().commit(success -> {
            if (success) {
                Messages.info("MEDIA OK");
            } else {
                Messages.info("MEDIA FAIL");
            }
            sdlService.updateMessagesLog();
        });
    }

    public void setNonMedia() {

    }

    public void setOnscreenPresets() {

    }

    public void setNavFullscreenMap() {

    }

    public void setNavList() {

    }

    public void setGraphicWithText() {
        sdlManager.getScreenManager().beginTransaction();
        sdlManager.getScreenManager().setTextField1("Text Field 1");
        sdlManager.getScreenManager().setTextField2("Text Field 2");
        sdlManager.getScreenManager().setMediaTrackTextField("Media Track Field");
        TemplateConfiguration templateConfiguration = new TemplateConfiguration().setTemplate(PredefinedLayout.GRAPHIC_WITH_TEXT.toString());
        sdlManager.getScreenManager().changeLayout(templateConfiguration, success -> {
            if (success) {
                Messages.info("GRAPHIC_WITH_TEXT OK");
            } else {
                Messages.info("GRAPHIC_WITH_TEXT FAIL");
            }
            sdlService.updateMessagesLog();
        });
        Picture carPicture = new Picture();
        sdlManager.getScreenManager().setPrimaryGraphic(carPicture.renderSmall());

        sdlManager.getScreenManager().commit(success -> {
            if (success) {
                Messages.info("GRAPHIC_WITH_TEXT OK");
            } else {
                Messages.info("GRAPHIC_WITH_TEXT FAIL");
            }
            sdlService.updateMessagesLog();
        });
    }

    public void setTextWithGraphic() {

    }

    public void setTilesOnly() {

    }

    public void setTextButtonsOnly() {

    }

    public void setGraphicWithTiles() {

    }

    public void setTilesWithGraphic() {

    }

    public void setGraphicWithTextAndSoftbuttons() {

    }

    public void setTextAndSoftbuttonsWithGraphic() {

    }

    public void setGraphicWithTextbuttons() {
        SoftButtonState textState = new SoftButtonState("statusTire", "Status", null);
        SoftButtonObject softButtonObject = new SoftButtonObject("softButtonObject", Collections.singletonList(textState), textState.getName(), new SoftButtonObject.OnEventListener() {
            @Override
            public void onPress(SoftButtonObject softButtonObject, OnButtonPress onButtonPress) {
                Messages.info("Click on Status button");
                sdlService.updateMessagesLog();
            }

            @Override
            public void onEvent(SoftButtonObject softButtonObject, OnButtonEvent onButtonEvent) {
                Messages.info("OnButton event triggered");
                sdlService.updateMessagesLog();

            }
        });

        SoftButtonState pressureState = new SoftButtonState("pressureTire", "Pressure", null);
        SoftButtonObject pressureButtonObject = new SoftButtonObject(
                "pressButtonObject",
                Collections.singletonList(pressureState),
                pressureState.getName(),
                new SoftButtonObject.OnEventListener() {
                    @Override
                    public void onPress(SoftButtonObject softButtonObject, OnButtonPress onButtonPress) {
                        Messages.info("Click on Pressure button");

                        sdlService.updateMessagesLog();
                    }

                    @Override
                    public void onEvent(SoftButtonObject softButtonObject, OnButtonEvent onButtonEvent) {
                        Messages.info("OnButton pressure event triggered");
                        sdlService.updateMessagesLog();

                    }
                });


        sdlManager.getScreenManager().beginTransaction();
        TemplateConfiguration templateConfiguration = new TemplateConfiguration().setTemplate(PredefinedLayout.GRAPHIC_WITH_TEXTBUTTONS.toString());
        sdlManager.getScreenManager().changeLayout(templateConfiguration, success -> {
            if (success) {
                Messages.info("GRAPHIC_WITH_TEXTBUTTONS ok");
            } else {
                Messages.info("GRAPHIC_WITH_TEXTBUTTONS fail");
            }
            sdlService.updateMessagesLog();
        });
        Picture carPicture = new Picture();
        sdlManager.getScreenManager().setPrimaryGraphic(carPicture.renderSmall());
        sdlManager.getScreenManager().setSoftButtonObjects(Arrays.asList(softButtonObject, pressureButtonObject));
        sdlManager.getScreenManager().commit(new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                if (success) {
                    Messages.info("GRAPHIC_WITH_TEXTBUTTONS OK");
                } else {
                    Messages.info("GRAPHIC_WITH_TEXTBUTTONS fail");
                }
                sdlService.updateMessagesLog();
            }
        });
    }

    public void setTextbuttonsWithGraphic() {

    }

    public void setLargeGraphicWithSoftbuttons() {
        SoftButtonState demoState = new SoftButtonState("demo1", "demo1", null);
        SoftButtonObject softButtonObject = new SoftButtonObject("softButtonObject", Collections.singletonList(demoState), demoState.getName(), new SoftButtonObject.OnEventListener() {
            @Override
            public void onPress(SoftButtonObject softButtonObject, OnButtonPress onButtonPress) {
                Messages.info("Click on demo1 button");
                sdlService.updateMessagesLog();
            }

            @Override
            public void onEvent(SoftButtonObject softButtonObject, OnButtonEvent onButtonEvent) {
                Messages.info("OnButton event triggered");
                sdlService.updateMessagesLog();

            }
        });

        SoftButtonState demo2State = new SoftButtonState("demo2", "demo2", null);
        SoftButtonObject pressureButtonObject = new SoftButtonObject(
                "pressButtonObject",
                Collections.singletonList(demo2State),
                demo2State.getName(),
                new SoftButtonObject.OnEventListener() {
                    @Override
                    public void onPress(SoftButtonObject softButtonObject, OnButtonPress onButtonPress) {
                        Messages.info("Click on Demo2 button");
                        sdlService.updateMessagesLog();
                    }

                    @Override
                    public void onEvent(SoftButtonObject softButtonObject, OnButtonEvent onButtonEvent) {
                        Messages.info("OnButton pressure event triggered");
                        sdlService.updateMessagesLog();

                    }
                });


        sdlManager.getScreenManager().beginTransaction();
        TemplateConfiguration templateConfiguration = new TemplateConfiguration().setTemplate(PredefinedLayout.LARGE_GRAPHIC_WITH_SOFTBUTTONS.toString());
        sdlManager.getScreenManager().changeLayout(templateConfiguration, new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                if (success) {
                    Messages.info("LARGE_GRAPHIC_WITH_SOFTBUTTONS OK");
                } else {
                    Messages.info("LARGE_GRAPHIC_WITH_SOFTBUTTONS FAIL");
                }
                sdlService.updateMessagesLog();
            }
        });
        Picture carPicture = new Picture();
        sdlManager.getScreenManager().setPrimaryGraphic(carPicture.renderSmall());
        sdlManager.getScreenManager().setSoftButtonObjects(Arrays.asList(softButtonObject, pressureButtonObject));
        sdlManager.getScreenManager().commit(success -> {
            if (success) {
                Messages.info("LARGE_GRAPHIC_WITH_SOFTBUTTONS OK");
            } else {
                Messages.info("LARGE_GRAPHIC_WITH_SOFTBUTTONS FAIL");
            }
            sdlService.updateMessagesLog();
        });
    }

    public void setDoubleGraphicWithSoftbuttons() {

    }

    public void setLargeGraphicOnly() {

    }

    public void setWebView() {

    }
}
