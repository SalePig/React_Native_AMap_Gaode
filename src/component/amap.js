import React, { Component} from 'react';
import {
    NativeAppEventEmitter,
    NativeModules,
    Platform,
    StyleSheet,
    requireNativeComponent,
    View,
    findNodeHandle,

} from 'react-native';

import { PropTypes } from 'prop-types';

const MapManager = NativeModules.AMapModule;

export default class AMap extends Component {

    static propTypes = {
        ...View.propTypes,
        options: PropTypes.shape({
            centerCoordinate: PropTypes.shape({
                latitude: PropTypes.number.isRequired,
                longitude: PropTypes.number.isRequired,
            }),
            zoomLevel: PropTypes.number,
            centerMarker: PropTypes.string,
        }).isRequired,
        onDidMoveByUser: PropTypes.func,
    }

    constructor(props) {
        super(props)
        this.state = {}
    }

    render() {
        return (
            <NativeAMap
                {...this.props}

            />

        );
    }
}

const NativeAMap = requireNativeComponent('RCTAMapView', AMap);