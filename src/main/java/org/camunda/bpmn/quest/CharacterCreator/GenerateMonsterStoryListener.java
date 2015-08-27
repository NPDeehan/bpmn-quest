package org.camunda.bpmn.quest.CharacterCreator;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

public class GenerateMonsterStoryListener implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		String monsterStory = (String) delegateTask.getVariable("storyText");
		
		CharacterModel monster = (CharacterModel) delegateTask.getVariable("thisMonster");
		
		String monsterId = monster.getId();
		
		monsterStory = getMonsterStory(monsterId);

		delegateTask.setVariable("storyText", monsterStory);
		
	}

	private String getMonsterStory(String monsterId) 
	{
		String newMonsterStory = "";
		 switch (monsterId) {
		 
         case "alfredo":  newMonsterStory = "A story about Alfredo \n He's small and no one likes him - but for good reason. He's a Jerk you should "
         		+ "probably crush him while you have the chance!";	
                  break;
         case "booneeda": newMonsterStory = "a story about Booneeda \n She's a fairly mean lady. I once saw her eat a kitten, a LIVE kitten. It was a very"
         		+ "sad day... AVENGE THE KITTEN!!" ;
                  break;
         case "falseoracle":  newMonsterStory = "a story about False Oracle \n Well he's big very big, some would say overweight but wouldn't"
         		+ "want to affend the guy. He's got a serious tempter and could probably crush you to death with his belly flaps... bad way to go - but without"
         		+ "risk there is no reward go forth! kill the fat jerk";
                  break;
         case "lordwebsfear":  newMonsterStory = "a story about Lord Websfear \n People know that he's made of evil and say that if you say his name"
         		+ "3 times in a mirror he'll show up and why you're acting so stupid. He really hurts a lot of peoples feelings! MURDER HIM!";
                  break;
         
         default: newMonsterStory = "This monster is mysterious as FUCK!";
         
		 }
        
		return newMonsterStory;
	}

}
