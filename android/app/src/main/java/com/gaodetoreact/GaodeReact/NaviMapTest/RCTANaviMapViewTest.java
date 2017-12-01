package com.gaodetoreact.GaodeReact.NaviMapTest;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.uimanager.ThemedReactContext;
import com.gaodetoreact.util.ErrorInfo;
import com.gaodetoreact.util.TTSController;
import java.util.ArrayList;
import java.util.List;


public class RCTANaviMapViewTest extends FrameLayout implements AMapNaviListener ,LifecycleEventListener {

    private AMapNaviView MAPVIEW;
    private ThemedReactContext CONTEXT;
    private ViewGroup.LayoutParams PARAM;

    protected AMapNavi mAMapNavi;
    protected TTSController mTtsManager;
    protected NaviLatLng mEndLatlng = new NaviLatLng(40.084894,116.603039);
    protected NaviLatLng mStartLatlng = new NaviLatLng(39.825934,116.342972);
    protected final List<NaviLatLng> sList = new ArrayList<NaviLatLng>();
    protected final List<NaviLatLng> eList = new ArrayList<NaviLatLng>();
    protected List<NaviLatLng> mWayPointList;


    public RCTANaviMapViewTest(ThemedReactContext context) {
        super(context);
        context.addLifecycleEventListener(this);
        this.CONTEXT = context;
        PARAM = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    }

    public RCTANaviMapViewTest(@NonNull ThemedReactContext context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        context.addLifecycleEventListener(this);
        this.CONTEXT = context;
        PARAM = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public RCTANaviMapViewTest(@NonNull ThemedReactContext context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        context.addLifecycleEventListener(this);
        this.CONTEXT = context;
        PARAM = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    /**
     * Activity onResume后调用view的onAttachedToWindow
     */
    @Override
    protected void onAttachedToWindow() {
//        init();
        super.onAttachedToWindow();
    }

    /**
     * 初始化控件,定位位置
     */
    private void init() {
        MAPVIEW = new AMapNaviView(CONTEXT);
        MAPVIEW.setLayoutParams(PARAM);
        this.addView(MAPVIEW);

        //实例化语音引擎
        mTtsManager = TTSController.getInstance(CONTEXT.getApplicationContext());
        mTtsManager.init();

        //
        mAMapNavi = AMapNavi.getInstance(CONTEXT.getApplicationContext());

        mAMapNavi.addAMapNaviListener(this);

        mAMapNavi.addAMapNaviListener(mTtsManager);
        //设置模拟导航的行车速度
        mAMapNavi.setEmulatorNaviSpeed(75);
        sList.add(mStartLatlng);
        eList.add(mEndLatlng);


        MAPVIEW.onCreate(CONTEXT.getCurrentActivity().getIntent().getExtras());
        setMapOptions();

    }


    /**
     * 设置一些amap的属性
     */
    private void setMapOptions() {
        AMapNaviViewOptions options = new AMapNaviViewOptions();
        options.setTilt(0);
        MAPVIEW.setViewOptions(options);
    }


    @Override
    protected Parcelable onSaveInstanceState() {
        if (CONTEXT.getCurrentActivity().getIntent() != null && (CONTEXT.getCurrentActivity().getIntent().getExtras() != null)) {
            MAPVIEW.onSaveInstanceState(CONTEXT.getCurrentActivity().getIntent().getExtras());
        }
        return super.onSaveInstanceState();
    }

//    @Override
//    protected void onDetachedFromWindow() {
//        this.removeView(MAPVIEW);
//        MAPVIEW.onDestroy();
//        //since 1.6.0 不再在naviview destroy的时候自动执行AMapNavi.stopNavi();请自行执行
//        mAMapNavi.stopNavi();
//        mAMapNavi.destroy();
//        mTtsManager.destroy();
//        super.onDetachedFromWindow();
//    }
//
//    /**
//     * 对应onResume、对应onPause
//     *
//     * @param hasWindowFocus
//     */
//    @Override
//    public void onWindowFocusChanged(boolean hasWindowFocus) {
//
//        super.onWindowFocusChanged(hasWindowFocus);
//
//        if (hasWindowFocus) {
////            对应onResume
//            MAPVIEW.onResume();
//        } else {
//            //对应onPause
//            MAPVIEW.onPause();
//            mTtsManager.stopSpeaking();
//        }
//    }

    @Override
    public void onHostResume() {
        //对应onResume
        init();
        MAPVIEW.onResume();
    }

    @Override
    public void onHostPause() {
        //对应onPause
        MAPVIEW.onPause();
        mTtsManager.stopSpeaking();
    }

    @Override
    public void onHostDestroy() {

        this.removeView(MAPVIEW);
        MAPVIEW.onDestroy();
        //since 1.6.0 不再在naviview destroy的时候自动执行AMapNavi.stopNavi();请自行执行
        mAMapNavi.stopNavi();
        mAMapNavi.destroy();
        mTtsManager.destroy();
    }


    /*****************************AMapNaviListener*************************************/


    @Override
    public void onInitNaviFailure() {
        Log.e("RCTANaviMapViewTest","init navi Failed");
        Log.e("定位", "init navi Failed");
    }

    @Override
    public void onInitNaviSuccess() {
        //初始化成功

        /**
         * 方法: int strategy=mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost, hightspeed, multipleroute); 参数:
         *
         * @congestion 躲避拥堵
         * @avoidhightspeed 不走高速
         * @cost 避免收费
         * @hightspeed 高速优先
         * @multipleroute 多路径
         *
         *  说明: 以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。
         *  注意: 不走高速与高速优先不能同时为true 高速优先与避免收费不能同时为true
         */
        int strategy = 0;
        try {
            //再次强调，最后一个参数为true时代表多路径，否则代表单路径
            strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAMapNavi.calculateDriveRoute(sList, eList, mWayPointList, strategy);
        Log.e("定位", "初始化成功");
    }

    @Override
    public void onStartNavi(int type) {
        //开始导航回调
        Log.e("定位", "开始导航回调");
    }

    @Override
    public void onTrafficStatusUpdate() {
        //
    }

    @Override
    public void onLocationChange(AMapNaviLocation location) {
        //当前位置回调
        Log.e("定位", "当前位置回调");
    }

    @Override
    public void onGetNavigationText(int type, String text) {
        //播报类型和播报文字回调
        Log.e("定位", "播报类型和播报文字回调");
    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onEndEmulatorNavi() {
        //结束模拟导航
        Log.e("定位", "结束模拟导航");
    }

    @Override
    public void onArriveDestination() {
        //到达目的地
        Log.e("定位", "到达目的地");
    }

    @Override
    public void onCalculateRouteFailure(int errorInfo) {
        //路线计算失败
        Log.e("dm", "--------------------------------------------");
        Log.i("dm", "路线计算失败：错误码=" + errorInfo + ",Error Message= " + ErrorInfo.getError(errorInfo));
        Log.i("dm", "错误码详细链接见：http://lbs.amap.com/api/android-navi-sdk/guide/tools/errorcode/");
        Log.e("dm", "--------------------------------------------");
        Log.e("RCTANaviMapViewTest","errorInfo：" + errorInfo + ",Message：" + ErrorInfo.getError(errorInfo));
    }

    @Override
    public void onReCalculateRouteForYaw() {
        //偏航后重新计算路线回调
        Log.e("定位", "偏航后重新计算路线回调");
    }

    @Override
    public void onReCalculateRouteForTrafficJam() {
        //拥堵后重新计算路线回调
        Log.e("定位", "拥堵后重新计算路线回调");
    }

    @Override
    public void onArrivedWayPoint(int wayID) {
        //到达途径点
        Log.e("定位", "到达途径点");
    }

    @Override
    public void onGpsOpenStatus(boolean enabled) {
        //GPS开关状态回调
        Log.e("定位", "GPS开关状态回调");
    }


    @Deprecated
    @Override
    public void onNaviInfoUpdated(AMapNaviInfo naviInfo) {
        //过时
    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapCameraInfos) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] amapServiceAreaInfos) {

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviinfo) {
        //导航过程中的信息更新，请看NaviInfo的具体说明
        Log.e("定位", "导航过程中的信息更新，请看NaviInfo的具体说明");
    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {
        //已过时
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {
        //已过时
    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {
        //显示转弯回调
        Log.e("定位", "显示转弯回调");
    }

    @Override
    public void hideCross() {
        //隐藏转弯回调
        Log.e("定位", "隐藏转弯回调");
    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] laneInfos, byte[] laneBackgroundInfo, byte[] laneRecommendedInfo) {
        //显示车道信息
        Log.e("定位", "显示车道信息");
    }

    @Override
    public void hideLaneInfo() {
        //隐藏车道信息
        Log.e("定位", "隐藏车道信息");
    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {
        //多路径算路成功回调

        mAMapNavi.startNavi(NaviType.EMULATOR);
        Log.e("定位", "多路径算路成功回调");
    }

    @Override
    public void notifyParallelRoad(int i) {
        if (i == 0) {
            Log.e("定位", "当前在主辅路过渡");
            return;
        }
        if (i == 1) {
            Log.e("定位", "当前在主路");
            return;
        }
        if (i == 2) {
            Log.e("定位", "当前在辅路");
        }
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {
        //更新交通设施信息
        Log.e("定位", "更新交通设施信息");
    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {
        //更新巡航模式的统计信息
        Log.e("定位", "更新巡航模式的统计信息");
    }


    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {
        //更新巡航模式的拥堵信息
        Log.e("定位", "更新巡航模式的拥堵信息");
    }

    @Override
    public void onPlayRing(int i) {

    }

}
