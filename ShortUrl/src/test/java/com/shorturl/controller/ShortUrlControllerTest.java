package com.shorturl.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
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
import com.shorturl.exception.NotFoundException;
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
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/url").accept(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(urlBean).getBytes()).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void generateShortUrlNull() throws Exception {
		UrlBean urlBean = new UrlBean(null, "");
		Mockito.when(shortUrlService.generateShortUrl(Mockito.any(UrlBean.class))).thenReturn(urlBean2);
		ObjectMapper mapper = new ObjectMapper();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/url").accept(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(urlBean).getBytes()).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

	@Test
	public void generateShortUrlEmpty() throws Exception {
		UrlBean urlBean = new UrlBean("", "");
		Mockito.when(shortUrlService.generateShortUrl(Mockito.any(UrlBean.class))).thenReturn(urlBean2);
		ObjectMapper mapper = new ObjectMapper();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/url").accept(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(urlBean).getBytes()).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

	@Test
	public void generateShortUrlEmptyPayload() throws Exception {
		Mockito.when(shortUrlService.generateShortUrl(Mockito.any(UrlBean.class))).thenReturn(urlBean2);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/url").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

	@Test
	public void generateShortUrlMaxOutofRange() throws Exception {
		UrlBean urlBean = new UrlBean(
				"http://www.google.com/deeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeedeeeeeeee"
						+ "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeedeeeeeeeeeeeeeeeee"
						+ "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeedededddddddddddddddddddddddd"
						+ "dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddedededddddddddddddddddddddddddddddd"
						+ "dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddedededdddddddddddddddddddddddddddddddd"
						+ "ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddedededdddddddddddddddddddddddddddddddddddd"
						+ "ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddedededdddddddddddddddddddddddddddddddddddddddd"
						+ "dccddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddedededdddddddddddddddddddddddddddddddddddd",
				"");
		Mockito.when(shortUrlService.generateShortUrl(Mockito.any(UrlBean.class))).thenReturn(urlBean2);
		ObjectMapper mapper = new ObjectMapper();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/url").accept(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(urlBean).getBytes()).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

	@Test
	public void generateShortUrlMinOutofRange() throws Exception {
		UrlBean urlBean = new UrlBean("http://ww", "");
		Mockito.when(shortUrlService.generateShortUrl(Mockito.any(UrlBean.class))).thenReturn(urlBean2);
		ObjectMapper mapper = new ObjectMapper();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/url").accept(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(urlBean).getBytes()).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

	@Test
	public void getLongUrl() throws Exception {
		Mockito.when(shortUrlService.getLongUrl(Mockito.anyString())).thenReturn(urlBean2);
		RequestBuilder requestBuilder=MockMvcRequestBuilders.get("/1234567").contentType(MediaType.APPLICATION_JSON);
		MvcResult result=mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse resp=result.getResponse();
		UrlBean urlBean = new UrlBean("http://google.com", "http://localhost:8080/1234567");
		ObjectMapper mapper = new ObjectMapper();
		assertEquals(HttpStatus.OK.value(), resp.getStatus());
		JSONAssert.assertEquals(mapper.writeValueAsString(urlBean), resp.getContentAsString(), false);
	}
	
	@Test
	public void getLongUrlNotFound() throws Exception {
		Mockito.when(shortUrlService.getLongUrl(Mockito.anyString())).thenThrow(new NotFoundException("No url found for given short url",null,false,false));
		RequestBuilder requestBuilder=MockMvcRequestBuilders.get("/562839").contentType(MediaType.APPLICATION_JSON);
		MvcResult result=mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse resp=result.getResponse();
		assertEquals(HttpStatus.NOT_FOUND.value(), resp.getStatus());
	}
	
	@Test
	public void getLongUrlForEmptyIInput() throws Exception {
		Mockito.when(shortUrlService.getLongUrl(Mockito.anyString())).thenReturn(urlBean2);
		RequestBuilder requestBuilder=MockMvcRequestBuilders.get("/").contentType(MediaType.APPLICATION_JSON);
		MvcResult result=mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse resp=result.getResponse();
		assertEquals(HttpStatus.NOT_FOUND.value(), resp.getStatus());
	}
}
