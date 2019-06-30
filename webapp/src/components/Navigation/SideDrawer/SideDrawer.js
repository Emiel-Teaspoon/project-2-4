import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Drawer from '@material-ui/core/Drawer';
import Divider from '@material-ui/core/Divider';
import IconButton from '@material-ui/core/IconButton';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import NavigationItems from '../NavigationItems/NavigationItems';
import Aux from '../../../hoc/Aux';
import Backdrop from '../../UI/Backdrop/Backdrop';
import { InputBase, Switch, FormControlLabel } from '@material-ui/core';
import SearchIcon from '@material-ui/icons/Search';

const drawerWidth = 240;

const styles = theme => ({
  root: {
    display: 'flex',
  },
  appBar: {
    transition: theme.transitions.create(['margin', 'width'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
  },
  appBarShift: {
    width: `calc(100% - ${drawerWidth}px)`,
    marginLeft: drawerWidth,
    transition: theme.transitions.create(['margin', 'width'], {
      easing: theme.transitions.easing.easeOut,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  searchroot: {
    padding: '2px 4px',
    display: 'flex',
    alignItems: 'center',
  },
  input: {
    marginLeft: 8,
    flex: 1,
  },
  iconButton: {
    padding: 10,
  },
  menuButton: {
    marginLeft: 12,
    marginRight: 20,
  },
  hide: {
    display: 'none',
  },
  drawer: {
    width: drawerWidth,
    flexShrink: 0,
  },
  drawerPaper: {
    width: drawerWidth,
  },
  drawerHeader: {
    display: 'flex',
    alignItems: 'center',
    padding: '0 8px',
    ...theme.mixins.toolbar,
    justifyContent: 'flex-end',
  },
  content: {
    flexGrow: 1,
    padding: theme.spacing.unit * 3,
    transition: theme.transitions.create('margin', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    marginLeft: -drawerWidth,
  },
  contentShift: {
    transition: theme.transitions.create('margin', {
      easing: theme.transitions.easing.easeOut,
      duration: theme.transitions.duration.enteringScreen,
    }),
    marginLeft: 0,
  },
  switch: {
    marginLeft: 12,
  }
});

class SideDrawer extends Component {

    state = {
      friendSwitch: false,
    }

    handleChange = name => event => {
      this.setState({ ...this.state, [name]: event.target.checked });
    };

    render() {
    const { classes, theme } = this.props;

    return (
        <Aux>
            <Backdrop show={this.props.open} clicked={this.props.closeDrawer}/>
            <Drawer
            className={classes.drawer}
            variant="persistent"
            anchor="left"
            open={this.props.open}
            classes={{
                paper: classes.drawerPaper,
            }}
            >
            <div className={classes.drawerHeader}>
                <IconButton onClick={() => this.props.closeDrawer()}>
                {theme.direction === 'ltr' ? <ChevronLeftIcon /> : <ChevronRightIcon />}
                </IconButton>
            </div>
            <Divider />
            <div className={classes.searchroot}>
            <InputBase
              className={classes.input}
              placeholder="Find User"
              inputProps={{ 'aria-label': 'Search Google Maps' }}
            />
            <IconButton className={classes.iconButton} aria-label="Search">
              <SearchIcon />
            </IconButton>
            </div>
            <Divider />
            <FormControlLabel
            className={classes.switch}
            label="Friend events only"
            control={
              <Switch
                checked={this.state.friendSwitch}
                onChange={this.handleChange('friendSwitch')}
                value="friendSwitch"
                color="secondary"
              />
            }
            />
            {this.props.auth ? 
                <NavigationItems/>
            :
            <div></div>}
            </Drawer>
        </Aux>
    );
  }
}

SideDrawer.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired,
};

export default withStyles(styles, { withTheme: true })(SideDrawer);