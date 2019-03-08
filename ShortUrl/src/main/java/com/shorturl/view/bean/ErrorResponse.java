package com.shorturl.view.bean;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class ErrorResponse {
	private Date timestamp;
	private String message;
	private String details;
}
