import React from 'react';
import Layout from './components/Layout/Layout';
import EventMap from './containers/EventMap/EventMap'
import './App.css';

function App() {
  return (
    <div>
      <Layout auth>
        <EventMap/>
      </Layout>
    </div>
  );
}

export default App;
