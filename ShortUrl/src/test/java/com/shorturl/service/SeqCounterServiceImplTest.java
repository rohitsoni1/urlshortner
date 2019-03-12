package com.shorturl.service;

import java.math.BigInteger;
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
import com.shorturl.repository.SequenceRepo;
import com.shorturl.view.bean.UrlBean;

@RunWith(SpringJUnit4ClassRunner.class)
public class SeqCounterServiceImplTest {
	@MockBean
	private Environment env;
	@MockBean
	private SequenceRepo sequence;
	@InjectMocks
	SeqCounterServiceImpl seqCounterService;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	String shortHash = "1234567";
	String longUrl = "http://google.com";
	Long counterVal = 100000000000000001l;
	ShortUrl urlEntity = new ShortUrl(shortHash, longUrl, new Date(), new Date());
	UrlBean urlBean = new UrlBean(longUrl, null,new Date());

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void increment() throws Exception {
		Mockito.when(sequence.getNextSequence()).thenReturn(new BigInteger("10000000"));
		Mockito.when(env.getProperty(Mockito.anyString())).thenReturn("10000");
		seqCounterService.processInit();
		seqCounterService.increment();
	}
	
	@Test
	public void generateSeqeunceTest()throws Exception{
		Mockito.when(sequence.getNextSequence()).thenReturn(new BigInteger("10000000"));
		Mockito.when(env.getProperty(Mockito.anyString())).thenReturn("10000");
		seqCounterService.processInit();
		seqCounterService.generateSeqeunce(10000l);
	}
	
	@Test
	public void generateSeqeunceTestNullNum()throws Exception{
		exception.expect(ShortUrlException.class);
		Mockito.when(sequence.getNextSequence()).thenReturn(new BigInteger("10000000"));
		Mockito.when(env.getProperty(Mockito.anyString())).thenReturn("10000");
		seqCounterService.processInit();
		seqCounterService.generateSeqeunce(null);
	}
	
	@Test
	public void generateSeqeunceTestZeroNum()throws Exception{
		exception.expect(ShortUrlException.class);
		Mockito.when(sequence.getNextSequence()).thenReturn(new BigInteger("10000000"));
		Mockito.when(env.getProperty(Mockito.anyString())).thenReturn("10000");
		seqCounterService.processInit();
		seqCounterService.generateSeqeunce(0l);
	}
}
