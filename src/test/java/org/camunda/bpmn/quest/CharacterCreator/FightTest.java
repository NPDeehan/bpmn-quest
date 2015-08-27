package org.camunda.bpmn.quest.CharacterCreator;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.init;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test case starting an in-memory database-backed Process Engine.
 */
public class FightTest {

  @Rule
  public ProcessEngineRule rule = new ProcessEngineRule();

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
   * Riddle Test
   */
  @Test
  @Deployment(resources = {"fight.bpmn"})
  public void testRiddle() {
    
	    HashMap variables = new HashMap();
	    
	    CharacterModel player = new CharacterModel("jakob", "Jakob the Hero", 50, 50, 50, 50, 50, 50, 50, 50);
	    variables.put("playerCharacter", player);

	    	  // Given we create a new process instance
	    ProcessInstance processInstance = rule.getRuntimeService().startProcessInstanceByKey("fight", variables);

	    Task task = rule.getTaskService().createTaskQuery().singleResult();
	    
	    assertEquals("Encounter Monster", task.getName());
	    rule.getTaskService().setVariable(task.getId(), "decision", "Fight to Death!");

	    rule.getTaskService().complete(task.getId());

	    task = rule.getTaskService().createTaskQuery().singleResult();
	    rule.getTaskService().complete(task.getId());
	  
  }
  

}
