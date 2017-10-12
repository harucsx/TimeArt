package me.devhi.timeart.models;


import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

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

    public static EnvData getEnvData() {
        RealmResults<EnvData> results = Realm.getDefaultInstance().where(EnvData.class).findAllSorted("pk", Sort.DESCENDING);
        return results.first();
    }

    public static void setMode(int mode) {
        EnvDataMode.mode = mode;
    }

    public static int getMode() {
        return EnvDataMode.mode;
    }

    public static double getValue() {
        refreshCurrentValue();
        return value;
    }

    public static String getStateString() {
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

    public static int getState() {
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

    public static String getName() {
        return getStateName(EnvDataMode.mode);
    }

    public static String getStateName(int mode) {
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

    public static String getCode() {
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

    public static void refreshCurrentValue() {
        switch (EnvDataMode.mode) {
            case DUST25:
                value = (double) getEnvData().getDust25();
                break;
            case DUST100:
                value = (double) getEnvData().getDust100();
                break;
            case DC_INDEX:
                value = (double) getEnvData().getDiscomfortIndex();
                break;
            case CO2:
                value = (double) getEnvData().getCo2();
                break;
        }
    }

    public static String getCurrentUnit() {
        return getUnit(EnvDataMode.mode);
    }

    public static String getUnit(int mode) {
        switch (mode) {
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
