import React, { Component } from 'react';

import PropTypes from 'prop-types';

import {requireNativeComponent,View,UIManager,findNodeHandle} from 'react-native';

export default class TestNaviMap extends Component{


    render(){
        return (
            <RCTAMapViewTest
                {...this.props}
            />
        );
    };
}

TestNaviMap.name = "RCTANaviMapViewTest";
TestNaviMap.propTypes = {
    test:PropTypes.string.isRequired,
    ...View.propTypes,
}

var RCTAMapViewTest = requireNativeComponent('RCTANaviMapViewTest',TestNaviMap);