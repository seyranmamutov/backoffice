package org.seyran.backoffice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.seyran.backoffice.controller.brand.dto.CreateBrandReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BrandControllerTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenCorrectDataProvidedThenReturns200() throws Exception {


        CreateBrandReq createBrandReq = new CreateBrandReq();
        createBrandReq.setName("SomeCorrectName");

        String json = objectMapper.writeValueAsString(createBrandReq);


        MvcResult result = mvc.perform(post("/brands")
                .content(json)
                .contentType("application/json"))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void whenBrandNameIsEmptyThenReturns400() throws Exception {


        CreateBrandReq createBrandReq = new CreateBrandReq();
        createBrandReq.setName("");

        String json = objectMapper.writeValueAsString(createBrandReq);


        MvcResult result = mvc.perform(post("/brands")
                .content(json)
                .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

}
