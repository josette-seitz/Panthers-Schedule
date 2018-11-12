import React, {Component}  from 'react';
import './App.css';
import AppNavbar           from './AppNavbar';
import {Button, Container} from 'reactstrap';
import {Link}              from 'react-router-dom';
import panthers            from './panthers.png';


class Home extends Component {
  state = {
    events: []
  };

  componentDidMount() {

    fetch('api/events')
      .then(response => response.json())
      .then(data => this.setState({events: data}));
  }

  render() {
    const {events} = this.state;

    return (
      <div>
        <AppNavbar/>
        <div className="App">
          <header className="App-header-panthers">
            <img src={panthers} className="App-logo" alt="logo"/>
            <h1 className="App-title">Welcome to the 2018-2019 Season</h1>
          </header>
          <Container fluid>
            <Button outline color="primary" tag={Link} to={'/events'}>
              Carolina Panthers Schedule
            </Button>
          </Container>
          <br/>
          <div className="App-intro">
            <h2>Regular Season Opponents</h2>
            {events.map(event =>
              <div key={event.id}>
                {event.opponent.name} at {event.stadium}
              </div>
            )}
          </div>
        </div>
      </div>
    );
  }
}

export default Home;