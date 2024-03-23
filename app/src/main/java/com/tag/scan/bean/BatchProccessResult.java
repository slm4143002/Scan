package com.tag.scan.bean;

import java.io.Serializable;

/** UT*/
public class BatchProccessResult extends BaseResp implements Serializable {
	private static final long serialVersionUID = 1L;
	// 批量号
	private String batchNumber;

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	
}
