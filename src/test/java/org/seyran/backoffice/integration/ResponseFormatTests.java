package org.seyran.backoffice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ResponseFormatTests {
    @Autowired
    private MockMvc mvc;

    @Test
    void whenContentTypeIsApplicationJsonThenReturnsJson() throws Exception {

        MvcResult result = mvc.perform(get("/brands")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String responseAsString = result.getResponse().getContentAsString();
        boolean json = isJSONValid(responseAsString);

        Assertions.assertTrue(json, "Have to be response in JSON format");
    }

    @Test
    void whenAcceptIsApplicationXmlThenReturnsXml() throws Exception {

        MvcResult result = mvc.perform(get("/brands")
                .accept("application/xml"))
                .andExpect(status().isOk())
                .andReturn();
        String responseAsString = result.getResponse().getContentAsString();

        boolean xml = isXml(responseAsString);

        Assertions.assertTrue(xml, "Have to be response in XML format");
    }

    private boolean isXml(String responseAsString) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(new StringReader(responseAsString)));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean isJSONValid(String test) {
        try {
            Map map = new ObjectMapper().readValue(test, Map.class);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}
