package org.camunda.bpmn.quest.riddle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpmn.quest.CharacterCreator.StoryModel;

public class MaterializeRiddleDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		List<Riddle> riddles = generateRiddlePool();
		
		// Get Riddle randomly
		Random rn = new Random();
		int randomNumber = rn.nextInt(5);
		Riddle riddle = riddles.get(randomNumber);
		execution.setVariable("thisRiddle", riddle);		

		StoryModel story = new StoryModel();
		story.setTitle("Solve a Riddle");
		story.setDescription(riddle.getQuestion());
		for (Iterator<String> iter = riddle.getPossibleAnswers().iterator(); iter.hasNext(); ) {
		    String element = iter.next();
			story.addOption(element);
		}
		
		ObjectValue storySerialized =
				Variables.objectValue(story).serializationDataFormat("application/json").create();
		
		execution.setVariable("storyText", storySerialized);		
	}

	
	private List<Riddle> generateRiddlePool() {
		List<Riddle> riddles = new ArrayList<Riddle>();
		
		riddles.add(new Riddle(
				"Which BPMN Event Type is only caught and never thrown?",
				"The Timer Event",
				"The Dramatic Event",
				"The Error Event",
				"The Timer Event",
				"The Compensation Event",
				10
				));
		
		riddles.add(new Riddle(
				"What does BPMN stand for?",
				"Business Process Model and Notation",
				"Business Process Model and Notation",
				"Business Process Modeling Notation",
				"Business Process Monster Nation",
				"Business Process Management Notation",
				10
				));

		riddles.add(new Riddle(
				"Which Task Type is not part of BPMN?",
				"Ask Task",
				"Manual Task",
				"Receive Task",
				"Ask Task",
				"Business Rule Task",
				10
				));

		riddles.add(new Riddle(
				"If Task B comes after Task A, and Task A comes after Task B, how can that be?",
				"The two Tasks are modeled as a loop.",
				"The two Tasks are part of an MI Subprocess.",
				"The two Tasks are modeled as a loop.",
				"The two Tasks are both Service Tasks.",
				"There is black Magic involved.",
				10
				));

		riddles.add(new Riddle(
				"An error event, a user task and an Complex Gateway go o holiday. Which one won't come back?",
				"I have too much time.",
				"The error event, because it keeps missing the flight.",
				"the user task, because it's used up.",
				"The Complex Gateway, because no one needs it.",
				"I have too much time.",
				10
				));

		
		return riddles;
	}
}
