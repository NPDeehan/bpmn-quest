package org.camunda.bpmn.quest.CharacterCreator;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import static org.camunda.spin.Spin.JSON;

public class FightDelegate implements JavaDelegate {
	CharacterModel player;
	
	public void execute(DelegateExecution execution) throws Exception {
		
		this.player = (CharacterModel) execution.getVariable("playerCharacter");
		CharacterModel monster = (CharacterModel) execution.getVariable("thisMonster");	
		
		//CharacterModel monster = new CharacterModel("monster", "Monster", 50, 50, 50, 50, 50, 10, 10);
		
		FightResult result = fightToDeath (monster);

		int wonXP = 0;
		String fightOutcome = "";
		
		if (player.getLifePoints() < 1) {
			fightOutcome = "died";
		} else {
			fightOutcome = "survived";
			wonXP = monster.getExperiencePoints();
			player.addExperiencePoints(wonXP);
		}
		execution.setVariable("fightOutcome", fightOutcome);
		execution.setVariable("wonXP", wonXP);

		// overwrite player in execution context to reflect lost lifepoints and gained experiencepoints
		ObjectValue playerDataValue = Variables.objectValue(player)
				  .serializationDataFormat(Variables.SerializationDataFormats.JSON)
				  .create();		
		execution.setVariable("playerCharacter", playerDataValue);

		
		ObjectValue resultDataValue = Variables.objectValue(result)
				  .serializationDataFormat(Variables.SerializationDataFormats.JSON)
				  .create();
		
		execution.setVariable("fightProtocol", resultDataValue);
		
	
	}
	
	// Fight until one character is dead, return the other as the winner 
	private FightResult fightToDeath (CharacterModel monster) {
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
			
	    	int lifePointsLost = attack(attacker, monster);
	    	defender.setLifePoints( defender.getLifePoints() - lifePointsLost);
	    	
	    	result.getProtocol().add("Round #" + rounds + ": " + attacker.getCharacterName() + " attacks " + defender.getCharacterName() + 
	    			" and rips of " + lifePointsLost + " LifePoints, leaving " + defender.getLifePoints() + " Lifepoints");
	    	
		
		} while (defender.getLifePoints() > 0);
			
		result.setWinner(attacker);
		result.setLoser(defender);
		
		result.setRounds(rounds);
		
		
		return result;
	}
	
	// Returns the number of life points drawed from the defender
	private int attack (CharacterModel attacker, CharacterModel defender) {
		// One attack can draw 0 to 10 Lifepoints
		// Attack and Defense is influenced by Strength (50%), Agility (30%) and Luck (20%)		

		long sPercentage = Math.round ( (diceRoll() * attacker.getStrength()) -  (diceRoll() * defender.getStrength()  ) );
	    long aPercentage = Math.round ( (diceRoll() * attacker.getAgility()) - ( (diceRoll() * defender.getAgility()) )  );
		long lPercentage = Math.round ( (diceRoll() * attacker.getLuck()) - ( (diceRoll() * defender.getLuck()) ) );
		
		long lostLifePoints = Math.round (sPercentage * 0.5) + Math.round (aPercentage * 0.3) + Math.round (lPercentage * 0.2);

		// Don't lose negative Lifepoints
		if (lostLifePoints < 0) lostLifePoints = 0;
		
		return (int) lostLifePoints;
	}
	
		private double diceRoll () {
			// Between 0 and 1
			return Math.random();
		}

}
