package com.shorturl.service;

import com.shorturl.view.bean.UrlBean;

public interface ShortUrlService {
	
	UrlBean generateShortUrl(UrlBean urlBean);
	UrlBean getLongUrl(String shortUrlHash);
}
