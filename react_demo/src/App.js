import React, {Component}                       from 'react';
import './App.css';
import Home                                     from './Home';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import OpponentList                             from './OpponentList';
import OpponentEdit                             from './OpponentEdit';

class App extends Component {
  render() {
    return (
      <Router>
        <Switch>
          <Route path='/' exact={true} component={Home}/>
          <Route path='/opponents' exact={true} component={OpponentList}/>
          <Route path='/opponent/:id' component={OpponentEdit}/>
        </Switch>
      </Router>
    );
  }
}

export default App;