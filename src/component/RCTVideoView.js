import React, { Component } from 'react';

import PropTypes from 'prop-types';

import {requireNativeComponent,View,UIManager,findNodeHandle} from 'react-native';


var RCT_VIDEO_REF = 'VideoView';

export default class VideoView extends Component{
    _onPrepared(event){
        if(!this.props.onPrepared){
            return;
        }
        this.props.onPrepared(event.nativeEvent.duration);
    }

    _onError(event){
        if(!this.props.onError){
            return;
        }
        this.props.onError(event.nativeEvent);
    }

    _onBufferUpdate(event){
        if(!this.props.onBufferUpdate){
            return;
        }
        this.props.onBufferUpdate(event.nativeEvent.buffer);
    }

    _onProgress(event){
        if(!this.props.onProgress){
            return;
        }
        this.props.onProgress(event.nativeEvent.progress);
    }


    pause(){
        UIManager.dispatchViewManagerCommand(
            findNodeHandle(this.refs[RCT_VIDEO_REF]),
            UIManager.RCTVideoView.Commands["pause"],
            null
        );
    }



    render(){
        //return <RCTVideoView {...this.props} onChange={this._onChange.bind(this)}/>;
        return <RCTVideoView

            ref = {RCT_VIDEO_REF}
            {...this.props}
            onPrepared={this._onPrepared.bind(this)}
            onError={this._onError.bind(this)}
            onBufferUpdate={this._onBufferUpdate.bind(this)}
            onProgress={this._onProgress.bind(this)}


        />;
    };
}

VideoView.name = "RCTVideoView";
VideoView.propTypes = {
    onPrepared:PropTypes.func,
    onCompletion:PropTypes.func,
    onError:PropTypes.func,
    onBufferUpdate:PropTypes.func,
    onProgress:PropTypes.func,
    style:View.propTypes.style,
    source:PropTypes.shape({
        url:PropTypes.string,
        headers:PropTypes.object,
    }),
    ...View.propTypes,
}

var RCTVideoView = requireNativeComponent('RCTVideoView',VideoView,{
    nativeOnly :{onChange : true}
});