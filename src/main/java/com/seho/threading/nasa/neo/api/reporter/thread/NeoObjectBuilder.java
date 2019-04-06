package com.seho.threading.nasa.neo.api.reporter.thread;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seho.threading.nasa.neo.api.reporter.bean.EstimatedDiameter;
import com.seho.threading.nasa.neo.api.reporter.bean.NearEarthObject;
import com.seho.threading.nasa.neo.api.reporter.config.Configuration;
import com.seho.threading.nasa.neo.api.reporter.startup.NasaApiReporter;

import jersey.repackaged.com.google.common.collect.Lists;

public class NeoObjectBuilder implements Callable<List<NearEarthObject>> {

	private List<Map<String, Object>> neoMapList;

	private String date;
	
	private Boolean isTest; 

	private final Logger logger = LoggerFactory.getLogger(NeoObjectBuilder.class);

	public NeoObjectBuilder(List<Map<String, Object>> neoMapList, String date, Boolean isTest) {
		this.neoMapList = neoMapList;
		this.date = date;
		this.isTest = isTest;
	}

	@Override
	public List<NearEarthObject> call() throws Exception {
		if(!isTest) {
			logger.info("Started building data of the date {}", date);
		}

		List<NearEarthObject> neoList = Lists.newArrayList();
		for(Map<String, Object> neoMap : neoMapList) {
			NearEarthObject nearEarthObject = new NearEarthObject();
			nearEarthObject.setId((String) neoMap.get(Configuration.ID));
			nearEarthObject.setNeoRefId((String) neoMap.get(Configuration.NEO_REF_ID));
			nearEarthObject.setName((String) neoMap.get(Configuration.NAME));

			EstimatedDiameter estimatedDiameter = buildEstimatedDiameter((Map<String, Object>) neoMap.get(Configuration.ESTIMATED_DIAMETER));
			nearEarthObject.setEstimatedDiameter(estimatedDiameter);

			Double closeApproachMissDataInKm = getCloseApproachMissDataInKm((List<Map<String, Object>>) neoMap.get(Configuration.CLOSE_APPROACH_DATA));
			nearEarthObject.setCloseApproachMissDataKm(closeApproachMissDataInKm);

			neoList.add(nearEarthObject);

			compareAndGetLargest(nearEarthObject);
			compareAndGetSmallest(nearEarthObject);
		}
		return neoList;
	}	

	private EstimatedDiameter buildEstimatedDiameter(Map<String, Object> estimatedDiameterMap) {
		Map<String, Object> kmData = (Map<String, Object>) estimatedDiameterMap.get(Configuration.KILOMETER);
		Double estimatedDiameterMin = (Double) kmData.get(Configuration.ESTIMATED_DIAMETER_MIN);
		Double estimatedDiameterMax = (Double) kmData.get(Configuration.ESTIMATED_DIAMETER_MAX);
		return new EstimatedDiameter(estimatedDiameterMin, estimatedDiameterMax);
	}

	private Double getCloseApproachMissDataInKm(List<Map<String, Object>> closeApproachDataMapList) {
		Map<String, Object> closeApproachDataMap = closeApproachDataMapList.get(0);
		Map<String, Object> missDistance = (Map<String, Object>) closeApproachDataMap.get(Configuration.MISS_DIATANCE);
		return Double.parseDouble((String) missDistance.get(Configuration.KILOMETER));
	}

	private void compareAndGetLargest(NearEarthObject nearEarthObject) {
		// determine largest NEO by the average of min and max
		EstimatedDiameter estimatedDiameter = nearEarthObject.getEstimatedDiameter();
		Double avgDiameter = (estimatedDiameter.getEstimatedDiameterMin() + estimatedDiameter.getEstimatedDiameterMax()) / 2;
		estimatedDiameter.setAvgEstimatedDiameter(avgDiameter);

		// compare and set the largest
		synchronized(NasaApiReporter.LARGEST_DIAMETER) {
			if(avgDiameter > NasaApiReporter.LARGEST_DIAMETER) {
				NasaApiReporter.LARGEST_DIAMETER = avgDiameter;
				NasaApiReporter.LARGEST_NEO = nearEarthObject;
			}
		}
	}

	private void compareAndGetSmallest(NearEarthObject nearEarthObject) {
		// compare and set the closest
		synchronized(NasaApiReporter.CLOEST_IN_KILOMETER) {
			if(nearEarthObject.getCloseApproachMissDataKm() < NasaApiReporter.CLOEST_IN_KILOMETER) {
				NasaApiReporter.CLOEST_IN_KILOMETER = nearEarthObject.getCloseApproachMissDataKm();
				NasaApiReporter.CLOEST_NEO = nearEarthObject;
			}
		}
	}

}
