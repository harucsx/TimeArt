package me.devhi.timeart;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.w3c.dom.Text;

public class HomeFragment extends Fragment {

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView txtDataTypeTitle = (TextView) view.findViewById(R.id.txtDataTypeTitle);
        txtDataTypeTitle.setText(Html.fromHtml("<u>" + "초미세먼지 PM2.5" + "</u>"));
//
//        Ion.with(getContext())
//                .load("http://naver.com")
//                .asJsonObject()
//                .setCallback(new FutureCallback<JsonObject>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonObject result) {
//                        Toast.makeText(getContext(), result.getAsString(), Toast.LENGTH_LONG).show();
//                    }
//                });

        return view;
    }
}
