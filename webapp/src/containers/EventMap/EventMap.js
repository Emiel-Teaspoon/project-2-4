import React, { Component } from 'react'

import Aux from '../../hoc/Aux';
import Map from '../../components/Map/Map'
import Event from '../../components/Event/Event';

import axios from 'axios';

class EventMap extends Component {

    latlng = {lat: 53.18, lng: 6.53}

    state = {
        map: null,
        openEventId: -1,
        user: null,
        events: [],
        onlyFriends: false,
    }

    shouldComponentUpdate(prevProps, nextProps)
    {
        return this.props.onlyFriends !== nextProps.onlyFriends;
    }

    componentDidMount() {
        this.setState({token: this.props.user.token});
        this.setState({onlyFriends: this.props.onlyFriends});
        this.getEvents();
    }
    
    addEventHandler = () => {

    }

    getIdFromKey = (id) => {
        return(this.state.events.findIndex(event => event.id === id));
    }

    openEventHandler = (id) => {
        const newEvents = [...this.state.events];
        if(this.state.openEventId > 0) {
            newEvents[this.getIdFromKey(this.state.openEventId)].open = false;
        }
        newEvents[this.getIdFromKey(id)].open = true;
        this.setState({openEventId: id, events: newEvents});
        this.moveMapTo(newEvents[this.getIdFromKey(id)].position);
        console.log(id);
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
        axios.get(this.state.onlyFriends ? "https://spicymemes.app/eventmap/public/FollowerEvents/" + this.props.user.userId : "https://spicymemes.app/eventmap/public/Events/50", { headers: { Authorization: 'Bearer ' + this.props.user.token }})
        .then(response => {
            console.log(response.data);
            if(response.data.Code === 200 && response.data.result) {
                const newEvent = response.data.result.map((event, index) => {
                    const eventObject = {
                        id: event.event_ID, 
                        open: false, 
                        size: Number(event.attendees), 
                        position: {
                            lat: Number(event.latitude), 
                            lng: Number(event.longitude)
                        }, 
                        details: {
                            title: event.title, 
                            description: event.description, 
                            img: event.image,
                            startDate: event.eventStartDT,
                            username: event.username,
                        }
                    };
                    return eventObject;
                })
                this.setState({events: newEvent})
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
                        open={event.open}
                        event={
                            {
                                details: {
                                    username: event.details.username,
                                    startDate: new Date(event.details.startDate).toUTCString(),
                                    title: event.details.title, 
                                    description: event.details.description, 
                                    img:event.details.img
                                },
                            }
                        }/>
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