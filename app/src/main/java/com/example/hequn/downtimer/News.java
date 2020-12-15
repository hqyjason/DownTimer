package com.example.hequn.downtimer;

/**
 * author:hequnyu
 * Description:
 * Date:2020/12/14
 */
public class News {

    private String driverName;

    private String driverId;

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    @Override
    public String toString() {
        return "News{" +
                "driverName='" + driverName + '\'' +
                ", driverId='" + driverId + '\'' +
                '}';
    }
}
