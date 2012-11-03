package com.bestbuy.bbym.ise.drp.triage2;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Component;

public class TriageTests implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String GENERIC = "GENERIC";
    public static final String TOUCH_SCREEN = "TOUCH SCREEN";
    public static final String MULTI_TOUCH_SCREEN = "MULTI TOUCH SCREEN";
    public static final String CAMERA_FLASH = "CAMERA FLASH";
    public static final String PROXIMITY_SENSOR = "PROXIMITY SENSOR";
    public static final String HEADSET = "HEADSET";
    public static final String HEADSET_MIC = "HEADSET MIC";
    public static final String SPEAKER = "SPEAKER";
    public static final String MICROPHONE = "MICROPHONE";
    public static final String GPS = "GPS";
    public static final String FRONT_CAMERA = "FRONT CAMERA";
    public static final String BLUETOOTH = "BLUETOOTH";
    public static final String WIFI = "WIFI";
    public static final String ACCELEROMETER_SENSOR = "ACCELEROMETER SENSOR";
    public static final String MAGNETIC_FIELD_SENSOR = "MAGNETIC FIELD SENSOR";
    public static final String BACK_CAMERA = "BACK CAMERA";
    public static final String VIBRATION = "VIBRATION";
    public static final String TELEPHONY = "TELEPHONY";
    public static final String GYROSCOPE_SENSOR = "GYROSCOPE SENSOR";
    public static final String SOUND = "SOUND";
    public static final String SILENT = "SILENT";

    private Map<String, String> iconClassMap = new HashMap<String, String>();
    private Map<String, String> titleMap = new HashMap<String, String>();

    private TriageTests() {
    }

    public static TriageTests generate(Component component) {
	TriageTests triageTests = new TriageTests();
	triageTests.iconClassMap.put(TOUCH_SCREEN, "touchscreen");
	triageTests.iconClassMap.put(MULTI_TOUCH_SCREEN, "multitouchscreen");
	triageTests.iconClassMap.put(CAMERA_FLASH, "cameraflash");
	triageTests.iconClassMap.put(PROXIMITY_SENSOR, "proxsensor");
	triageTests.iconClassMap.put(HEADSET, "headset");
	triageTests.iconClassMap.put(HEADSET_MIC, "headset_mic");
	triageTests.iconClassMap.put(SPEAKER, "speaker");
	triageTests.iconClassMap.put(MICROPHONE, "mic");
	triageTests.iconClassMap.put(GPS, "gps");
	triageTests.iconClassMap.put(FRONT_CAMERA, "frontcam");
	triageTests.iconClassMap.put(BLUETOOTH, "bluetooth");
	triageTests.iconClassMap.put(WIFI, "wifi");
	triageTests.iconClassMap.put(ACCELEROMETER_SENSOR, "accsensor");
	triageTests.iconClassMap.put(MAGNETIC_FIELD_SENSOR, "magfieldsensor");
	triageTests.iconClassMap.put(BACK_CAMERA, "backcam");
	triageTests.iconClassMap.put(VIBRATION, "vibrate");
	triageTests.iconClassMap.put(TELEPHONY, "telephony");
	triageTests.iconClassMap.put(GYROSCOPE_SENSOR, "gyrosensor");
	triageTests.iconClassMap.put(GENERIC, "generic");
	triageTests.iconClassMap.put(SOUND, "sound");
	triageTests.iconClassMap.put(SILENT, "silent");
	triageTests.titleMap.put(TOUCH_SCREEN, component.getString("triageTest.name.touchscreen"));
	triageTests.titleMap.put(MULTI_TOUCH_SCREEN, component.getString("triageTest.name.multitouchscreen"));
	triageTests.titleMap.put(CAMERA_FLASH, component.getString("triageTest.name.cameraflash"));
	triageTests.titleMap.put(PROXIMITY_SENSOR, component.getString("triageTest.name.proxsensor"));
	triageTests.titleMap.put(HEADSET, component.getString("triageTest.name.headset"));
	triageTests.titleMap.put(HEADSET_MIC, component.getString("triageTest.name.headset_mic"));
	triageTests.titleMap.put(SPEAKER, component.getString("triageTest.name.speaker"));
	triageTests.titleMap.put(MICROPHONE, component.getString("triageTest.name.mic"));
	triageTests.titleMap.put(GPS, component.getString("triageTest.name.gps"));
	triageTests.titleMap.put(FRONT_CAMERA, component.getString("triageTest.name.frontcam"));
	triageTests.titleMap.put(BLUETOOTH, component.getString("triageTest.name.bluetooth"));
	triageTests.titleMap.put(WIFI, component.getString("triageTest.name.wifi"));
	triageTests.titleMap.put(ACCELEROMETER_SENSOR, component.getString("triageTest.name.accsensor"));
	triageTests.titleMap.put(MAGNETIC_FIELD_SENSOR, component.getString("triageTest.name.magfieldsensor"));
	triageTests.titleMap.put(BACK_CAMERA, component.getString("triageTest.name.backcam"));
	triageTests.titleMap.put(VIBRATION, component.getString("triageTest.name.vibrate"));
	triageTests.titleMap.put(TELEPHONY, component.getString("triageTest.name.telephony"));
	triageTests.titleMap.put(GYROSCOPE_SENSOR, component.getString("triageTest.name.gyrosensor"));
	triageTests.titleMap.put(SILENT, component.getString("triageTest.name.silent"));
	triageTests.titleMap.put(SOUND, component.getString("triageTest.name.sound"));
	triageTests.titleMap.put(GENERIC, GENERIC);
	return triageTests;
    }

    public String getIconClass(String test) {
	String key = null;
	if (test != null){
	    key = test.toUpperCase();
	}
	return iconClassMap.get(key);
    }

    public String getTitle(String test) {
	String key = null;
	if (test != null){
	    key = test.toUpperCase();
	}
	return titleMap.get(key);
    }
}
