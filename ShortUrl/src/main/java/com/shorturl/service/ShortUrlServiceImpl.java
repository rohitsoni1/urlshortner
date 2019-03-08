package com.shorturl.service;

import org.springframework.stereotype.Service;

import com.shorturl.view.bean.UrlBean;
@Service
public class ShortUrlServiceImpl implements ShortUrlService {

	@Override
	public UrlBean generateShortUrl(UrlBean urlBean) {
		return null;
	}

	@Override
	public UrlBean getLongUrl(String shortUrlHash) {
		return null;
	}

}
