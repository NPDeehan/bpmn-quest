package org.camunda.bpmn.quest.superStoryQuestOne;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpmn.quest.CharacterCreator.CharacterModel;

public class CalculateResultsFromMMFight implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception 
	{
		CharacterModel player = (CharacterModel) execution.getVariable("playerCharacter");
		
		

	}

}
