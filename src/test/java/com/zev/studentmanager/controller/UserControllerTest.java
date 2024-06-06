package com.zev.studentmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zev.studentmanager.dto.request.UpdateUserInfoRequest;
import com.zev.studentmanager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.Date;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UpdateUserInfoRequest request;

    @BeforeEach
    void initData(){

        request = UpdateUserInfoRequest.builder()
                .firstName("Tran Thi")
                .lastName("Hoa")
                .email("tranthihoa@gmail.com")
                .phone("0943030678")
                .dateOfBirth(new Date(2011-11-2))
                .build();
    }


//    @Test
//    void updateUserInfo_invalidatedInput() throws Exception {
//
//        // input
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        String content = objectMapper.writeValueAsString(request);
//
//        Mockito.when(userService.updateUserInformation());
//
//        // when
//        mockMvc.perform(
//                MockMvcRequestBuilders.post("/user/update/{id}")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(content))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("code").value(200));
//    }

}
