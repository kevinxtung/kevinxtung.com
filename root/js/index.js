function tick() {
  const element = (
    <p>The time is {new Date().toLocaleTimeString()}.</p>
  );
  ReactDOM.render(element, document.getElementById("root"));
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
tick();
setInterval(tick, 1000);
