package com.farukcankaya.springcase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SpringCaseApplication {

  @RequestMapping("/")
  @ResponseBody
  String home() {
    return "<html><head><title>Springcase</title><body><h1>Springcase</h1><p>Available endpoints:</p><p><ul><li><a href=\"http://localhost:8090/v1/campaigns\">GET /v1/campaigns</a></li><li><a href=\"http://localhost:8090/v1/campaigns/1\">GET /v1/campaigns/:campaign_id</a></li><li>POST /v1/campaigns</li><li>PUT /v1/campaigns/:campaign_id</li><li>DELETE /v1/campaigns/:campaign_id</li></ul></p><p></p><p>In memory database management: <a href=\"http://localhost:8090/h2\">localhost:8090/h2</a></p><p></p><p>Postman Collaction:<br /><a href=\"https://www.getpostman.com/collections/486580ae3f27027d30fa\">https://www.getpostman.com/collections/486580ae3f27027d30fa</a><br />You need to add environment variable named <strong>host</strong> with <strong>localhost:8090/v1</strong value to use postman collection.> </p><p></p></body></html>";
  }

  public static void main(String[] args) {
    SpringApplication.run(SpringCaseApplication.class, args);
  }
}
