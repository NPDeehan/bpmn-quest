package org.camunda.bpmn.quest.CharacterCreator;

import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.engine.impl.util.LogUtil;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.Deployment;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
import static org.junit.Assert.*;

/**
 * Test case starting an in-memory database-backed Process Engine.
 */
public class InMemoryH2Test {

  @Rule
  public ProcessEngineRule rule = new ProcessEngineRule();

  private static final String PROCESS_DEFINITION_KEY = "adventure";

  // enable more detailed logging
  static {
//    LogUtil.readJavaUtilLoggingConfigFromClasspath(); // process engine
//    LogFactory.useJdkLogging(); // MyBatis
  }

  @Before
  public void setup() {
    init(rule.getProcessEngine());
  }

  /**
   * Just tests if the process definition is deployable.
   */
  @Ignore
  @Test
  @Deployment(resources = {"adventure.bpmn", "fight.bpmn"})
  public void testParsingAndDeployment() {
    
	  // Given we create a new process instance
	    ProcessInstance processInstance = rule.getRuntimeService().startProcessInstanceByKey(PROCESS_DEFINITION_KEY);
	    // Then it should be active
	    assertThat(processInstance).isActive();
	    

	    // Create Character Task
	    Task task = rule.getTaskService().createTaskQuery().singleResult();
	    
	    assertEquals("Create Your Character", task.getName());
	    rule.getTaskService().complete(task.getId());

	    // Fight or Flee Task
	    task = rule.getTaskService().createTaskQuery().singleResult();
	    
//	    assertEquals("Fight or Flee", task.getName());
//	    rule.getTaskService().setVariable(task.getId(), "startFight", true);
//	    
//	    rule.getTaskService().complete(task.getId());
	    
	    // Then the process instance should be ended
//	    assertThat(processInstance).isEnded();

	  
	  
  }
  
  @Test
  @Deployment(resources = {"adventure.bpmn", "fight.bpmn"})
  public void testFighting() {
	  
	  String businessKey =  "theBuzKey";
	  Map<String, Object> vars = new HashMap<String, Object>();
	  
	   ProcessInstance processInstance = rule.getRuntimeService().startProcessInstanceByKey(PROCESS_DEFINITION_KEY, businessKey);
	    // Then it should be active
	    assertThat(processInstance).isActive();
	    

	    // Create Character Task
	    Task task = rule.getTaskService().createTaskQuery().singleResult();
	    
	    assertEquals("Create Your Character", task.getName());
	    
	    //StoryModel story = rule.getRuntimeService().getVariable(executionId, variableName)
	    
	    rule.getTaskService().complete(task.getId());

	    // Fight or Flee Task
	    task = rule.getTaskService()
	    		.createTaskQuery()
	    		.processInstanceBusinessKey(businessKey)
	    		.singleResult();
	    
	    // This is the encounter monster task
	    assertEquals("Encounter Monster", task.getName());
	    
	    StoryModel story = (StoryModel) rule.getRuntimeService().getVariable(task.getExecutionId(), "storyText");
	    System.out.println(story);
	    
	    CharacterModel player = (CharacterModel) rule.getRuntimeService().getVariable(task.getExecutionId(), "playerCharacter");
	    System.out.println("Player's life points are: "+ player.getLifePoints());
	    
	    vars.put("startFight", "YES");
	    
	    rule.getTaskService().complete(task.getId(),vars );
	    
	    // This is the review fight task
	    task = rule.getTaskService()
	    		.createTaskQuery()
	    		.processInstanceBusinessKey(businessKey)
	    		.singleResult();
	    
	    assertEquals("Review Fight", task.getName());
	    
	    player = (CharacterModel) rule.getRuntimeService().getVariable(task.getExecutionId(), "playerCharacter");
	    System.out.println("Player's life points after fighting are: "+ player.getLifePoints());
	    // #{fightOutcome=='died'}
	    String outcome = (String) rule.getRuntimeService().getVariable(task.getExecutionId(), "fightOutcome");
	    
	    rule.getTaskService().complete(task.getId());
	    
	    if(outcome.equals("died"))
	    {
	    	task = rule.getTaskService()
	 	    		.createTaskQuery()
	 	    		.processInstanceBusinessKey(businessKey)
	 	    		.singleResult();
	 	    
	 	    assertEquals("Moan Death", task.getName());
	    	
	    }else if (outcome.equals("survived"))
	    {
	    	 task = rule.getTaskService()
	 	    		.createTaskQuery()
	 	    		.processInstanceBusinessKey(businessKey)
	 	    		.singleResult();
	 	    
	 	    assertEquals("Celebrate Survival", task.getName());

	    }
	    
  }

}
