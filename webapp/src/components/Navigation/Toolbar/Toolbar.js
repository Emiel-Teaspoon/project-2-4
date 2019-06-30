import React from 'react';

import classes from './Toolbar.css';

import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import AccountCircle from '@material-ui/icons/AccountCircle';
import AddCircle from '@material-ui/icons/AddCircle';
import MenuItem from '@material-ui/core/MenuItem';
import Menu from '@material-ui/core/Menu';
import Button from '@material-ui/core/Button';

class MenuAppBar extends React.Component {
    state = {
      anchorEl: null,
    };
  
    handleMenu = event => {
      this.setState({ anchorEl: event.currentTarget });
    };
  
    handleClose = () => {
      this.setState({ anchorEl: null });
    };

    handleLogout = () => {
      this.setState({ anchorEl: null });
      this.props.onLogout();
    }

    handleAdd = () => {
      this.setState({ anchorEl: null });
      this.props.onAdd();
    }

    render() {

      const { anchorEl } = this.state;
      const open = Boolean(anchorEl);

      const accountButton = 
      <div>
        <IconButton
          aria-owns={open ? 'menu-appbar' : undefined}
          aria-haspopup="true"
          onClick={this.handleAdd}
          color="inherit"
        >
          <AddCircle />
        </IconButton>

        <IconButton
          aria-owns={open ? 'menu-appbar' : undefined}
          aria-haspopup="true"
          onClick={this.handleMenu}
          color="inherit"
        >
          <AccountCircle />
        </IconButton>

        <Menu
          id="menu-appbar"
          anchorEl={anchorEl}
          anchorOrigin={{
            vertical: 'top',
            horizontal: 'right',
          }}
          transformOrigin={{
            vertical: 'top',
            horizontal: 'right',
          }}
          open={open}
          onClose={this.handleClose}
        >
          <MenuItem onClick={this.handleClose}>Profile</MenuItem>
          <MenuItem onClick={this.handleClose}>Friends</MenuItem>
          <MenuItem onClick={this.handleLogout}>Logout</MenuItem>
        </Menu>
      </div>

      const loginButton = 
      <Button color="inherit" onClick={() => this.props.onLogin()}>
        Log in
      </Button>

      let drawerToggle = <div/>;
      if(!this.props.showLogin && this.props.auth) {
        drawerToggle = 
        <IconButton className={classes.MenuButton} color="inherit" aria-label="Menu" onClick={() => this.props.openDrawer()}>
          <MenuIcon />
        </IconButton>
      }

      return (
        <div className={classes.Root}>
          <AppBar position="relative">
            <Toolbar>
              {drawerToggle}
              <Typography variant="h6" color="inherit" className={classes.Grow}>
                {this.props.children}
              </Typography>
              {this.props.auth ? accountButton : loginButton}
            </Toolbar>
          </AppBar>
        </div>
      );
    }
  }
  
  export default MenuAppBar;