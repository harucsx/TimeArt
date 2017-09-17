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

    static void setMode(int mode){
        EnvDataMode.mode = mode;
    }
    
    static int getMode(){
        return EnvDataMode.mode;
    }

    public static double getValue() {
        refreshCurrentValue();
        return value;
    }

    static String getStateString(){
        if (EnvDataMode.value < 30) {
            return "좋음";
        } else if (EnvDataMode.value  < 80) {
            return "보통";
        } else if (EnvDataMode.value  < 150) {
            return "나쁨";
        } else {
            return "매우 나쁨";
        }
    }

    static int getState(){
        if (EnvDataMode.value < 30) {
            return EnvDataMode.GOOD;
        } else if (EnvDataMode.value  < 80) {
            return EnvDataMode.NORMAL;
        } else if (EnvDataMode.value  < 150) {
            return EnvDataMode.BAD;
        } else {
            return EnvDataMode.VERY_BAD;
        }
    }
    
    static String getName(){
        return getOtherName(EnvDataMode.mode);
    }
    
    static String getOtherName(int mode){
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

    static String getCode(){
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

    static void refreshCurrentValue(){
        switch (EnvDataMode.mode) {
            case DUST25:
                value = getEnvData().getDust25();
            case DUST100:
                value = getEnvData().getDust100();
            case DC_INDEX:
                value = getEnvData().getDcIndex();
            case CO2:
                value = getEnvData().getCo2();
        }
    }
}
