import React from 'react';
import Layout from './components/Layout/Layout';
import EventMap from './containers/EventMap/EventMap'
import './App.css';
import Login from './form/login/Login';
import Cookies from 'universal-cookie';

const cookies = new Cookies();

class App extends React.Component {

  state = {
    auth: false,
    showLogin: false,
    user: [],
  }

  componentDidMount() {
    const autoLogin = true;
    if(autoLogin) {
        if(cookies.get('loggedIn')) {
            const user = {
                userId: cookies.get('userId'), 
                username: cookies.get('username'),
                apiKey: cookies.get('apiKey'),
            }
            this.setState({isAuthenticated: true, user: user});
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
    fetch('https://spicymemes.app/eventmap/public/login/' + params.username + '/' + params.password + '/browser')
        .then(response => response.json())
        .then(data => {
            console.log(data); 
            this.setState({isLoading: false});
            if(data.Code === 200) {
                const user = {
                    userId: data.UserID, 
                    username: data.Username,
                    apiKey: data.APIKey,
                }
                this.setState({isAuthenticated: true, user: user});
                cookies.set('userId', user.userId);
                cookies.set('apiKey', user.apiKey);
                cookies.set('username', user.username);
                console.log(data);
            }
        }).catch(this.setState({isLoading: false}));
  }

  async register (params) {
    fetch('https://spicymemes.app/eventmap/public/user/' + params.username + '/' + params.password + '/' + params.email, {method: 'post'})
    .then(response => response.json())
    .then(data => {
        console.log(data); 
        this.setState({isLoading: false});
        if(data.Code === 200) {
            const user = {
                userId: data.UserID, 
                username: data.Username,
                apiKey: data.APIKey,
            }
            this.setState({isAuthenticated: true, user: user});
            console.log(data);
          }
    }).catch(this.setState({isLoading: false}));
  }

  handleLoginClick = () => {
    this.setState({showLogin: true});
  };

  render() {
    return (
      <div>
        <Layout onLogin={this.handleLoginClick} showLogin={this.state.showLogin}>
          {this.state.showLogin ? <Login username={this.state.username} password={this.state.password} onLogin={this.loginHandler} isLoading={this.state.isLoading} onRegister={this.registerHandler}/> : <EventMap/>}
        </Layout>
      </div>
    )
  }
}

export default App;
