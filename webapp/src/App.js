import React from 'react';
import Layout from './components/Layout/Layout';
import EventMap from './containers/EventMap/EventMap'
import './App.css';

class App extends React.Component {

  state = {
    auth: false,
    showLogin: false,
  }

  handleLoginClick = () => {
    
  };

  render() {
    return (
      <div>
        <Layout>
          <EventMap/>
        </Layout>
      </div>
    )
  }
}

export default App;
