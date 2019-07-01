import React, { Component } from 'react';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';

import classes from './NavigationItem.css';
import { IconButton } from '@material-ui/core';

class NavigationItem extends Component {

    render() {
        return (
            <ListItem>
                <ListItemIcon>{this.props.icon}</ListItemIcon>
                <ListItemText primary={this.props.text} />
                {this.props.sideIcon ? <IconButton className={classes.nextIcon} onClick={this.props.click}>{this.props.sideIcon}</IconButton> : <div></div>}
            </ListItem>
        )
    }
}

export default NavigationItem