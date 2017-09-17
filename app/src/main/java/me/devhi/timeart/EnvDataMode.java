package me.devhi.timeart;


public class EnvDataMode {
    public final static int DUST25 = 1;
    public final static int DUST100 = 2;
    public final static int DC_INDEX = 3;
    public final static int CO2 = 4;

    public final static int GOOD = 11;
    public final static int NORMAL = 12;
    public final static int BAD = 13;
    public final static int VERY_BAD = 14;

    private static int mode = DUST25;
    private static double value = 0.0;

    private static EnvData envData = new EnvData();

    public static EnvData getEnvData() {
        return envData;
    }

    static void setMode(int mode) {
        EnvDataMode.mode = mode;
    }

    static int getMode() {
        return EnvDataMode.mode;
    }

    public static double getValue() {
        refreshCurrentValue();
        return value;
    }

    static String getStateString() {
        if (getValue() < 30.0f) {
            return "좋음";
        } else if (getValue() < 80.0f) {
            return "보통";
        } else if (getValue() < 150.0f) {
            return "나쁨";
        } else {
            return "매우 나쁨";
        }
    }

    static int getState() {
        if (getValue() < 30.0f) {
            return EnvDataMode.GOOD;
        } else if (getValue() < 80.0f) {
            return EnvDataMode.NORMAL;
        } else if (getValue() < 150.0f) {
            return EnvDataMode.BAD;
        } else {
            return EnvDataMode.VERY_BAD;
        }
    }

    static String getName() {
        return getOtherName(EnvDataMode.mode);
    }

    static String getOtherName(int mode) {
        switch (EnvDataMode.mode) {
            case DUST25:
                return "초미세먼지 PM2.5";
            case DUST100:
                return "미세먼지 PM10";
            case DC_INDEX:
                return "불쾌지수";
            case CO2:
                return "이산화탄소";
        }

        return "null";
    }

    static String getCode() {
        switch (EnvDataMode.mode) {
            case DUST25:
                return "dust25";
            case DUST100:
                return "dust100";
            case DC_INDEX:
                return "discomfort_index";
            case CO2:
                return "co2";
        }

        return "null";
    }

    static void refreshCurrentValue() {
        switch (EnvDataMode.mode) {
            case DUST25:
                value = (double) getEnvData().getDust25();
                break;
            case DUST100:
                value = (double) getEnvData().getDust100();
                break;
            case DC_INDEX:
                value = (double) getEnvData().getDcIndex();
                break;
            case CO2:
                value = (double) getEnvData().getCo2();
                break;
        }
    }

    static String getCurrentUnit(){
        switch (EnvDataMode.mode) {
            case DUST25:
            case DUST100:
                return "μg/m³";
            case DC_INDEX:
                return "";
            case CO2:
                return "ppm";
        }

        return "";
    }
}
