package org.camunda.bpmn.quest.superStoryQuestOne;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpmn.quest.CharacterCreator.CharacterModel;
import org.camunda.bpmn.quest.CharacterCreator.NameEditableFieldsUtil;
import org.camunda.bpmn.quest.CharacterCreator.StoryModel;

public class GenerateIntroDialog implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		CharacterModel  charMod = (CharacterModel) execution.getVariable("playerCharacter");
				
		String storytext = "As you make your way to a local pub for a well deserved drink you come accross a group of thugs beating up an innocent marketing manager."
				+ "While gernally you're not to fond of marketing people, you feel maybe if you help he'll learn a lesson and perhaps re-skill to become"
				+ " something useful to society... or you could just gab some popcorn and watch the action! \n ";
		
		StoryModel newStory = new StoryModel(charMod.getCharacterName() + "finds a bit of trouble",  storytext );
		newStory.addOption("Help");
		newStory.addOption("Ignore");
		
	
		// this is a map of fields that the user can edit 
//		// it contains the name of the variable and the name of the lable
//		Map<String, String> fields = new HashMap<String, String>();
//		fields.put("characterChoice", "Make your call");
//	
//		NameEditableFieldsUtil nefUtil = new NameEditableFieldsUtil();
//		
//		// this returns the json of the fields
//		String editableFields = nefUtil.getEditableFieldsInJson(fields, null);
//
//		
//		execution.setVariable("editableFields", editableFields);
		execution.setVariable("storytext", storytext);
		execution.setVariable("characterChoice", "Help"); // this is a default that can be edited by the user
		
		
	}


}
