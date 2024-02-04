package org.camunda.bpmn.quest.CharacterCreator;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

public class RecalculateLifePointsListener implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		CharacterModel player = (CharacterModel) delegateTask.getVariable("playerCharacter");
		
		player.generateLifePoints();
		
		ObjectValue playerSerialized =
				Variables.objectValue(player).serializationDataFormat("application/json").create();
		
		delegateTask.setVariable("playerCharacter", playerSerialized);


	}

}
