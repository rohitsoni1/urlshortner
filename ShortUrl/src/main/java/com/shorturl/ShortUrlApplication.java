package com.shorturl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import com.shorturl.constants.Constants;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ShortUrlApplication implements CommandLineRunner {
	/**
	 * Environment files for reading application configuration
	 */
	@Autowired
	private Environment env;
	
	public static void main(String[] args) {
		//starting boot application
		SpringApplication.run(ShortUrlApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String baseString = env.getProperty(Constants.BASE_STRING_KEY);
		if(StringUtils.isEmpty(baseString)) {
			//this SOP is given intentionally so that admin can see the error during server startup.
			System.err.println("##############BASE STRING VALUE IS NOT CONFIGURED IN APPLICATION PROPERTIES, SO EXITING THE SERVER STARTUP, PLEASE "
					+ "	CONFIGURE THE VALUE AND RESTART ##############");	
			System.exit(0);
		}
		String urlPrefix = env.getProperty(Constants.URL_PREFIX);
		if(StringUtils.isEmpty(urlPrefix)) {
			//this SOP is given intentionally so that admin can see the error during server startup.
			System.err.println("##############URL PREFIX VALUE IS NOT CONFIGURED IN APPLICATION PROPERTIES, SO EXITING THE SERVER STARTUP, PLEASE "
					+ "	CONFIGURE THE VALUE AND RESTART ##############");	
			System.exit(0);
		}
		String bufferSize=env.getProperty(Constants.BUFFER_SIZE_KEY);
		if(StringUtils.isEmpty(bufferSize)) {
			//this SOP is given intentionally so that admin can see the error during server startup.
			System.out.println("##############BUFFER SIZE VALUE IS NOT CONFIGURED IN APPLICATION PROPERTIES, SO DEFAULTING TO \""
							+ Constants.DEFAULT_BUFFER_SIZE+ "\", PLEASE "
					+ "	CONFIGURE THE VALUE IF NEEDS A CHANGE AND RESTART ##############");	
		}
	}

}
