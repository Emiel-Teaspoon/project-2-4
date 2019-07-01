import React from 'react';
import Layout from './components/Layout/Layout';
import EventMap from './containers/EventMap/EventMap'
import './App.css';
import Login from './form/login/Login';
import Cookies from 'universal-cookie';

import axios from 'axios';
import { Modal } from '@material-ui/core';
import Aux from './hoc/Aux';
import EventMaker from './components/Event/EventMaker/EventMaker';

const cookies = new Cookies();

class App extends React.Component {

  state = {
    isAuthenticated: false,
    showLogin: false,
    isError: false,
    isUserError: false,
    logError: "",
    createEvent: false,
    user: [],
  }

  componentWillMount() {
    const autoLogin = true;
    if(autoLogin) {
      if(parseInt(cookies.get('loggedIn')) === 1) {
          const user = {
              userId: cookies.get('userId'), 
              username: cookies.get('username'),
              apiKey: cookies.get('apiKey'),
              token: cookies.get('token'),
          }
        this.setState({isLoading: false, isAuthenticated: true, user: user, showLogin:false, isError:false, logError:""});
      }
      else {
        this.setState({isLoading: false, isAuthenticated: false, user: []});
      }
    }
  }

  loginHandler = (event, details) => {
    event.preventDefault();
    this.setState({isLoading: true});
    if(this.state.isLoading) {
      return;
    }
    else {
      this.login(details);
    }
  }

  registerHandler = (event, details) => {
    event.preventDefault();
    this.setState({isLoading: true});
    if(this.state.isLoading) {
        return;
    }
    else {
        this.register(details);
    }
  }

  async login (params) {
    axios.post('https://spicymemes.app/eventmap/public/login', {
        username: params.username,
        password: params.password
    })
    .then(res => {
      const data = res.data;
      if(data.Code === 200) {
        const user = {
          userId: data.UserID, 
          username: data.Username,
          apiKey: data.APIKey,
          token: data.token,
        }
        cookies.set('userId', user.userId);
        cookies.set('apiKey', user.apiKey);
        cookies.set('username', user.username);
        cookies.set('token', data.token);
        cookies.set('loggedIn', 1);
        this.setState({isLoading: false, token: data.token, isAuthenticated: true, user: user, showLogin:false, isError:false, logError:""});
      }
      else {
        this.setState({isLoading: false, isError:true, isUserError:false, logError:data.Message});
        console.log(res);
      }
    })
    .catch(
      err => {
        console.log(err);
        console.log(err.response);
        this.setState({isLoading: false, isError:true, isUserError:true, logError:"Error " + err.response.status + " " + err.response.data.message});
      }
    );
  }

  async register (params) {
    axios.post('https://spicymemes.app/eventmap/public/user', {
      username: params.username,
      password: params.password,
      email: params.email
    })
    .then(res => {
      const data = res.data;
      if(data.Code === 200) {
        const user = {
          userId: data.UserID, 
          username: data.Username,
          apiKey: data.APIKey,
          token: data.token,
        }
        this.setState({isLoading: false, isAuthenticated: true, user: user, showLogin:false, isError:false, logError:""});
        cookies.set('userId', user.userId);
        cookies.set('apiKey', user.apiKey);
        cookies.set('username', user.username);
        cookies.set('token', data.token);
        cookies.set('loggedIn', 1);
      }
      else {
        this.setState({isLoading: false, isUserError:true, isError:false, logError:data.Message});
      }
    });
  }

  handleAddClick = () => {
    const createEvent = !this.state.createEvent;
    this.setState({createEvent: createEvent});
  }

  handleLoginClick = () => {
    this.setState({showLogin: true});
  };

  handleLogoutClick = () => {
    this.setState({isLoading: false, isAuthenticated: false, user: []});
    cookies.set('userId', "");
    cookies.set('apiKey', "");
    cookies.set('username', "");
    cookies.set('loggedIn', 0);
    cookies.set('token', "");
  };

  handleFriendsOnly = (value) => {
    this.setState({onlyFriends: value});
  }

  handleClose = () => {
    this.setState({createEvent: false});
  };

  render() {

    return (
      <Aux>
        <Modal open={this.state.createEvent} onClose={this.handleClose}><EventMaker userID={this.state.user.userId} token={this.state.user.token} apiKey={this.state.user.apiKey} closeHandler={this.handleClose}/></Modal>
        <Layout onLogin={this.handleLoginClick} onLogout={this.handleLogoutClick} showLogin={this.state.showLogin} isAuthenticated={this.state.isAuthenticated} onAdd={this.handleAddClick} user={this.state.user} toggleHandler={this.handleFriendsOnly}>
          {this.state.showLogin ? 
          <Login username={this.state.username} 
            password={this.state.password} 
            onLogin={this.loginHandler} 
            isLoading={this.state.isLoading} 
            onRegister={this.registerHandler} 
            isError={this.state.isError} 
            isUserError={this.state.isUserError} 
            logError={this.state.logError}/> : 
            <EventMap user={this.state.user} onlyFriends={this.state.onlyFriends}/>
          }
        </Layout>
      </Aux>
    )
  }
}

export default App;
