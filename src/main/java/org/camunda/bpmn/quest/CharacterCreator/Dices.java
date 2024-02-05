package org.camunda.bpmn.quest.CharacterCreator;

import java.util.Random;

public class Dices {

    private static final Random dices = new Random();

    protected static void setSeed(long seed){
        dices.setSeed(seed);
    }

    public static int roll(int n, int d) {
        int result = 0;
        for (int i = 0; i < n; i++)
        {
            result += dices.nextInt(d) + 1;
        }
        return result;
    }

}
