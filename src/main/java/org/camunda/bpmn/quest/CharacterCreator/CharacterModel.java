package org.camunda.bpmn.quest.CharacterCreator;

public class CharacterModel {
	
	String characterName;
	
	int lifePoints = 50;
	
	// all stats are between 1 and 100 
	int strength = 50;		// Helps you hit enemies better
	int perception = 50;		// Might give you more options when making a decision.
	int endurance = 50;		// determins how much life you have
	int charisma = 50;		// Will make some people like you more
	int inteligance = 50;	// You'll be able to perform more complicated tasks 
	int agility = 50;		// Helps in fighting somehow :)
	int luck = 50;			// Helps in various unforeeable ways.
	
	
	
	// Constructor that creates a custom character with custom attributes
	public CharacterModel(String characterName, int strength, int perception,
			int endurance, int charisma, int inteligance, int agility, int luck) {
		super();
		this.characterName = characterName;
		this.strength = strength;
		this.perception = perception;
		this.endurance = endurance;
		this.charisma = charisma;
		this.inteligance = inteligance;
		this.agility = agility;
		this.luck = luck;
		
		setLifePoints();
		
	}
	
	// Creates a character with a custom name and default attributes
	public CharacterModel(String characterName) {
		super();
		this.characterName = characterName;
		
		setLifePoints();
	}


	public String getCharacterName() {
		return characterName;
	}



	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}



	public int getLifePoints() {
		return lifePoints;
	}



	public void setLifePoints() {
		this.lifePoints = 50 + this.endurance ;
		
	}



	public int getStrength() {
		return strength;
	}



	public void setStrength(int strength) {
		this.strength = strength;
	}



	public int getPerception() {
		return perception;
	}



	public void setPerception(int perception) {
		this.perception = perception;
	}



	public int getEndurance() {
		return endurance;
	}



	public void setEndurance(int endurance) {
		this.endurance = endurance;
	}



	public int getCharisma() {
		return charisma;
	}



	public void setCharisma(int charisma) {
		this.charisma = charisma;
	}



	public int getInteligance() {
		return inteligance;
	}



	public void setInteligance(int inteligance) {
		this.inteligance = inteligance;
	}



	public int getAgility() {
		return agility;
	}



	public void setAgility(int agility) {
		this.agility = agility;
	}



	public int getLuck() {
		return luck;
	}



	public void setLuck(int luck) {
		this.luck = luck;
	}

	

}