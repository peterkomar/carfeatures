package com.peterkomar.carfeatures.Car;

import android.text.TextUtils;

import com.peterkomar.carfeatures.Activity.Messages;
import com.peterkomar.carfeatures.SmartDeviceLink.SdlService;
import com.smartdevicelink.managers.SdlManager;
import com.smartdevicelink.managers.lifecycle.SystemCapabilityManager;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.rpc.GetVehicleData;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.SystemCapability;
import com.smartdevicelink.proxy.rpc.WindowCapability;

import java.util.List;

public class Permissions {

    public static void identifyPermissions(final SdlManager sdlManager, SdlService service) {

        // get version
        SdlMsgVersion rpcSpecVersion = sdlManager.getRegisterAppInterfaceResponse().getSdlMsgVersion();
        Messages.info(String.format("SDL version: %s.%s.%s", rpcSpecVersion.getMajorVersion().toString(), rpcSpecVersion.getMinorVersion(), rpcSpecVersion.getPatchVersion()));
        service.updateMessagesLog();

        Messages.info("Validate permissions:");
        service.updateMessagesLog();
        // check permissions
        boolean parameterAllowed = sdlManager.getPermissionManager().isPermissionParameterAllowed(FunctionID.GET_VEHICLE_DATA, GetVehicleData.KEY_TIRE_PRESSURE);
        if (parameterAllowed) {
            Messages.info("Tire Pressure OK");
        } else {
            Messages.info("Tire Pressure FAIL");
        }
        service.updateMessagesLog();

        parameterAllowed = sdlManager.getPermissionManager().isPermissionParameterAllowed(FunctionID.GET_VEHICLE_DATA, GetVehicleData.KEY_BODY_INFORMATION);
        if (parameterAllowed) {
            Messages.info("Body info OK");
        } else {
            Messages.info("Body info FAIL");
        }
        service.updateMessagesLog();

        Messages.info("Get list of supported templates:");
        //get available templates list
        SystemCapabilityManager m = sdlManager.getSystemCapabilityManager();
        if (m != null) {
            WindowCapability w = m.getDefaultMainWindowCapability();
            if (w != null) {
                List<String> listTempl = w.getTemplatesAvailable();
                if (listTempl != null) {
                    Messages.info(TextUtils.join("\n", listTempl));
                } else {
                    Messages.info("Not supported custom layouts");
                }
            } else {
                Messages.info("getDefaultMainWindowCapability returns null");
            }
        } else {
            Messages.info("getSystemCapabilityManager returns null");
        }
    }
}
