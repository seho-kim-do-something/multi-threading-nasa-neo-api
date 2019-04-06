package com.seho.threading.nasa.neo.api.reporter.startup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seho.threading.nasa.neo.api.reporter.bean.NearEarthObject;
import com.seho.threading.nasa.neo.api.reporter.config.Configuration;
import com.seho.threading.nasa.neo.api.reporter.thread.NeoObjectBuilder;

import jersey.repackaged.com.google.common.collect.Lists;

public class NasaApiReporter {
	private Client client;

	private Response response;
	
	private Boolean isTest; 

	private Integer totalNumNeos;

	public static NearEarthObject LARGEST_NEO;

	public static NearEarthObject CLOEST_NEO;

	// get largest from average of min and max
	public static Double LARGEST_DIAMETER = 0.0;

	// get closest from miss_distance in kilometer
	public static Double CLOEST_IN_KILOMETER = Double.MAX_VALUE;

	private final Logger logger = LoggerFactory.getLogger(NasaApiReporter.class);

	public NasaApiReporter(Boolean isTest) {
		this.isTest = isTest;
		
		if(!isTest) {
			logger.info("Calling Rest api - start_date: {}, end_date: 7 days after start_date (default value set by the api)", Configuration.CURRENT_DATE);
			
			this.client = ClientBuilder.newClient();
			this.response = client.target(Configuration.API_URI)
					.queryParam("start_date", Configuration.CURRENT_DATE)
					.queryParam("api_key", Configuration.API_KEY)
					.request(MediaType.APPLICATION_JSON).get();
			processResponse();
		}
	}

	private void processResponse() {
		try {
			Map<String, Object> map = response.readEntity(new GenericType<Map<String, Object>>() {});
			if(map.get(Configuration.NEAR_EARTH_OBJECTS) == null) {
				logger.error("Unexpected Response from Api.. Status Code: {}", response.getStatus());
				System.exit(0);
			}
			totalNumNeos = (int) map.get(Configuration.ELEMENT_COUNT);

			Map<String, Object> neoMap = (Map<String, Object>) map.get(Configuration.NEAR_EARTH_OBJECTS);
			buildNeoObjects(neoMap);
		} catch(Exception e) {
			logger.error("Error occured while processing API response: {}!!", e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
	}

	private void buildNeoObjects(Map<String, Object> neoMap) {
		logger.info("Spawning threads... one thread per single date of NEOs data in a fixed thread pool of {} ...", Configuration.NUM_THREADS);

		// create thread worker for each date of NEOs data
		// in case of lots of data, for instance, a year data, multi-threading must be much faster
		List<NearEarthObject> neoList = executeThreading(neoMap);

		logger.info("********************************* RESULT ****************************************");
		logger.info("Total number of NEOs: {}", totalNumNeos);
		logger.info("The ** LARGEST NEO ** by the average of estimated_diameter_min and estimated_diameter_max:\n{}", LARGEST_NEO);
		logger.info("The ** CLOEST NEO **:\n{}", CLOEST_NEO);
		logger.info("*********************************************************************************");
	}

	private List<NearEarthObject> executeThreading(Map<String, Object> neoMap) {
		List<NearEarthObject> neoList = Lists.newArrayList();

		ExecutorService executor = Executors.newFixedThreadPool(Configuration.NUM_THREADS);
		List<Future<List<NearEarthObject>>> futureList = new ArrayList<Future<List<NearEarthObject>>>();

		for(String date : neoMap.keySet()) {
			List<Map<String, Object>> neoMapList = (List<Map<String, Object>>) neoMap.get(date);
			Future<List<NearEarthObject>> future = executor.submit(new NeoObjectBuilder(neoMapList, date, isTest));
			futureList.add(future);
		}

		for(Future<List<NearEarthObject>> future : futureList) {
			try {
				neoList.addAll(future.get());
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			} catch (ExecutionException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		executor.shutdown();

		return neoList;
	}

	/**
	 * testing function for JUnit
	 * same function as buildNeoObjects(Map<String, Object> neoMap)
	 * 
	 * @param neoMap
	 * @return
	 */
	public Map<String, String> testBuildNeoObjects(Map<String, Object> neoMap) {
		List<NearEarthObject> neoList = executeThreading(neoMap);

		Map<String, String> result = new HashMap<String, String>();
		result.put("largest", LARGEST_NEO.getName());
		result.put("closest", CLOEST_NEO.getName());
		
		return result;
	}

}
