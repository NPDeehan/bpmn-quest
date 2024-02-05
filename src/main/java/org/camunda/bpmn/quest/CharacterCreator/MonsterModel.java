package org.camunda.bpmn.quest.CharacterCreator;
import java.io.Serializable;

import org.camunda.bpmn.quest.CharacterCreator.CharacterModel;


public class MonsterModel extends CharacterModel {

	private static final long serialVersionUID = 1L;
	
	private String monsterStory;
	
	public MonsterModel() {
		//.
	}

	public MonsterModel(String id, String characterName, int strength,
			int perception, int endurance, int charisma, int inteligance,
			int agility, int luck, int experiencePoints, String monsterStory) {
		
		super(id, characterName, strength, perception, endurance, charisma,
				inteligance, agility, luck, experiencePoints);
		
		this.monsterStory = monsterStory;

	}

	public MonsterModel(String characterName) {
		super(characterName);
	}

	public String getMonsterStory() {
		return monsterStory;
	}

	public void setMonsterStory(String monsterStory) {
		this.monsterStory = monsterStory;
	}

}
