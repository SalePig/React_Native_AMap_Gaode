import React, { Component } from 'react';

import PropTypes from 'prop-types';

import {requireNativeComponent,View,UIManager,findNodeHandle} from 'react-native';

export default class MapViewTest extends Component{


    render(){
        return (
            <MapView
                {...this.props}
            />
        );
    };
}

MapViewTest.name = "MapViewTest";
MapViewTest.propTypes = {
    test:PropTypes.string.isRequired,
    ...View.propTypes,
}

var MapView = requireNativeComponent('MapViewTest',MapViewTest);