package me.devhi.timeart.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import me.devhi.timeart.R;
import me.devhi.timeart.models.EnvData;
import me.devhi.timeart.models.EnvDataMode;

public class ReportDetailFragment extends Fragment {

    Realm realm;

    private BarChart report_chart;
    private TextView text_report_detail_min, text_report_detail_max, text_report_detail_average;
    private TextView title_report_detail;

    public ReportDetailFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_detail, container, false);

        realm = Realm.getDefaultInstance();

        title_report_detail = (TextView) view.findViewById(R.id.title_report_detail);
        text_report_detail_min = (TextView) view.findViewById(R.id.text_report_detail_min);
        text_report_detail_max = (TextView) view.findViewById(R.id.text_report_detail_max);
        text_report_detail_average = (TextView) view.findViewById(R.id.text_report_detail_average);
        report_chart = (BarChart) view.findViewById(R.id.report_chart);

//        Bundle arguments = getArguments();
//
//        if (arguments != null) {
//            title_report_detail.setText(arguments.getString("title", "Default"));
//        }

        setupChart();
        initializeEnvData();
        initializeMetaData();

        return view;
    }

    private void initializeMetaData() {
        RealmResults<EnvData> results = realm.where(EnvData.class).findAllSorted("pk", Sort.DESCENDING);
        int max = results.max("dust25").intValue();
        int min = results.min("dust25").intValue();
        int average = (int) results.average("dust25");

        text_report_detail_average.setText("평균 " + average + EnvDataMode.getUnit(EnvDataMode.DUST25));
        text_report_detail_max.setText("최대 " + max + EnvDataMode.getUnit(EnvDataMode.DUST25));
        text_report_detail_min.setText("최소 " + min + EnvDataMode.getUnit(EnvDataMode.DUST25));
    }

    private void initializeEnvData() {
        RealmResults<EnvData> results = realm.where(EnvData.class).findAllSorted("pk", Sort.DESCENDING);
        int limit = 10;

        List<BarEntry> entries = new ArrayList<>();

        for (EnvData envData : results) {
            if (--limit < 0) break;

            entries.add(new BarEntry(limit, envData.getDust25()));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Label"); // add entries to mDataset
        dataSet.setColor(getResources().getColor(R.color.colorBR));

        BarData lineData = new BarData(dataSet);
        lineData.setBarWidth(0.3f);

        report_chart.setData(lineData);

        if (report_chart.getYMax() < 80) {
            report_chart.getAxisLeft().setAxisMaximum(80.0f);
        }

        report_chart.invalidate(); // refresh
        report_chart.animateXY(1000, 1000);
    }

    private void setupChart() {
        report_chart.setDrawGridBackground(true);
        report_chart.setGridBackgroundColor(getResources().getColor(R.color.colorChartBackground));
        report_chart.setNoDataText("서버로부터 정보를 불러올 수 없습니다.");
        report_chart.setHighlightPerDragEnabled(false);
        report_chart.setHighlightPerTapEnabled(false);
        report_chart.getAxisRight().setEnabled(false);
        report_chart.getXAxis().setEnabled(false);
        report_chart.getAxisLeft().setTextColor(getResources().getColor(android.R.color.white));
        report_chart.setDescription(null);
        report_chart.getLegend().setEnabled(false);
    }
}
