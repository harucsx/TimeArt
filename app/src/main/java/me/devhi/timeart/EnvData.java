package me.devhi.timeart;

public class EnvData {
    private int dust25 = 0;
    private int dust100 = 0;
    private int dcIndex = 0;
    private int co2 = 0;
    private double temp = 0.0;
    private double humid = 0.0;

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

    public int getDcIndex() {
        return dcIndex;
    }

    public void setDcIndex(int dcIndex) {
        this.dcIndex = dcIndex;
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
