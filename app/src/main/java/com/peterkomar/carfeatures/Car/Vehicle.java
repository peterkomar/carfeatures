package com.peterkomar.carfeatures.Car;

import android.annotation.SuppressLint;

import com.peterkomar.carfeatures.Activity.Messages;
import com.peterkomar.carfeatures.SmartDeviceLink.SdlService;
import com.smartdevicelink.managers.SdlManager;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.BodyInformation;
import com.smartdevicelink.proxy.rpc.FuelRange;
import com.smartdevicelink.proxy.rpc.GPSData;
import com.smartdevicelink.proxy.rpc.GearStatus;
import com.smartdevicelink.proxy.rpc.GetVehicleData;
import com.smartdevicelink.proxy.rpc.GetVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.TireStatus;
import com.smartdevicelink.proxy.rpc.enums.PRNDL;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

import java.util.List;

public class Vehicle extends CarManager {

    public Vehicle(final SdlManager sdl, SdlService service) {
        super(sdl, service);
    }

    public void getFuel() {
        GetVehicleData vdRequest = new GetVehicleData();
        Messages.info("Getting fuel...");
        sdlService.updateMessagesLog();
        vdRequest.setFuelLevel(true);
        vdRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Messages.info("Response from car: " + response.getInfo());
                sdlService.updateMessagesLog();
                if(response.getSuccess()){
                    Double fuel = ((GetVehicleDataResponse) response).getFuelLevel();
                    Messages.info("Fuel: " + fuel);
                } else {
                    Messages.info("Error getting fuel");
                }
                sdlService.updateMessagesLog();
            }
        });
        sdlManager.sendRPC(vdRequest);
    }

    public void getFuelRange() {
        GetVehicleData vdRequest = new GetVehicleData();
        Messages.info("Getting fuel range...");
        sdlService.updateMessagesLog();
        vdRequest.setFuelRange(true);
        vdRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Messages.info("Response from car: " + response.getInfo());
                sdlService.updateMessagesLog();
                if(response.getSuccess()){
                    List<FuelRange> fuelRange = ((GetVehicleDataResponse) response).getFuelRange();
                    for (int i=0; i<fuelRange.size(); i++) {
                        FuelRange r = fuelRange.get(i);
                        Messages.info("Fuel type: " + r.getType().toString());
                        Messages.info("Fuel range: " + r.getRange().toString());
                        Messages.info("Fuel level: " + r.getLevel());
                        Messages.info("Fuel capacity: " + r.getCapacity());
                    }
                } else {
                    Messages.info("Fuelrange Fail");
                }
                sdlService.updateMessagesLog();
            }
        });
        sdlManager.sendRPC(vdRequest);
    }

    public void getPressure() {
        GetVehicleData vdRequest = new GetVehicleData();
        Messages.info("Getting pressure...");
        sdlService.updateMessagesLog();
        vdRequest.setTirePressure(true);
        vdRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Messages.info("Response from car: " + response.getInfo());
                sdlService.updateMessagesLog();
                if(response.getSuccess()){
                    Messages.info("Pressure ok");

                    TireStatus status = ((GetVehicleDataResponse) response).getTirePressure();
                    String lf = status.getLeftFront().getStatus().toString();
                    String lfv = "-";
                    try {
                        lfv = String.format("%.2f", status.getLeftFront().getPressure());
                    } catch (Exception e) {
                        lfv = e.getMessage();
                    }
                    Messages.info(String.format("Left Front: %s %s", lf, lfv));

                    String rf = status.getRightFront().getStatus().toString();
                    String rfv = "-";
                    try {
                        rfv = String.format("%.2f", status.getRightFront().getPressure());
                    } catch (Exception e) {
                        rfv = e.getMessage();
                    }
                    Messages.info(String.format("Right Front: %s %s", rf, rfv));

                    String lr = status.getLeftRear().getStatus().toString();
                    String lrv = "-";
                    try {
                        lrv = String.format("%.2f", status.getLeftRear().getPressure());
                    } catch (Exception e) {
                        lrv = e.getMessage();
                    }
                    Messages.info(String.format("Left Rear: %s %s", lr, lrv));

                    String rr = status.getRightRear().getStatus().toString();
                    String rrv = "-";
                    try {
                        rrv = String.format("%.2f", status.getRightRear().getPressure());
                    } catch (Exception e) {
                        rrv = e.getMessage();
                    }
                    Messages.info(String.format("Right Rear: %s %s", rr, rrv));


                } else {
                    Messages.info("Pressure fail");
                }
                sdlService.updateMessagesLog();
            }
        });
        sdlManager.sendRPC(vdRequest);

    }

    public void getOilLife() {
        GetVehicleData vdRequest = new GetVehicleData();
        Messages.info("Getting oil...");
        sdlService.updateMessagesLog();
        vdRequest.setEngineOilLife(true);
        vdRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Messages.info("Response from car: " + response.getInfo());
                sdlService.updateMessagesLog();
                if(response.getSuccess()){
                    Float oillife = ((GetVehicleDataResponse) response).getEngineOilLife();
                    Messages.info(String.format("Oil Life: %f", oillife));
                } else {
                    Messages.info("Error getting oil life");
                }
                sdlService.updateMessagesLog();
            }
        });
        sdlManager.sendRPC(vdRequest);
    }

    public void getVinCode() {
        GetVehicleData vdRequest = new GetVehicleData();
        Messages.info("Getting vin...");
        sdlService.updateMessagesLog();
        vdRequest.setVin(true);
        vdRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Messages.info("Response from car: " + response.getInfo());
                sdlService.updateMessagesLog();
                if(response.getSuccess()){
                    String vincode = ((GetVehicleDataResponse) response).getVin();
                    Messages.info("Vin code: " + vincode);
                } else {
                    Messages.info("VIN code FAIL");
                }
                sdlService.updateMessagesLog();
            }
        });
        sdlManager.sendRPC(vdRequest);
    }

    public void getGps() {
        GetVehicleData vdRequest = new GetVehicleData();
        Messages.info("Getting gps...");
        sdlService.updateMessagesLog();
        vdRequest.setGps(true);
        vdRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Messages.info("Response from car: " + response.getInfo());
                sdlService.updateMessagesLog();
                if(response.getSuccess()){
                    GPSData gps = ((GetVehicleDataResponse) response).getGps();
                    Messages.info(String.format("GPS: %s", gps.getCompassDirection().toString()));
                    sdlService.updateMessagesLog();
                } else {
                    Messages.info("GPS fail");
                }
                sdlService.updateMessagesLog();
            }
        });
        sdlManager.sendRPC(vdRequest);
    }

    public void getExternalTemperature() {
        GetVehicleData vdRequest = new GetVehicleData();
        Messages.info("Getting external temperature ...");
        sdlService.updateMessagesLog();
        vdRequest.setExternalTemperature(true);
        vdRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Messages.info("Response from car: " + response.getInfo());
                sdlService.updateMessagesLog();
                if(response.getSuccess()){
                    Double temperature = ((GetVehicleDataResponse) response).getExternalTemperature();
                    Messages.info("External Temperature: " + temperature);
                    sdlService.updateMessagesLog();
                } else {
                    Messages.info("External Temperature fail");
                }
                sdlService.updateMessagesLog();
            }
        });
        sdlManager.sendRPC(vdRequest);
    }

    public void getOdometer() {
        GetVehicleData vdRequest = new GetVehicleData();
        Messages.info("Getting odometer ...");
        sdlService.updateMessagesLog();
        vdRequest.setOdometer(true);
        vdRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Messages.info("Response from car: " + response.getInfo());
                sdlService.updateMessagesLog();
                if(response.getSuccess()){
                    Integer odometer = ((GetVehicleDataResponse) response).getOdometer();
                    Messages.info("Odometer: " + odometer);
                    sdlService.updateMessagesLog();
                } else {
                    Messages.info("Odometer fail");
                }
                sdlService.updateMessagesLog();
            }
        });
        sdlManager.sendRPC(vdRequest);
    }

    public void getGearGearStatus() {
        GetVehicleData vdRequest = new GetVehicleData();
        Messages.info("Getting gear status ...");
        sdlService.updateMessagesLog();
        vdRequest.setGearStatus(true);
        vdRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Messages.info("Response from car: " + response.getInfo());
                sdlService.updateMessagesLog();
                if(response.getSuccess()){
                    GearStatus gearStatus = ((GetVehicleDataResponse) response).getGearStatus();
                    Messages.info("User Gear: " + gearStatus.getUserSelectedGear().toString());
                    sdlService.updateMessagesLog();
                } else {
                    Messages.info("User Gear fail");
                }
                sdlService.updateMessagesLog();
            }
        });
        sdlManager.sendRPC(vdRequest);
    }

    public void getPrndl() {
        GetVehicleData vdRequest = new GetVehicleData();
        Messages.info("Getting prndl ...");
        sdlService.updateMessagesLog();
        vdRequest.setPrndl(true);
        vdRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Messages.info("Response from car: " + response.getInfo());
                sdlService.updateMessagesLog();
                if(response.getSuccess()){
                    PRNDL prndl = ((GetVehicleDataResponse) response).getPrndl();
                    Messages.info("PRNDL: " + prndl.toString());
                    sdlService.updateMessagesLog();
                } else {
                    Messages.info("PRNDL fail");
                }
                sdlService.updateMessagesLog();
            }
        });
        sdlManager.sendRPC(vdRequest);
    }

    public void getBodyInfo() {
        GetVehicleData vdRequest = new GetVehicleData();
        Messages.info("Getting body info ...");
        sdlService.updateMessagesLog();
        vdRequest.setBodyInformation(true);
        vdRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Messages.info("Response from car: " + response.getInfo());
                sdlService.updateMessagesLog();
                if(response.getSuccess()){
                    BodyInformation body = ((GetVehicleDataResponse) response).getBodyInformation();
                    Messages.info("Park Brake Active: " + body.getParkBrakeActive());
                    Messages.info("IgnitionStableStatus: " + body.getIgnitionStableStatus().toString());
                    Messages.info("IgnitionStatus: " + body.getIgnitionStatus().toString());
                    Messages.info("DriverDoorAjar: " + body.getDriverDoorAjar());
                    Messages.info("PassengerDoorAjar: " + body.getPassengerDoorAjar());
                    Messages.info("RearLeftDoorAjar: " + body.getRearLeftDoorAjar());
                    Messages.info("RearRightDoorAjar: " + body.getRearRightDoorAjar());
                    sdlService.updateMessagesLog();
                } else {
                    Messages.info("Body info: fail");
                }
                sdlService.updateMessagesLog();
            }
        });
        sdlManager.sendRPC(vdRequest);
    }
}
