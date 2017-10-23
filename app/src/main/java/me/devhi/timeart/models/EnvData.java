package me.devhi.timeart.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class EnvData extends RealmObject {

    @PrimaryKey
    private long pk;

    private int dust25 = 0;
    private int dust100 = 0;
    private int discomfort_index = 0;
    private int co2 = 0;
    private double temp = 0.0;
    private double humid = 0.0;
    private String created_at = "";

    public long getPk() {
        return pk;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }

    public int getDust25() {
        return dust25;
    }

    public void setDust25(int dust25) {
        this.dust25 = dust25;
    }

    public int getDust100() {
        return dust100;
    }

    public void setDust100(int dust100) {
        this.dust100 = dust100;
    }

    public int getDiscomfortIndex() {
        return discomfort_index;
    }

    public void setDiscomfortIndex(int discomfort_index) {
        this.discomfort_index = discomfort_index;
    }

    public int getCo2() {
        return co2;
    }

    public void setCo2(int co2) {
        this.co2 = co2;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getHumid() {
        return humid;
    }

    public void setHumid(double humid) {
        this.humid = humid;
    }

}
