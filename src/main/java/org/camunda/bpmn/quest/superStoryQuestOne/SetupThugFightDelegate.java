package org.camunda.bpmn.quest.superStoryQuestOne;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpmn.quest.CharacterCreator.CharacterModel;
import org.camunda.bpmn.quest.CharacterCreator.StoryModel;

public class SetupThugFightDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		CharacterModel  charMod = (CharacterModel) execution.getVariable("playerCharacter");
		
		String storytext = "You watch as the poor Marketing guy is beaten up - you feel a little guilty - but not as guilty as you should. After they"
				+ " get bored of watching the marketing guy cry they see you. Then they run right for you! ";
		
		StoryModel newStory = new StoryModel("Watch and Laugh...",  storytext );
		newStory.addOption("Continue");
		//newStory.setPicture("http://ec2-52-19-141-24.eu-west-1.compute.amazonaws.com:8080/CharacterCreator/monsers/img/thugs.png");
		
	
		ObjectValue storySerialized =
				Variables.objectValue(newStory).serializationDataFormat("application/json").create();
		
		execution.setVariable("storyText", storySerialized);
	
		execution.setVariable("storytext", storytext);
		execution.setVariable("requestedMonsterId", "thug");
		//execution.setVariable("characterChoice", "Help"); // this is a default that can be edited by the user

	}

}
