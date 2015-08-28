package org.camunda.bpmn.quest.superStoryQuestOne;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpmn.quest.CharacterCreator.CharacterModel;
import org.camunda.bpmn.quest.CharacterCreator.StoryModel;

public class SetupBossFightDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
CharacterModel  charMod = (CharacterModel) execution.getVariable("playerCharacter");
		
		String storytext = "To finally complete your quest you need to slay the worst enemy of all! A MYSTERIOUS BOSS!!! But there's more than one way to win a fight... what option do you want to for?";
		
		StoryModel newStory = new StoryModel("The Final Showdown!.",  storytext );
		newStory.addOption("Just Attack!");
		newStory.addOption("Do Something Smart");
		newStory.addOption("Run Away");
		if(charMod.getLuck() > 50)
			newStory.addOption("Punch At Random");

		//newStory.setPicture("http://localhost:8080/CharacterCreator/img/evilboss.jpg");
		newStory.setPicture("http://ec2-52-19-141-24.eu-west-1.compute.amazonaws.com:8080/CharacterCreator/img/warning.png");
		
	
		ObjectValue storySerialized =
				Variables.objectValue(newStory).serializationDataFormat("application/json").create();
		
		execution.setVariable("storyText", storySerialized);
	
		execution.setVariable("storytext", storytext);
		execution.setVariable("requestedMonsterId", "thug");

	}

}
