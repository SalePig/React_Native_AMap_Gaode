package com.gaodetoreact.GaodeReact;

import com.amap.api.navi.AMapNaviView;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

import java.util.Map;

import javax.annotation.Nullable;

/**
 * Created by qiuzhiwen on 2017/11/28.
 */

public class AMapNaviViewManager extends SimpleViewManager<CustomAMapNaviView>{

    @Override
    public String getName() {
        return "RCTAMapNaviView";
    }

    @Override
    protected CustomAMapNaviView createViewInstance(ThemedReactContext reactContext) {
        CustomAMapNaviView aMapNaviView = new CustomAMapNaviView(reactContext);
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

    @Override
    public void onDropViewInstance(CustomAMapNaviView view) {
        super.onDropViewInstance(view);
        view.onDestroy();
    }
}
