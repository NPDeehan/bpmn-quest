package org.camunda.bpmn.quest.storyItem;

import java.util.Arrays;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpmn.quest.CharacterCreator.StoryModel;

public class StoryItemDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		String storyTitle = (String) execution.getVariable("storyTitle");
		String storyText = (String) execution.getVariable("storyText");
		String storyPicture = (String) execution.getVariable("storyPicture");
		String decisionOptions = (String) execution.getVariable("decisionOptions");
		
	
		StoryModel story = new StoryModel();
		
		story.setTitle(storyTitle);
		story.setDescription(storyText);
		story.setPicture(storyPicture);

		if (decisionOptions != null) {
			List<String> decisionOptionsList = Arrays.asList(decisionOptions.split("\\s*,\\s*"));
			story.setOptions(decisionOptionsList);
		}
		
		
		ObjectValue storySerialized =
				Variables.objectValue(story).serializationDataFormat("application/json").create();
		
		execution.setVariable("storyText", storySerialized);		
	}

}
