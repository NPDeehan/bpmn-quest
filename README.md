# A Camunda BPM Process Application Adventure
This is a simple Adventure Game built as a Process Application for [Camunda BPM](http://docs.camunda.org).

This project has been generated originally by the Maven archetype
[camunda-archetype-servlet-war-7.3.1](http://docs.camunda.org/latest/guides/user-guide/#process-applications-maven-project-templates-archetypes).

## How to use it?
1. Build application WAR (See below)
2. Deploy (copy) the WAR file to the Camunda Tomcat webapps folder
3. Go to *http://(YourServer)/CharacterCreator/frontend/*  
   Eg. http://localhost:8080/CharacterCreator/frontend/

## How does it work?
It is a complete Camunda BPM Process Application, which should be run by a Camunda 7 Tomcat Application Server.
You can download that from here: https://camunda.com/download/platform-7

The project itself also incorporating a Camunda 7 server but only for unit testing purposes.

To get started refer to the `InMemoryH2Test`.

## Build
You should build the application into a WAR file in order to use.

### Maven
You can simply build the application WAR with Maven: `maven install`

### Ant
You can also use `ant` to build and deploy the example to an application server.
For that to work you need to copy the file `build.properties.example` to `build.properties`
and configure the path to your application server inside it.
Alternatively, you can also copy it to `${user.home}/.camunda/build.properties`
to have a central configuration that works with all projects generated by the
[Camunda BPM Maven Archetypes](http://docs.camunda.org/latest/guides/user-guide/#process-applications-maven-project-templates-archetypes).

## Alternative running method
Once you deployed the application you can run it using
[Camunda Tasklist](http://docs.camunda.org/latest/guides/user-guide/#tasklist)
and inspect it using
[Camunda Cockpit](http://docs.camunda.org/latest/guides/user-guide/#cockpit).

But this way you are not supported by any game UI.

## Environment Restrictions
Built and tested against Camunda BPM version 7.20.0.


## Known Limitations
The simple game mainly created for enjoying the program modifications, not really the game itself.


## Improvements Backlog
Upgraded to Camunda 7.20.0

## License
[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

<!-- HTML snippet for index page
  <tr>
    <td><img src="snippets/CharacterCreator/src/main/resources/process.png" width="100"></td>
    <td><a href="snippets/CharacterCreator">Camunda BPM Process Application</a></td>
    <td>A Process Application for [Camunda BPM](http://docs.camunda.org).</td>
  </tr>
-->