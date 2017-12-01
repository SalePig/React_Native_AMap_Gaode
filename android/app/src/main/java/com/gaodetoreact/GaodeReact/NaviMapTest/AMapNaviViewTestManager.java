package com.gaodetoreact.GaodeReact.NaviMapTest;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.gaodetoreact.GaodeReact.CustomAMapNaviView;
import com.gaodetoreact.GaodeReact.Test.RCTAMapViewTest;

import java.util.Map;

import javax.annotation.Nullable;

/**
 * Created by qiuzhiwen on 2017/11/28.
 */

public class AMapNaviViewTestManager extends SimpleViewManager<RCTANaviMapViewTest>{

    @Override
    public String getName() {
        return "RCTANaviMapViewTest";
    }

    @Override
    protected RCTANaviMapViewTest createViewInstance(ThemedReactContext reactContext) {
        RCTANaviMapViewTest aMapNaviView = new RCTANaviMapViewTest(reactContext,null);
        return aMapNaviView;
    }


    /**
     * 自定义事件 必须重写此方法 进行回调
     * @return
     */
    @Nullable
    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        return super.getExportedCustomDirectEventTypeConstants();
    }

    @ReactProp(name = "test")
    public void setTest(RCTANaviMapViewTest view, String test) {

    }



}
