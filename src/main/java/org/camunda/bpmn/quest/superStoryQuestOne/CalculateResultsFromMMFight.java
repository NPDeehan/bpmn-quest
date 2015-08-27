package org.camunda.bpmn.quest.superStoryQuestOne;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpmn.quest.CharacterCreator.CharacterModel;
import org.camunda.bpmn.quest.CharacterCreator.StoryModel;

public class CalculateResultsFromMMFight implements JavaDelegate {

	CharacterModel player;
	StoryModel theStory;
	boolean marketingGuySaved= false;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception 
	{
		this.player = (CharacterModel) execution.getVariable("playerCharacter");
		this.theStory = new StoryModel();
		
		String choice = (String) execution.getVariable("decision");
		
		if(choice.equals("Attack Them"))
		{
			generateAttckStory();
			execution.setVariable("requestedMonsterId", "thug");
		}else if (choice.equals("Talk to Them"))
		{
			generateTalkStory();
		}
		else if(choice.equals("Kick their Leader in the Balls")){
			generatKickStory();
		}
		
		ObjectValue storySerialized =
				Variables.objectValue(this.theStory).serializationDataFormat("application/json").create();
		
		execution.setVariable("storyText", storySerialized);
		execution.setVariable("MMSaved", marketingGuySaved);
		

	}

	private void generatKickStory() {
		String storytext = "Well - like the brave warrior that you are - you kicked a man in the dick... I drops like a sack of potatoes and the rest of the "
				+ "thugs scatter. You gain 55 experience points ";
		
		this.theStory = new StoryModel("Ohhh.. that looked bad",  storytext );
		theStory.addOption("Continue");
		theStory.setPicture("http://ec2-52-19-141-24.eu-west-1.compute.amazonaws.com:8080/CharacterCreator/monsters/img/nutskick.jpg");
		
		player.addExperiencePoints(55);
		marketingGuySaved = true;
		
	}

	private void generateTalkStory() {
		if(player.getCharisma() > 59)
		{
			String storytext = "Maybe these poor thugs just needed someone to talk to because you manage to explain to them how"
					+ " badly they're contributing to society and that maybe they should think about what they've done, which they do. You gain 50 experiecne for"
					+ " being so charasmatic ";
			
			this.theStory = new StoryModel("Let's just talk about this...",  storytext );
			theStory.addOption("Continue");
			
			player.addExperiencePoints(50);
			marketingGuySaved = true;
			
		}else{
			String storytext = "You're try you best to explain that you are right and they should have been murdered at birth, but this just seems to make them"
					+ "more angry... maybe they're less resonable than you thought. Also i think they're gonna fight you now...";
			
			this.theStory = new StoryModel("Let's just talk about this...",  storytext );
			theStory.addOption("Continue");
			marketingGuySaved = false;

		}
		
	}

	private void generateAttckStory() {
		String storytext = "You run at them, silently hoping that they become started by your bravery and scatter - but no... they see you coming and are "
				+ "seemingly quite excited about maybe getting to beat you up ";
		
		this.theStory = new StoryModel("ATTACK!!",  storytext );
		theStory.addOption("Continue");
		theStory.setPicture("http://ec2-52-19-141-24.eu-west-1.compute.amazonaws.com:8080/CharacterCreator/monsters/img/thugs.png");
		marketingGuySaved = false;
		
	}

}
