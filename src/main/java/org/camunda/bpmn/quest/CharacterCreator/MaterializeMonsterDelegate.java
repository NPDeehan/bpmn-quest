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
		
		List<CharacterModel> monsters = generateMonsterPool();
		
		Random rn = new Random();
		int randomNumber = rn.nextInt(4);
		
		CharacterModel thisMonster = monsters.get(randomNumber);
		
		ObjectValue monsterDataValue = Variables.objectValue(thisMonster)
				  .serializationDataFormat(Variables.SerializationDataFormats.JSON)
				  .create();
		
		execution.setVariable("thisMonster", monsterDataValue);
		
	}
	
	private List<CharacterModel> generateMonsterPool () {
		
		List<CharacterModel> monsters = new ArrayList<CharacterModel>();

		monsters.add(new CharacterModel(
				"alfredo",
				"Alfredo",
				30, // Strength
				30, // Perception
				30, // Endurance
				30, // Charisma
				30, // Intelligence
				30, // Agility
				30, // Luck
				10 // Experience Points
				));
	
		monsters.add(new CharacterModel(
				"booneeda",
				"Boo Needa",
				40, // Strength
				30, // Perception
				30, // Endurance
				30, // Charisma
				30, // Intelligence
				70, // Agility
				40, // Luck
				40 // Experience Points
				));

		monsters.add(new CharacterModel(
				"falseoracle",
				"The False Oracle",
				70, // Strength
				30, // Perception
				30, // Endurance
				30, // Charisma
				30, // Intelligence
				30, // Agility
				10, // Luck
				70 // Experience Points
				));
		
		monsters.add(new CharacterModel(
				"lordwebsfear",
				"Lord Web's Fear",
				100, // Strength
				30, // Perception
				30, // Endurance
				30, // Charisma
				30, // Intelligence
				10, // Agility
				20, // Luck
				100 // Experience Points
				));
		
		return monsters;		
	}

}
