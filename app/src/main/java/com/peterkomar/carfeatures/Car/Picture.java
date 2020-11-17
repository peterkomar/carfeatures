package com.peterkomar.carfeatures.Car;

import com.peterkomar.carfeatures.R;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.proxy.rpc.enums.FileType;

public class Picture {

    Picture() {
    }

    public SdlArtwork renderSmall() {
        return new SdlArtwork("carpicture.png", FileType.GRAPHIC_PNG, R.drawable.car_small,false);
    }
}
