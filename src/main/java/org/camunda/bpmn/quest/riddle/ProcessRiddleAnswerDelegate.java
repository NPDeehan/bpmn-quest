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
	
			
		} else {
			// if it's not correct, prepare Story Object
			story.setTitle("Sorry, that was wrong!");
			story.setDescription("The right answer is: " + riddle.getAnswer());

		}

		story.addOption("Continue");
		ObjectValue storySerialized =
				Variables.objectValue(story).serializationDataFormat("application/json").create();
		execution.setVariable("storyText", storySerialized);			
		
	}

}
