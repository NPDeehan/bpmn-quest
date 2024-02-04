package org.camunda.bpmn.quest.CharacterCreator;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.init;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

public class SuperStoryTest {
	
	@Rule
	  public ProcessEngineRule rule = new ProcessEngineRule();

	 // enable more detailed logging
	  static {
//	    LogUtil.readJavaUtilLoggingConfigFromClasspath(); // process engine
//	    LogFactory.useJdkLogging(); // MyBatis
	  }

	  @Before
	  public void setup() {
	    init(rule.getProcessEngine());
	  }

	  
	  /**
	   * Kick in the nuts Test
	   */
	  //@Test
	  @Deployment(resources = {"story-adventure.bpmn", "fight.bpmn"})
	  public void testBallKicking() {
	    
		    HashMap<String, Object> variables = new HashMap<String, Object>();
		    
		    CharacterModel player = new CharacterModel("niall", "Niall Rawks", 50, 50, 50, 50, 50, 50, 50, 50);
		    variables.put("playerCharacter", player);

		    	  // Given we create a new process instance
		    ProcessInstance processInstance = rule.getRuntimeService().startProcessInstanceByKey("superStory", variables);
			  
		    
		    rule.getRuntimeService().createProcessInstanceModification(processInstance.getId())
			  //.startBeforeActivity("Task_1")
			  //.cancelAllForActivity("UserTask_1lkce2d")
			  .execute();
			  
		    Task task = rule.getTaskService().createTaskQuery().singleResult();		    
		    assertEquals("Read Opening Dialog", task.getName());
		    variables.put("decision", "Help");		    
		    rule.getTaskService().complete(task.getId(), variables);
		    
		    //Next Task = Pick a way to save the guy!
		    
		    task = rule.getTaskService().createTaskQuery().singleResult();	    
		    assertEquals("Pick a way to save the guy!", task.getName());
		    variables.put("decision", "Kick their Leader in the Balls");		    
		    rule.getTaskService().complete(task.getId(), variables);
		    
		    //Next Task - Peaceful Resolution
		    
		    task = rule.getTaskService().createTaskQuery().singleResult();	    
		    assertEquals("View Results", task.getName());
		    //variables.put("decision", "Kick their Leader in the Balls");		    
		    rule.getTaskService().complete(task.getId());
		    
		    //Next Task - Peaceful Resolution
		    
		    task = rule.getTaskService().createTaskQuery().singleResult();	    
		    assertEquals("Peaceful Resolution", task.getName());
		    //variables.put("decision", "Kick their Leader in the Balls");		    
		    rule.getTaskService().complete(task.getId());
		    
		  
	  }
	  


}
