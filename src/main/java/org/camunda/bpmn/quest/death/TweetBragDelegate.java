package org.camunda.bpmn.quest.death;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpmn.quest.CharacterCreator.CharacterModel;
import org.camunda.bpmn.quest.CharacterCreator.StoryModel;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TweetBragDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		Date date = new Date();
		SimpleDateFormat dt = new SimpleDateFormat("dd MM hh:mm");
		String dateString = dt.format(date);
		
		CharacterModel player = (CharacterModel) execution.getVariable("playerCharacter");
		String tweet = player.getCharacterName() + " has played #bpmnQuest and reached " + player.getExperiencePoints() + " XP. " + dateString;
		
		System.out.println ("now tweeting: " + tweet);
		
		// Twitter Authentication with Demo User
	    AccessToken accessToken = new AccessToken("220324559-jet1dkzhSOeDWdaclI48z5txJRFLCnLOK45qStvo", "B28Ze8VDucBdiE38aVQqTxOyPc7eHunxBVv7XgGim4say");
	    Twitter twitter = new TwitterFactory().getInstance();
	    twitter.setOAuthConsumer("lRhS80iIXXQtm6LM03awjvrvk", "gabtxwW8lnSL9yQUNdzAfgBOgIMSRqh7MegQs79GlKVWF36qLS");
	    twitter.setOAuthAccessToken(accessToken);    

		twitter.updateStatus(tweet);

		StoryModel story = new StoryModel();
		story.setTitle("Thank you for playing!");
		story.setDescription("It was great fun - at least it was great fun to create this game, which happened in 2 days at the 2015 Camunda Hackdays. See you next year :-)" );
		story.setPicture("http://ec2-52-19-141-24.eu-west-1.compute.amazonaws.com:8080/CharacterCreator/team.jpg");
		
		ObjectValue storySerialized =
				Variables.objectValue(story).serializationDataFormat("application/json").create();
		
		execution.setVariable("storyText", storySerialized);		

	}

}
