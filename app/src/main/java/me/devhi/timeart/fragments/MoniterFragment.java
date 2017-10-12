package me.devhi.timeart.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import me.devhi.timeart.models.EnvData;
import me.devhi.timeart.adapters.HistoryAdapter;
import me.devhi.timeart.R;

public class MoniterFragment extends Fragment {

    private Realm realm;

    private RecyclerView history_list;
    private RecyclerView.Adapter history_adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<EnvData> mDataset;
    private BarChart report_chart;
    private TextView title_moniter;

    public MoniterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_moniter, container, false);

        realm = Realm.getDefaultInstance();

        report_chart = (BarChart) view.findViewById(R.id.report_chart);
        history_list = (RecyclerView) view.findViewById(R.id.history_list);

        setupChart();
        setupHistoryList();

        initializeEnvData();

        return view;
    }

    private void initializeEnvData(){
        RealmResults<EnvData> results = realm.where(EnvData.class).findAllSorted("pk", Sort.DESCENDING);
        int limit = 10;

        List<BarEntry> entries = new ArrayList<>();

        for (EnvData envData : results) {
            if (--limit < 0) break;

            entries.add(new BarEntry(limit, envData.getDust25()));
            mDataset.add(envData);
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


    private void setupHistoryList(){
        history_list.setHasFixedSize(false);
        history_list.setNestedScrollingEnabled(false);

        layoutManager = new LinearLayoutManager(getContext());
        history_list.setLayoutManager(layoutManager);

        mDataset = new ArrayList<>();
        history_adapter = new HistoryAdapter(mDataset);
        history_list.setAdapter(history_adapter);
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
