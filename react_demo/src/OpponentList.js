import React, {Component}                      from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
import AppNavbar                               from './AppNavbar';
import {Link}                                  from 'react-router-dom';

class OpponentList extends Component {

  constructor(props) {
    super(props);
    this.state = {opponents: [], isLoading: true};
    this.remove = this.remove.bind(this);
  }

  componentDidMount() {
    this.setState({isLoading: true});

    fetch('api/opponents')
      .then(response => response.json())
      .then(data => this.setState({opponents: data, isLoading: false}));
  }

  async remove(id) {
    await fetch(`/api/opponent/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    }).then(() => {
      let updatedOpponents = [...this.state.opponents].filter(i => i.id !== id);
      this.setState({groups: updatedOpponents});
    });
  }

  render() {
    const {opponents, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const opponentList = opponents.map(opponent => {
      const location = `${opponent.stadium || ''} in ${opponent.city || ''}, ${opponent.state || ''}`;
      return <tr key={opponent.id}>
        <td style={{whiteSpace: 'nowrap'}}>{opponent.name}</td>
        <td>{location}</td>
        <td>{opponent.event.date}</td>
        <td>{opponent.event.score}</td>
        <td>{opponent.event.winner}</td>
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={'/opponents/' + opponent.id}>Edit</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(opponent.id)}>Delete</Button>
          </ButtonGroup>
        </td>
      </tr>;
    });

    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/opponent/new">Add Group</Button>
          </div>
          <h3>NFL Results</h3>
          <Table className="mt-sm-4">
            <thead>
              <tr>
                <th>Name</th>
                <th>Location</th>
                <th>Time</th>
                <th>Score</th>
                <th>Winner</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {opponentList}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default OpponentList;

