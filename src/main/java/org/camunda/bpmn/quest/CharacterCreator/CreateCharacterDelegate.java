package org.camunda.bpmn.quest.CharacterCreator;

import java.util.Vector;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

public class CreateCharacterDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception 
	{
		CharacterModel defaulChar = new CharacterModel("Hero!");
		
//		ObjectValue defaultCharSerialized = Variables.objectValue(defaulChar)
//				  .serializationDataFormat(Variables.SerializationDataFormats.JAVA)
//				  .create();
		

		ObjectValue defaultCharSerialized =
				Variables.objectValue(defaulChar).serializationDataFormat("application/json").create();
		
		String storyText = "Welcome to BPMN Quest! First thing's first, you need to create a character. Start by choosing a name and then move onto adding"
				+ "your stats. All stats are editiable except for life points and in the end all of your stats must add up to 350. Good luck!";
		
		Vector<String> fields = new Vector<String>();
		fields.add("characterName");
		fields.add("strength");
		fields.add("perception");
		fields.add("endurance");
		fields.add("charisma");
		fields.add("inteligance");
		fields.add("agility");
		fields.add("luck");
		
		NameEditableFieldsUtil nefUtil = new NameEditableFieldsUtil();
		
		String editableFields = nefUtil.getEditableFieldsInJson(fields, "playerCharacter");
		
		System.out.println(editableFields);
		
		execution.setVariable("storyText", storyText);
		
		execution.setVariable("editableFields", editableFields);
		execution.setVariable("playerCharacter", defaultCharSerialized);

	}

}
