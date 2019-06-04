import React, { Component } from 'react';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';

class NavigationItem extends Component {

    render() {
        return (
            <ListItem button onClick={this.props.click}>
                <ListItemIcon>{this.props.icon}</ListItemIcon>
                <ListItemText primary={this.props.text} />
            </ListItem>
        )
    }
}

export default NavigationItem