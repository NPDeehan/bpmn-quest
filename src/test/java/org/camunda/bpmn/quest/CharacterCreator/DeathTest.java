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

/**
 * Test case starting an in-memory database-backed Process Engine.
 */
public class DeathTest {

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
  @Ignore
  @Deployment(resources = {"adventure.bpmn"})
  public void testDeath() {
    
	    HashMap variables = new HashMap();
	    
	    CharacterModel player = new CharacterModel("jakob", "Jakob the Hero", 50, 50, 50, 50, 50, 50, 50, 50);
	    variables.put("playerCharacter", player);

	    	  // Given we create a new process instance
	    ProcessInstance processInstance = rule.getRuntimeService().startProcessInstanceByKey("adventure", variables);
		  
	    
	    rule.getRuntimeService().createProcessInstanceModification(processInstance.getId())
		  .startBeforeActivity("Task_1")
		  .cancelAllForActivity("UserTask_1lkce2d")
		  .execute();
		  
	    Task task = rule.getTaskService().createTaskQuery().singleResult();
	    
	    assertEquals("Moan Death", task.getName());

	    rule.getTaskService().complete(task.getId());

	  
  }
  

}
