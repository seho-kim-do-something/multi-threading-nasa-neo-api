package com.seho.threading.nasa.neo.api.reporter.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartupReporter {

	private static final Logger logger = LoggerFactory.getLogger(StartupReporter.class);

	public static void main(String[] args) throws Exception {
		try {
			logger.info("Start ...");

			new NasaApiReporter(false);

			logger.info("Reporting is done");			
		} catch(Exception e) {
			logger.error("Error occured while launching ESLoaderExecutor: {}!!", e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
	}
}
