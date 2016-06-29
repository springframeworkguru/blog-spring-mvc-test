package guru.springframework.controllers;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(IndexController.class)
public class IndexControllerTest {
    /*Autowiring MockMvc not working*/
      /*  @Autowired*/


       private MockMvc mockMvc;
       @Before
        public void setUp() {
            mockMvc = MockMvcBuilders.standaloneSetup(new IndexController()).build();
        }
        @Test
        public void testIndex() throws Exception{

            MvcResult result= this.mockMvc.perform(get("/"))
                      .andExpect(view().name("index"))
                 //   .andExpect(content().string(Matchers.containsString("Hello")))
                    .andReturn();
            String content = result.getResponse().getContentAsString();
            assertNotNull(content);
            System.out.println("Content: "+content);
            //assertEquals("Hello", content);
        }
  }
