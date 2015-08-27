package org.camunda.bpmn.quest.superStoryQuestOne;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpmn.quest.CharacterCreator.CharacterModel;
import org.camunda.bpmn.quest.CharacterCreator.StoryModel;

public class WaysOfSavingPM implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		CharacterModel  charMod = (CharacterModel) execution.getVariable("playerCharacter");
		
		String storytext = "You've made the honerable choice of helping out the marketing manager.. but how exactly do you plan on helping him.."
				+ "";
		
		StoryModel newStory = new StoryModel(charMod.getCharacterName() + " wants to help!",  storytext );
		newStory.addOption("Attack Them");
		newStory.addOption("Talk to Them");
		if(charMod.getPerception()>60)
			newStory.addOption("Kick their Leader in the Balls");
		

		execution.setVariable("storytext", storytext);
		execution.setVariable("characterChoice", "Help");
		
	}



}
