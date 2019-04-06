package com.seho.threading.nasa.neo.api.reporter.bean;

public class NearEarthObject {
	
	private String id;
	
	private String neoRefId;
	
	private String name;
	
	private EstimatedDiameter estimatedDiameter;
	
	private Double closeApproachMissDataKm;

	public NearEarthObject() {
	}
	
	public NearEarthObject(String id, String neoRefId, String name, EstimatedDiameter estimatedDiameter,
			Double closeApproachMissDataKm) {
		this.id = id;
		this.neoRefId = neoRefId;
		this.name = name;
		this.estimatedDiameter = estimatedDiameter;
		this.closeApproachMissDataKm = closeApproachMissDataKm;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNeoRefId() {
		return neoRefId;
	}

	public void setNeoRefId(String neoRefId) {
		this.neoRefId = neoRefId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EstimatedDiameter getEstimatedDiameter() {
		return estimatedDiameter;
	}

	public void setEstimatedDiameter(EstimatedDiameter estimatedDiameter) {
		this.estimatedDiameter = estimatedDiameter;
	}

	public Double getCloseApproachMissDataKm() {
		return closeApproachMissDataKm;
	}

	public void setCloseApproachMissDataKm(Double closeApproachMissDataKm) {
		this.closeApproachMissDataKm = closeApproachMissDataKm;
	}

	@Override
	public String toString() {
		return "Near Earth Object\n\t id = " + id + "\n\t neo_reference_id = " + neoRefId + "\n\t name = " + name 
				+ "\n\t " + estimatedDiameter + "\n\t miss_distance of close_approach_data = " + closeApproachMissDataKm + " kilometers";
	}

}

