package me.devhi.timeart.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import me.devhi.timeart.R;

public class ReportFragment extends Fragment implements View.OnClickListener {

    private final int FRAGMENT_HOURLY = 1;
    private final int FRAGMENT_DAILY = 2;
    private final int FRAGMENT_WEEKLY = 3;
    private final int FRAGMENT_MONTHLY = 4;

    private LinearLayout menu_hourly, menu_daily, menu_weekly, menu_monthly;
    private LinearLayout bar_hourly, bar_daily, bar_weekly, bar_monthly;

    public ReportFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        menu_hourly = (LinearLayout) view.findViewById(R.id.menu_hourly);
        menu_daily = (LinearLayout) view.findViewById(R.id.menu_daily);
        menu_weekly = (LinearLayout) view.findViewById(R.id.menu_weekly);
        menu_monthly = (LinearLayout) view.findViewById(R.id.menu_monthly);

        bar_hourly = (LinearLayout) view.findViewById(R.id.bar_hourly);
        bar_daily = (LinearLayout) view.findViewById(R.id.bar_daily);
        bar_weekly = (LinearLayout) view.findViewById(R.id.bar_weekly);
        bar_monthly = (LinearLayout) view.findViewById(R.id.bar_monthly);

        menu_hourly.setOnClickListener(this);
        menu_daily.setOnClickListener(this);
        menu_weekly.setOnClickListener(this);
        menu_monthly.setOnClickListener(this);

        changeReportView(FRAGMENT_HOURLY);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == menu_hourly) {
            changeButtonStyle(FRAGMENT_HOURLY);
            changeReportView(FRAGMENT_HOURLY);
        } else if (v == menu_daily) {
            changeButtonStyle(FRAGMENT_DAILY);
            changeReportView(FRAGMENT_DAILY);
        } else if (v == menu_weekly) {
            changeButtonStyle(FRAGMENT_WEEKLY);
            changeReportView(FRAGMENT_WEEKLY);
        } else if (v == menu_monthly) {
            changeButtonStyle(FRAGMENT_MONTHLY);
            changeReportView(FRAGMENT_MONTHLY);
        }
    }

    private void changeButtonStyle(int no){
        menu_hourly.setBackgroundResource(R.drawable.white);
        menu_daily.setBackgroundResource(R.drawable.white);
        menu_weekly.setBackgroundResource(R.drawable.white);
        menu_monthly.setBackgroundResource(R.drawable.white);

        bar_hourly.setVisibility(View.INVISIBLE);
        bar_daily.setVisibility(View.INVISIBLE);
        bar_weekly.setVisibility(View.INVISIBLE);
        bar_monthly.setVisibility(View.INVISIBLE);

        switch (no){
            case FRAGMENT_HOURLY:
                menu_hourly.setBackgroundResource(R.drawable.white70);
                bar_hourly.setVisibility(View.VISIBLE);
                break;
            case FRAGMENT_DAILY:
                menu_daily.setBackgroundResource(R.drawable.white70);
                bar_daily.setVisibility(View.VISIBLE);

                break;
            case FRAGMENT_WEEKLY:
                menu_weekly.setBackgroundResource(R.drawable.white70);
                bar_weekly.setVisibility(View.VISIBLE);

                break;
            case FRAGMENT_MONTHLY:
                menu_monthly.setBackgroundResource(R.drawable.white70);
                bar_monthly.setVisibility(View.VISIBLE);

                break;
        }
    }

    private void changeReportView(int fragment_no) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("title", Integer.toString(fragment_no));

        ReportDetailFragment reportDetailFragment = new ReportDetailFragment();
        reportDetailFragment.setArguments(bundle);
        transaction.replace(R.id.fragment_container_report, reportDetailFragment);
        transaction.commit();
    }
}
