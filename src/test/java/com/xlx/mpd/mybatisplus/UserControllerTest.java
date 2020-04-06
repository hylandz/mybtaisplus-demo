package com.xlx.mpd.mybatisplus;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xlx.mpd.system.entity.User;
import com.xlx.mpd.system.enums.GenderEnum;
import com.xlx.mpd.system.enums.UserStatusEnum;
import com.xlx.mpd.system.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @author xielx at 2020/4/5 19:26
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserControllerTest {


    
    @Autowired
    private WebApplicationContext wac;
    
    private MockMvc mockMvc;
    
    @Before
    public void init(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    
    @Test
    public void testQueryUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/list").contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.records.size()").value(10));
    }
    
    @Test
    public void testQueryUserByName() throws Exception {
        String username = "F3860565";
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/user/search").contentType(MediaType.APPLICATION_JSON_UTF8)
                                                         .param("userName", username))
                                         .andExpect(MockMvcResultMatchers.status().isOk())
                                         .andExpect(MockMvcResultMatchers.jsonPath("$.data.user").doesNotExist())
                                         .andReturn().getResponse().getContentAsString();
        log.info("查询用户名为[{}]的结果:{}" ,username,result);
    }
    
    @Test
    public void testGetUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/get").contentType(MediaType.APPLICATION_JSON_UTF8)
                                .param("userName","admin"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.userName").value("admin"));
    }
    
    
    @Test
    public void testInsert() throws Exception {
        User user = new User();
        user.setDeptId(5L)
                .setNickName("倔强的啥子")
                .setAvatarUrl("default.jpg")
                .setUserName("F3860565")
                .setRealName("宝海一")
                .setPassword("123456")
                .setSalt("adadadda")
                .setToken(UUID.randomUUID().toString())
                .setBirth(LocalDate.parse("1996-09-19", DateTimeFormatter.ISO_LOCAL_DATE))
                .setEmail("baohaiyi@qq.com")
                .setPhone("13576637348")
                .setGender(GenderEnum.MALE.getGenderNum());
        String content = JSON.toJSONString(user);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/user/create").contentType(MediaType.APPLICATION_JSON_UTF8)
                                                         .content(content))
                                         .andExpect(MockMvcResultMatchers.status().isOk())
                                         .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                                         .andReturn().getResponse().getContentAsString();
        log.info("新增用户返回的结果:" + result);
    }
    @Test
    public void testUpdate() throws Exception {
        User user = new User();
        user.setUserId(13L)
                .setToken(UUID.randomUUID().toString())
                .setVersion(1);
        String content = JSON.toJSONString(user);
        String result = mockMvc.perform(MockMvcRequestBuilders.put("/user/modify").contentType(MediaType.APPLICATION_JSON_UTF8)
                                                .content(content))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                                .andReturn().getResponse().getContentAsString();
        log.info("修改用户返回的结果:" + result);
    }
    @Test
    public void testDelete() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.delete("/user/del/14").contentType(MediaType.APPLICATION_JSON_UTF8))
                                         .andExpect(MockMvcResultMatchers.status().isOk())
                                         .andReturn().getResponse().getContentAsString();
        log.info("删除用户结果:" + result);
    }
}
