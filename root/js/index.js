class Clock extends React.Component {
  constructor(props) {
    super(props);
    this.state = {date: new Date()};
  }

  componentDidMount() {
    this.timerID = setInterval(
      () => this.tick(),
      1000
    );
  }

  componentWillUnmount() {
    clearInterval(this.timerID);
  }

  tick() {
    this.setState({
      date: new Date()
    });
  }

  render() {
    return (
      <div>
        <p>The time is {this.state.date.toLocaleTimeString()}.</p>
      </div>
    );
  }
}

function checkForSavedTheme() {
  if (document.cookie.indexOf('theme=') != -1) {
    setTheme(getCookieValue("theme"));
  }
  else {
    setTheme('light');
  }
}

function setTheme(theme) {
  document.cookie = "theme=" + theme;
  document.getElementById("css_theme").setAttribute("href", "/css/" + theme + ".css");
}

function getCookieValue(name) {
  function escape(s) { return s.replace(/([.*+?\^${}()|\[\]\/\\])/g, '\\$1'); };
  var match = document.cookie.match(RegExp('(?:^|;\\s*)' + escape(name) + '=([^;]*)'));
  return match ? match[1] : null;
}

checkForSavedTheme();
ReactDOM.render(<Clock />, document.getElementById('root'));
