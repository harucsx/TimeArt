package me.devhi.timeart;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment implements View.OnClickListener {

    TextView txtDataTypeTitle, txtDataState, txtDataValue, txtDataTime, txtDataTemp, txtDataHumid;
    TextView txtDataDust25, txtDataDust100, txtDataDCIndex, txtDataCO2;

    //    EnvDataRecieveTask envDataRecieveTask = new EnvDataRecieveTask();
    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        txtDataTypeTitle = (TextView) view.findViewById(R.id.txtDataTypeTitle);
        txtDataState = (TextView) view.findViewById(R.id.txtDataState);
        txtDataValue = (TextView) view.findViewById(R.id.txtDataValue);
        txtDataTime = (TextView) view.findViewById(R.id.txtDataTime);
        txtDataTemp = (TextView) view.findViewById(R.id.txtDataTemp);
        txtDataHumid = (TextView) view.findViewById(R.id.txtDataHumid);

        txtDataDust25 = (TextView) view.findViewById(R.id.txtDataDust25);
        txtDataDust100 = (TextView) view.findViewById(R.id.txtDataDust100);
        txtDataDCIndex = (TextView) view.findViewById(R.id.txtDataDCIndex);
        txtDataCO2 = (TextView) view.findViewById(R.id.txtDataCO2);

        txtDataDust25.setOnClickListener(this);
        txtDataDust100.setOnClickListener(this);
        txtDataDCIndex.setOnClickListener(this);
        txtDataCO2.setOnClickListener(this);

        TimerTask loadEnvDataTask = new TimerTask() {
            public void run() {
                try {

                    Ion.with(getContext())
                            .load("http://121.67.246.208/add/")
                            .asString();

                    Ion.with(getContext())
                            .load("http://121.67.246.208/latest/")
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    saveDataSet(result);
                                    changeDataSet();
                                }
                            });

                } catch (Exception e) {

                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(loadEnvDataTask, 0, 5000); // 0초후 첫실행, 3초마다 계속실행

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        ((MainActivity) getActivity()).BackgroundLayout.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.bg_good));
    }

    @Override
    public void onClick(View v) {
        if (v == txtDataDust25) {
            EnvDataMode.setMode(EnvDataMode.DUST25);
        } else if (v == txtDataDust100) {
            EnvDataMode.setMode(EnvDataMode.DUST100);
        } else if (v == txtDataDCIndex) {
            EnvDataMode.setMode(EnvDataMode.DC_INDEX);
        } else if (v == txtDataCO2) {
            EnvDataMode.setMode(EnvDataMode.CO2);
        }

        changeDataSet();
    }

    private void saveDataSet(JsonObject result){
        EnvData envData = EnvDataMode.getEnvData();

        envData.setDust25(result.get("dust25").getAsInt());
        envData.setDust100(result.get("dust100").getAsInt());
        envData.setDcIndex(result.get("discomfort_index").getAsInt());
        envData.setCo2(result.get("co2").getAsInt());
        envData.setTemp(result.get("temp").getAsDouble());
        envData.setHumid(result.get("humid").getAsDouble());
        envData.setDatetime(result.get("created_at").getAsString());
    }

    private void changeDataSet(){
        changeDataBackground();
        changeDataButton();
        changeDataButtonStyle();
    }

    private void changeDataBackground(){
        int background_resource = 0;

        switch (EnvDataMode.getState()) {
            case EnvDataMode.GOOD:
                background_resource = R.mipmap.bg_good;
                break;
            case EnvDataMode.NORMAL:
                background_resource = R.mipmap.bg_normal;
                break;
            case EnvDataMode.BAD:
            case EnvDataMode.VERY_BAD:
                background_resource = R.mipmap.bg_bad;
                break;
        }

        ((MainActivity) getActivity()).BackgroundLayout.setBackground(ContextCompat.getDrawable(getContext(), background_resource));

    }

    private void changeDataButton(){
        EnvData envData = EnvDataMode.getEnvData();

        txtDataTypeTitle.setText(Html.fromHtml("<u>" + EnvDataMode.getName() + "</u>"));
        txtDataState.setText(EnvDataMode.getStateString());
        txtDataValue.setText(EnvDataMode.getValue() + " " + EnvDataMode.getCurrentUnit());
        txtDataTime.setText(envData.getDatetime());
        txtDataTemp.setText(envData.getTemp() + " ℃");
        txtDataHumid.setText(envData.getHumid() + "%");
        txtDataDust25.setText("초미세먼지 PM2.5\n" + envData.getDust25() + " μg/m³");
        txtDataDust100.setText("미세먼지 PM10\n" + envData.getDust100() + " μg/m³");
        txtDataDCIndex.setText("불쾌지수 " + envData.getDcIndex());
        txtDataCO2.setText("이산화탄소 " + envData.getCo2() + "ppm");
    }

    private void changeDataButtonStyle(){
        Drawable data_background = null;

        switch (EnvDataMode.getState()){
            case EnvDataMode.GOOD:
                data_background = getResources().getDrawable(R.mipmap.rounded_border_br);
                break;
            case EnvDataMode.NORMAL:
                data_background = getResources().getDrawable(R.mipmap.rounded_border_gr);
                break;
            case EnvDataMode.BAD:
            case EnvDataMode.VERY_BAD:
                data_background = getResources().getDrawable(R.mipmap.rounded_border_rd);
                break;
        }

        txtDataDust25.setBackground(getResources().getDrawable(R.mipmap.rounded_border));
        txtDataDust100.setBackground(getResources().getDrawable(R.mipmap.rounded_border));
        txtDataDCIndex.setBackground(getResources().getDrawable(R.mipmap.rounded_border));
        txtDataCO2.setBackground(getResources().getDrawable(R.mipmap.rounded_border));

        switch (EnvDataMode.getMode()){
            case EnvDataMode.DUST25:
                txtDataDust25.setBackground(data_background);
                break;
            case EnvDataMode.DUST100:
                txtDataDust100.setBackground(data_background);
                break;
            case EnvDataMode.DC_INDEX:
                txtDataDCIndex.setBackground(data_background);
                break;
            case EnvDataMode.CO2:
                txtDataCO2.setBackground(data_background);
                break;
        }
    }

//    private void changeColor() {
//        if (Build.VERSION.SDK_INT >= 21) {
//
//        }
//        Window window = getActivity().getWindow();
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUENT_STATUS);
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUND);
//        window.setStatusBarColor(null);
//    }


//    private class EnvDataRecieveTask extends AsyncTask<String, EnvData, Void> {
//        @Override
//        protected void doInBackground(String... url) {
//
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected void onProgressUpdate(EnvData... envData) {
//
//        }
//
//        @Override
//        protected void onPostExecute(void...) {
//            // doInBackground 에서 받아온 total 값 사용 장소
//        }
//    }
}
