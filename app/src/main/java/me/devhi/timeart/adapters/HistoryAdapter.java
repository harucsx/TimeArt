package me.devhi.timeart.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import me.devhi.timeart.R;
import me.devhi.timeart.models.EnvData;
import me.devhi.timeart.models.EnvDataMode;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private ArrayList<EnvData> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text_list_history_time, text_list_history_date, text_list_history_dust25;
        public TextView text_list_history_dust100, text_list_history_dc_index, text_list_history_co2;

        public ViewHolder(View view) {
            super(view);
            text_list_history_time = (TextView) view.findViewById(R.id.text_list_history_time);
            text_list_history_date = (TextView) view.findViewById(R.id.text_list_history_date);
            text_list_history_dust25 = (TextView) view.findViewById(R.id.text_list_history_dust25);
            text_list_history_dust100 = (TextView) view.findViewById(R.id.text_list_history_dust100);
            text_list_history_dc_index = (TextView) view.findViewById(R.id.text_list_history_dc_index);
            text_list_history_co2 = (TextView) view.findViewById(R.id.text_list_history_co2);
        }
    }

    public HistoryAdapter(ArrayList<EnvData> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_history, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text_list_history_dust25.setText(mDataset.get(position).getDust25() + EnvDataMode.getUnit(EnvDataMode.DUST25));
        holder.text_list_history_dust100.setText(mDataset.get(position).getDust100() + EnvDataMode.getUnit(EnvDataMode.DUST100));
        holder.text_list_history_dc_index.setText(mDataset.get(position).getDiscomfortIndex() + EnvDataMode.getUnit(EnvDataMode.DC_INDEX));
        holder.text_list_history_co2.setText(mDataset.get(position).getCo2() + EnvDataMode.getUnit(EnvDataMode.CO2));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
