package com.gaodetoreact.GaodeReact.Test;


import com.amap.api.maps2d.model.LatLng;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.gaodetoreact.GaodeReact.Location.amap.RCTAMapView;

import java.util.Map;

public class RCTAMapTestManager extends ViewGroupManager<RCTAMapViewTest> {
//    public static final LatLng SHANGHAI = new LatLng(31.238068, 121.501654);// 上海市经纬度
    @Override
    public String getName() {
        return "RCTAMapViewTest";
    }


    @Override
    protected RCTAMapViewTest createViewInstance(ThemedReactContext reactContext) {
        RCTAMapViewTest mapView = new RCTAMapViewTest(reactContext);
        return mapView;
    }

    @ReactProp(name = "test")
    public void setTest(RCTAMapViewTest view, String test) {

    }
//
//    @Override
//    protected void addEventEmitters(
//            final ThemedReactContext reactContext,
//            final RCTAMapView view) {
//    }
//
//    @Override
//    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
//        return MapBuilder.<String, Object>builder()
//                .put("onDidMoveByUser", MapBuilder.of("registrationName", "onDidMoveByUser"))//registrationName 后的名字,RN中方法也要是这个名字否则不执行
//                .build();
//    }

}
