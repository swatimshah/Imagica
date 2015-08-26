package com.example.imagica.vo;

import java.io.Serializable;

public class PatientVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long patientId;
	private String patientName;
	private int age;
	private int bed;
	private String admissionDate;
	private String icu;
	
	
	
	public Long getPatientId() {
		return patientId;
	}
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getBed() {
		return bed;
	}
	public void setBed(int bed) {
		this.bed = bed;
	}
	public String getAdmissionDate() {
		return admissionDate;
	}
	public void setAdmissionDate(String admissionDate) {
		this.admissionDate = admissionDate;
	}
	public String getIcu() {
		return icu;
	}
	public void setIcu(String icu) {
		this.icu = icu;
	}
	
	
	
}
