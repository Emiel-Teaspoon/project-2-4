import React, { Component } from 'react';

import Aux from '../../hoc/Aux';
import classes from './Layout.css';
import Toolbar from '../Navigation/Toolbar/Toolbar';
import CssBaseline from '@material-ui/core/CssBaseline';
import SideDrawer from '../Navigation/SideDrawer/SideDrawer';
import Add from '@material-ui/icons/Add';
import { Fab, Snackbar, SnackbarContent } from '@material-ui/core';

class Layout extends Component {
    
    constructor(props) {
        super(props);
        this.state = {
            showDrawer: false,
            showSnackbar: false,
            isAuthenticated: false,
        }
    
        this.handleClose = this.handleClose.bind(this);
    }

    

    drawerClosedHandler = () => {
        this.setState({showDrawer: false})
    }

    drawerOpenHandler = () => {
        this.setState({ showDrawer: true })
    }
    
    openSnackbar() {
        this.setState({showSnackbar: true});
    }

    handleClose(event, reason) {
        if (reason === 'clickaway') {
          return;
        }
    
        this.setState({showSnackbar: false});
    }

    componentDidUpdate() {
        if(this.state.isAuthenticated !== this.props.isAuthenticated){
            this.setState({isAuthenticated: this.props.isAuthenticated});
            if(this.props.isAuthenticated === false) {
                this.openSnackbar();
                console.log("asdfsadfsdaf");
            }
        }
    }

    render () {
        return (
            <Aux>
                <div className={classes.Container}>
                    <CssBaseline />
                    <Toolbar 
                    openDrawer={this.drawerOpenHandler} 
                    className={classes.Header} 
                    auth={this.props.isAuthenticated} 
                    onLogout={this.props.onLogout} 
                    onLogin={this.props.onLogin} 
                    onAdd={this.props.onAdd}
                    showLogin={this.props.showLogin}>Event Map</Toolbar>
                    <SideDrawer 
                    open={this.state.showDrawer} 
                    closeDrawer={this.drawerClosedHandler} 
                    auth={this.props.isAuthenticated}/>
                    <div className={classes.Fab} hidden={!this.props.isAuthenticated}>
                        <Fab color="secondary" onClick={() => this.props.onAdd()}>
                            <Add/>
                        </Fab>
                    </div>
                    <Snackbar
                    anchorOrigin={{
                        vertical: 'bottom',
                        horizontal: 'left',
                    }}
                    open={this.state.showSnackbar}
                    autoHideDuration={2000}
                    onClose={this.handleClose}> 
                        <SnackbarContent message="Logged out succesfully!" onClose={this.handleClose}/>
                    </Snackbar>
                    <main className={classes.Content}>
                        {this.props.children}
                    </main>
                </div>
            </Aux>
        );
    }
}

export default Layout;