package org.camunda.bpmn.quest.storyItem;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpmn.quest.CharacterCreator.StoryModel;

public class StoryItemDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		String storyTitle = (String) execution.getVariable("storyTitle");
		
		
		StoryModel story = new StoryModel();
		story.setTitle(storyTitle);
		
		ObjectValue storySerialized =
				Variables.objectValue(story).serializationDataFormat("application/json").create();
		
		execution.setVariable("storyText", storySerialized);		
	}

}
