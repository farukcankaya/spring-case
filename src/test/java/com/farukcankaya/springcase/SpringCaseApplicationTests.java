package com.farukcankaya.springcase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.MOCK,
  classes = SpringCaseApplication.class
)
public class SpringCaseApplicationTests {

  @Test
  public void contextLoads() {}
}
