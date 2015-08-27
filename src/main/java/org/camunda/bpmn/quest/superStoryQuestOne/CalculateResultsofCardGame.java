package org.camunda.bpmn.quest.superStoryQuestOne;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpmn.quest.CharacterCreator.CharacterModel;
import org.camunda.bpmn.quest.CharacterCreator.StoryModel;



public class CalculateResultsofCardGame implements JavaDelegate {
	
	StoryModel theStory = new StoryModel();
	CharacterModel player = new CharacterModel();
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		boolean isDead = false;
		
		player = (CharacterModel)execution.getVariable("playerCharacter");
		if(player.getLuck()> 64)
		{
			generateLuckyStory();
			
		}else if (player.getLuck() < 50)
		{
			generateVeryUnluckyStory();
			isDead = true;
		}else if (player.getInteligance() > 54)
		{
			generateInteligantStory();
			
		}else {
			generateRandomStory();
			
		}
		
		theStory.addOption("Continue");
		ObjectValue storySerialized =
				Variables.objectValue(theStory).serializationDataFormat("application/json").create();
		
		execution.setVariable("storyText", storySerialized);
		execution.setVariable("isDead", isDead);
		

	}

	private void generateRandomStory() {
		
		boolean yourCard = random();
		if(yourCard)
		{
			generateLuckyStory();
		}else {
			generateUnluckyStory();
			
		}
		
	}

	private void generateInteligantStory() {
		String storytext = "You managed to use your superior intellect to realize that gambling is stupid and pointless! Why would anyone.. WAIT! you guessed correctly! "
				+ "15 experience points - GAMBLING IS BRILLIANT!! W00000 ";
		
		this.theStory = new StoryModel("Is THIS your card?...",  storytext );
		theStory.addOption("Continue");
		theStory.setPicture("http://ec2-52-19-141-24.eu-west-1.compute.amazonaws.com:8080/CharacterCreator/monsters/img/money.png");
		
		player.addExperiencePoints(15);

		
	}

	private void generateVeryUnluckyStory()  
	{
		String storytext = "You make your guess and wait for your card to show up! - But you are SO unluckly that not only does your card not show up - but the room "
				+ "bursts into flames... you don't mangage to escape.. and you die :(";
		
		this.theStory = new StoryModel("Is THIS your... FIRE?...",  storytext );
		theStory.addOption("Continue");
		theStory.setPicture("http://ec2-52-19-141-24.eu-west-1.compute.amazonaws.com:8080/CharacterCreator/monsters/img/fire.png");
		
		player.setLifePoints(0);
		
		

	}
	

	private void generateUnluckyStory()  
	{
		String storytext = "You make your guess and wait for your card to show up! - But you are SO unluckly that not only does your card not show up - but the room "
				+ "bursts into flames... you don't mangage to escape.. and you die :(";
		
		this.theStory = new StoryModel("Is THIS your... FIRE?...",  storytext );
		theStory.addOption("Continue");
		theStory.setPicture("http://ec2-52-19-141-24.eu-west-1.compute.amazonaws.com:8080/CharacterCreator/monsters/img/fire.png");
		
		this.player.setLifePoints(this.player.getLifePoints() - 5);
		
		

	}
	private void generateLuckyStory() 
	{
		String storytext = "Well you're so lucky that no only did your card show up but you ALSO get 15 experience points - today is a good day to be alive! ";
		
		this.theStory = new StoryModel("Is THIS your card?...",  storytext );
		theStory.addOption("Continue");
		theStory.setPicture("http://ec2-52-19-141-24.eu-west-1.compute.amazonaws.com:8080/CharacterCreator/monsters/img/money.png");
		
		player.addExperiencePoints(15);

	}
	
	boolean random()
	{
	   int value = (int)(Math.random() * 100);
	   
	   if(value < 50)
		   return true; //it's a hit!
	   else
		   return false; // it's a miss!
	}

}
