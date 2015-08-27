package org.camunda.bpmn.quest.CharacterCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

public class MaterializeMonsterDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		
		List<MonsterModel> monsters = generateMonsterPool();
		String monsterNeeded = (String) execution.getVariable("requestedMonsterId");
		
		MonsterModel thisMonster = null;
		
		if(monsterNeeded != null )
		{
			for(MonsterModel thisLovelyMonster : monsters)
			{
				if(thisLovelyMonster.getId().equals(monsterNeeded))
				{
					thisMonster = thisLovelyMonster;
					break;
				}
			}
					
		}
		
		if(monsterNeeded == null)
		{
			Random rn = new Random();
			int randomNumber = rn.nextInt(monsters.size());
			
			thisMonster = monsters.get(randomNumber);

		}
		
		
		ObjectValue monsterDataValue = Variables.objectValue(thisMonster)
				  .serializationDataFormat(Variables.SerializationDataFormats.JSON)
				  .create();
		
		execution.setVariable("thisMonster", monsterDataValue);
		
		StoryModel story = new StoryModel();
		story.setTitle("There is " + thisMonster.getCharacterName() + "!");
		story.setDescription(thisMonster.getMonsterStory() + "\n What do you want to do?" );
		story.setPicture("http://ec2-52-19-141-24.eu-west-1.compute.amazonaws.com:8080/CharacterCreator/monsters/img/" + thisMonster.getId() + ".png");
		
		story.addOption("Fight to Death!");
		story.addOption("Sneak away...");
		
		
		//delegateTask.setVariable("storyText", monster.getMonsterStory());
		
		ObjectValue storySerialized =
				Variables.objectValue(story).serializationDataFormat("application/json").create();
		
		execution.setVariable("storyText", storySerialized);
		execution.removeVariable("requestedMonsterId");
		
	}
	
	private List<MonsterModel> generateMonsterPool () {
		
		List<MonsterModel> monsters = new ArrayList<MonsterModel>();

		monsters.add(new MonsterModel(
				"alfredo",
				"Alfredo",
				30, // Strength
				30, // Perception
				30, // Endurance
				30, // Charisma
				30, // Intelligence
				30, // Agility
				30, // Luck
				10, // Experience Points
				"A story about Alfredo \n He's small and no one likes him - but for good reason. He's a Jerk you should "
         		+ "probably crush him while you have the chance!" // Monster Story
				));
	
		monsters.add(new MonsterModel(
				"booneeda",
				"Boo Needa",
				40, // Strength
				30, // Perception
				30, // Endurance
				30, // Charisma
				30, // Intelligence
				70, // Agility
				40, // Luck
				40, // Experience Points
				"a story about Boon Needa \n She's a fairly mean lady. I once saw her eat a kitten, a LIVE kitten. It was a very"
         		+ "sad day... AVENGE THE KITTEN!!"
				));

		monsters.add(new MonsterModel(
				"falseoracle",
				"The False Oracle",
				70, // Strength
				30, // Perception
				30, // Endurance
				30, // Charisma
				30, // Intelligence
				30, // Agility
				10, // Luck
				70, // Experience Points
				"a story about False Oracle \n Well he's big very big, some would say overweight but wouldn't"
         		+ "want to affend the guy. He's got a serious tempter and could probably crush you to death with his belly flaps... bad way to go - but without"
         		+ "risk there is no reward go forth! kill the fat jerk"
				));
		
		monsters.add(new MonsterModel(
				"lordwebsfear",
				"Lord Web's Fear",
				100, // Strength
				30, // Perception
				30, // Endurance
				30, // Charisma
				30, // Intelligence
				10, // Agility
				20, // Luck
				100, // Experience Points
				"a story about Lord Web's Fear \n People know that he's made of evil and say that if you say his name"
         		+ "3 times in a mirror he'll show up and why you're acting so stupid. He is very hard to install and really hurts a lot of peoples feelings! MURDER HIM!"
				));
		
		monsters.add(new MonsterModel(
				"thug",
				"Thug",
				50, // Strength
				30, // Perception
				20, // Endurance
				30, // Charisma
				30, // Intelligence
				30, // Agility
				50, // Luck
				25, // Experience Points
				"This is guy is what's wrong with the youth of today - or so you tell yourself, in reality you're just bitter about "
				+ "being older and less spritly" // Monster Story
				));
		
		return monsters;		
	}

}
