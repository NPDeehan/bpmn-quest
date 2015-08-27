window.addEventListener('load', function(evt) {
  'use strict';

  var useAlternativeLandingPage = Math.random() < .05;
  if(useAlternativeLandingPage) {
    document.getElementById('landingPage').style.backgroundImage = "url('resources/alternativeLandingPageDark.jpg')";
    document.getElementById('gameContainer').style.backgroundImage = "url('resources/alternativeLandingPageDark.jpg')";
  } else {
    document.getElementById('landingPage').style.backgroundImage = "url('resources/landingPageBackgroundDark.jpg')";
    document.getElementById('gameContainer').style.backgroundImage = "url('resources/landingPageBackgroundDark.jpg')";
  }


  var GAME_ID = '';
  var currentTask = '';
  var currentPIID = '';

  var addLine = function(text) {
    var line = document.createElement('li');
    line.textContent = text;
    document.getElementById('story').appendChild(line);
  };

  var createInputs = function(inputFields, variables) {
    var table = document.createElement('table');
    for(var path in inputFields) {
      if (inputFields.hasOwnProperty(path)) {
        var pathParts = path.split('.');
        var value = variables;
        for(var j = 0; j < pathParts.length; j++) {
          value = value[pathParts[j]];
          if(j === 0) {
            value = JSON.parse(value.value);
          }
        }

        var row = document.createElement('tr');
        var label = document.createElement('td');
        label.textContent = inputFields[path];
        row.appendChild(label);

        var cell2 = document.createElement('td');
        var input = document.createElement('input');
        input.setAttribute('type', 'text');
        input.value = value;
        cell2.appendChild(input);
        row.appendChild(cell2);

        table.appendChild(row);

        inputFields.inputMap = inputFields.inputMap || {};

        inputFields.inputMap[path] = input;
      }
    }
    var listElement = document.createElement('li');
    listElement.appendChild(table);

    document.getElementById('story').appendChild(listElement);
    return inputFields;
  };

  // HACK HACK HACK
  var creationCompleted = false;
  var completeStep = function(inputFields) {

    if(!creationCompleted) {
      creationCompleted = true;
      var payload = {
        "value" : "{\"characterName\":\"Hero!\",\"id\":null,\"lifePoints\":1,\"strength\":50,\"perception\":50,\"endurance\":50,\"charisma\":50,\"inteligance\":50,\"agility\":50,\"luck\":50}",
        "type" : "Object",
        "valueInfo" : {
          "objectTypeName": "org.camunda.bpmn.quest.CharacterCreator.CharacterModel",
          "serializationDataFormat": "application/json"
        }
      }
      var val = JSON.parse(payload.value);
      for(var key in inputFields.inputMap) {
        var name = key.substr(key.indexOf('.')+1);
        val[name] = inputFields.inputMap[key].value;
      }
      payload.value = JSON.stringify(val);

      var xmlhttp = new XMLHttpRequest();

      xmlhttp.onreadystatechange = function() {
          if (xmlhttp.readyState == XMLHttpRequest.DONE ) {
            requestFirstTask();
          }
      };

      xmlhttp.open('POST', 'http://ec2-52-19-141-24.eu-west-1.compute.amazonaws.com:8080/engine-rest/task/'+currentTask+'/complete', true);

      xmlhttp.setRequestHeader('Content-type', 'application/json');

      xmlhttp.send(JSON.stringify({
        variables: {
          playerCharacter: payload
        }
      }));
    }

  };

  var addSubmitButton = function(inputFields) {
    var row = document.createElement('li');
    var button = document.createElement('button');
    button.textContent = 'continue';
    row.appendChild(button);

    button.addEventListener('click', function() {
      completeStep(inputFields);
    });

    document.getElementById('story').appendChild(row);
  };

  var requestVariables = function() {
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == XMLHttpRequest.DONE ) {
          if(xmlhttp.status == 200){
            var jsonResponse = JSON.parse(xmlhttp.responseText);

            console.log('done loading variables', jsonResponse);

            addLine(jsonResponse.storyText.value);

            //TODO: do not use split
            var inputFields = createInputs(JSON.parse(jsonResponse.editableFields.value), jsonResponse);

            addSubmitButton(inputFields);

          } else {
            console.log('error loading variABLES', xmlhttp);
          }
        }
    };

    xmlhttp.open('GET', 'http://ec2-52-19-141-24.eu-west-1.compute.amazonaws.com:8080/engine-rest/process-instance/'+currentPIID+'/variables/?deserializeValues=false', true);

    xmlhttp.send();

  };

  var requestFirstTask = function() {
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == XMLHttpRequest.DONE ) {
          if(xmlhttp.status == 200){
            console.log('done loading character creation', xmlhttp);
            var jsonResponse = JSON.parse(xmlhttp.responseText);
            console.log('current task is', jsonResponse);
            currentTask = jsonResponse[0].id;
            currentPIID = jsonResponse[0].processInstanceId;

            // get the variables

            requestVariables();
          } else {
            console.log('error loading character creation', xmlhttp);
          }
        }
    };

    xmlhttp.open('GET', 'http://ec2-52-19-141-24.eu-west-1.compute.amazonaws.com:8080/engine-rest/task/?processInstanceBusinessKey='+GAME_ID, true);

    xmlhttp.send();
  };

  var doStartCall = function() {
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == XMLHttpRequest.DONE ) {
          if(xmlhttp.status == 200){
            console.log('done', xmlhttp);

            requestFirstTask();
          } else {
            console.log('error', xmlhttp);
          }
        }
    };

    xmlhttp.open('POST', 'http://ec2-52-19-141-24.eu-west-1.compute.amazonaws.com:8080/engine-rest/process-definition/key/adventure/start', true);

    xmlhttp.setRequestHeader('Content-type', 'application/json');

    GAME_ID = Math.random().toString(36).substr(2);
    xmlhttp.send(JSON.stringify({
      variables: {},
      businessKey: GAME_ID
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
