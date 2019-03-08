package com.shorturl.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shorturl.service.ShortUrlService;
import com.shorturl.view.bean.UrlBean;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
/**
 * Hosts the different service entry point which will be invoked from client  
 * <li>
 * 	<ul>Generating short url for given long url</ul>
 * 	<ul>Get api for getting the long url for redirection</ul>
 * </li>
 * @author rohit
 *
 */
public class ShortUrlController {
	
	@Autowired
	private ShortUrlService shortUrlService;
	
	/**
	 * Method is entry point for the creating short url for the given long url.
	 * @param requestBody, user uploaded data which contains long url.
	 * @param request, http request object.
	 * @return, method return short url and given long url in the response.
	 */
	@PostMapping(consumes="application/json", value="/url",produces="application/json")
	public UrlBean generateShortUrl(@Valid @RequestBody UrlBean urlBean, HttpServletRequest request) {
		UrlBean response=null;
		log.info("Started on shortening the url from user ");
		shortUrlService.generateShortUrl(urlBean);
				
		return response;		
	}
	
	/**
	 * Method return the long url for the given short url. This method sets the response 
	 * @param request, http request object.
	 * @return, method return short url and given long url in the response.
	 */
	@GetMapping(value="/{shortUrl}",produces="application/json")
	public UrlBean getLongUrl(@PathVariable(value="shortUrl") String shortUrl, HttpServletRequest request) {
		UrlBean response=null;
		log.info("Started on getting long url for short url");
		shortUrlService.getLongUrl(shortUrl);		
				
		return response;		
	}
	
}
