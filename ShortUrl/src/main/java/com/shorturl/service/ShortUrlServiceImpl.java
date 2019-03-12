package com.shorturl.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.shorturl.constants.Constants;
import com.shorturl.entity.ShortUrl;
import com.shorturl.exception.NotFoundException;
import com.shorturl.exception.ShortUrlException;
import com.shorturl.repository.UrlRepo;
import com.shorturl.view.bean.UrlBean;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
/**
 * Service class which handles the business logic for save short url and
 * retrieving the long url for given short url.
 * 
 * @author rohit
 *
 */
public class ShortUrlServiceImpl implements ShortUrlService {

	/**
	 * Atomic Counter class to handle thread safe management of sequence numbers.
	 */
	@Autowired
	private SeqCounterService counter;

	/**
	 * Repository class for saving and retrieving values from database.
	 */
	@Autowired
	private UrlRepo urlRepo;

	/**
	 * Environment files for reading application configuration
	 */
	@Autowired
	private Environment env;

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
	@Override
	public UrlBean generateShortUrl(UrlBean urlBean) {
		log.info("incrementing the counter");
		Long num = counter.increment();
		log.debug("incrementing the counter to {}", num);
		log.info("making the new short url");
		String shortHash = counter.generateSeqeunce(num);
		if (!StringUtils.isEmpty(shortHash)) {
			log.debug("Created the short url {} for given long url {}", shortHash, urlBean.getLongUrl());
			ShortUrl shortUrl = new ShortUrl();
			BeanUtils.copyProperties(urlBean, shortUrl);
			log.debug("After copying the input request to entity object");
			shortUrl.setShortUrl(shortHash);
			// saving the data in database
			ShortUrl createObj = urlRepo.save(shortUrl);
			// TODO after saving the value in database we need to push it to Cache
			// server(Redis) so that user can access it faster.
			log.debug("Saved the new long url and short url in database ");
			UrlBean respBean = new UrlBean();
			BeanUtils.copyProperties(createObj, respBean);
			StringBuilder sb = new StringBuilder();
			sb.append(env.getProperty(Constants.URL_PREFIX)).append(respBean.getShortUrl());
			respBean.setShortUrl(sb.toString());
			log.info("after saving short url");
			return respBean;
		} else {
			throw new ShortUrlException("Unable to save long url, please retry", null, false, false);
		}

	}

	/**
	 * getLongUrl method retrieves the given long url by calling repo object.
	 * 
	 * @param shortUrlHash,
	 *            short url has for given long url.
	 * @return UrlBean, value received from database.
	 */
	@Override
	public UrlBean getLongUrl(String shortUrlHash) {
		Optional<ShortUrl> datas = urlRepo.findById(shortUrlHash);
		if (datas.isPresent()) {
			return populate(datas.get());
		} else {
			throw new NotFoundException("No Url found", null, false, false);
		}
	}

	/**
	 * populate method populates data from entity object to UrlBean
	 * 
	 * @param data
	 *            ShortUrl entity
	 * @return populated UrlBean object
	 */
	public UrlBean populate(ShortUrl data) {
		UrlBean respBean = new UrlBean();
		BeanUtils.copyProperties(data, respBean);
		StringBuilder sb = new StringBuilder();
		sb.append(env.getProperty(Constants.URL_PREFIX)).append(respBean.getShortUrl());
		respBean.setShortUrl(sb.toString());
		return respBean;
	}

}
