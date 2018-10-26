import React, {Component}                                 from 'react';
import {Link, withRouter}                                 from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import AppNavbar                                          from './AppNavbar';

class OpponentEdit extends Component {

  emptyItem = {
    name: '',
    stadium: '',
    city: '',
    state: ''
  };

  constructor(props) {
    super(props);
    this.state = {
      item: this.emptyItem
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  async componentDidMount() {
    if (this.props.match.params.id !== 'new') {
      const opponent = await (await fetch(`/api/opponent/${this.props.match.params.id}`)).json();
      this.setState({item: opponent});
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

    await fetch('/api/opponent', {
      method: (item.id) ? 'PUT' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item)
    });
    this.props.history.push('/opponents');
  }

  render() {
    const {item} = this.state;
    const title = <h2>{item.id ? 'Edit Record' : 'Add Record'}</h2>;

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
                   onChange={this.handleChange} autoComplete="stadium-level1"/>
          </FormGroup>
          <div className="row">
            <FormGroup className="col-md-4 mb-3">
              <Label for="city">City</Label>
              <Input type="text" name="city" id="city" value={item.city || ''}
                     onChange={this.handleChange} autoComplete="stadium-level1"/>
            </FormGroup>
            <FormGroup className="col-md-4 mb-3">
              <Label for="state">State</Label>
              <Input type="text" name="state" id="state" value={item.state || ''}
                     onChange={this.handleChange} autoComplete="address-level1"/>
            </FormGroup>
          </div>
          <FormGroup>
            <Button color="primary" type="submit">Save</Button>{' '}
            <Button color="secondary" tag={Link} to="/opponents">Cancel</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>;
  }
}

export default withRouter(OpponentEdit);