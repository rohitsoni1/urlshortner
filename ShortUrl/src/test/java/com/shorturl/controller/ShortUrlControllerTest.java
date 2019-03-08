package com.shorturl.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shorturl.service.ShortUrlService;
import com.shorturl.view.bean.UrlBean;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ShortUrlController.class)
public class ShortUrlControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private ShortUrlService shortUrlService;

	
	String shortUrl = "http://localhost:8080/1234567";
	UrlBean urlBean2 = new UrlBean("http://google.com", "http://localhost:8080/1234567");

	@Test
	public void generateShortUrl() throws Exception {
		UrlBean urlBean = new UrlBean("http://google.com", "");
		Mockito.when(shortUrlService.generateShortUrl(Mockito.any(UrlBean.class))).thenReturn(urlBean2);
		ObjectMapper mapper = new ObjectMapper();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/url")
				.accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(urlBean).getBytes())
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	public void generateShortUrlNull() throws Exception {
		UrlBean urlBean = new UrlBean(null, "");
		Mockito.when(shortUrlService.generateShortUrl(Mockito.any(UrlBean.class))).thenReturn(urlBean2);
		ObjectMapper mapper = new ObjectMapper();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/url")
				.accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(urlBean).getBytes())
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	public void generateShortUrlEmpty() throws Exception {
		UrlBean urlBean = new UrlBean(null, "");
		Mockito.when(shortUrlService.generateShortUrl(Mockito.any(UrlBean.class))).thenReturn(urlBean2);
		ObjectMapper mapper = new ObjectMapper();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/url")
				.accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(urlBean).getBytes())
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	public void generateShortUrlEmptyPayload() throws Exception {
		Mockito.when(shortUrlService.generateShortUrl(Mockito.any(UrlBean.class))).thenReturn(urlBean2);
		ObjectMapper mapper = new ObjectMapper();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/url")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
}
