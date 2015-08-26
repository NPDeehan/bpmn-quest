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
		
		Vector<String> feilds = new Vector<String>();
		feilds.add("characterName");
		feilds.add("strength");
		feilds.add("perception");
		feilds.add("endurance");
		feilds.add("charisma");
		feilds.add("inteligance");
		feilds.add("agility");
		feilds.add("luck");
		
		NameEditableFieldsUtil nefUtil = new NameEditableFieldsUtil();
		
		String editableFields = nefUtil.getEditableFieldsInJson(feilds, "playerCharacter");
		
		System.out.println(editableFields);
		
		execution.setVariable("storyText", storyText);
		
		execution.setVariable("editiableFeilds", editableFields);
		execution.setVariable("playerCharacter", defaultCharSerialized);

	}

}
