package org.camunda.bpmn.quest.CharacterCreator;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

public class GenerateMonsterStoryListener implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {

		MonsterModel monster = (MonsterModel) delegateTask.getVariable("thisMonster");
		
		StoryModel story = new StoryModel("You Come Accross "+monster.getCharacterName(), monster.getMonsterStory() + "\n What do you want to do?");
		story.addOption("Fight");
		story.addOption("Sneak");
		
		
		//delegateTask.setVariable("storyText", monster.getMonsterStory());
		
		ObjectValue storySerialized =
				Variables.objectValue(story).serializationDataFormat("application/json").create();
		
		delegateTask.setVariable("storyText", storySerialized);
		
//		Map<String, String> fields = new HashMap<String, String>();
//		fields.put("startFight", "Do you want to fight the monster? (YES/NO)");
//	
//		NameEditableFieldsUtil nefUtil = new NameEditableFieldsUtil();
//		
//		// this returns the json of the fields
//		String editableFields = nefUtil.getEditableFieldsInJson(fields, null);
//
//		delegateTask.setVariable("editableFields", editableFields);

	}
}
