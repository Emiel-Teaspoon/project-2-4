import React, { Component } from 'react';
import NavigationItem from './NavigationItem/NavigationItem';
import Divider from '@material-ui/core/Divider'
import List from '@material-ui/core/List';

import Icon from '@material-ui/icons/Favorite';

class NavigationItems extends Component {

    clicked = () => {
        console.log("clicked");
    }

    render() {
        return (
            <div>
            <List>
                <NavigationItem text="TestItem" icon={<Icon/>} click={() => this.clicked()}/>
                <NavigationItem text="TestItem" icon={<Icon/>} click={() => this.clicked()}/>
                <NavigationItem text="TestItem" icon={<Icon/>} click={() => this.clicked()}/>
                <NavigationItem text="TestItem" icon={<Icon/>} click={() => this.clicked()}/>
            </List>
            <Divider />
            <List>
                <NavigationItem text="TestItem" icon={<Icon/>} click={() => this.clicked()}/>
                <NavigationItem text="TestItem" icon={<Icon/>} click={() => this.clicked()}/>
                <NavigationItem text="TestItem" icon={<Icon/>} click={() => this.clicked()}/>
            </List>
            </div>
        )
    }
}

export default NavigationItems