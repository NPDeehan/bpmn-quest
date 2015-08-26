package org.camunda.bpmn.quest.CharacterCreator;

import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.engine.impl.util.LogUtil;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.Deployment;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
import static org.junit.Assert.*;

/**
 * Test case starting an in-memory database-backed Process Engine.
 */
public class InMemoryH2Test {

  @Rule
  public ProcessEngineRule rule = new ProcessEngineRule();

  private static final String PROCESS_DEFINITION_KEY = "CharacterCreator";

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
  @Test
  @Deployment(resources = "createcharacter.bpmn")
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
	    
	    assertEquals("Fight or Flee", task.getName());
	    rule.getTaskService().setVariable(task.getId(), "startFight", true);
	    
	    rule.getTaskService().complete(task.getId());
	    
	    // Then the process instance should be ended
//	    assertThat(processInstance).isEnded();

	  
	  
  }

}
