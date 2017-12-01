package com.gaodetoreact.GaodeReact.Test;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.facebook.react.uimanager.ThemedReactContext;
import com.gaodetoreact.R;

public class RCTAMapViewTest extends FrameLayout implements AMap.OnMapClickListener, LocationSource, AMapLocationListener {
    private ViewGroup.LayoutParams PARAM ;
    private MapView MAPVIEW;
    private AMap mAMap;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private OnLocationChangedListener mListener;
    private ThemedReactContext CONTEXT;


    public RCTAMapViewTest(ThemedReactContext context) {
        super(context);
        this.CONTEXT = context;
        PARAM = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public RCTAMapViewTest(@NonNull ThemedReactContext context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.CONTEXT = context;
        PARAM = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public RCTAMapViewTest(@NonNull ThemedReactContext context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.CONTEXT = context;
        PARAM = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }


    /**
     * Activity onResume后调用view的onAttachedToWindow
     */
    @Override
    protected void onAttachedToWindow() {
        init();
        super.onAttachedToWindow();
    }

    /**
     * 初始化控件,定位位置
     */
    private void init() {
        MAPVIEW = new MapView(CONTEXT);
        MAPVIEW.setLayoutParams(PARAM);
        this.addView(MAPVIEW);

        MAPVIEW.onCreate(CONTEXT.getCurrentActivity().getIntent().getExtras());

        setMapOptions();

    }


    /**
     * 设置一些amap的属性
     */
    private void setMapOptions() {

        if (mAMap == null) {
            mAMap = MAPVIEW.getMap();
            mAMap.getUiSettings().setRotateGesturesEnabled(false);
            mAMap.moveCamera(CameraUpdateFactory.zoomBy(6));
            setUpMap();
        }
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        mAMap.setOnMapClickListener(this);
        mAMap.setLocationSource(this);// 设置定位监听
        mAMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(
                BitmapDescriptorFactory.fromResource(R.mipmap.gps_point));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        // 自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(0);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        // 将自定义的 myLocationStyle 对象添加到地图上
        mAMap.setMyLocationStyle(myLocationStyle);
        mAMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        mAMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }


    @Override
    protected Parcelable onSaveInstanceState() {
        if (CONTEXT.getCurrentActivity().getIntent() != null && (CONTEXT.getCurrentActivity().getIntent().getExtras() != null)) {
            MAPVIEW.onSaveInstanceState(CONTEXT.getCurrentActivity().getIntent().getExtras());
        }
        return super.onSaveInstanceState();
    }

    @Override
    protected void onDetachedFromWindow() {
        this.removeView(MAPVIEW);
        MAPVIEW.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
        super.onDetachedFromWindow();
    }

    /**
     * 对应onResume、对应onPause
     *
     * @param hasWindowFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {

        super.onWindowFocusChanged(hasWindowFocus);

        if (hasWindowFocus) {
//            对应onResume
            MAPVIEW.onResume();
        } else {
            //对应onPause
            MAPVIEW.onPause();

        }

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(CONTEXT);
            mLocationOption = new AMapLocationClientOption();
            // 设置定位监听
            mlocationClient.setLocationListener(this);
            // 设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            // 只是为了获取当前位置，所以设置为单次定位
            mLocationOption.setOnceLocation(true);
            // 设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                Log.e("onLocationChanged", aMapLocation.getAddress());
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": "
                        + aMapLocation.getErrorInfo();
                Log.e("onLocationChanged", errText);

            }
        }
    }
}
