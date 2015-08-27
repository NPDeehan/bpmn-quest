package org.camunda.bpmn.quest.death;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpmn.quest.CharacterCreator.StoryModel;

public class DyingDelegate implements JavaDelegate {


	@Override
	public void execute(DelegateExecution execution) throws Exception {

		StoryModel story = new StoryModel();
		story.setTitle("You're dead...");
		story.setDescription("Never mind, you'll live to fight another day... somehow. At least you've gained some experience, so why not tell the world about it!? \n\nWhen you hit 'Continue', you're achievements will be tweeted on the 'Camunda Demo' Account on Twitter, with #bpmnQuest - see you soon for another round of BPMN Quest!" );
		story.setPicture("");
		story.setPicture("http://ec2-52-19-141-24.eu-west-1.compute.amazonaws.com:8080/CharacterCreator/monsters/img/died.png");
		
		story.addOption("Tweet and Finish the Game");
		
		ObjectValue storySerialized =
				Variables.objectValue(story).serializationDataFormat("application/json").create();
		
		execution.setVariable("storyText", storySerialized);
		
	}

}
