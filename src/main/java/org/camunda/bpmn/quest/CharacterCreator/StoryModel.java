package org.camunda.bpmn.quest.CharacterCreator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class StoryModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	String title = "";
	String storyText = "";
	String picture = "";
	List<String> actionLog = new ArrayList<String>();
	List<String> options = new ArrayList<String>();
	
		
	
	public StoryModel() {
		super();
	}


	public StoryModel(String title, String description) {
		super();
		this.title = title;
		this.storyText = description;
		
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return storyText;
	}
	public void setDescription(String description) {
		this.storyText = description;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public List<String> getFightLog() {
		return actionLog;
	}
	public void setFightLog(List<String> fightLog) {
		this.actionLog = fightLog;
	}
	public List<String> getOptions() {
		return options;
	}
	public void setOptions(List<String> options) {
		this.options = options;
	}
	public void addOption(String option)
	{
		this.options.add(option);
	}

}
