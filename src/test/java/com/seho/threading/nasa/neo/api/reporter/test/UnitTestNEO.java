package com.seho.threading.nasa.neo.api.reporter.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seho.threading.nasa.neo.api.reporter.config.Configuration;
import com.seho.threading.nasa.neo.api.reporter.startup.NasaApiReporter;

/**
 * Unit test for getting largest and closest NEO
 */
public class UnitTestNEO {
	
	ObjectMapper mapper = new ObjectMapper();
	
	private String testJson = "{\"near_earth_objects\":{\"2019-02-14\":[{\"id\":\"1\",\"neo_reference_id\":\"ref-1\",\"name\":\"name-1\",\"estimated_diameter\":{\"kilometers\":{\"estimated_diameter_min\":100.1,\"estimated_diameter_max\":200.1}},\"close_approach_data\":[{\"miss_distance\":{\"kilometers\":\"1000.1\"}}]},{\"id\":\"2\",\"neo_reference_id\":\"ref-2\",\"name\":\"name-2\",\"estimated_diameter\":{\"kilometers\":{\"estimated_diameter_min\":200.1,\"estimated_diameter_max\":300.1}},\"close_approach_data\":[{\"miss_distance\":{\"kilometers\":\"9000.1\"}}]},{\"id\":\"3\",\"neo_reference_id\":\"ref-3\",\"name\":\"name-3\",\"estimated_diameter\":{\"kilometers\":{\"estimated_diameter_min\":300.1,\"estimated_diameter_max\":400.1}},\"close_approach_data\":[{\"miss_distance\":{\"kilometers\":\"8000.1\"}}]}],\"2019-02-13\":[{\"id\":\"4\",\"neo_reference_id\":\"ref-4\",\"name\":\"name-4\",\"estimated_diameter\":{\"kilometers\":{\"estimated_diameter_min\":400.1,\"estimated_diameter_max\":500.1}},\"close_approach_data\":[{\"miss_distance\":{\"kilometers\":\"7000.1\"}}]},{\"id\":\"5\",\"neo_reference_id\":\"ref-5\",\"name\":\"name-5\",\"estimated_diameter\":{\"kilometers\":{\"estimated_diameter_min\":500.1,\"estimated_diameter_max\":600.1}},\"close_approach_data\":[{\"miss_distance\":{\"kilometers\":\"6000.1\"}}]},{\"id\":\"6\",\"neo_reference_id\":\"ref-6\",\"name\":\"name-6\",\"estimated_diameter\":{\"kilometers\":{\"estimated_diameter_min\":600.1,\"estimated_diameter_max\":700.1}},\"close_approach_data\":[{\"miss_distance\":{\"kilometers\":\"5000.1\"}}]},{\"id\":\"7\",\"neo_reference_id\":\"ref-7\",\"name\":\"name-7\",\"estimated_diameter\":{\"kilometers\":{\"estimated_diameter_min\":700.1,\"estimated_diameter_max\":800.1}},\"close_approach_data\":[{\"miss_distance\":{\"kilometers\":\"4000.1\"}}]},{\"id\":\"8\",\"neo_reference_id\":\"ref-8\",\"name\":\"name-8\",\"estimated_diameter\":{\"kilometers\":{\"estimated_diameter_min\":800.1,\"estimated_diameter_max\":900.1}},\"close_approach_data\":[{\"miss_distance\":{\"kilometers\":\"103000.1\"}}]}]}}";

    @Test
    public void testLargestAndCloest() throws JsonParseException, JsonMappingException, IOException {    	
    	Map<String, Object> map = mapper.readValue(
    			testJson, new TypeReference<Map<String, Object>>() {});  
    	
    	Map<String, Object> neoMap = (Map<String, Object>) map.get(Configuration.NEAR_EARTH_OBJECTS);
		
    	NasaApiReporter testingNasaApiReporter = new NasaApiReporter(true);
    	Map<String, String> result = testingNasaApiReporter.testBuildNeoObjects(neoMap);
    	
    	assertEquals("name-8", result.get("largest"));
    	assertEquals("name-1", result.get("closest"));
    }
}
