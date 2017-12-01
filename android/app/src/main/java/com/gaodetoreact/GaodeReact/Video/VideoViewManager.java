package com.gaodetoreact.GaodeReact.Video;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.widget.VideoView;

import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * Created by qiuzhiwen on 2017/11/27.
 *
 *
 */

public class VideoViewManager extends SimpleViewManager<VideoViewManager.RCTVideoView> {


    @Override
    public String getName() {
        return "RCTVideoView";
    }

    @Override
    protected RCTVideoView createViewInstance(ThemedReactContext reactContext) {
        RCTVideoView videoView = new RCTVideoView(reactContext);
        return videoView;
    }


    /**
     * 自定义事件 必须重写此方法 进行回调
     *
     *  builder.put(event.toString(),MapBuilder.of("registrationName",event.toString()));
     *
     *  参数1：java端发送事件的名称
     *  MapBuilder.of
     *    参数1：registrationName字符串的值是固定的，不能修改
     *    参数2：定义在js端的回调方法

     * @return
     */
    @Nullable
    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        MapBuilder.Builder<String,Object> builder = MapBuilder.builder();
        for(VideoEvent event : VideoEvent.values()){
            builder.put(event.toString(), MapBuilder.of("registrationName",event.toString()));
        }
        return builder.build();
    }

    @Override
    public void onDropViewInstance(RCTVideoView view) { //对象销毁
        super.onDropViewInstance(view);
        ((ThemedReactContext) view.getContext()).removeLifecycleEventListener((RCTVideoView) view);
        view.stopPlayback();//停止播放
    }

    @ReactProp(name="source")
    public void setSource(RCTVideoView videoView, ReadableMap source){
        if(source !=null){
            if(source.hasKey("url")){
                String url = source.getString("url");
                System.out.print("url = " + url);
                Map<String,String> headerMap = new HashMap<>();
                if(source.hasKey("headers")){
                    ReadableMap headers = source.getMap("headers");
                    ReadableMapKeySetIterator readableMapKeySetIterator = headers.keySetIterator();

                    while (readableMapKeySetIterator.hasNextKey()){
                        String key = readableMapKeySetIterator.nextKey();
                        headerMap.put(key,headers.getString(key));
                    }
                }

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    videoView.setVideoURI(Uri.parse(url),headerMap);
                }else{
                    try {
                        Method setVideoURI = videoView.getClass().getMethod("setVideoURI", Uri.class, Map.class);
                        setVideoURI.invoke(videoView,Uri.parse(url),headerMap);
                    }catch (Exception e){

                    }

                }
                videoView.start();

            }
        }

    }



    private enum VideoEvent{
        EVENT_PREPARE("onPrepared"),
        EVENT_PROGRESS("onProgress"),
        EVENT_UPDATE("onBufferUpdate"),
        EVENT_ERROR("onError"),
        EVENT_COMPLETION("onCompletion");

        private String mName;
        VideoEvent(String name) {
            this.mName = name;
        }

        @Override
        public String toString() {
            return mName;
        }
    }


    public static class RCTVideoView extends VideoView implements
            LifecycleEventListener,
            MediaPlayer.OnPreparedListener,
            MediaPlayer.OnCompletionListener,
            MediaPlayer.OnErrorListener,
            MediaPlayer.OnInfoListener,
            MediaPlayer.OnBufferingUpdateListener,
            Runnable{


        private Handler mHandler;

        public RCTVideoView(ThemedReactContext context) {
            super(context);

            context.addLifecycleEventListener(this);
            setOnPreparedListener(this);
            setOnCompletionListener(this);
            setOnErrorListener(this);
            mHandler  = new Handler();
        }

        @Override
        public void onHostResume() {
            FLog.e(VideoViewManager.class,"onHostResume");
        }

        @Override
        public void onHostPause() {
            FLog.e(VideoViewManager.class,"onHostPause");
            pause();
        }

        @Override
        public void onHostDestroy() {
            FLog.e(VideoViewManager.class,"onHostDestroy");
            mHandler.removeCallbacks(this);
        }



        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {//视频加载成功准备播放
            int duration = mediaPlayer.getDuration();
            FLog.e(VideoViewManager.class,"onPrepared duration = "+duration);

            mediaPlayer.setOnInfoListener(this);
            mediaPlayer.setOnBufferingUpdateListener(this);

            WritableMap map = Arguments.createMap();
            map.putInt("duration",duration);//key用于js中的nativeEvent

            dispatchEvent(VideoEvent.EVENT_PREPARE.toString(),map);

            mHandler.post(this);

        }


        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {//视频播放结束
            FLog.e(VideoViewManager.class,"onCompletion");
            dispatchEvent(VideoEvent.EVENT_COMPLETION.toString(),null);
            mHandler.removeCallbacks(this);
            int progress = getDuration();
            WritableMap event = Arguments.createMap();
            event.putInt("progress",progress);
            dispatchEvent(VideoEvent.EVENT_PROGRESS.toString(),event);
        }



        @Override
        public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {//视频播放出错
            FLog.e(VideoViewManager.class,"onError what = "+ what+" extra = "+extra);
            mHandler.removeCallbacks(this);
            WritableMap event = Arguments.createMap();
            event.putInt("what",what);
            event.putInt("extra",what);
            dispatchEvent(VideoEvent.EVENT_ERROR.toString(),event);
            return true;
        }


        @Override
        public boolean onInfo(MediaPlayer mediaPlayer, int what, int extra) {
            FLog.e(VideoViewManager.class,"onInfo");
            switch (what) {
                /**
                 * 开始缓冲
                 */
                case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                    FLog.e(VideoViewManager.class,"开始缓冲");
                    break;
                /**
                 * 结束缓冲
                 */
                case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                    FLog.e(VideoViewManager.class,"结束缓冲");
                    break;
                /**
                 * 开始渲染视频第一帧画面
                 */
                case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                    FLog.e(VideoViewManager.class,"开始渲染视频第一帧画面");
                    break;
                default:
                    break;
            }
            return false;
        }



        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            FLog.e(VideoViewManager.class,"onBufferingUpdate percent = "+percent);
            int buffer = (int) Math.round((double) (mp.getDuration() * percent) / 100.0);
            WritableMap event = Arguments.createMap();
            event.putInt("buffer",buffer);
            dispatchEvent(VideoEvent.EVENT_UPDATE.toString(),event);
        }


        @Override
        public void run() {
            int progress = getCurrentPosition();
            WritableMap event = Arguments.createMap();
            event.putInt("progress",progress);
            dispatchEvent(VideoEvent.EVENT_PROGRESS.toString(),event);
            mHandler.postDelayed(this,1000);
        }


        private void dispatchEvent(String eventName,WritableMap eventData){
            ReactContext reactContext = (ReactContext) getContext();

            reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                    getId(),//native和js两个视图会依据getId()而关联在一起
                    eventName,//事件名称
                    eventData
            );
        }
    }



//

     /******************* js层向native层发送命令 ************************/

    private static final int COMMAND_PAUSE_ID = 1;
    private static final String COMMAND_PAUSE_NAME = "pause";


    /**
     * getCommandsMap接收多组命令，每组命令需要包括名称(js端调用的方法名)和命令id
     */
    @Nullable
    @Override
    public Map<String, Integer> getCommandsMap() {
        return MapBuilder.of(COMMAND_PAUSE_NAME,COMMAND_PAUSE_ID);
    }

    /**
     * 处理相应的命令
     */
    @Override
    public void receiveCommand(RCTVideoView root, int commandId, @Nullable ReadableArray args) {
        switch (commandId){
            case COMMAND_PAUSE_ID:
                root.pause();
                break;
            default:
                break;
        }
    }
}
