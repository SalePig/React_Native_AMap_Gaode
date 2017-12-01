package com.gaodetoreact.GaodeReact.NaviMapTest;

import android.content.Context;

import com.amap.api.navi.AMapNaviView;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.uimanager.ThemedReactContext;

/**
 * Created by qiuzhiwen on 2017/12/1.
 */

public class AMapNaviViewTest extends AMapNaviView implements LifecycleEventListener{


    private ThemedReactContext mContext;
    public AMapNaviViewTest(ThemedReactContext context) {
        super(context);
        mContext=context;
    }

    @Override
    public void onHostResume() {

    }

    @Override
    public void onHostPause() {

    }

    @Override
    public void onHostDestroy() {

    }
}
