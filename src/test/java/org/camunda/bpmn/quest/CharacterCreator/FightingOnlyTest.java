package org.camunda.bpmn.quest.CharacterCreator;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FightingOnlyTest {

    @Test
    public void testFightingOnly() {
        // Disable randomness
        Dices.setSeed(1);

        FightDelegate fightDelegate = new FightDelegate();

        CharacterModel player  = new CharacterModel("jakob", "Jakob the Hero!", 50, 50, 50, 50, 50, 50, 50, 50);
        CharacterModel monster = new CharacterModel("thug",  "Thug the Monsta", 30, 30, 30, 30, 30, 30, 30, 50);

        fightDelegate.player = player;

        FightResult fightResult = fightDelegate.fightToDeath(monster);

        /*
        for (String fightEvent : fightResult.getProtocol())
        {
            System.out.println(fightEvent);
        }
        */
        // Round #18: Thug the Monsta attacks Jakob the Hero! and rips off 7 LifePoints, leaving 75 Lifepoints
        //...
        // Round #25: Jakob the Hero! attacks Thug the Monsta and rips off 13 LifePoints, leaving -5 Lifepoints
        assertTrue(player.getLifePoints() > 0); // 75
        assertTrue(monster.getLifePoints() <= 0); // -5
    }
}
