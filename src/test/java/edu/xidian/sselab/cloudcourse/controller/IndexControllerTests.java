package edu.xidian.sselab.cloudcourse.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IndexControllerTests {
    
    @Autowired
    private MockMvc mvc;
    
    @Test
    public void IndexTest() throws Exception {
        mvc.perform(get("/").accept(MediaType.TEXT_HTML))
            .andExpect(status().isOk());
    }
    
    @Test
    public void BMapTest() throws Exception {
        mvc.perform(get("/bmap").accept(MediaType.TEXT_HTML))
            .andExpect(status().isOk());
    }
}
