package org.camunda.bpmn.quest.CharacterCreator;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

public class GenerateFightReviewStoryListener implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {

		CharacterModel  charMod = (CharacterModel) delegateTask.getVariable("playerCharacter");
		String storytext = charMod.getCharacterName() + " finds a bit of trouble \n "
				+ "As you make your way to a local pub for a well deserved drink you come accross a group of thugs beating up an innocent marketing manager."
				+ "While gernally you're not to fond of marketing people, you feel maybe if you help he'll learn a lesson and perhaps re-skill to become"
				+ " something useful to society... or you could just gab some popcorn and watch the action! \n "
				+ "To help the marketing manager type 'Help' to ignore him type 'Keep Moving' ";

		// this is a map of fields that the user can edit 
		// it contains the name of the variable and the name of the lable
		Map<String, String> fields = new HashMap<String, String>();
		fields.put("characterChoice", "Make your call");
	
		NameEditableFieldsUtil nefUtil = new NameEditableFieldsUtil();
		
		// this returns the json of the fields
		String editableFields = nefUtil.getEditableFieldsInJson(fields, null);

		delegateTask.setVariable("editableFields", editableFields);
		delegateTask.setVariable("storytext", storytext);
		delegateTask.setVariable("characterChoice", "Help"); // this is a default that can be edited by the user
	}
}
