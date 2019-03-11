package com.shorturl.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortUrl {

	@Id
	private String shortUrl;
	@Size(max=1000,min=20)
	private String longUrl;
	private Date createdDate;
	private Date expiryDate;

	@PrePersist
	void preInsert() {
		if (this.createdDate == null)
			this.createdDate = new Date();
	}

}
