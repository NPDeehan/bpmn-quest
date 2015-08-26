package org.camunda.bpmn.quest.CharacterCreator;

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
		
		execution.setVariable("playerCharacter", defaultCharSerialized);

	}

}
