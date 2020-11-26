package com.peterkomar.carfeatures.Car;

import com.peterkomar.carfeatures.Activity.Messages;
import com.peterkomar.carfeatures.R;
import com.peterkomar.carfeatures.SmartDeviceLink.SdlService;
import com.smartdevicelink.managers.SdlManager;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.Alert;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.ImageField;
import com.smartdevicelink.proxy.rpc.OnButtonPress;
import com.smartdevicelink.proxy.rpc.ScrollableMessage;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;
import com.smartdevicelink.proxy.rpc.enums.StaticIconName;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Display extends CarManager {

    public Display(final SdlManager sdl, SdlService service) {
        super(sdl, service);
    }

    public void setTextsFields() {
        sdlManager.getScreenManager().beginTransaction();
        sdlManager.getScreenManager().setTextField1("1 - 123456789123456789123456789");
        sdlManager.getScreenManager().setTextField2("2 - 123456789123456789123456789");
        sdlManager.getScreenManager().setTextField3("3 - 123456789123456789123456789");
        sdlManager.getScreenManager().setTextField4("4 - 123456789123456789123456789");
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

    public void setUploadGraphics() {

        Picture carPicture = new Picture();
        sdlManager.getScreenManager().beginTransaction();
        sdlManager.getScreenManager().setPrimaryGraphic(carPicture.renderSmall());
        sdlManager.getScreenManager().commit(success -> {
            if (success) {
                Messages.info("Graphic ok ");
            } else {
                Messages.info("Graphic fail");
            }
            sdlService.updateMessagesLog();
        });
    }

    public void setUploadGraphicsSecondary() {

        Picture carPicture = new Picture();
        sdlManager.getScreenManager().beginTransaction();
        sdlManager.getScreenManager().setSecondaryGraphic(carPicture.renderSmall());
        sdlManager.getScreenManager().commit(success -> {
            if (success) {
                Messages.info("Secondary Graphic ok ");
            } else {
                Messages.info("Secondary Graphic fail");
            }
            sdlService.updateMessagesLog();
        });
    }

    public void setUploadGraphicsStatic() {

        SdlArtwork staticIconArt = new SdlArtwork(StaticIconName.BATTERY_CAPACITY_4_OF_5);

        sdlManager.getScreenManager().beginTransaction();
        sdlManager.getScreenManager().setPrimaryGraphic(staticIconArt);
        sdlManager.getScreenManager().setSecondaryGraphic(staticIconArt);
        sdlManager.getScreenManager().commit(success -> {
            if (success) {
                Messages.info("Static Graphic ok ");
            } else {
                Messages.info("Static Graphic fail");
            }
            sdlService.updateMessagesLog();
        });
    }

    public void uploadFile() {

        int bytesAvailable = sdlManager.getFileManager().getBytesAvailable();
        Messages.info("Avaiable storage: " + bytesAvailable);

        final SdlArtwork image = new SdlArtwork("artworkName", FileType.GRAPHIC_PNG, R.drawable.car_small, true);

        sdlManager.getFileManager().uploadFile(image, success -> {
            if (success) {
                Messages.info("File uploaded");
            } else {
                Messages.info("File not uploaded");
            }
            sdlService.updateMessagesLog();
        });
    }

    public void setAlert() {
        Alert alert = new Alert();
        alert.setAlertText1("Line 1");
        alert.setAlertText2("Line 2");
        alert.setAlertText3("Line 3");

        final int softButtonId = 123; // Set it to any unique ID
        SoftButton okButton = new SoftButton(SoftButtonType.SBT_TEXT, softButtonId);
        okButton.setText("OK");

        alert.setSoftButtons(Collections.singletonList(okButton));

        sdlManager.addOnRPCNotificationListener(FunctionID.ON_BUTTON_PRESS, new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {
                OnButtonPress onButtonPress = (OnButtonPress) notification;
                if (onButtonPress.getCustomButtonID() == softButtonId){
                    Messages.info("Alert OK pressed");
                    sdlService.updateMessagesLog();
                }
            }
        });

        alert.setAlertIcon(new Image("artworkName", ImageType.DYNAMIC));

        // Handle RPC response
        alert.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if (response.getSuccess()){
                    Messages.info("Alert was shown successfully");
                    sdlService.updateMessagesLog();
                }
            }
        });
        sdlManager.sendRPC(alert);
    }

    public void setAlertStaticImage() {
        Alert alert = new Alert();
        alert.setAlertText1("Line 1");
        alert.setAlertText2("Line 2");
        alert.setAlertText3("Line 3");

        final int softButtonId = 123; // Set it to any unique ID
        SoftButton okButton = new SoftButton(SoftButtonType.SBT_TEXT, softButtonId);
        okButton.setText("OK");

        alert.setSoftButtons(Collections.singletonList(okButton));

        sdlManager.addOnRPCNotificationListener(FunctionID.ON_BUTTON_PRESS, new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {
                OnButtonPress onButtonPress = (OnButtonPress) notification;
                if (onButtonPress.getCustomButtonID() == softButtonId){
                    Messages.info("Alert OK pressed");
                    sdlService.updateMessagesLog();
                }
            }
        });

        alert.setAlertIcon(new Image(StaticIconName.BATTERY_CAPACITY_4_OF_5.toString(), ImageType.STATIC));

        // Handle RPC response
        alert.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if (response.getSuccess()){
                    Messages.info("Alert was shown successfully");
                    sdlService.updateMessagesLog();
                }
            }
        });
        sdlManager.sendRPC(alert);
    }

    public void showScrollableMessage() {
        String scrollableMessageText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Vestibulum mattis ullamcorper velit sed ullamcorper morbi tincidunt ornare. Purus in massa tempor nec feugiat nisl pretium fusce id. Pharetra convallis posuere morbi leo urna molestie at elementum eu. Dictum sit amet justo donec enim diam.";

        ScrollableMessage scrollableMessage = new ScrollableMessage();
        scrollableMessage.setScrollableMessageBody(scrollableMessageText);
        sdlManager.sendRPC(scrollableMessage);
    }

    public void showScrollableMessageUA() {
        String scrollableMessageText = "Цей український текст створено щоб відображати на цьому дисплеї, тет на скільки він довний може бути і чи є піжтримка кирилиці іта інші всякі такі штуки...";

        ScrollableMessage scrollableMessage = new ScrollableMessage();
        scrollableMessage.setScrollableMessageBody(scrollableMessageText);
        sdlManager.sendRPC(scrollableMessage);
    }

    public void getRemoveFiles() {
        List<String> files = sdlManager.getFileManager().getRemoteFileNames();
        final int size = files.size();
        Messages.info("Remove files: ");
        for (int i = 0; i < size; i++)
        {
            String file = files.get(i);
            Messages.info(file);
        }
        sdlService.updateMessagesLog();
    }

}
