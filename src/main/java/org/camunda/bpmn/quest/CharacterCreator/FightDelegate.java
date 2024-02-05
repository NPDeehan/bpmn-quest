package org.camunda.bpmn.quest.CharacterCreator;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

public class FightDelegate implements JavaDelegate {
	CharacterModel player;

	public void execute(DelegateExecution execution) throws Exception {
		
		this.player = (CharacterModel) execution.getVariable("playerCharacter");
		CharacterModel monster = (CharacterModel) execution.getVariable("thisMonster");	
		
		FightResult result = fightToDeath (monster);
		String fightOutcome = "";

		StoryModel thisStory = new StoryModel();
		thisStory.actionLog = result.getProtocol();
		thisStory.addOption("Continue");

		if (player.getLifePoints() < 1) {
			// The Player has died
			fightOutcome = "died";
			thisStory.setTitle(monster.getCharacterName() + " has killed you!");
			thisStory.setDescription("It was a fair fight and you lost, loser!");
		} else {
			// The Player has won
			fightOutcome = "survived";
			thisStory.setTitle("You've killed " + monster.getCharacterName() + "!");
			thisStory.setDescription("It was a fair fight and you won, winner!");
			thisStory.setPicture("/CharacterCreator/monsters/img/survived.png");

			player.addExperiencePoints(monster.getExperiencePoints());
		}
		
		// overwrite player in execution context to reflect lost lifepoints and gained experiencepoints
		ObjectValue playerDataValue = Variables.objectValue(player)
				  .serializationDataFormat(Variables.SerializationDataFormats.JSON)
				  .create();		
		execution.setVariable("playerCharacter", playerDataValue);
		
		
		ObjectValue storySerial = Variables.objectValue(thisStory)
				  .serializationDataFormat(Variables.SerializationDataFormats.JSON)
				  .create();
		
		execution.setVariable("storyText", storySerial);

		execution.setVariable("fightOutcome", fightOutcome);
		
		/* Legacy

		int wonXP = 0;
		
		if (player.getLifePoints() < 1) {
			fightOutcome = "died";
		} else {
			fightOutcome = "survived";
			wonXP = monster.getExperiencePoints();
			player.addExperiencePoints(wonXP);
		}

		execution.setVariable("fightOutcome", fightOutcome);
		execution.setVariable("wonXP", wonXP);
		ObjectValue resultDataValue = Variables.objectValue(result)
				  .serializationDataFormat(Variables.SerializationDataFormats.JSON)
				  .create();
		
		execution.setVariable("fightProtocol", resultDataValue);
		
		 */
		
	}
	
	// Fight until one character is dead, return the other as the winner 
	protected FightResult fightToDeath (CharacterModel monster) {
		FightResult result = new FightResult();
		
		CharacterModel attacker = monster;
		CharacterModel defender = this.player;
		
		int rounds = 0;
		
		// player attacks the monster, then the monster the player, until someone is dead
		do {
			// taking turns
			if (attacker == player) {
				attacker = monster;
				defender = player;
			} else {
				attacker = player;
				defender = monster;
			}

			rounds ++;
			
	    	int lifePointsLost = attack(attacker, defender);
	    	defender.setLifePoints( defender.getLifePoints() - lifePointsLost);

			String attack = "Round #" + rounds + ": " + attacker.getCharacterName() + " attacks " + defender.getCharacterName();
			if (lifePointsLost > 0) {
				attack += " and rips off " + lifePointsLost + " LifePoints, leaving " + defender.getLifePoints() + " Lifepoints";
			} else {
				attack += " but misses...";
			}
			result.getProtocol().add(attack);

		} while (defender.getLifePoints() > 0);
			
		//result.setWinner(attacker);
		//result.setLoser(defender);
		
		result.setRounds(rounds);
		
		
		return result;
	}
	
	// Returns the number of life points drawed from the defender
	protected int attack (CharacterModel attacker, CharacterModel defender) {
		// One attack can draw 0 to 20 Lifepoints
		// Attack and Defense is influenced by Strength (50%), Agility (30%) and Luck (20%)		
		long strengthRoll = Math.round((Dices.roll(4, 6) + attacker.getStrength()/10.0) - (Dices.roll(4, 6) + defender.getStrength()/10.0));
	    long agilityRoll  = Math.round((Dices.roll(4, 6) + attacker.getAgility()/10.0 ) - (Dices.roll(4, 6) + defender.getAgility()/10.0 ));
		long luckRoll     = Math.round((Dices.roll(4, 6) + attacker.getLuck()/10.0    ) - (Dices.roll(4, 6) + defender.getLuck()/10.0    ));
		boolean attackerWin = Math.round(strengthRoll * 0.5 + agilityRoll * 0.3 + luckRoll * 0.2) > 0;

		long lostLifePoints = 0;
		if (attackerWin) {
			lostLifePoints = Dices.roll(1, 10) + attacker.getStrength()/10;
		} else {
			// Miss
		}
		return (int) lostLifePoints;
	}

}
