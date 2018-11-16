import React, {Component}                                 from 'react';
import {Link, withRouter}                                 from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import AppNavbar                                          from './AppNavbar';

class EventEdit extends Component {

  emptyItem = {
    name: '',
    stadium: '',
    city: '',
    state: '',
    eventDate: '',
    score: '',
    winner: ''
  };

  constructor(props) {
    super(props);
    this.state = {
      item: this.emptyItem, isHidden: true
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  async componentDidMount() {
    if (this.props.match.params.id !== 'new') {
      //GET /event/{id} json
      const event = await (await fetch(`/api/event/${this.props.match.params.id}`)).json();
      this.setState({item: event, isHidden: false});
    }
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = {...this.state.item};
    item[name] = value;
    this.setState({item});
  }

  async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;

    //On Submit- PUT /event/{id} OR POST /event
    await fetch('/api/event', {
      method: (item.id) ? 'PUT' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item)

    });

    this.props.history.push('/events');
  }

  render() {
    const item = this.state.item;
    const title = <h2>{item.id ? 'Edit Game' : 'Add Game'}</h2>;
    const hide = this.state.isHidden ? {display: 'none'} : {};

    return <div>
      <AppNavbar/>
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
          <FormGroup>
            <Label for="name">Opponent Name</Label>
            <Input type="text" name="name" id="name" value={item.name || ''}
                   onChange={this.handleChange} autoComplete="name"/>
          </FormGroup>
          <FormGroup>
            <Label for="stadium">Stadium</Label>
            <Input type="text" name="stadium" id="stadium" value={item.stadium || ''}
                   onChange={this.handleChange} autoComplete="stadium"/>
          </FormGroup>
          <div className="row">
            <FormGroup className="col-md-4 mb-3">
              <Label for="city">City</Label>
              <Input type="text" name="city" id="city" value={item.city || ''}
                     onChange={this.handleChange} autoComplete="city"/>
            </FormGroup>
            <FormGroup className="col-md-4 mb-3">
              <Label for="state">State</Label>
              <Input type="text" name="state" id="state" value={item.state || ''}
                     onChange={this.handleChange} autoComplete="state"/>
            </FormGroup>
            <FormGroup className="col-md-4 mb-3">
              <Label for="eventDate">Date & Time</Label>
              <Input type="datetime-local" name="eventDate" id="eventDate" value={item.eventDate || ''}
                     onChange={this.handleChange} autoComplete="eventDate"/>
            </FormGroup>
            <FormGroup className="col-md-4 mb-3" style={hide}>
              <Label for="score">Score</Label>
              <Input type="text" name="score" id="score" value={item.score || ''}
                     onChange={this.handleChange} autoComplete="score"/>
            </FormGroup>
            <FormGroup className="col-md-4 mb-3" style={hide}>
              <Label for="winner">Winner</Label>
              <Input type="text" name="winner" id="winner" value={item.winner || ''}
                     onChange={this.handleChange} autoComplete="score"/>
            </FormGroup>
          </div>
          <FormGroup>
            <Button color="primary" type="submit">Save</Button>{' '}
            <Button color="secondary" tag={Link} to="/events">Cancel</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>;
  }
}

export default withRouter(EventEdit);