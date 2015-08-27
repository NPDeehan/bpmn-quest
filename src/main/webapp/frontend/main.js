window.addEventListener('load', function(evt) {
  'use strict';

  var BpmnViewer = window.BpmnJS;

  var viewer = new BpmnViewer({ container: '#map' });

  function loadDiagram() {

    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == XMLHttpRequest.DONE ) {
          var jsonResponse = JSON.parse(xmlhttp.responseText);
          var diagramXML = jsonResponse.bpmn20Xml;
          importDiagram(diagramXML);
        }
    };

    xmlhttp.open('GET', 'http://ec2-52-19-141-24.eu-west-1.compute.amazonaws.com:8080/engine-rest/process-definition/key/adventure/xml', true);

    xmlhttp.setRequestHeader('Content-type', 'application/json');

    xmlhttp.send();

    function importDiagram(diagram) {
      viewer.importXML(diagram, function(err) {
        if (!err) {
          console.log('diagram loaded!');
          viewer.get('dungeon').start();
        } else {
          console.log('something went wrong:', err);
        }
      });
    }
  }


  var useAlternativeLandingPage = Math.random() < .05;
  if(useAlternativeLandingPage) {
    document.getElementById('landingPage').style.backgroundImage = "url('resources/alternativeLandingPageDark.jpg')";
    document.getElementById('gameContainer').style.backgroundImage = "url('resources/alternativeLandingPageDark.jpg')";
  } else {
    document.getElementById('landingPage').style.backgroundImage = "url('resources/landingPageBackgroundDark.jpg')";
    document.getElementById('gameContainer').style.backgroundImage = "url('resources/landingPageBackgroundDark.jpg')";
  }

  // replace bpmnio logo
  var bjsContainer =document.getElementsByClassName('bjs-powered-by')[0];
  bjsContainer.innerHTML = '<img src="resources/bpmn_io_logo.png" />';



  var GAME_ID = '';
  var currentTask = '';
  var currentPIID = '';

  var addStory = function(storyObject) {
    storyObject = JSON.parse(storyObject);
    console.log('will add story', storyObject);

    // HEAD
    if(storyObject.title && storyObject.title !== '') {
      var line = document.createElement('li');
      var elem = document.createElement('h2');
      elem.textContent = storyObject.title;
      line.appendChild(elem);
      document.getElementById('story').appendChild(line);
    }

    // Description
    if(storyObject.description && storyObject.description !== '') {
      var line = document.createElement('li');
      var elem = document.createElement('span');
      elem.textContent = storyObject.description;
      line.appendChild(elem);
      document.getElementById('story').appendChild(line);
    }

    // IMAGE
    if(storyObject.picture && storyObject.picture !== '') {
      var line = document.createElement('li');
      var elem = document.createElement('img');
      elem.setAttribute('src', storyObject.picture);
      line.appendChild(elem);
      line.style.textAlign = 'center';
      document.getElementById('story').appendChild(line);
    }

    // Decisions
    // clear the button container
    document.getElementById('buttonContainer').innerHTML = '';
    for(var i = 0; i < storyObject.options.length; i++) {
      var opt = storyObject.options[i];
      var btn = document.createElement('button');
      btn.textContent = opt;
      (function(opt) {
        btn.addEventListener('click', function() {
          completeStep(opt);
        });
      })(opt);
      document.getElementById('buttonContainer').appendChild(btn);
    }

    // need to recalculate the height of the story container because the decisions can be huge
  };

  var createInputs = function(inputFields, variables) {
    var table = document.createElement('table');
    for(var path in inputFields) {
      if (inputFields.hasOwnProperty(path)) {
        var pathParts = path.split('.');
        var value = variables;
        for(var j = 0; j < pathParts.length; j++) {
          value = value[pathParts[j]];
          if(!value) break;
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
        if(value) {
          input.value = value;
        }
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
  var completeStep = function(decision) {

    console.log('you decided for', decision);

    var inputFields = CURRENT_INPUTS;
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

      // set the player name and display the player character
      document.getElementById('playerName').textContent = val.characterName;
      document.getElementById('playerData').style.display = 'initial';

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
          playerCharacter: payload,
          editableFields: {
            value: '{}',
            type: 'String',
            valueInfo: {}
          },
          storyText: {
            value: '',
            type: 'String',
            valueInfo: {}
          }
        }
      }));
    } else {
      var val = {};
      for(var key in inputFields.inputMap) {
        var name = key;
        val[name] = {value: inputFields.inputMap[key].value, type:'String' };
      }
      val.editableFields = {
        value: '{}',
        type: 'String',
        valueInfo: {}
      };
      val.storyText = {
            value: '',
            type: 'String',
            valueInfo: {}
          };
      val.decision = {
        value: decision,
        type: 'String',
        valueInfo: {}
      };
      //payload.value = JSON.stringify(val);

      var xmlhttp = new XMLHttpRequest();

      xmlhttp.onreadystatechange = function() {
          if (xmlhttp.readyState == XMLHttpRequest.DONE ) {
            requestFirstTask();
          }
      };

      xmlhttp.open('POST', 'http://ec2-52-19-141-24.eu-west-1.compute.amazonaws.com:8080/engine-rest/task/'+currentTask+'/complete', true);

      xmlhttp.setRequestHeader('Content-type', 'application/json');

      xmlhttp.send(JSON.stringify({
        variables: val
      }));
    }

  };

  var CURRENT_INPUTS;

  var requestVariables = function() {
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == XMLHttpRequest.DONE ) {
          if(xmlhttp.status == 200){
            var jsonResponse = JSON.parse(xmlhttp.responseText);

            console.log('done loading variables', jsonResponse);


            addStory(jsonResponse.storyText.value);

            CURRENT_INPUTS = createInputs(JSON.parse(jsonResponse.editableFields.value), jsonResponse);

            var heightBefore = document.getElementById('story').scrollHeight;
            document.getElementById('story').scrollTop = heightBefore;

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
            loadDiagram();
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
