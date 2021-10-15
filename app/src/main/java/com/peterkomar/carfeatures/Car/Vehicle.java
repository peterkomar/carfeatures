package com.peterkomar.carfeatures.Car;

import android.annotation.SuppressLint;

import com.peterkomar.carfeatures.Activity.Messages;
import com.peterkomar.carfeatures.SmartDeviceLink.SdlService;
import com.smartdevicelink.managers.SdlManager;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.AirbagStatus;
import com.smartdevicelink.proxy.rpc.BeltStatus;
import com.smartdevicelink.proxy.rpc.BodyInformation;
import com.smartdevicelink.proxy.rpc.ClusterModeStatus;
import com.smartdevicelink.proxy.rpc.DeviceStatus;
import com.smartdevicelink.proxy.rpc.FuelRange;
import com.smartdevicelink.proxy.rpc.GPSData;
import com.smartdevicelink.proxy.rpc.GearStatus;
import com.smartdevicelink.proxy.rpc.GetVehicleData;
import com.smartdevicelink.proxy.rpc.GetVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.HeadLampStatus;
import com.smartdevicelink.proxy.rpc.MyKey;
import com.smartdevicelink.proxy.rpc.TireStatus;
import com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus;
import com.smartdevicelink.proxy.rpc.enums.ElectronicParkBrakeStatus;
import com.smartdevicelink.proxy.rpc.enums.PRNDL;
import com.smartdevicelink.proxy.rpc.enums.TurnSignal;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.proxy.rpc.enums.WiperStatus;
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
        vdRequest.setInstantFuelConsumption(true);
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

    public void getInstantFuelConsumption() {
        GetVehicleData vdRequest = new GetVehicleData();
        Messages.info("getInstantFuelConsumption ...");
        sdlService.updateMessagesLog();
        vdRequest.setInstantFuelConsumption(true);
        vdRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Messages.info("Response from car: " + response.getInfo());
                sdlService.updateMessagesLog();
                if(response.getSuccess()){
                    Double value = ((GetVehicleDataResponse) response).getInstantFuelConsumption();
                    Messages.info("InstantFuelConsumption: " + value.toString());
                    sdlService.updateMessagesLog();
                } else {
                    Messages.info("InstantFuelConsumption: fail");
                }
                sdlService.updateMessagesLog();
            }
        });
        sdlManager.sendRPC(vdRequest);
    }

    public void getFuelLevelState() {
        GetVehicleData vdRequest = new GetVehicleData();
        Messages.info("getFuelLevelState ...");
        sdlService.updateMessagesLog();
        vdRequest.setFuelLevelState(true);
        vdRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Messages.info("Response from car: " + response.getInfo());
                sdlService.updateMessagesLog();
                if(response.getSuccess()){
                    ComponentVolumeStatus value = ((GetVehicleDataResponse) response).getFuelLevelState();
                    Messages.info("FuelLevelState: " + value.toString());
                    sdlService.updateMessagesLog();
                } else {
                    Messages.info("FuelLevelState: fail");
                }
                sdlService.updateMessagesLog();
            }
        });
        sdlManager.sendRPC(vdRequest);
    }

    public void getBeltStatus() {
        GetVehicleData vdRequest = new GetVehicleData();
        Messages.info("getBeltStatus ...");
        sdlService.updateMessagesLog();
        vdRequest.setBeltStatus(true);
        vdRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Messages.info("Response from car: " + response.getInfo());
                sdlService.updateMessagesLog();
                if(response.getSuccess()){
                    BeltStatus value = ((GetVehicleDataResponse) response).getBeltStatus();
                    Messages.info("BeltStatus: " + value.toString());
                    sdlService.updateMessagesLog();
                } else {
                    Messages.info("BeltStatus: fail");
                }
                sdlService.updateMessagesLog();
            }
        });
        sdlManager.sendRPC(vdRequest);
    }

    public void getDriverBraking() {
        GetVehicleData vdRequest = new GetVehicleData();
        Messages.info("getDriverBraking ...");
        sdlService.updateMessagesLog();
        vdRequest.setDriverBraking(true);
        vdRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Messages.info("Response from car: " + response.getInfo());
                sdlService.updateMessagesLog();
                if(response.getSuccess()){
                    VehicleDataEventStatus value = ((GetVehicleDataResponse) response).getDriverBraking();
                    Messages.info("DriverBraking: " + value.toString());
                    sdlService.updateMessagesLog();
                } else {
                    Messages.info("DriverBraking: fail");
                }
                sdlService.updateMessagesLog();
            }
        });
        sdlManager.sendRPC(vdRequest);
    }

    public void getWiperStatus() {
        GetVehicleData vdRequest = new GetVehicleData();
        Messages.info("getWiperStatus ...");
        sdlService.updateMessagesLog();
        vdRequest.setWiperStatus(true);
        vdRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Messages.info("Response from car: " + response.getInfo());
                sdlService.updateMessagesLog();
                if(response.getSuccess()){
                    WiperStatus value = ((GetVehicleDataResponse) response).getWiperStatus();
                    Messages.info("WiperStatus: " + value.toString());
                    sdlService.updateMessagesLog();
                } else {
                    Messages.info("WiperStatus: fail");
                }
                sdlService.updateMessagesLog();
            }
        });
        sdlManager.sendRPC(vdRequest);
    }

    public void getHeadLampStatus() {
        GetVehicleData vdRequest = new GetVehicleData();
        Messages.info("getHeadLampStatus ...");
        sdlService.updateMessagesLog();
        vdRequest.setHeadLampStatus(true);
        vdRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Messages.info("Response from car: " + response.getInfo());
                sdlService.updateMessagesLog();
                if(response.getSuccess()){
                    HeadLampStatus value = ((GetVehicleDataResponse) response).getHeadLampStatus();
                    Messages.info("HeadLampStatus: " + value.toString());
                    Messages.info("AmbientLightStatus: " + value.getAmbientLightStatus().toString());
                    Messages.info("HighBeamsOn: " + value.getHighBeamsOn().toString());
                    Messages.info("LowBeamsOn: " + value.getLowBeamsOn().toString());
                    sdlService.updateMessagesLog();
                } else {
                    Messages.info("HeadLampStatus: fail");
                }
                sdlService.updateMessagesLog();
            }
        });
        sdlManager.sendRPC(vdRequest);
    }

    public void getEngineTorque() {
        GetVehicleData vdRequest = new GetVehicleData();
        Messages.info("getEngineTorque ...");
        sdlService.updateMessagesLog();
        vdRequest.setEngineTorque(true);
        vdRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Messages.info("Response from car: " + response.getInfo());
                sdlService.updateMessagesLog();
                if(response.getSuccess()){
                    Double value = ((GetVehicleDataResponse) response).getEngineTorque();
                    Messages.info("EngineTorque: " + value.toString());
                    sdlService.updateMessagesLog();
                } else {
                    Messages.info("EngineTorque: fail");
                }
                sdlService.updateMessagesLog();
            }
        });
        sdlManager.sendRPC(vdRequest);
    }

    public void getACCPedalPosition() {
        GetVehicleData vdRequest = new GetVehicleData();
        Messages.info("getACCPedalPosition ...");
        sdlService.updateMessagesLog();
        vdRequest.setAccPedalPosition(true);
        vdRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Messages.info("Response from car: " + response.getInfo());
                sdlService.updateMessagesLog();
                if(response.getSuccess()){
                    Double value = ((GetVehicleDataResponse) response).getAccPedalPosition();
                    Messages.info("ACCPedalPosition: " + value.toString());
                    sdlService.updateMessagesLog();
                } else {
                    Messages.info("ACCPedalPosition: fail");
                }
                sdlService.updateMessagesLog();
            }
        });
        sdlManager.sendRPC(vdRequest);
    }

    public void getSteeringWheelAngle() {
        GetVehicleData vdRequest = new GetVehicleData();
        Messages.info("getSteeringWheelAngle ...");
        sdlService.updateMessagesLog();
        vdRequest.setSteeringWheelAngle(true);
        vdRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Messages.info("Response from car: " + response.getInfo());
                sdlService.updateMessagesLog();
                if(response.getSuccess()){
                    Double value = ((GetVehicleDataResponse) response).getSteeringWheelAngle();
                    Messages.info("SteeringWheelAngle: " + value.toString());
                    sdlService.updateMessagesLog();
                } else {
                    Messages.info("SteeringWheelAngle: fail");
                }
                sdlService.updateMessagesLog();
            }
        });
        sdlManager.sendRPC(vdRequest);
    }

    public void getAirbagStatus() {
        GetVehicleData vdRequest = new GetVehicleData();
        Messages.info("getAirbagStatus ...");
        sdlService.updateMessagesLog();
        vdRequest.setAirbagStatus(true);
        vdRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Messages.info("Response from car: " + response.getInfo());
                sdlService.updateMessagesLog();
                if(response.getSuccess()){
                    AirbagStatus value = ((GetVehicleDataResponse) response).getAirbagStatus();
                    Messages.info("AirbagStatus: " + value.toString());
                    Messages.info("DriverAirbagDeployed: " + value.getDriverAirbagDeployed().toString());
                    Messages.info("DriverKneeAirbagDeployed: " + value.getDriverKneeAirbagDeployed().toString());
                    Messages.info("DriverCurtainAirbagDeployed: " + value.getDriverCurtainAirbagDeployed().toString());
                    sdlService.updateMessagesLog();
                } else {
                    Messages.info("AirbagStatus: fail");
                }
                sdlService.updateMessagesLog();
            }
        });
        sdlManager.sendRPC(vdRequest);
    }

    public void getClusterModeStatus() {
        GetVehicleData vdRequest = new GetVehicleData();
        Messages.info("getClusterModeStatus ...");
        sdlService.updateMessagesLog();
        vdRequest.setClusterModeStatus(true);
        vdRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Messages.info("Response from car: " + response.getInfo());
                sdlService.updateMessagesLog();
                if(response.getSuccess()){
                    ClusterModeStatus value = ((GetVehicleDataResponse) response).getClusterModeStatus();
                    Messages.info("ClusterModeStatus: " + value.toString());
                    Messages.info("CarModeStatus: " + value.getCarModeStatus().toString());
                    Messages.info("PowerModeStatus: " + value.getPowerModeStatus().toString());
                    Messages.info("PowerModeActive: " + value.getPowerModeActive().toString());
                    Messages.info("PowerModeQualificationStatus: " + value.getPowerModeQualificationStatus().toString());
                    sdlService.updateMessagesLog();
                } else {
                    Messages.info("ClusterModeStatus: fail");
                }
                sdlService.updateMessagesLog();
            }
        });
        sdlManager.sendRPC(vdRequest);
    }

    public void getMykey() {
        GetVehicleData vdRequest = new GetVehicleData();
        Messages.info("getMykey ...");
        sdlService.updateMessagesLog();
        vdRequest.setMyKey(true);
        vdRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Messages.info("Response from car: " + response.getInfo());
                sdlService.updateMessagesLog();
                if(response.getSuccess()){
                    MyKey value = ((GetVehicleDataResponse) response).getMyKey();
                    Messages.info("Mykey: " + value.toString());
                    Messages.info("E911Override: " + value.getE911Override().toString());
                    sdlService.updateMessagesLog();
                } else {
                    Messages.info("Mykey: fail");
                }
                sdlService.updateMessagesLog();
            }
        });
        sdlManager.sendRPC(vdRequest);
    }

    public void getTurnSignal() {
        GetVehicleData vdRequest = new GetVehicleData();
        Messages.info("getTurnSignal ...");
        sdlService.updateMessagesLog();
        vdRequest.setTurnSignal(true);
        vdRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Messages.info("Response from car: " + response.getInfo());
                sdlService.updateMessagesLog();
                if(response.getSuccess()){
                    TurnSignal value = ((GetVehicleDataResponse) response).getTurnSignal();
                    Messages.info("TurnSignal: " + value.toString());
                    sdlService.updateMessagesLog();
                } else {
                    Messages.info("TurnSignal: fail");
                }
                sdlService.updateMessagesLog();
            }
        });
        sdlManager.sendRPC(vdRequest);
    }

    public void getElectronicParkBrakeStatus() {
        GetVehicleData vdRequest = new GetVehicleData();
        Messages.info("ElectronicParkBrakeStatus ...");
        sdlService.updateMessagesLog();
        vdRequest.setElectronicParkBrakeStatus(true);
        vdRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Messages.info("Response from car: " + response.getInfo());
                sdlService.updateMessagesLog();
                if(response.getSuccess()){
                    ElectronicParkBrakeStatus value = ((GetVehicleDataResponse) response).getElectronicParkBrakeStatus();
                    Messages.info("ElectronicParkBrakeStatus: " + value.toString());
                    sdlService.updateMessagesLog();
                } else {
                    Messages.info("ElectronicParkBrakeStatus: fail");
                }
                sdlService.updateMessagesLog();
            }
        });
        sdlManager.sendRPC(vdRequest);
    }

    public void getRpm() {
        GetVehicleData vdRequest = new GetVehicleData();
        Messages.info("RPM ...");
        sdlService.updateMessagesLog();
        vdRequest.setRpm(true);
        vdRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Messages.info("Response from car: " + response.getInfo());
                sdlService.updateMessagesLog();
                if(response.getSuccess()){
                    Integer value = ((GetVehicleDataResponse) response).getRpm();
                    Messages.info("RPM: " + value.toString());
                    sdlService.updateMessagesLog();
                } else {
                    Messages.info("RPM: fail");
                }
                sdlService.updateMessagesLog();
            }
        });
        sdlManager.sendRPC(vdRequest);
    }

    public void getSpeed() {
        GetVehicleData vdRequest = new GetVehicleData();
        Messages.info("Speed ...");
        sdlService.updateMessagesLog();
        vdRequest.setSpeed(true);
        vdRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Messages.info("Response from car: " + response.getInfo());
                sdlService.updateMessagesLog();
                if(response.getSuccess()){
                    Double value = ((GetVehicleDataResponse) response).getSpeed();
                    Messages.info("Speed: " + value.toString());
                    sdlService.updateMessagesLog();
                } else {
                    Messages.info("Speed: fail");
                }
                sdlService.updateMessagesLog();
            }
        });
        sdlManager.sendRPC(vdRequest);
    }

    public void getDeviceStatus() {
        GetVehicleData vdRequest = new GetVehicleData();
        Messages.info("DeviceStatus ...");
        sdlService.updateMessagesLog();
        vdRequest.setDeviceStatus(true);
        vdRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Messages.info("Response from car: " + response.getInfo());
                sdlService.updateMessagesLog();
                if(response.getSuccess()){
                    DeviceStatus value = ((GetVehicleDataResponse) response).getDeviceStatus();
                    Messages.info("DeviceStatus: " + value.toString());
                    Messages.info("BattLevelStatus: " + value.getBattLevelStatus().toString());
                    Messages.info("SignalLevelStatus: " + value.getSignalLevelStatus().toString());
                    Messages.info("BtIconOn: " + value.getBtIconOn().toString());
                    Messages.info("CallActive: " + value.getCallActive().toString());
                    Messages.info("ECallEventActive: " + value.getECallEventActive().toString());
                    Messages.info("MonoAudioOutputMuted: " + value.getMonoAudioOutputMuted().toString());
                    Messages.info("PhoneRoaming: " + value.getPhoneRoaming().toString());
                    Messages.info("PrimaryAudioSource: " + value.getPrimaryAudioSource().toString());
                    Messages.info("StereoAudioOutputMuted: " + value.getStereoAudioOutputMuted().toString());
                    Messages.info("TextMsgAvailable: " + value.getTextMsgAvailable().toString());
                    Messages.info("VoiceRecOn: " + value.getVoiceRecOn().toString());
                    sdlService.updateMessagesLog();
                } else {
                    Messages.info("DeviceStatus: fail");
                }
                sdlService.updateMessagesLog();
            }
        });
        sdlManager.sendRPC(vdRequest);
    }
}
