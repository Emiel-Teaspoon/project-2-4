import React, { Component } from 'react';
import { Paper, Typography, Grid, Button, Input, InputLabel, FormControl, Link, TextField, FormHelperText } from '@material-ui/core';
import classes from './EventMaker.css';
import Map from '../../Map/Map';
import EventMarker from '../EventMarker/EventMarker';
import axios from 'axios';
import DateFnsUtils from '@date-io/date-fns';
import {
  MuiPickersUtilsProvider,
  KeyboardTimePicker,
  KeyboardDatePicker,
} from '@material-ui/pickers';

class EventMaker extends Component {

    constructor(props) {
        super(props);
        this.state = {
            map: null,
            name: "",
            description: "",
            adress: "",
            city: "",
            image: "",
            lat: 0,
            lng: 0,
            myMarker: null,
            selectedDate: new Date(),
            selectedEndDate: new Date(),
            adressError: "",
            adressIsError: false,
        }
    
        this.handleNameChange = this.handleNameChange.bind(this);
        this.handleDescriptionChange = this.handleDescriptionChange.bind(this);
        this.handleAdressChange = this.handleAdressChange.bind(this);
        this.handleCityChange = this.handleCityChange.bind(this);
        this.handleDateChange = this.handleDateChange.bind(this);
        this.handleEndDateChange = this.handleEndDateChange.bind(this);
    }

    setMapCallback = (map) => {
        this.setState({map: map});
    }

    handleNameChange(event) {
        this.setState({name: event.target.value});
    }
    
    handleDescriptionChange(event) {
        this.setState({description: event.target.value});
    }
    
    handleAdressChange(event) {
        this.setState({adress: event.target.value});
    }

    handleCityChange(event) {
        this.setState({city: event.target.value});
    }

    markerClickHandler(id) {

    }

    makeClickHandler(params) {
        this.checkMap();
        const userID = this.props.userID;

        axios.post("https://spicymemes.app/eventmap/public/addEvent",
        {
            title: params.name,
            description: params.description,
            latd: Number(params.lat),
            lotd: Number(params.lng),
            attendees: 0,
            iamge: "https://spicymemes.app/eventimage/default.jpg",
            eventStartDT: params.selectedDate,
            eventEndDT: params.selectedEndDate,
            owner: userID,
        }, 
        { headers: { Authorization: 'Bearer ' + this.props.token }})
        .then(res =>{
            console.log(res);
            if(res.status === 200) {
                this.props.closeHandler();
            }
        }).catch(err => {
            console.log(err);
        });
    }

    handleDateChange(date) {
        this.setState({selectedDate : date});
    }

    handleEndDateChange(date) {
        this.setState({selectedEndDate : date});
    }

    checkMap() {
        if(this.state.adress !== "" && this.state.city !== "") {
            const adress = this.state.adress + " " + this.state.city; 
            const encodedAdress = adress.replace(" ", "+");
            axios.get("https://maps.googleapis.com/maps/api/geocode/json?address=" + encodedAdress + "&key=AIzaSyDv-X6mjYHdmyAXyoF2st-GKpQn-lyyt8Y").then(
                res => {
                    const data = res.data;
                    if(res.data.results[0]){
                        this.setState({adressIsError: false, lat:data.results[0].geometry.location.lat, lng:data.results[0].geometry.location.lng, myMarker: <EventMarker lat={data.results[0].geometry.location.lat} lng={data.results[0].geometry.location.lng} id={1} clickHandler={this.markerClickHandler}/>});
                        this.state.map.setZoom(17);
                        this.state.map.panTo(data.results[0].geometry.location);
                    }
                    else {
                        this.setState({adressIsError: true, adressError: "Invalid Adress"});
                    }
                }
            )
        }
    }

    render() {
        const filledIn = this.state.name !== "" && this.state.adress !== "" && this.state.city !== "" && this.state.myMarker;
        return (
            <Paper className={classes.paper}> 
                <Typography variant="h6" gutterBottom>
                    New Event
                </Typography>
                <Grid container className={classes.root} spacing={2} >
                    <Grid item xs={12}>
                        <FormControl margin="normal" required fullWidth >
                            <InputLabel htmlFor="eventname">Name of event</InputLabel>
                            <Input id="eventname" name="eventname" onChange={this.handleNameChange} autoFocus />
                        </FormControl>
                    </Grid>
                    <Grid item xs={12}>
                    <TextField
                        id="standard-textarea"
                        label="Event description"
                        placeholder="Placeholder"
                        multiline
                        fullWidth
                        onChange={this.handleDescriptionChange}
                        margin="normal"
                    />
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <FormControl margin="normal" required fullWidth error={this.state.adressIsError}>
                            <InputLabel htmlFor="adress">Adress</InputLabel>
                            <Input id="adress" name="adress" onChange={this.handleAdressChange} />
                            {this.state.adressIsError ? <FormHelperText id="component-error-text">{this.state.adressError}</FormHelperText> : <div/>}
                        </FormControl>
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <FormControl margin="normal" required fullWidth >
                            <InputLabel htmlFor="city">City</InputLabel>
                            <Input id="city" name="city" onChange={this.handleCityChange} />
                        </FormControl>
                    </Grid>
                    <Grid item xs={12}>
                        <Link
                            className={classes.linkButton}
                            component="button"
                            variant="body2"
                            onClick={() => {
                                this.checkMap();
                            }}>
                            Refresh map
                        </Link>
                    </Grid>
                </Grid>
                <div className={classes.map}>
                    <Map mapCallback={this.setMapCallback}> 
                        {this.state.myMarker}
                    </Map>
                </div>
                <MuiPickersUtilsProvider utils={DateFnsUtils}>
                    <Grid container className={classes.grid} justify="space-around" spacing={2} >
                        <Grid item xs={12} sm={6}>
                            <KeyboardDatePicker
                            margin="normal"
                            id="mui-pickers-date"
                            label="Start date"
                            value={this.state.selectedDate}
                            onChange={this.handleDateChange}
                            fullWidth
                            KeyboardButtonProps={{
                                'aria-label': 'change date',
                            }}
                            />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <KeyboardTimePicker
                            margin="normal"
                            id="mui-pickers-time"
                            label="Start time"
                            fullWidth
                            value={this.state.selectedDate}
                            onChange={this.handleDateChange}
                            KeyboardButtonProps={{
                                'aria-label': 'change time',
                            }}
                            />
                        </Grid>
                        <Grid  item xs={12} sm={6}>
                            <KeyboardDatePicker
                            margin="normal"
                            id="mui-pickers-date"
                            label="End date"
                            fullWidth
                            value={this.state.selectedEndDate}
                            onChange={this.handleEndDateChange}
                            KeyboardButtonProps={{
                                'aria-label': 'change date',
                            }}
                            />
                            </Grid>
                        <Grid  item xs={12} sm={6}>
                            <KeyboardTimePicker
                            margin="normal"
                            id="mui-pickers-time"
                            label="End time"
                            fullWidth
                            value={this.state.selectedEndDate}
                            onChange={this.handleEndDateChange}
                            KeyboardButtonProps={{
                                'aria-label': 'change time',
                            }}
                            />
                        </Grid>
                    </Grid>
                </MuiPickersUtilsProvider>
                <div className={classes.submit}>
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        color="primary"
                        disabled={!filledIn}
                        className={classes.submit}
                        onClick={() => this.makeClickHandler(this.state)}>
                            Make event
                    </Button>
                </div>
            </Paper>
        ); 
    }
}

export default EventMaker;