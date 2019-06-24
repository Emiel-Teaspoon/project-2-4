import React from 'react';
import Layout from './components/Layout/Layout';
import EventMap from './containers/EventMap/EventMap'
import './App.css';
import Login from './form/login/Login';
import Cookies from 'universal-cookie';

import axios from 'axios';

const cookies = new Cookies();

class App extends React.Component {

  state = {
    isAuthenticated: false,
    showLogin: false,
    isError: false,
    logError: "",
    user: [],
  }

  componentDidMount() {
    const autoLogin = true;
    if(autoLogin) {
      if(cookies.get('loggedIn') === 1) {
          const user = {
              userId: cookies.get('userId'), 
              username: cookies.get('username'),
              apiKey: cookies.get('apiKey'),
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
    axios.get('https://spicymemes.app/eventmap/public/login/' + params.username + '/' + params.password)
    .then(res => {
      const data = res.data;
      if(data.Code === 200) {
        const user = {
          userId: data.UserID, 
          username: data.Username,
          apiKey: data.APIKey,
        }
        this.setState({isLoading: false, isAuthenticated: true, user: user, showLogin:false, isError:false, logError:""});
        cookies.set('userId', user.userId);
        cookies.set('apiKey', user.apiKey);
        cookies.set('username', user.username);
        cookies.set('loggedIn', 1);
      }
      else {
        this.setState({isLoading: false, isError:true, logError:data.Message});
      }
    });
  }

  async register (params) {
    axios.post('https://spicymemes.app/eventmap/public/user/' + params.username + '/' + params.password + '/' + params.email)
    .then(res => {
      const data = res.data;
      if(data.Code === 200) {
        const user = {
          userId: data.UserID, 
          username: data.Username,
          apiKey: data.APIKey,
        }
        this.setState({isLoading: false, isAuthenticated: true, user: user, showLogin:false, isError:false, logError:""});
        cookies.set('userId', user.userId);
        cookies.set('apiKey', user.apiKey);
        cookies.set('username', user.username);
        cookies.set('loggedIn', 1);
      }
      else {
        this.setState({isLoading: false, isError:true, logError:data.Message});
      }
    });
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
  };

  render() {
    return (
      <div>
        <Layout onLogin={this.handleLoginClick} onLogout={this.handleLogoutClick} showLogin={this.state.showLogin} isAuthenticated={this.state.isAuthenticated}>
          {this.state.showLogin ? 
          <Login username={this.state.username} 
            password={this.state.password} 
            onLogin={this.loginHandler} 
            isLoading={this.state.isLoading} 
            onRegister={this.registerHandler} 
            isError={this.state.isError} 
            logError={this.state.logError}/> : <EventMap/>}
        </Layout>
      </div>
    )
  }
}

export default App;
