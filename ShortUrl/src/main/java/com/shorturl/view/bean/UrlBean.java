package com.shorturl.view.bean;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shorturl.Constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class used for response for the url shortening API. It is also used in
 * receiving the request for getting actual long url.
 * 
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
	@Size(min=20, max=2048)
	@URL
	private String longUrl;
	/**
	 * Stores Short Url which user generated for long url
	 */
	private String shortUrl;
	/**
	 * Stores expiry date for the url.
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_FORMAT)
	@DateTimeFormat(pattern = Constants.DATE_FORMAT)
	@NotNull
	@FutureOrPresent
	private Date expiryDate;
}
