package com.shorturl.view.bean;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class used for response for the url shortening API. It is also used in 
 * receiving the request for getting actual long url.
 * @author rohit
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlBean implements Serializable {
	/**
	 * Stores Long Url for which user wants to generate short url
	 */
	@NotEmpty
	@Size(min=10,max=1000)
	private String longUrl;
	/**
	 * Stores Short Url which user generated for long url
	 */
	private String shortUrl;
}
