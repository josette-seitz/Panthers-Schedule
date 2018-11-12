import React, {Component}                      from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
import AppNavbar                               from './AppNavbar';
import {Link}                                  from 'react-router-dom';

class EventList extends Component {

  constructor(props) {
    super(props);
    this.state = {events: [], isLoading: true};
    this.remove = this.remove.bind(this);
  }

  componentDidMount() {
    this.setState({isLoading: true});

    fetch('api/events')
      .then(response => response.json())
      .then(data => this.setState({events: data, isLoading: false}));
  }

  async remove(id) {
    await fetch(`/api/event/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    }).then(() => {
      let updatedEvents = [...this.state.events].filter(i => i.id !== id);
      this.setState({events: updatedEvents});
    });
  }

  render() {
    const {events, isLoading} = this.state;
    const options = {
      hour: 'numeric', minute: 'numeric', hour12: true
    };

    const formatDate = (dateValue) => {
      if (dateValue == null) {
        return '';
      }
      else {
        dateValue = new Date(dateValue);
        return dateValue.toLocaleDateString('en-US', options);
      }
    };

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const eventList = events.map(event => {
      const location = `${event.stadium || ''} in ${event.city || ''}, ${event.state || ''}`;
      return <tr key={event.id}>
        <td style={{whiteSpace: 'nowrap'}}>{event.opponent.name}</td>
        <td>{location}</td>
        <td>{formatDate(event.eventDate)}</td>
        <td>{event.score}</td>
        <td>{event.winner}</td>
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={'/event/' + event.id}>Edit</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(event.id)}>Delete</Button>
          </ButtonGroup>
        </td>
      </tr>;
    });

    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/event/new">Add Group</Button>
          </div>
          <h3>NFL Results</h3>
          <Table className="mt-sm-4">
            <thead>
              <tr>
                <th>Name</th>
                <th>Location</th>
                <th>Date & Time</th>
                <th>Score</th>
                <th>Winner</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {eventList}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default EventList;

