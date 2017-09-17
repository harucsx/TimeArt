package me.devhi.timeart;

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
                                    txtDataTypeTitle.setText(Html.fromHtml("<u>" + EnvDataMode.getName() + "</u>"));

                                    int current_value = result.get("dust25").getAsInt();
                                    int background_resource = 0;

                                    switch (EnvDataMode.getState()){
                                        case EnvDataMode.GOOD:
                                            background_resource = R.mipmap.bg_good;
                                            break;
                                        case EnvDataMode.NORMAL:

                                            background_resource = R.mipmap.bg_normal;
                                            break;
                                        case EnvDataMode.BAD:

                                            background_resource = R.mipmap.bg_bad;
                                            break;
                                        case EnvDataMode.VERY_BAD:

                                            background_resource = R.mipmap.bg_bad;
                                            break;
                                    }

                                    ((MainActivity) getActivity()).BackgroundLayout.setBackground(ContextCompat.getDrawable(getContext(), background_resource));

                                    txtDataState.setText(EnvDataMode.getStateString());
                                    txtDataValue.setText(result.get("dust25").getAsString() + " μg/m³");
                                    txtDataTime.setText(result.get("created_at").getAsString());
                                    txtDataTemp.setText(result.get("temp").getAsString() + " ℃");
                                    txtDataHumid.setText(result.get("humid").getAsString() + "%");
                                    txtDataDust25.setText("초미세먼지 PM2.5\n" + result.get("dust25").getAsString() + " μg/m³");
                                    txtDataDust100.setText("미세먼지 PM10\n" + result.get("dust100").getAsString() + " μg/m³");
                                    txtDataDCIndex.setText("불쾌지수 " + result.get("discomfort_index").getAsString());
                                    txtDataCO2.setText("이산화탄소 " + result.get("co2").getAsString() + "ppm");

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

    private class EnvData {
        int dust25;
        int dust100;
        int co2;
        float temp;
        float humid;
    }

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
