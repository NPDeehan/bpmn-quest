window.addEventListener('load', function(evt) {
  'use strict';

  var GAME_ID = '';
  var currentTask = '';

  var addLine = function(text) {
    var line = document.createElement('li');
    line.textContent = text;
    document.getElementById('story').appendChild(line);
  };

  var requestVariables = function() {
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == XMLHttpRequest.DONE ) {
          if(xmlhttp.status == 200){
            var jsonResponse = JSON.parse(xmlhttp.responseText);

            console.log('done loading variables', jsonResponse);

            addLine(jsonResponse.storyText.value);

          } else {
            console.log('error loading variABLES', xmlhttp);
          }
        }
    };

    xmlhttp.open('GET', 'http://ec2-52-19-141-24.eu-west-1.compute.amazonaws.com:8080/engine-rest/process-instance/'+GAME_ID+'/variables/?deserializeValues=false', true);

    xmlhttp.send();

  };

  var requestFirstTask = function() {
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == XMLHttpRequest.DONE ) {
          if(xmlhttp.status == 200){
            console.log('done loading character creation', xmlhttp);
            var jsonResponse = JSON.parse(xmlhttp.responseText);
            currentTask = jsonResponse.id;

            // get the variables

            requestVariables();
          } else {
            console.log('error loading character creation', xmlhttp);
          }
        }
    };

    xmlhttp.open('GET', 'http://ec2-52-19-141-24.eu-west-1.compute.amazonaws.com:8080/engine-rest/task/?processInstanceId='+GAME_ID, true);

    xmlhttp.send();
  };

  var doStartCall = function() {
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == XMLHttpRequest.DONE ) {
          if(xmlhttp.status == 200){
            console.log('done', xmlhttp);
            var jsonResponse = JSON.parse(xmlhttp.responseText);
            GAME_ID = jsonResponse.id;

            requestFirstTask();
          } else {
            console.log('error', xmlhttp);
          }
        }
    };

    xmlhttp.open('POST', 'http://ec2-52-19-141-24.eu-west-1.compute.amazonaws.com:8080/engine-rest/process-definition/key/CharacterCreator/start', true);

    xmlhttp.setRequestHeader('Content-type', 'application/json');

    xmlhttp.send(JSON.stringify({
      variables: {}
    }));
  };

  var hideStartButton = function() {
    document.getElementById('landingPage').style.display = 'none';
  };

  var showGame = function () {
    document.getElementById('gameContainer').style.display = 'inherit';
  };

  document.getElementById('startButton').addEventListener('click', function( evt) {
    console.log('starting game');

    doStartCall();

    hideStartButton();

    showGame();
  });

});
