package com.shorturl.service;

import com.shorturl.view.bean.UrlBean;

/**
 * SeqCounterServiceImpl class is used for creating sequence value for short
 * hash creation. It has utility method to load buffer counter value. It uses
 * AtomicLong class to manage thread safety of increment to counter.
 * 
 * @author rohit
 *
 */
public interface ShortUrlService {
	/**
	 * generateShortUrl contains business logic for incrementing the counter
	 * generating mapped value for given long url, and ensures that it gets stored
	 * in database.
	 * 
	 * @param urlBean,
	 *            Stores users input sent from controller class.
	 * @return UrlBean object containing the mapped short url value with given long
	 *         url.
	 */
	UrlBean generateShortUrl(UrlBean urlBean);

	/**
	 * getLongUrl method retrieves the given long url by calling repo object.
	 * 
	 * @param shortUrlHash,
	 *            short url has for given long url.
	 * @return UrlBean, value received from database.
	 */
	UrlBean getLongUrl(String shortUrlHash);
}
