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
		String requestedMonsterId = (String) execution.getVariable("requestedMonsterId");
		
		MonsterModel thisMonster = null;
		
		if(requestedMonsterId != null )
		{
			List<MonsterModel> allMonsters = generateAllMonstersPool();
			for(MonsterModel thisLovelyMonster : allMonsters)
			{
				if(thisLovelyMonster.getId().equals(requestedMonsterId))
				{
					thisMonster = thisLovelyMonster;
					break;
				}
			}
					
		}
		
		if(thisMonster == null)
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
		story.setPicture("/CharacterCreator/monsters/img/" + thisMonster.getId() + ".png");
		
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
				"Dr. Bad Freienwalde",
				30, // Strength
				30, // Perception
				30, // Endurance
				30, // Charisma
				30, // Intelligence
				30, // Agility
				30, // Luck
				10, // Experience Points
				"Alfredo is small and no one likes him - but for good reason. He's very annoying, you might argue that killing him would be TOO easy. "
         		+ " I say that you should probably crush him while you have the chance!" // Monster Story
				));
	
		monsters.add(new MonsterModel(
				"booneeda",
				"Görli",
				40, // Strength
				30, // Perception
				30, // Endurance
				30, // Charisma
				30, // Intelligence
				70, // Agility
				40, // Luck
				40, // Experience Points
				"Görli is a fairly mean lady. I once saw her eat a kitten, a LIVE kitten. It was a very "
         		+ "sad day... AVENGE THE KITTEN!"
				));

		monsters.add(new MonsterModel(
				"hellishator",
				"The Hellish Ator",
				70, // Strength
				30, // Perception
				30, // Endurance
				30, // Charisma
				30, // Intelligence
				30, // Agility
				10, // Luck
				70, // Experience Points
				"He may look cute but don't be fooled! behind that adorable little cape is a creature who would destroy the world if given the chance. "
				+ "It would certainly be the most endearing way to end the world - but the downside is that the world could be gone "
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
				"So, Lord Web's Fear is scary! I've heard that he's made of pure evil and that if you say his name "
         		+ "3 times in a mirror he'll show up and ask you why you're acting so stupid. He really hurts a lot of peoples feelings! MURDER HIM!"
				));
		

		
		return monsters;		
	}
	
	private List<MonsterModel> generateSpecialMonsterPool () {
		
		List<MonsterModel> monsters = new ArrayList<MonsterModel>();

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
				"This guy is what's wrong with the youth of today - or so you tell yourself, in reality you're just bitter about "
				+ "being older and less spritely" // Monster Story
				));
		
		monsters.add(new MonsterModel(
				"jakob",
				"Jakob The Great",
				70, // Strength
				30, // Perception
				20, // Endurance
				30, // Charisma
				30, // Intelligence
				10, // Agility
				55, // Luck
				100, // Experience Points
				"The fact he's your boss makes him a lot more scray... but at least you get to try and punch your boss - most people don't get that chance!" // Monster Story
				));
		
		monsters.add(new MonsterModel(
				"sebastian",
				"Sneaky Sebastian",
				50, // Strength
				30, // Perception
				90, // Endurance
				30, // Charisma
				30, // Intelligence
				60, // Agility
				50, // Luck
				100, // Experience Points
				"With the awesome power of front end development, Sebastian is a powerful enemy! " // Monster Story
				));
		
		monsters.add(new MonsterModel(
				"paddy",
				"Paddy Lord of Laptop Destructive",
				90, // Strength
				30, // Perception
				10, // Endurance
				30, // Charisma
				30, // Intelligence
				10, // Agility
				90, // Luck
				100, // Experience Points
				"If he can break you as easily as he broke his own computer then you're in for some serious trouble... " // Monster Story
				));
		
		monsters.add(new MonsterModel(
				"niall",
				"Niall the Injury-Prone",
				50, // Strength
				30, // Perception
				70, // Endurance
				30, // Charisma
				30, // Intelligence
				40, // Agility
				90, // Luck
				100, // Experience Points
				"If you wait long enought he'll probably fall over and kill himself - but you don't have time - you're going to have hit the poor little guy... like the bully you are!  " // Monster Story
				));
		
		return monsters;	
	}
	
	private List<MonsterModel> generateAllMonstersPool(){
		
		List<MonsterModel> monsters = generateMonsterPool();
		monsters.addAll(generateSpecialMonsterPool());
		
		return monsters;
	}
	

}
