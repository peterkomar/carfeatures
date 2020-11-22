package com.peterkomar.carfeatures.Car;

import com.peterkomar.carfeatures.Activity.Messages;
import com.peterkomar.carfeatures.SmartDeviceLink.SdlService;
import com.smartdevicelink.managers.SdlManager;
import com.smartdevicelink.proxy.rpc.ImageField;

import java.util.List;

public class Compatibilities extends CarManager {

    public Compatibilities(final SdlManager sdl, SdlService service) {
        super(sdl, service);
    }

    public void getSupportedImageFields() {
        List<ImageField> images = sdlManager.getSystemCapabilityManager().getDefaultMainWindowCapability().getImageFields();
        final int size = images.size();
        Messages.info("Remove image fields: ");
        for (int i = 0; i < size; i++)
        {
            ImageField file = images.get(i);
            Messages.info(file.getName().toString());
            Messages.info(file.getImageResolution().getResolutionWidth().toString());
            Messages.info(file.getImageTypeSupported().toString());
        }
        sdlService.updateMessagesLog();


    }
}
