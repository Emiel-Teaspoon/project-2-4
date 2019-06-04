import React, { Component } from 'react'

import EventMarker from './EventMarker/EventMarker';
import EventInformationBox from './EventInformationBox/EventInformationBox';

class Event extends Component {

    render() {

        const margins = null;//{'margin-left' : }

        return (
            <div>
                <EventMarker clickHandler={this.props.click} id={this.props.id} size={this.props.size}/>
                {this.props.open ? <EventInformationBox style={margins} close={this.props.closeHandler}/> : <div/>}
            </div>
        )
    }
}

export default Event;