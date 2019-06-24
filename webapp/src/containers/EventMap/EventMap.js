import React, { Component } from 'react'

import Aux from '../../hoc/Aux';
import Map from '../../components/Map/Map'
import Event from '../../components/Event/Event';

import axios from 'axios';
import { array } from 'prop-types';
import eventMarker from '../../components/Event/EventMarker/EventMarker';

class EventMap extends Component {

    latlng = {lat: 53.18, lng: 6.53}

    state = {
        map: null,
        openEventId: -1,
        events: [],
    }
    
    componentDidMount() {
        this.getEvents();
    }

    addEventHandler = () => {

    }

    getIdFromKey = (id) => {
        return( this.state.events.findIndex(event => event.id === id) );
    }

    openEventHandler = (id) => {
        const newEvents = [...this.state.events];
        if(this.state.openEventId > 0) {
            newEvents[this.getIdFromKey(this.state.openEventId)].open = false;
        }
        newEvents[this.getIdFromKey(id)].open = true;
        this.setState({openEventId: id, events: newEvents});
        this.moveMapTo(newEvents[this.getIdFromKey(id)].position);
    }

    closeEventHandler = () => {
        if(this.state.openEventId > 0) {
            const newEvents = [...this.state.events];
            newEvents[this.getIdFromKey(this.state.openEventId)].open = false;
            this.setState({openEventId: -1, events: newEvents});
        }
    }

    moveMapTo = (latlng) => {
        if(this.state.map != null) {
            this.state.map.setZoom(15);
            this.state.map.panTo(latlng);
        }
    }

    setMapCallback = (map) => {
        this.setState({map: map});
    }

    async getEvents () {
        axios.get("https://spicymemes.app/eventmap/public/Events/50", { crossdomain: true })
        .then(response => {
            if(response.data.Code === 200) {
                let events = [];
                response.data.result.map((event, index) => {
                    const eventObject = {
                        id: index, 
                        open: false, 
                        size: Number(event.attendees), 
                        position: {
                            lat: Number(event.latitude), 
                            lng: Number(event.longitude)
                        }, 
                        details: {
                            title: event.title, 
                            description: event.description, 
                            img:event.image
                        }
                    };
                    events.push(eventObject);
                })
                console.log(events);
                this.setState({events: events})
            }
        });
      }

    render () 
    {
        const events = this.state.events.map(
            event => {
                return (
                    <Event 
                        key={event.id} 
                        id={event.id} 
                        lat={event.position.lat} 
                        lng={event.position.lng} 
                        click={this.openEventHandler} 
                        closeHandler={this.closeEventHandler} 
                        size={event.size} 
                        open={event.open}/>
                );
            }
        );

        return(
            <Aux>
                <Map mapCallback={this.setMapCallback} >
                    {events}
                </Map>
            </Aux>
        );
    }
}

export default EventMap;