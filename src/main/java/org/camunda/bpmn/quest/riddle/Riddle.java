package org.camunda.bpmn.quest.riddle;

import java.util.ArrayList;
import java.util.List;

public class Riddle {
	private String question;
	private List<String> possibleAnswers = new ArrayList<String>();
	private String answer;
	
	private int experiencePoints;
	
	public Riddle() {
		
	}
	
	public Riddle(String question, String answer, String answer1, String answer2, String answer3, String answer4, int xp) {
		this.question = question;
		this.answer = answer;
		this.possibleAnswers.add(answer1);
		this.possibleAnswers.add(answer2);
		this.possibleAnswers.add(answer3);
		this.possibleAnswers.add(answer4);
		this.experiencePoints = xp;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public void addPossibleAnswer(String answer) {
		this.possibleAnswers.add(answer);
	}

	public int getExperiencePoints() {
		return experiencePoints;
	}

	public void setExperiencePoints(int experiencePoints) {
		this.experiencePoints = experiencePoints;
	}

	public List<String> getPossibleAnswers() {
		return possibleAnswers;
	}

	public void setPossibleAnswers(List<String> possibleAnswers) {
		this.possibleAnswers = possibleAnswers;
	}
	
}
