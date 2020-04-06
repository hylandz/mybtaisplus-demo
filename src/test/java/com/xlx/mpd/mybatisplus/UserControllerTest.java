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
        mockMvc.perform(MockMvcRequestBuilders.get("/user/search").contentType(MediaType.APPLICATION_JSON_UTF8)
                                .param("userName","G"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.records.size()").value(10));
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
        String content = JSON.toJSONString(user);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/user/create").contentType(MediaType.APPLICATION_JSON_UTF8)
                                                         .content(content))
                                         .andExpect(MockMvcResultMatchers.status().isOk())
                                         .andExpect(MockMvcResultMatchers.jsonPath("$.data.success").value(true))
                                         .andReturn().getResponse().getContentAsString();
        log.info("新增用户返回的结果:" + result);
    }
    @Test
    public void testUpdate() throws Exception {
        User user = new User();
        user.setDeptId(5L)
                .setNickName("红孩儿")
                .setAvatarUrl("default.jpg")
                .setUserName("H3860565")
                .setRealName("张海")
                .setPassword("123456")
                .setSalt("adadadda")
                .setBirth(LocalDate.parse("1995-09-19", DateTimeFormatter.ISO_LOCAL_DATE))
                .setEmail("zhanghai@qq.com")
                .setGender(GenderEnum.FEMALE.getGenderNum())
                .setDeleted(0)
                .setStatus(UserStatusEnum.NORMAL.getStateNum());
        String content = JSON.toJSONString(user);
        String result = mockMvc.perform(MockMvcRequestBuilders.put("/user/create").contentType(MediaType.APPLICATION_JSON_UTF8)
                                                .content(content))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.data.success").value(true))
                                .andReturn().getResponse().getContentAsString();
        log.info("修改用户返回的结果:" + result);
    }
    @Test
    public void testDelete() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.delete("/user/del/13").contentType(MediaType.APPLICATION_JSON_UTF8))
                                         .andExpect(MockMvcResultMatchers.status().isOk())
                                         .andReturn().getResponse().getContentAsString();
        log.info("删除用户结果:" + result);
    }
}
