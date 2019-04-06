package com.seho.threading.nasa.neo.api.reporter.bean;

public class EstimatedDiameter {
	
	private Double estimatedDiameterMin;
	
	private Double estimatedDiameterMax;
	
	private Double avgEstimatedDiameter;

	public EstimatedDiameter(Double estimatedDiameterMin, Double estimatedDiameterMax) {
		this.estimatedDiameterMin = estimatedDiameterMin;
		this.estimatedDiameterMax = estimatedDiameterMax;
	}

	public Double getEstimatedDiameterMin() {
		return estimatedDiameterMin;
	}

	public void setEstimatedDiameterMin(Double estimatedDiameterMin) {
		this.estimatedDiameterMin = estimatedDiameterMin;
	}

	public Double getEstimatedDiameterMax() {
		return estimatedDiameterMax;
	}

	public void setEstimatedDiameterMax(Double estimatedDiameterMax) {
		this.estimatedDiameterMax = estimatedDiameterMax;
	}
	

	public Double getAvgEstimatedDiameter() {
		return avgEstimatedDiameter;
	}

	public void setAvgEstimatedDiameter(Double avgEstimatedDiameter) {
		this.avgEstimatedDiameter = avgEstimatedDiameter;
	}

	@Override
	public String toString() {
		return "estimated_diameter_min=" + estimatedDiameterMin + ", estimated_diameter_max="
				+ estimatedDiameterMax + ", estimated_diameter_average=" + avgEstimatedDiameter;
	}

}
