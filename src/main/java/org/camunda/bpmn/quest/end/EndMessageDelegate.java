package org.camunda.bpmn.quest.end;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpmn.quest.CharacterCreator.StoryModel;

public class EndMessageDelegate implements JavaDelegate {


	@Override
	public void execute(DelegateExecution execution) throws Exception {

		
		String end = (String) execution.getVariable("end");
		
		StoryModel story = new StoryModel();

		if (end.equals("died")) {
			story.setTitle("You're dead...");
			story.setDescription("Never mind, you'll live to fight another day... somehow. At least you've gained some experience, so why not tell the world about it!? \n\nWhen you continue, your achievements will be tweeted on the 'Camunda Demo' Account on Twitter, with #bpmnQuest - see you soon for another round of BPMN Quest!" );
			story.setPicture("http://ec2-52-19-141-24.eu-west-1.compute.amazonaws.com:8080/CharacterCreator/monsters/img/died.png");
		} else {
			story.setTitle("You've won this Game!");
			story.setDescription("You're absolutely super awewsome - you need to tell the world about it! \n\nWhen you continue, your achievements will be tweeted on the 'Camunda Demo' Account on Twitter, with #bpmnQuest - see you soon for another round of BPMN Quest!" );
			story.setPicture("https://media.giphy.com/media/PVL1e2rCaaiXu/giphy.gif");
			
		}
		story.addOption("Tweet and Finish the Game");
		
		ObjectValue storySerialized =
				Variables.objectValue(story).serializationDataFormat("application/json").create();
		
		execution.setVariable("storyText", storySerialized);
		
	}

}
