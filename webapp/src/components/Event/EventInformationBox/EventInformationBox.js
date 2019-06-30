import React, { Component } from 'react'
import classes from './EventInformationBox.css';

import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import { CardHeader, IconButton, Avatar } from '@material-ui/core';
import MoreVertIcon from '@material-ui/icons/MoreVert';
import PersonIcon from '@material-ui/icons/Person';

export default class EventInformationBox extends Component {

    constructor(props) {
        super(props);
    
        this.setWrapperRef = this.setWrapperRef.bind(this);
        this.handleClickOutside = this.handleClickOutside.bind(this);
      }
    
      componentDidMount() {
        document.addEventListener('mousedown', this.handleClickOutside);
      }
    
      componentWillUnmount() {
        document.removeEventListener('mousedown', this.handleClickOutside);
      }
    
      /**
       * Set the wrapper ref
       */
      setWrapperRef(node) {
        this.wrapperRef = node;
      }
    
      /**
       * Alert if clicked on outside of element
       */
      handleClickOutside(event) {
        if (this.wrapperRef && !this.wrapperRef.contains(event.target)) {
          console.log('You clicked outside of me!');
          this.props.close();
        }
      }

    render() {
        return (
            <div className={classes.Details} ref={this.setWrapperRef} style={this.props.style}>
              <Card>
                <CardHeader
                  avatar={
                    <Avatar aria-label="Recipe" className={classes.avatar}>
                      <PersonIcon/>
                    </Avatar>
                  }
                  action={
                    <IconButton aria-label="Settings">
                      <MoreVertIcon />
                    </IconButton>
                  }
                  title={this.props.details.username}
                  subheader={this.props.details.startDate}
                />
                <CardActionArea>
                  <CardMedia
                    component="img"
                    alt="Event Image"
                    height="140"
                    image={this.props.details.img}
                    title="Event Image"
                  />
                  <CardContent>
                    <Typography gutterBottom variant="h5" component="h2">
                      {this.props.details.title}
                    </Typography>
                    <Typography variant="body2" color="textSecondary" component="p">
                      {this.props.details.description}
                    </Typography>
                  </CardContent>
                </CardActionArea>
                <CardActions>
                  <Button size="small" color="primary">
                    Attend
                  </Button>
                  <Button size="small" color="primary">
                    Go to user
                  </Button>
                </CardActions>
              </Card>
            </div>
        )
    }
}
