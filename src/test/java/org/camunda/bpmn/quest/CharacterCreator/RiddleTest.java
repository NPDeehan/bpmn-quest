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
public class RiddleTest {

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
  @Deployment(resources = {"riddle.bpmn"})
  public void testRiddle() {
    
	  // Given we create a new process instance
	    ProcessInstance processInstance = rule.getRuntimeService().startProcessInstanceByKey("riddle");

	    Task task = rule.getTaskService().createTaskQuery().singleResult();
	    
	    assertEquals("Present Riddle", task.getName());
	    rule.getTaskService().setVariable(task.getId(), "decision", "The Timer Event");

	    rule.getTaskService().complete(task.getId());

	    task = rule.getTaskService().createTaskQuery().singleResult();
	    rule.getTaskService().complete(task.getId());
	  
  }
  

}
