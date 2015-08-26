package org.camunda.bpmn.quest.CharacterCreator;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

public class InitializeAdventureListener implements ExecutionListener {

	@Override
	public void notify(DelegateExecution execution) throws Exception {

		// Just for Testing
		CharacterModel player = new CharacterModel("jakob", "Jakob the Hero", 50, 50, 50, 50, 50, 10, 150);
		execution.setVariable("playerCharacter", player);
		
		

	}

}
