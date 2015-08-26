package org.camunda.bpmn.quest.CharacterCreator;

import static org.camunda.spin.Spin.JSON;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;


public class NameEditableFieldsUtil 
{
	
	Object javaObject;
	
	
	public NameEditableFieldsUtil() {
		// TODO Auto-generated constructor stub
	}
	
	/*[{
	   key: "playerCharacter.name",
	   label: "Name"
	  },
	  {
	   key: "playerCharacter.strength",
	   label: "Strength"
	  },
	  ...
	  {
	   key: "playerCharacter.luck",
	   label: "Luck"
	  }
	]*/


	public String getEditableFieldsInJson(Vector<String> fieldsList, String variableName)
	{
		HashMap<String, String> nameWithField = new HashMap<String, String>();
		for (String myValue : fieldsList) {
		    String newName = variableName + "." + myValue;
		    
		    nameWithField.put(newName, capitalize(myValue));
 
		}
		
		 String json = JSON(nameWithField).toString();
		    
		 return json;
		
		
	}

	private String capitalize(String myValue) {

		String output = myValue.substring(0, 1).toUpperCase() + myValue.substring(1);
		
		return output;
	}

}
