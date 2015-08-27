package org.camunda.bpmn.quest.CharacterCreator;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

public class GenerateMonsterStoryListener implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		MonsterModel monster = (MonsterModel) delegateTask.getVariable("thisMonster");
		delegateTask.setVariable("storyText", monster.getMonsterStory());
	}
}
