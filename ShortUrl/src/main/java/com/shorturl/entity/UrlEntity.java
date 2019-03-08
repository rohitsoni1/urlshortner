package com.shorturl.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class UrlEntity {

	@Id
	private String shortUrl;
	private String longUrl;
	private Date createdDate;
	
}
