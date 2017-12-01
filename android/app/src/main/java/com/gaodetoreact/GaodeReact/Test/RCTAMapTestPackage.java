package com.gaodetoreact.GaodeReact.Test;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.gaodetoreact.GaodeReact.Location.amap.RCTAMapManager;
import com.gaodetoreact.GaodeReact.Location.amap.RCTAMapModule;
import com.gaodetoreact.GaodeReact.Video.VideoViewManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class RCTAMapTestPackage implements ReactPackage {

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Arrays.<ViewManager>asList(new RCTAMapTestManager());
    }
}
