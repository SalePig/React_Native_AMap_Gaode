/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  View,
  TouchableOpacity
} from 'react-native';
import TestMap from './src/component/TestMap';

import TestNaviMap from './src/component/TestNaviMap';
import MapViewTest from './src/component/MapViewTest';

export default class App extends Component<{}> {

    render() {
      return (
        <View style={styles.container}>
            <View style={styles.viewStyle}>
                {/*<TestMap style={styles.mapStyle} test={"qiu"}/>*/}

                {/*<TestNaviMap style={styles.mapStyle} test={"qiu"}/>*/}
                <MapViewTest style={styles.mapStyle} test={"qiu"}/>

            </View>

            {/*<Text>{"22222"}</Text>*/}
        </View>
      );
    }




    // //暂停播放
    // _onPressPause(){
    //     if(this.video){
    //         alert('000');
    //         // RCTVideoView.pause();
    //         if(this.video.pause){
    //             this.video.pause();
    //         }else{
    //             alert('222');
    //         }
    //     }else{
    //         alert('111')
    //     }
    // }
    //
    //
    // render() {
    //     return (
    //         <View style={styles.container}>
    //
    //             <RCTVideoView
    //
    //                 ref={(video)=>{this.video = video;
    //
    //                     alert("");
    //
    //                 }}
    //                 style={styles.videoStyle}
    //                 source={
    //                     {
    //                         url:'http://qiubai-video.qiushibaike.com/A14EXG7JQ53PYURP.mp4',
    //                         headers:{
    //                             'refer':'myRefer'
    //                         }
    //                     }
    //                 }
    //
    //                 onPrepared={this._onPrepared}
    //                 onCompletion={()=>{
    //                     console.log("JS onCompletion");
    //                 }}
    //                 onError={(e)=>{
    //                     console.log("what="+e.what+" extra="+e.extra);
    //                 }}
    //                 onBufferUpdate={(buffer)=>{
    //                     console.log("JS buffer = "+buffer);
    //                 }}
    //                 onProgress={(progress)=>{
    //                     console.log("JS progress = "+progress);
    //                 }}
    //             />
    //
    //             <View style={{height:50,flexDirection:'row',justifyContent:'flex-start'}}>
    //
    //                 <TouchableOpacity style={{marginLeft:10}} onPress={this._onPressPause.bind(this)}>
    //                     <Text>暂停</Text>
    //                 </TouchableOpacity>
    //             </View>
    //
    //         </View>
    //     );
    // }










  // render() {
  //   return (
  //     <View style={styles.container}>
  //         <AMapView style={{flex:1}} options={{centerCoordinate:{latitude:31.239201,longitude:121.431598}}}
  //                   LatLng={{latitude:31.239201,longitude:121.431598}}
  //                   ref={ component => this._MapView = component }
  //                   onPoiSearch={this._onPoiSearch}
  //                   onMapViewCenterChangeFinish={this._onMapViewCenterChangeFinish}
  //                   UISETTING={{CENTERMARKER:'poi_marker'}}
  //
  //         />
  //     </View>
  //   );
  // }
  //   _onPoiSearch=(e)=>{
  //       let array=e.nativeEvent.searchResultList;
  //       if(this.refs.TextInput.value!='') {
  //           this.setState({componentDataSource: this.state.componentDataSource.cloneWithRows(array)});
  //       }
  //   }
  //   _onMapViewCenterChangeFinish=(e)=>{
  //
  //       this._MapView.poiSearchRound(e.nativeEvent.latitude, e.nativeEvent.longitude,'',0)
  //   }
  //   _startLocation = (e)=> {
  //       this._MapView.startLocation()
  //   }
  //   _getCenterLocation = (e)=> {
  //       this._MapView.getCenterLocation()
  //   }
    /* _onSubmitEditing = ()=> {

         if (value === '') {
             this.setState({componentDataSource: this.state.componentDataSource.cloneWithRows(componentData)});
         }
         else {

             this._MapView.poisearch(value, '上海')
         }


     }*/
    // _changeText = (e)=> {
    //     let timestamp = (new Date()).valueOf();
    //     if (timestamp - FirstEdit > 1000) {
    //         FirstEdit = timestamp;
    //
    //         if (e === '') {
    //             this.setState({componentDataSource: this.state.componentDataSource.cloneWithRows(componentData)});
    //         } else {
    //
    //             this._MapView.poisearch(e, '',0)
    //         }
    //     }
    //     if (e === '') {
    //         this.setState({componentDataSource: this.state.componentDataSource.cloneWithRows(componentData)});
    //     }
    //
    //     this.setState({value: e});
    //
    // }






}







const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },
    videoStyle: {
        height: 250,
        width: 380,
    },

    viewStyle: {
        flex: 1,
        width:380,
        height:300,
        backgroundColor:'#ff0',
    },

    mapStyle: {
        flex: 1,
        // height: 380,
        // width: 380,
    },
});
