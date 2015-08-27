package org.camunda.bpmn.quest.superStoryQuestOne;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpmn.quest.CharacterCreator.CharacterModel;
import org.camunda.bpmn.quest.CharacterCreator.StoryModel;

public class GameOfChanceDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		CharacterModel  charMod = (CharacterModel) execution.getVariable("playerCharacter");
		
		String storytext = "Well we've already shown that you drink to much and have issues with violen children so why no become a gambeler? All you need to"
				+ " is pick a card - and you'll get... something... presumably.. ";
		
		StoryModel newStory = new StoryModel("Pick a Card any Card",  storytext);
		newStory.addOption("Ace");
		newStory.addOption("King");
		newStory.addOption("Queen");
		newStory.addOption("Ten");
		newStory.setPicture("http://ec2-52-19-141-24.eu-west-1.compute.amazonaws.com:8080/CharacterCreator/monsers/img/card.png");
		
	
		ObjectValue storySerialized =
				Variables.objectValue(newStory).serializationDataFormat("application/json").create();
		
		execution.setVariable("storyText", storySerialized);
	
		execution.setVariable("storytext", storytext);
		execution.setVariable("characterChoice", "Help"); // this is a default that can be edited by the user
	


	}

}
