package com.wugengkj.dance.controller;


import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by zwl on 2018/5/11.
 * May god bless me
 */
//@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
    private MockMvc mockMvc; // 模拟MVC对象，通过MockMvcBuilders.webAppContextSetup(this.wac).build()初始化。

    @Autowired
    private WebApplicationContext wac; // 注入WebApplicationContext

    @Autowired
    private MockHttpSession session;// 注入模拟的http session

    @Autowired
    private MockHttpServletRequest request;// 注入模拟的http request\

    @Before // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    @Test
    public void _status() throws Exception {

        MvcResult result = mockMvc.perform(post("/user/status").param("code","111"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
        /**
          成功
         {
         "code":1,
         "msg":"success",
         "data":0
         }


         */
    }

    @Test
    public void _post() throws Exception {
        MvcResult result = null;

            result = mockMvc.perform(post("/user/post")
                    .param("code", "111")
                    .param("name", "单元测试时的名字")
                    .param("age", "19")
                    .param("sex", "测试男")
                    .param("phone", "单元测试电话")
                    .param("qq", "单元测试qq")
            )
                    .andExpect(status().isOk())// 模拟向testRest发送get请求
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                    .andReturn();// 返回执行请求的结果


        System.out.println(result.getResponse().getContentAsString());
        /**
         成功

         {
         "code":1,
         "msg":"success",
         "data":true
         }

         */
    }

    @Test
    public void _ticket() throws Exception {
        MvcResult result = mockMvc.perform(post("/user/ticket").param("code","111"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
        /**
        成功
         {
         "code":1,
         "msg":"success",
         "data":{
         "id":1,
         "name":"测试票",
         "content":"测试票内容",
         "detail":"测试票内容详细",
         "total":"111111",
         "currentNum":1111,
         "createTime":1525972004000,
         "validTime":1525972004000
         }
         }



         */

    }

}