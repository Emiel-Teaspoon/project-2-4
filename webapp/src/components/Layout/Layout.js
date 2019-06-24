import React, { Component } from 'react';

import Aux from '../../hoc/Aux';
import classes from './Layout.css';
import Toolbar from '../Navigation/Toolbar/Toolbar';
import CssBaseline from '@material-ui/core/CssBaseline';
import SideDrawer from '../Navigation/SideDrawer/SideDrawer';

class Layout extends Component {
    
    state = {
        showDrawer: false,
    }
    
    drawerClosedHandler = () => {
        this.setState({showDrawer: false})
        console.log("close");
    }

    drawerOpenHandler = () => {
        this.setState({ showDrawer: true })
        console.log("open");
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
                    showLogin={this.props.showLogin}>Event Map</Toolbar>
                    <SideDrawer 
                    open={this.state.showDrawer} 
                    closeDrawer={this.drawerClosedHandler} 
                    auth={this.props.isAuthenticated}/>
                    <main className={classes.Content}>
                        {this.props.children}
                    </main>
                </div>
            </Aux>
        );
    }
}

export default Layout;