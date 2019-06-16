import React, { Component } from 'react'

import Aux from '../../hoc/Aux';
import Map from '../../components/Map/Map'
import Event from '../../components/Event/Event';

class EventMap extends Component {

    latlng = {lat: 53.18, lng: 6.53}

    state = {
        map: null,
        openEventId: -1,
        events: [
            {
                id: 10,
                open: false,
                size: 10,
                position: {
                    lat: 53.2180043,
                    lng: 6.5668195, 
                },
                details: {
                    title: "Title",
                    description: "Description",
                    img: "https://media.resources.festicket.com/www/admin/uploads/images/SW4_8iu8hh9.png",
                }
            },
            {
                id: 11,
                open: false,
                size: 15,
                position: {
                    lat: 53.2,
                    lng: 6.59, 
                },
                details: {
                    title: "Title",
                    description: "Description",
                    img: "https://media.resources.festicket.com/www/admin/uploads/images/SW4_8iu8hh9.png",
                }
            },
            {
                id: 12,
                open: false,
                size: 300,
                position: {
                    lat: 53.25,
                    lng: 6.51, 
                },
                details: {
                    title: "Title",
                    description: "Description",
                    img: "https://media.resources.festicket.com/www/admin/uploads/images/SW4_8iu8hh9.png",
                }
            },            
            {
                id: 13,
                open: false,
                size: 1,
                position: {
                    lat: 53.25,
                    lng: 6.6, 
                },
                details: {
                    title: "Title",
                    description: "Description",
                    img: "https://media.resources.festicket.com/www/admin/uploads/images/SW4_8iu8hh9.png",
                }
            },
        ],
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
        this.moveMapTo(this.state.events[this.getIdFromKey(id)].position);
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
        fetch('https://spicymemes.app/eventmap/public/Events/' + 50)
        .then(response => response.json())
        .then(data => {
            console.log(data); 
            // this.setState({isLoading: false});
            // if(data.Code === 200) {
            //     const user = {
            //         userId: data.UserID, 
            //         username: data.Username,
            //         apiKey: data.APIKey,
            //     }
            //     this.setState({isAuthenticated: true, user: user});
            //     console.log(data);
            //   }
        });//.catch(this.setState({isLoading: false}));
      }

    render () 
    {
        const events = this.state.events.map(
            event => {
                return (
                    <Event key={event.id} id={event.id} lat={event.position.lat} lng={event.position.lng} click={this.openEventHandler} closeHandler={this.closeEventHandler} size={event.size} open={event.open}/>
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