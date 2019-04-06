package com.seho.threading.nasa.neo.api.reporter.config;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Some of these values could be in .properties file
 */
public interface Configuration {
	
	/*
	 *  number of threads in the fixed thread pool
	 */
	int NUM_THREADS = 10;
	
	
	/*
	 * API details
	 */	
	String API_URI = "https://api.nasa.gov/neo/rest/v1/feed";
	
	String API_KEY = "eJKhSg10TEEIsUbCHZdUHw5BJ7w22P5jbcqOw3SZ";
	
	String CURRENT_DATE = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	
	
	/*
	 * keys to extract from api response
	 */	
	String ELEMENT_COUNT = "element_count";
	
	String NEAR_EARTH_OBJECTS = "near_earth_objects";
	
	String ID = "id";
	
	String NEO_REF_ID = "neo_reference_id";
	
	String NAME = "name";
	
	String ESTIMATED_DIAMETER = "estimated_diameter";
	
	String KILOMETER = "kilometers";
	
	String ESTIMATED_DIAMETER_MIN = "estimated_diameter_min";
	
	String ESTIMATED_DIAMETER_MAX = "estimated_diameter_max";
	
	String CLOSE_APPROACH_DATA = "close_approach_data";
	
	String MISS_DIATANCE = "miss_distance";
	
}
