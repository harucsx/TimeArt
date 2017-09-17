package me.devhi.timeart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

public class ReportFragment extends Fragment {

    BarChart report_chart;

    public ReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        report_chart = (BarChart) view.findViewById(R.id.report_chart);
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

//        report_chart.setGr

//        EnvData[] dataObjects = ...;

        List<BarEntry> entries = new ArrayList<BarEntry>();

//        for (EnvData data : dataObjects) {
//
//            // turn your data into Entry objects
//            entries.add(new BarEntry(data.getValueX(), data.getValueY()));
//        }

        entries.add(new BarEntry(1, 19));
        entries.add(new BarEntry(2, 20));
        entries.add(new BarEntry(3, 36));
        entries.add(new BarEntry(4, 24));
        entries.add(new BarEntry(5, 35));
        entries.add(new BarEntry(6, 40));
        entries.add(new BarEntry(7, 23.8f));
        entries.add(new BarEntry(8, 20));
        entries.add(new BarEntry(9, 31));
        entries.add(new BarEntry(10, 44));
        entries.add(new BarEntry(11, 33));

        BarDataSet dataSet = new BarDataSet(entries, "Label"); // add entries to dataset
        dataSet.setColor(getResources().getColor(R.color.colorBR));
//        dataSet.set
        //dataSet.setValueTextColor(...); // styling, ...

        BarData lineData = new BarData(dataSet);
        lineData.setBarWidth(0.3f);

        report_chart.setData(lineData);

        if (report_chart.getYMax() < 80) {
            report_chart.getAxisLeft().setAxisMaximum(80.0f);
        }

        report_chart.invalidate(); // refresh
        report_chart.animateXY(1000, 1000);

        return view;
    }
}
