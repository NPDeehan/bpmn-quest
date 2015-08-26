package org.camunda.bpmn.quest.CharacterCreator;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

public class InitializeAdventureListener implements ExecutionListener {

	@Override
	public void notify(DelegateExecution execution) throws Exception {

		// Just for Testing
		CharacterModel player = new CharacterModel("jakob", "Jakob the Hero", 50, 50, 50, 50, 50, 10, 150, 0);
		
		ObjectValue playerDataValue = Variables.objectValue(player)
				  .serializationDataFormat(Variables.SerializationDataFormats.JSON)
				  .create();
		
		execution.setVariable("playerCharacter", playerDataValue);
		
		

	}

}
