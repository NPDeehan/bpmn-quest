package org.camunda.bpmn.quest.superStoryQuestOne;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpmn.quest.CharacterCreator.CharacterModel;
import org.camunda.bpmn.quest.CharacterCreator.NameEditableFieldsUtil;
import org.camunda.bpmn.quest.CharacterCreator.StoryModel;

public class GenerateIntroDialog implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		CharacterModel  charMod = (CharacterModel) execution.getVariable("playerCharacter");
				
		String storytext = "•	As you make your way to a local pub for a well-deserved drink you come across a group of thugs beating up an innocent marketing manager. While you're not too fond of marketing people, you feel maybe if you help he'll learn a lesson and perhaps re-skill to become"
				+ " something useful to society... or you could just grab some popcorn and watch the action!";
		
		StoryModel newStory = new StoryModel(charMod.getCharacterName() + " finds a bit of trouble",  storytext );
		newStory.addOption("Help!");
		newStory.addOption("Laugh!");
		newStory.setPicture("http://ec2-52-19-141-24.eu-west-1.compute.amazonaws.com:8080/CharacterCreator/img/thug_fight.jpg");
		//newStory.setPicture("http://localhost:8080/CharacterCreator/img/thug_fight.jpg");
		
	
		ObjectValue storySerialized =
				Variables.objectValue(newStory).serializationDataFormat("application/json").create();
		
		execution.setVariable("storyText", storySerialized);

		execution.setVariable("characterChoice", "Help"); // this is a default that can be edited by the user
		
		
	}


}
