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


        MvcResult result = mockMvc.perform(post("/business/info"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
    }
    /**

     没有参数

     {
     "ret":"200",
     "msg":"success",
     "data":{
     "id":1,
     "name":"默认商家",
     "appid":null,
     "content":"商家活动内容信息",
     "detail":"商家活动详细描述信息",
     "regular":"商家活动规则",
     "address":"商家活动地址",
     "ticketAddress":"商家活动领票地址",
     "totalTicket":1000,
     "surplusTicket":1000,
     "createTime":"2018-05-11 04:20:50"
     }
     }

     */








}