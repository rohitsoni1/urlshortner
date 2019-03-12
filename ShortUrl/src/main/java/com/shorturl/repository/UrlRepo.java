package com.shorturl.repository;

import org.springframework.stereotype.Repository;

import com.shorturl.entity.ShortUrl;

/**
 * UrlRepo interface provide support to save the data in the database system for
 * ShortUrl entity.
 * 
 * @author rohit
 *
 */
@Repository
public interface UrlRepo extends BaseRepository<ShortUrl, String> {

}
