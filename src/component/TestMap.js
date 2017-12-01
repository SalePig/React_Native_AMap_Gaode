import React, { Component } from 'react';

import PropTypes from 'prop-types';

import {requireNativeComponent,View,UIManager,findNodeHandle} from 'react-native';

export default class TestMap extends Component{


    render(){
        return (
            <RCTAMapViewTest
                {...this.props}
            />
        );
    };
}

TestMap.name = "RCTAMapViewTest";
TestMap.propTypes = {
    test:PropTypes.string.isRequired,
    ...View.propTypes,
}

var RCTAMapViewTest = requireNativeComponent('RCTAMapViewTest',TestMap);