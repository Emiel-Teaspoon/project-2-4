import React, { Component } from 'react';
import { Paper, Typography, Grid, Button, Input, InputLabel, FormControl } from '@material-ui/core';
import classes from './EventMaker.css';
import Map from '../../Map/Map';
import EventMarker from '../EventMarker/EventMarker';
import { tsParenthesizedType } from '@babel/types';

class EventMaker extends Component {

    constructor(props) {
        super(props);
        this.state = {
            map: null,
            name: "",
            adress: "",
            city: "",
            lat: 91,
            lng: 91
        }
    
        this.handleNameChange = this.handleNameChange.bind(this);
        this.handleAdressChange = this.handleAdressChange.bind(this);
        this.handleCityChange = this.handleCityChange.bind(this);
    }

    myMarker = null;

    setMapCallback = (map) => {
        this.setState({map: map});
    }

    handleNameChange(event) {
        this.setState({name: event.target.value});
    }
    
    handleAdressChange(event) {
        this.setState({adress: event.target.value});
    }

    handleCityChange(event) {
        this.setState({city: event.target.value});
    }


    markerClickHandler(id) {

    }

    checkMap() {
        this.myMarker = <EventMarker lat={this.state.lat} lng={this.state.lng} id={1} clickHandler={this.markerClickHandler}/>
    }

    render() {
        const filledIn = this.state.name !== "" && this.state.adress !== "" && this.state.city !== "";
        if(filledIn) {
            this.checkMap();
        }
        return (
            <Paper className={classes.paper}> 
                <Typography variant="h6" gutterBottom>
                    Add a new Event
                </Typography>
                <Grid container className={classes.root} spacing={16}>
                    <Grid item xs={12}>
                        <FormControl margin="normal" required fullWidth error={this.props.isUserError}>
                            <InputLabel htmlFor="name">Name of event</InputLabel>
                            <Input id="name" name="name" onChange={this.handleNameChange} autoFocus />
                        </FormControl>
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <FormControl margin="normal" required fullWidth error={this.props.isUserError}>
                            <InputLabel htmlFor="adress">Adress</InputLabel>
                            <Input id="adress" name="adress" onChange={this.handleAdressChange} autoFocus />
                        </FormControl>
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <FormControl margin="normal" required fullWidth error={this.props.isUserError}>
                            <InputLabel htmlFor="city">City</InputLabel>
                            <Input id="city" name="city" onChange={this.handleCityChange} autoFocus />
                        </FormControl>
                    </Grid>
                </Grid>
                <div className={classes.map}>
                    <Map mapCallback={this.setMapCallback}> 
                        {this.myMarker}
                    </Map>
                </div>
                <div className={classes.submit}>
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        color="primary"
                        disabled={!filledIn}
                        className={classes.submit}>
                            Make event
                    </Button>
                </div>
            </Paper>
        ); 
    }
}

export default EventMaker;