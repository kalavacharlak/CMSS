package com.broadridge.cmss;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:test-servlet-context.xml")
public class ACHInCreateTest {
	
	  @Autowired
	  private WebApplicationContext wac;

	  private MockMvc mockMvc;

	  @Before
	  public void setup() {
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	  }
	  
	  @Test
	  public void testACHInCreate() throws Exception {
		  this.mockMvc.perform(get("/achin/create")
					.accept(MediaType.APPLICATION_XML))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_XML))
				.andExpect(xpath("/response/message").string("SUCCESS"));
	  }
	

}