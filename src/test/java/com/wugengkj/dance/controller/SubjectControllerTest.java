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
public class SubjectControllerTest {
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
    public void _get() throws Exception {

        MvcResult result = mockMvc.perform(post("/subject/get").param("code","111"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
        /**



         */
    }

    @Test
    public void _post() throws Exception {
        String subject="[\"1\":\"A\",\"2\":\"B\",\"3\":\"B\",\"4\":\"C\"]";
        MvcResult result = mockMvc.perform(post("/subject/post")
                .header("code","111")

        )
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
        /**
         参数
         .param("code","111")
         .param("subject","{\"1\":\"A\",\"2\":\"B\",\"3\":\"B\",\"4\":\"C\"}")

         {
         "code":0,
         "msg":"fail",
         "data":{
         "code":"2004",
         "error":"HTTP错误",
         "message":"无法匹配请求参数，请检查后重试",
         "detail":"org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException",
         "exception":"null:interface java.util.List",
         "path":""
         }
         }

        参数

         ["1":"A","2":"B","3":"B","4":"C"]
         {
         "code":0,
         "msg":"fail",
         "data":{
         "code":"2004",
         "error":"HTTP错误",
         "message":"无法匹配请求参数，请检查后重试",
         "detail":"org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException",
         "exception":"null:interface java.util.List",
         "path":""
         }
         }


         */
    }

}