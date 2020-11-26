package com.peterkomar.carfeatures.Car;

import com.peterkomar.carfeatures.Activity.Messages;
import com.peterkomar.carfeatures.SmartDeviceLink.SdlService;
import com.smartdevicelink.managers.SdlManager;
import com.smartdevicelink.proxy.rpc.ImageField;
import com.smartdevicelink.proxy.rpc.TextField;

import java.util.List;

public class Compatibilities extends CarManager {

    public Compatibilities(final SdlManager sdl, SdlService service) {
        super(sdl, service);
    }

    public void getSupportedImageFields() {
        List<ImageField> images = sdlManager.getSystemCapabilityManager().getDefaultMainWindowCapability().getImageFields();
        final int size = images.size();
        Messages.info("Remote image fields: ");
        sdlService.updateMessagesLog();
        for (int i = 0; i < size; i++)
        {
            ImageField file = images.get(i);
            Messages.info(file.getName().toString());
            Messages.info(file.getImageResolution().getResolutionWidth().toString());
            Messages.info(file.getImageTypeSupported().toString());
        }
        sdlService.updateMessagesLog();


    }

    public void getSupportedTextFields() {
        List<TextField> textx = sdlManager.getSystemCapabilityManager().getDefaultMainWindowCapability().getTextFields();
        final int sizet = textx.size();
        Messages.info("Remote text fields: ");
        sdlService.updateMessagesLog();
        for (int i = 0; i < sizet; i++)
        {
            TextField textField = textx.get(i);
            Messages.info(textField.getName().toString());
            Messages.info("Rows: " + textField.getRows());
            Messages.info("With: " + textField.getWidth());
            Messages.info("characterSet: " + textField.getCharacterSet().toString());
        }
        sdlService.updateMessagesLog();
    }
}
