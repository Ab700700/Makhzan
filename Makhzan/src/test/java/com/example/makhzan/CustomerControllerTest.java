package com.example.makhzan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


/////////////////////////
import com.example.makhzan.Controller.CustomerController;
import com.example.makhzan.DTO.CustomerDTO;
import com.example.makhzan.Model.Customer;
import com.example.makhzan.Model.User;
import com.example.makhzan.Service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CustomerController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class CustomerControllerTest {
    @MockBean
    CustomerService customerService;

    @Autowired
    MockMvc mockMvc;

    CustomerDTO customerDTO1;
    User user;

    @BeforeEach
    void setUp() {
        user = new User(1, "Maha", "123456","fdv@gmail.com","Maha", "ADMIN", "1234567890",null,null);
        customerDTO1 = new CustomerDTO(1,
                "customer1",
                user.getPassword(),
                user.getEmail(),
                user.getName(),
                user.getRole(),
                user.getPhonenumber(),
                LocalDate.parse("1997-03-27"));
    }


    @Test
    public void testRegister() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mockMvc.perform(post("/api/v1/makhzan/customer/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(customerDTO1)))
                .andExpect(status().isOk());
    }


    @Test
    public void testDeleteCustomer() throws Exception {
        mockMvc.perform(delete("/api/v1/makhzan/customer/delete"))
                .andExpect(status().isOk());
    }

    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(post("/api/v1/makhzan/customer/login"))
                .andExpect(status().isOk());
    }

    @Test
    public void testLogout() throws Exception {
        mockMvc.perform(post("/api/v1/makhzan/customer/logout"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mockMvc.perform(put("/api/v1/makhzan/customer/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(customerDTO1)))
                .andExpect(status().isOk());
    }
}
