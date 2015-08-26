package org.camunda.bpmn.quest.CharacterCreator;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class CreateCharacterDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception 
	{
		CharacterModel defaulChar = new CharacterModel("Hero!");
		
		execution.setVariable("defualChar", defaulChar);

	}

}
