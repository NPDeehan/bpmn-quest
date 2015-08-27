window.addEventListener('load', function(evt) {
  'use strict';

  var BpmnViewer = window.BpmnJS;

  var viewer = new BpmnViewer({ container: '#map' });

  //var serverUrl = "http://ec2-52-19-141-24.eu-west-1.compute.amazonaws.com:8080";
  var serverUrl = "http://localhost:8080";

  var MAIN_PROCESS_INSTANCE_ID;

  function loadDiagram() {

    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == XMLHttpRequest.DONE ) {
          var jsonResponse = JSON.parse(xmlhttp.responseText);
          var diagramXML = jsonResponse.bpmn20Xml;
          importDiagram(diagramXML);
          requestFirstTask();
        }
    };

    xmlhttp.open('GET', serverUrl + '/engine-rest/process-definition/key/adventure/xml', true);

    xmlhttp.setRequestHeader('Content-type', 'application/json');

    xmlhttp.send();

    function importDiagram(diagram) {
      console.log('importing dungeon map');
      viewer.importXML(diagram, function(err) {
        if (!err) {
          console.log('dungeon map loaded!');
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

  var playerName = '';
  var enemyName = '';
  var playerHealth = 100;
  var playerMaxHealth = 100;
  var enemyHealth = 100;
  var enemyMaxHealth = 100;

  var updateHealthDisplay = function() {
    document.getElementById('lifePoints').setAttribute('max', playerMaxHealth);
    document.getElementById('lifePoints').setAttribute('value', playerHealth);

    if(enemyName !== '') {
      document.getElementById('enemyName').textContent = enemyName;
      document.getElementById('enemyLifePoints').setAttribute('max', enemyMaxHealth);
      document.getElementById('enemyLifePoints').setAttribute('value', enemyHealth);

      document.getElementById('enemyData').style.display = 'initial';

    } else {
      document.getElementById('enemyData').style.display = 'none';
    }
  };

  var addStory = function(storyObject, callback) {
    var fightSpeed = 750;
    storyObject = JSON.parse(storyObject);
    console.log('will add story', storyObject);

    // fight log
    for(var i = 0; i < storyObject.fightLog.length; i++) {
      (function(i) {
        window.setTimeout(function() {
          var line = document.createElement('li');
          var elem = document.createElement('span');
          elem.textContent = storyObject.fightLog[i];
          line.appendChild(elem);
          document.getElementById('story').appendChild(line);

          var heightBefore = document.getElementById('story').scrollHeight;
          document.getElementById('story').scrollTop = heightBefore;

          // parse text to get the current lifePoints
          if(storyObject.fightLog[i].indexOf('attacks '+playerName) !== -1) {
            var lpsegment = storyObject.fightLog[i].substr(storyObject.fightLog[i].indexOf('leaving ') + 8);
            playerHealth = parseInt(lpsegment, 10);
            updateHealthDisplay();
          } else {
            var lpsegment = storyObject.fightLog[i].substr(storyObject.fightLog[i].indexOf('leaving ') + 8);
            enemyHealth = parseInt(lpsegment, 10);
            updateHealthDisplay();
          }

        }, i * fightSpeed);
      })(i);
    }

    window.setTimeout(function() {

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
      elem.innerHTML = storyObject.description;
      line.appendChild(elem);
      document.getElementById('story').appendChild(line);
    }

    // IMAGE
    if(storyObject.picture && storyObject.picture !== '') {
      var line = document.createElement('li');
      var elem = document.createElement('img');
      elem.setAttribute('src', storyObject.picture);
      elem.addEventListener('load', function() {
          var heightBefore = document.getElementById('story').scrollHeight;
          document.getElementById('story').scrollTop = heightBefore;
      });
      line.appendChild(elem);
      line.style.textAlign = 'center';
      document.getElementById('story').appendChild(line);
    }

    // Decisions
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
    var heightBefore = document.getElementById('story').scrollHeight;
          document.getElementById('story').scrollTop = heightBefore;

    callback();

    }, storyObject.fightLog.length * fightSpeed + (storyObject.fightLog.length ? fightSpeed : 0));

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

  var updateMaxPlayerHealth = false;

  // HACK HACK HACK
  var creationCompleted = false;
  var completeStep = function(decision) {

    console.log('you decided for', decision);

    var inputFields = CURRENT_INPUTS;
    if(!creationCompleted) {

      var payload = {
        "value" : "{}",
        "type" : "Object",
        "valueInfo" : {
          "objectTypeName": "org.camunda.bpmn.quest.CharacterCreator.CharacterModel",
          "serializationDataFormat": "application/json"
        }
      }

      var counter = 0;
      var val = JSON.parse(payload.value);
      for(var key in inputFields.inputMap) {
        var name = key.substr(key.indexOf('.')+1);
        if(name !== 'characterName') {
          val[name] = parseInt(inputFields.inputMap[key].value,10);
          if(val[name] < 0 || !val[name]) {
            window.alert('You have to assign a positive value for ' + name);
            return;
          }
          counter += val[name];
        } else {
          val[name] = inputFields.inputMap[key].value
        }
      }
      payload.value = JSON.stringify(val);

      if(counter > 350) {
        window.alert('You can only assign 350 points in total');
        return;
      }
      creationCompleted = true;

      // set the player name and display the player character
      document.getElementById('playerName').textContent = val.characterName;
      playerName = val.characterName;
      document.getElementById('playerData').style.display = 'initial';

      var xmlhttp = new XMLHttpRequest();

      xmlhttp.onreadystatechange = function() {
          if (xmlhttp.readyState == XMLHttpRequest.DONE ) {
            updateMaxPlayerHealth = true;
            requestFirstTask();
          }
      };

      xmlhttp.open('POST', serverUrl + '/engine-rest/task/'+currentTask+'/complete', true);

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

      xmlhttp.open('POST', serverUrl + '/engine-rest/task/'+currentTask+'/complete', true);

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

            if(updateMaxPlayerHealth) {
              updateMaxPlayerHealth = false;
              playerHealth = JSON.parse(jsonResponse.playerCharacter.value).lifePoints;
              playerMaxHealth = JSON.parse(jsonResponse.playerCharacter.value).lifePoints;

              updateHealthDisplay();
            }

            if(jsonResponse.thisMonster) {
              var monsterInfo = JSON.parse(jsonResponse.thisMonster.value)
              if(monsterInfo.lifePoints > 0) {
                enemyName = monsterInfo.characterName;
                if(enemyName !== monsterInfo.characterName) {
                  enemyMaxHealth = monsterInfo.lifePoints;
                }
                enemyHealth = enemyMaxHealth;

                updateHealthDisplay();
              }

            } else {
              enemyName = '';
              updateHealthDisplay();
            }

            addStory(jsonResponse.storyText.value, function() {
              CURRENT_INPUTS = createInputs(JSON.parse(jsonResponse.editableFields.value), jsonResponse);

              var heightBefore = document.getElementById('story').scrollHeight;
              document.getElementById('story').scrollTop = heightBefore;
            });


          } else {
            console.log('error loading variABLES', xmlhttp);
          }
        }
    };

    xmlhttp.open('GET', serverUrl + '/engine-rest/process-instance/'+currentPIID+'/variables/?deserializeValues=false', true);

    xmlhttp.send();

  };


  function getFootprints() {

    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == XMLHttpRequest.DONE ) {

          var response = JSON.parse(xmlhttp.responseText);

          var elementIds = [];

          for (var i=0; i<response.length; i++) {

            elementIds.push(response[i].id.split(':')[0]);


          }
          console.log(elementIds);
          var dungeon = viewer.get('dungeon');

          dungeon.hideAll();
          dungeon.showElements(elementIds);

        }
    };

    xmlhttp.open('GET', serverUrl + '/engine-rest/history/activity-instance?processInstanceId='+MAIN_PROCESS_INSTANCE_ID, true);
    xmlhttp.setRequestHeader('Content-type', 'application/json');
    xmlhttp.send();
  }

  var requestFirstTask = function() {
    // clear the button container
    document.getElementById('buttonContainer').innerHTML = '';

    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == XMLHttpRequest.DONE ) {
          if(xmlhttp.status == 200){
            console.log('done loading character creation', xmlhttp);
            var jsonResponse = JSON.parse(xmlhttp.responseText);
            console.log('current task is', jsonResponse);
            currentTask = jsonResponse[0].id;
            currentPIID = jsonResponse[0].processInstanceId;

            getFootprints(currentPIID);

            // get the variables
            requestVariables();
          } else {
            console.log('error loading character creation', xmlhttp);
          }
        }
    };

    xmlhttp.open('GET', serverUrl + '/engine-rest/task/?processInstanceBusinessKey='+GAME_ID, true);

    xmlhttp.send();
  };

  var doStartCall = function() {
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == XMLHttpRequest.DONE ) {
          if(xmlhttp.status == 200){
            console.log('done', xmlhttp);

            MAIN_PROCESS_INSTANCE_ID = JSON.parse(xmlhttp.responseText).id;

            loadDiagram();
          } else {
            console.log('error', xmlhttp);
          }
        }
    };

    xmlhttp.open('POST', serverUrl + '/engine-rest/process-definition/key/adventure/start', true);

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
