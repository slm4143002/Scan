package com.tag.scan.bean;


import java.io.Serializable;

/**
 * <功能详细描述>
 *
 */
public class PrepareResp  extends BaseResp implements Serializable {
    private static final long serialVersionUID = 1L;

    private String batchNumber;
    private String machineCategoryName;
    private String machineCount;
    private String carCount;
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

    public String getMachineCount() {
        return machineCount;
    }

    public void setMachineCount(String machineCount) {
        this.machineCount = machineCount;
    }

    public String getCarCount() {
        return carCount;
    }

    public void setCarCount(String carCount) {
        this.carCount = carCount;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }
}
