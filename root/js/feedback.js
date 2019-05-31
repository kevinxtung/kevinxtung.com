class FeedbackForm extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <form action="/api/feedback" method="POST">
        Name: <input type="text" name="name"></input><br />
        Email: <input type="email" name="email"></input><br />
        Feedback: <textarea rows="10" cols="30" name="feedback"></textarea><br />
        <input type="submit" value="Submit"></input><br />
      </form>
    );
  }
}

class FeedbackButton extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    if (!this.props.showForm) {
      return (
        <button onClick={this.props.handleClick}>Hide Feedback Form</button>
      );
    }
    else {
      return (
        <button onClick={this.props.handleClick}>Show Feedback Form</button>

      );
    }
  }
}

class FeedbackRoot extends React.Component {
    constructor(props) {
      super(props);
      this.state = {showForm: true};
      this.handleClick = this.handleClick.bind(this);      
    }

    handleClick() {
      this.setState(prevState => ({
        showForm: !prevState.showForm
      }));
    }    
  
    render() {
      if (this.state.showForm)
        return <FeedbackButton handleClick = {this.handleClick} showForm = {this.state.showForm} />
      else
        return (
          <div>
            <FeedbackButton handleClick = {this.handleClick} showForm = {this.state.showForm} />
            <FeedbackForm />
          </div>
        );
    }
  }
  
  ReactDOM.render(
    <FeedbackRoot />,
    document.getElementById('feedback')
  );