package org.camunda.bpmn.quest.riddle;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpmn.quest.CharacterCreator.CharacterModel;
import org.camunda.bpmn.quest.CharacterCreator.StoryModel;

public class ProcessRiddleAnswerDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		// Get Decision from Player
		String decision = (String) execution.getVariable("decision");

		// Get the Riddle that was to be solved
		Riddle riddle = (Riddle) execution.getVariable("thisRiddle");
		
		StoryModel story = new StoryModel();
		boolean riddleSolved = false;
		
		// Check if answer is correct
		if (riddle.getAnswer().equals(decision)) {
			// Provide XP to Player
			CharacterModel player = (CharacterModel) execution.getVariable("playerCharacter");
			player.addExperiencePoints(riddle.getExperiencePoints());
			// overwrite player in execution context to reflect lost lifepoints and gained experiencepoints
			ObjectValue playerDataValue = Variables.objectValue(player)
					  .serializationDataFormat(Variables.SerializationDataFormats.JSON)
					  .create();		
			execution.setVariable("playerCharacter", playerDataValue);
			
			// prepare Story Object
			story.setTitle("You've solved the riddle!");
			story.setDescription("You're freaking awesome and you've gained " + riddle.getExperiencePoints() + " Experience Points.");
			story.setPicture("http://cdn.smosh.com/sites/default/files/legacy.images/smosh-pit/092010/dancefail-18.gif");
	
			// Put variable that Riddle is solved 
			riddleSolved = true;
		} else {
			// if it's not correct, prepare Story Object
			story.setTitle("Sorry, that was wrong!");
			story.setDescription("The right answer is: " + riddle.getAnswer());
			story.setPicture("http://notalwaysright.com/wp-content/uploads/2013/09/funny-quotes-stupid-people.png");

		}

		story.addOption("Continue");
		ObjectValue storySerialized =
				Variables.objectValue(story).serializationDataFormat("application/json").create();
		execution.setVariable("storyText", storySerialized);	
		
		execution.setVariable("riddleSolved", riddleSolved);
		
	}

}
