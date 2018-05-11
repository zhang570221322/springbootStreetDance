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
 * 测试BussinessController类 算了，看类名都知道了
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class BusinessControllerTest {

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
    public void info() throws Exception {


        MvcResult result = mockMvc.perform(post("/business/info").param("code","111"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
    }
    /**

     没有参数

     {
     "code": 0,
     "msg": "fail",
     "data": {
     "code": "2002",
     "error": "HTTP错误",
     "message": "缺少请求参数，请填写完整请求参数",
     "detail": "org.springframework.web.bind.MissingServletRequestParameterException",
     "exception": "code",
     "path": ""
     }
     }

     code=1111

     {
     "code": 1,
     "msg": "success",
     "data": {
     "id": 1,
     "name": "默认商家1",
     "appid": null,
     "content": "默认内容",
     "detail": "默认细节",
     "regular": "默认规则",
     "address": "默认地址",
     "ticketAddress": "默认领票地址",
     "totalTicket": null,
     "surplusTicket": null,
     "createTime": 1525925971000
     }
     }

     */








}