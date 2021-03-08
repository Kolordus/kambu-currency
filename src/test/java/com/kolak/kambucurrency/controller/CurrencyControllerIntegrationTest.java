package com.kolak.kambucurrency.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kolak.kambucurrency.model.Currency;
import com.kolak.kambucurrency.model.PersistedRequest;
import org.flywaydb.core.Flyway;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Objects;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CurrencyControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    Flyway flyway;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void shouldReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/all-available-currencies"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]", Is.is("GBP")))
                .andReturn();
    }


    @Test
    public void shouldThrowErrorNotSupportedCurrency() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/convert?amount=100&base=gbp&desired=euras"))
                .andExpect(MockMvcResultMatchers.status().is(404))
                .andReturn();

        String actual = Objects.requireNonNull(mvcResult.getResolvedException()).getMessage();
        String expected = "euras is not supported currency";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldThrowErrorAmountMustBePositive() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/convert?amount=0&base=gbp&desired=eur"))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andReturn();

        String actual = Objects.requireNonNull(mvcResult.getResolvedException()).getMessage();
        String expected = "Amount must be a positive number";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldSaveRequestsAndReturnThem() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/convert?amount=10&base=gbp&desired=eur"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/convert?amount=20&base=gbp&desired=pln"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/convert?amount=3&base=gbp&desired=isk"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/convert?amount=3&base=gbp&desired=usd"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/convert?amount=10&base=gbp&desired=gbp"));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/all-requests"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();


        PersistedRequest[] persistedRequests = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PersistedRequest[].class);

        int expectedAmountOfRequests = 6; // last request is saved as well!
        String expectedCurrency = "ISK";
        double expectedAmount = 10;

        Assertions.assertEquals(expectedAmountOfRequests, persistedRequests.length);
        Assertions.assertEquals(expectedCurrency, persistedRequests[2].getDesiredCurrenciesResponse().keySet().iterator().next());
        Assertions.assertEquals(expectedAmount, persistedRequests[4].getAmount());

    }


    @AfterEach
    public void cleanDB() {
        flyway.clean();
        flyway.migrate();
    }

}
