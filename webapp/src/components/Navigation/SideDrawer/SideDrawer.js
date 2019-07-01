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
import { InputBase, Switch, FormControlLabel, CardActionArea } from '@material-ui/core';
import SearchIcon from '@material-ui/icons/Search';
import axios from 'axios';
import Icon from '@material-ui/icons/Person';
import AddIcon from '@material-ui/icons/Add';
import NavigationItem from '../NavigationItems/NavigationItem/NavigationItem';
import { thisTypeAnnotation } from '@babel/types';

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
    padding: theme.spacing(3),
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
    margin: 8,
  }
});

class SideDrawer extends Component {

  constructor(props) {
    super(props)
    this.state = {
      friendSwitch: false,
      findText: "",
      foundUsers: [],
      user: props.user,
    }

    this.onChangeListener = this.onChangeListener.bind(this);
    this.clickUser = this.clickUser.bind(this);
  }

  clickUser = (id) => {
    console.log("Remove " + id);
    console.log(this.state.user);
    axios.delete('https://spicymemes.app/eventmap/public/unfollowUser/' + this.state.user.user_id + '/' + id, { headers: { Authorization: 'Bearer ' + this.state.user.token }})
    .then(res => {
      console.log(res);
    })
    .catch(
      err => {
        console.log(err);
      }
    );
  }

  addUser = (id) => {
    const newFriends = [...this.state.foundUsers];
    for(var i = 0; i < newFriends.length; i++)
    {
      if(newFriends[i].user_id === id)
      {
        newFriends[i].isFollowing = true;
      }
    }
    this.setState({foundUsers: newFriends});
    console.log("Add " + id);
    axios.post('https://spicymemes.app/eventmap/public/follow', {user_id: this.state.user.userId, follower_id: id}, { headers: { Authorization: 'Bearer ' + this.state.user.token }})
    .then(res => {
      console.log(res);
    })
    .catch(
      err => {
        console.log(err);
      }
    );
  }
 
  handleChange = () => event => {
    this.setState({friendSwitch: event.target.checked });
    console.log(event.target.checked);
    this.props.toggleHandler(event.target.checked);
  };

  onChangeListener(event) {
    this.setState({findText: event.target.value});
  }

  handleSearch = () => {
    this.findUser(this.state.findText, this.props.user.token);
  }

  findUser = (username, token) => {
    axios.get('https://spicymemes.app/eventmap/public/findUserByUsername/' + username + '/' + this.props.user.userId, { headers: { Authorization: 'Bearer ' + token }})
    .then(res => {
      console.log(res);
      if(res.data.Code === 200) {
            if(res.data.result) {
                this.setState({foundUsers: res.data.result});
            }
            else {
              this.setState({foundUsers: []});
            }
        }
        else {
            console.log(res);
        }
    })
    .catch(
      err => {
        console.log(err);
        this.setState({isLoading: false, isError:true, isUserError:true, logError:"Error " + err.response.status + " " + err.response.data.message});
      }
    );
  }

  render() {
    const { classes, theme } = this.props;

    const users = this.state.foundUsers.map(
      friend => {
        return (
            <NavigationItem 
              key={friend.user_id} 
              text={friend.username}
              icon={<Icon/>}
              sideIcon={friend.isFollowing ? null : <AddIcon color="primary"/>}
              click={() => this.addUser(friend.user_id)}/>
        );
      }
    );

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
            onChange={this.onChangeListener}
            inputProps={{ 'aria-label': 'Search Google Maps' }}
          />
          <IconButton className={classes.iconButton} aria-label="Search" onClick={this.handleSearch}>
            <SearchIcon />
          </IconButton>
          </div>
          <Divider />
            {users.length > 0 ? 
            <div>
              {users}
              <Divider/>
            </div>
            :
            null}
          <CardActionArea>
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
          </CardActionArea>
          <Divider />
          {this.props.auth ? 
            <NavigationItems user={this.props.user} clicked={this.clickUser}/>
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