import React, { Component } from 'react';

import GoogleMapReact from 'google-map-react';
import classes from './Map.css';
import * as data from './Style.json';

function createMapOptions(maps) {
    // next props are exposed at maps
    // "Animation", "ControlPosition", "MapTypeControlStyle", "MapTypeId",
    // "NavigationControlStyle", "ScaleControlStyle", "StrokePosition", "SymbolPath", "ZoomControlStyle",
    // "DirectionsStatus", "DirectionsTravelMode", "DirectionsUnitSystem", "DistanceMatrixStatus",
    // "DistanceMatrixElementStatus", "ElevationStatus", "GeocoderLocationType", "GeocoderStatus", "KmlLayerStatus",
    // "MaxZoomStatus", "StreetViewStatus", "TransitMode", "TransitRoutePreference", "TravelMode", "UnitSystem"
    return {
      styles: data.lightStyle,
      mapTypeControl: false,
      fullscreenControl: true,
      scaleControl: false,
      streetViewControl: true,
      zoomControl: false,
    };
  }

const groningen = {lat:53.2180043,lng:6.5668195}

class Map extends Component {

    defaultMapOptions = {
        fullscreenControl: false,
    };

    handleApiLoaded = (map, maps) => {
        // use map and maps objects
        this.props.mapCallback(map);
    };

    render() {
        const defaultLocation = groningen;

        const map = <GoogleMapReact
        bootstrapURLKeys={{ key: "AIzaSyDv-X6mjYHdmyAXyoF2st-GKpQn-lyyt8Y" }}
        defaultCenter={defaultLocation}
        defaultZoom={13}
        yesIWantToUseGoogleMapApiInternals
        defaultOptions={this.defaultMapOptions}
        options={createMapOptions}
        onGoogleApiLoaded={({ map, maps }) => this.handleApiLoaded(map, maps)}>
        {this.props.children}
        </GoogleMapReact>

        return (
            <div className={classes.Map}>
                {map}
            </div>
        );
    }
}

export default Map;