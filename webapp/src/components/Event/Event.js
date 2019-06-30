import React, { Component } from 'react'

import EventMarker from './EventMarker/EventMarker';
import EventInformationBox from './EventInformationBox/EventInformationBox';

class Event extends Component {

    constructor(props) {
        super(props);
        this.state = {
            event: props.event,
        }
    }

    render() {

        return (
            <div>
                <EventMarker clickHandler={this.props.click} id={this.props.id} size={this.props.size}/>
                {this.props.open ? <EventInformationBox close={this.props.closeHandler} details={this.state.event.details}/> : <div/>}
            </div>
        )
    }
}

export default Event;