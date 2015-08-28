package org.camunda.bpmn.quest.superStoryQuestOne;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpmn.quest.CharacterCreator.CharacterModel;
import org.camunda.bpmn.quest.CharacterCreator.StoryModel;

public class ChooseBossDelegate implements JavaDelegate {

	CharacterModel player;
	StoryModel theStory;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		this.player = (CharacterModel) execution.getVariable("playerCharacter");
		this.theStory = new StoryModel();
		
		String choice = (String) execution.getVariable("decision");
		
		if(choice.equals("Just Attack!"))
		{
			generateAttckStory();
			execution.setVariable("requestedMonsterId", "jakob");
			
		}else if (choice.equals("Do Something Smart"))
		{
			generateSmartStory();
			execution.setVariable("requestedMonsterId", "sebastian");

		}
		else if(choice.equals("Punch At Random")){
			generatRandomStory();
			execution.setVariable("requestedMonsterId", "niall");
		}
		else if(choice.equals("Run Away")){
			generatRunStory();
			execution.setVariable("requestedMonsterId", "paddy");
		}
		
		ObjectValue storySerialized =
				Variables.objectValue(this.theStory).serializationDataFormat("application/json").create();
		
		execution.setVariable("storyText", storySerialized);

	}
	
	private void generatRunStory() {
		String storytext = "Running away from the final boss was not a good idea - he's found you and he wants to kill you now more than EVER!";
		
		this.theStory = new StoryModel("Terrible Idea!",  storytext );
		theStory.addOption("Continue");
		theStory.setPicture("http://ec2-52-19-141-24.eu-west-1.compute.amazonaws.com:8080/CharacterCreator/monsters/img/nutskick.jpg");
		
		player.addExperiencePoints(55);
		
		
	}

	private void generatRandomStory() {
		String storytext = "You randomly hit a guy in the face - poor guy - but now he wants to fight you to the death.. not the BEST final boss but "
				+ "he's all you got!  ";
		
		this.theStory = new StoryModel("You Punched a Guy!",  storytext );
		theStory.addOption("Continue");
		theStory.setPicture("http://ec2-52-19-141-24.eu-west-1.compute.amazonaws.com:8080/CharacterCreator/monsters/img/nutskick.jpg");
		
		player.addExperiencePoints(55);
		
		
	}

	private void generateSmartStory() {
		String storytext = "You cleverly wait until the evil boss is asleep and then sneak into his house in an attempt to murder him in his sleep."
				+ " Sadly he wakes! it's fight time!";
		
		this.theStory = new StoryModel("Smart Move...",  storytext );
		theStory.addOption("Continue");
		theStory.setPicture("http://ec2-52-19-141-24.eu-west-1.compute.amazonaws.com:8080/CharacterCreator/monsters/img/nutskick.jpg");
		
		player.addExperiencePoints(55);
		
		
	}

	private void generateAttckStory() {
		String storytext = "You decided not to over think things and just go for a wild swining attack... maybe you'll survive this!  ";
		
		this.theStory = new StoryModel("Just attack and accept the consequences!",  storytext );
		theStory.addOption("Continue");
		theStory.setPicture("http://ec2-52-19-141-24.eu-west-1.compute.amazonaws.com:8080/CharacterCreator/monsters/img/nutskick.jpg");
		
		player.addExperiencePoints(55);
		
	}

}
