package me.devhi.timeart.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import me.devhi.timeart.models.EnvData;
import me.devhi.timeart.models.EnvDataMode;
import me.devhi.timeart.MainActivity;
import me.devhi.timeart.R;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private Realm realm;
    private TimerTask loadEnvDataTask;

    private TextView txtDataTypeTitle, txtDataState, txtDataValue, txtDataTime, txtDataTemp, txtDataHumid;
    private TextView txtDataDust25, txtDataDust100, txtDataDCIndex, txtDataCO2;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        realm = Realm.getDefaultInstance();

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

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        enableDataLoader(loadEnvDataTask);
    }

    @Override
    public void onPause() {
        super.onPause();

        disableDataLoader(loadEnvDataTask);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        MainActivity mainActivity = (MainActivity) getActivity();
        ConstraintLayout backgroundLayout = mainActivity.backgroundLayout;
        backgroundLayout.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.bg_good));
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

    private void enableDataLoader(TimerTask timerTask) {
        timerTask = new TimerTask() {
            public void run() {
                try {
                    // TODO : TEST CODE

                    Log.i("TimeArt", "Task do.");

                    Ion.with(getActivity())
                            .load("http://121.67.246.209:81/add/")
                            .asString();

                    Ion.with(getActivity())
                            .load("http://121.67.246.209:81/latest/")
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    try {
                                        saveDataSet(result);
                                    } catch (Exception e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            });

                } catch (Exception e) {
                    e.printStackTrace();
                    this.cancel();
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 5000); // 0초후 첫 실행, 5초마다 반복 실행
    }

    private void disableDataLoader(TimerTask timerTask) {
        if (timerTask != null) {
            timerTask.cancel();
        }
    }

    private void saveDataSet(final JsonObject result) {
        if (result != null) {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    try{
                        realm.createObjectFromJson(EnvData.class, result.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    changeDataSet();
                }
            });
        }
    }

    private void changeDataSet() {
        changeDataBackground();
        changeDataButtonLabel();
        changeDataButtonStyle();
    }

    private void changeDataBackground() {
        ConstraintLayout backgroundLayout = null;
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

        if (getActivity() != null) {
            backgroundLayout = ((MainActivity) getActivity()).backgroundLayout;

            if (backgroundLayout != null) {
                backgroundLayout.setBackground(ContextCompat.getDrawable(getContext(), background_resource));
            }
        }
    }

    private void changeDataButtonLabel() {
        EnvData envData = EnvDataMode.getEnvData();

        txtDataTypeTitle.setText(Html.fromHtml("<u>" + EnvDataMode.getName() + "</u>"));

        txtDataState.setText(EnvDataMode.getStateString());
        txtDataValue.setText(EnvDataMode.getValue() + " " + EnvDataMode.getCurrentUnit());

        txtDataTime.setText(envData.getCreatedAt());
        txtDataTemp.setText(envData.getTemp() + " ℃");
        txtDataHumid.setText(envData.getHumid() + "%");
        txtDataDust25.setText("초미세먼지 PM2.5\n" + envData.getDust25() + " μg/m³");
        txtDataDust100.setText("미세먼지 PM10\n" + envData.getDust100() + " μg/m³");
        txtDataDCIndex.setText("불쾌지수 " + envData.getDiscomfortIndex());
        txtDataCO2.setText("이산화탄소 " + envData.getCo2() + "ppm");
    }

    private void changeDataButtonStyle() {
        Drawable data_background = null;

        switch (EnvDataMode.getState()) {
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

        switch (EnvDataMode.getMode()) {
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
}
