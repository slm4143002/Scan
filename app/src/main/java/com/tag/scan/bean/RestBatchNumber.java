package com.tag.scan.bean;

import java.io.Serializable;

public class RestBatchNumber extends BaseResp implements Serializable {
	private static final long serialVersionUID = 1L;
	// 批量号
	private String batchNumber;
	// 机种名称
	private String machineCategoryName;
	// 总台数
	private int machineCount;
	// 现在使用台数
	private int machineNum;
	// 车数
	private int carCount;

	// 日期
	private String writeDate;
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public String getMachineCategoryName() {
		return machineCategoryName;
	}
	public void setMachineCategoryName(String machineCategoryName) {
		this.machineCategoryName = machineCategoryName;
	}
	public int getMachineCount() {
		return machineCount;
	}
	public void setMachineCount(int machineCount) {
		this.machineCount = machineCount;
	}
	public int getMachineNum() {
		return machineNum;
	}
	public void setMachineNum(int machineNum) {
		this.machineNum = machineNum;
	}
	public int getCarCount() {
		return carCount;
	}
	public void setCarCount(int carCount) {
		this.carCount = carCount;
	}
	public String getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

}