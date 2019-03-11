package com.shorturl.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shorturl.entity.ShortUrl;
import com.shorturl.exception.ShortUrlException;
import com.shorturl.repository.UrlRepo;
import com.shorturl.view.bean.UrlBean;

@RunWith(SpringJUnit4ClassRunner.class)
public class ShortUrlServiceImplTest {

	@MockBean
	private UrlRepo urlRepo;
	@MockBean
	private SeqCounterService counter;
	@MockBean
	private Environment env;
	@InjectMocks
	private ShortUrlServiceImpl shortUrlService;
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	String shortHash = "1234567";
	String longUrl = "http://google.com";
	Long counterVal = 100000000000000001l;
	ShortUrl urlEntity = new ShortUrl(shortHash, longUrl, new Date(), new Date());
	UrlBean urlBean = new UrlBean(longUrl, null);

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void generateShortUrlTest() throws Exception {
		Mockito.when(urlRepo.save(Mockito.any(ShortUrl.class))).thenReturn(urlEntity);
		Mockito.when(counter.increment()).thenReturn(counterVal);
		Mockito.when(counter.generateSeqeunce(Mockito.anyLong())).thenReturn(shortHash);
		Mockito.when(env.getProperty(Mockito.anyString()))
				.thenReturn("Ds3K9ZNvWmHcakr1oPnxh4qpMEzAye8wX5IdJ2LFujUgtC07lOTb6GYBQViSfR");
		UrlBean urlBean1 = shortUrlService.generateShortUrl(urlBean);
		assertEquals(urlBean1.getLongUrl(), urlBean.getLongUrl());
		assertEquals(urlBean1.getShortUrl(), shortHash);
	}

	@Test
	public void generateShortUrlTestNullShortHash()  throws Exception{
		exception.expect(ShortUrlException.class);
		Mockito.when(counter.increment()).thenReturn(counterVal);
		Mockito.when(counter.generateSeqeunce(Mockito.anyLong())).thenReturn(null);
		shortUrlService.generateShortUrl(urlBean);
	}
	
	@Test
	public void generateShortUrlTestEmptyShortHash()  throws Exception{
		exception.expect(ShortUrlException.class);
		Mockito.when(counter.increment()).thenReturn(counterVal);
		Mockito.when(counter.generateSeqeunce(Mockito.anyLong())).thenReturn(null);
		shortUrlService.generateShortUrl(urlBean);
	}
	/*
	@Test
	public void generateShortUrlTestNullSequence()  throws Exception{
		exception.expect(ShortUrlException.class);
		Mockito.when(counter.increment()).thenReturn(null);
		Mockito.when(counter.generateSeqeunce(Mockito.anyLong())).thenReturn(shortHash);
		shortUrlService.generateShortUrl(urlBean);
	}
	
	@Test
	public void generateShortUrlTestZeroSequence()  throws Exception{
		exception.expect(ShortUrlException.class);
		Mockito.when(counter.increment()).thenReturn(0l);
		Mockito.when(counter.generateSeqeunce(Mockito.anyLong())).thenReturn(shortHash);
		shortUrlService.generateShortUrl(urlBean);
	}*/

}
