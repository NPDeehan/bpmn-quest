package org.camunda.bpmn.quest.superStoryQuestOne;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpmn.quest.CharacterCreator.CharacterModel;
import org.camunda.bpmn.quest.CharacterCreator.StoryModel;

public class WaysOfSavingPM implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		CharacterModel  charMod = (CharacterModel) execution.getVariable("playerCharacter");
		
		String storytext = "You've made the honorable choice of helping out the marketing manager… But how exactly do you plan on helping him.."
				+ "";
		
		StoryModel newStory = new StoryModel(charMod.getCharacterName() + " wants to help!",  storytext );
		newStory.addOption("Attack Them");
		newStory.addOption("Talk to Them");
		//newStory.setPicture("http://localhost:8080/CharacterCreator/img/hero_stand.png");
		newStory.setPicture("/CharacterCreator/img/hero_stand.png");
		
		if(charMod.getPerception()>60)
			newStory.addOption("Kick their Leader in the Balls");
		
		ObjectValue storySerialized =
				Variables.objectValue(newStory).serializationDataFormat("application/json").create();
		
		execution.setVariable("storyText", storySerialized);
		execution.setVariable("characterChoice", "Help");
		
	}



}
