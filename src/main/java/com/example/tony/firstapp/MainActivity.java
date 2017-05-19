package com.example.tony.firstapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private G_ControlClient controller = G_ControlClient.instance();
    AsyncCaller background_task;
    ArrayList<String> list_titles;
    ArrayAdapter<String> array_adapter;
    Button add_delegate;
    EditText zip_text;
    ListView weather_reports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        background_task = new AsyncCaller();
        list_titles = new ArrayList<>();
        array_adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                list_titles);
        add_delegate = (Button) findViewById(R.id.queryButton);
        add_delegate.setOnClickListener(queryHandler);
        zip_text = (EditText) findViewById(R.id.cityText);
        weather_reports = (ListView) findViewById(R.id.listView);
        weather_reports.setAdapter(array_adapter);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    View.OnClickListener queryHandler = new View.OnClickListener() {
        public void onClick(View v) {
            int zip = (int) Integer.valueOf(zip_text.getText().toString());
            controller.addZipDelegate(zip);
            new AsyncCaller().execute();
        }
    };

    public void refreshListView() {
        array_adapter.notifyDataSetChanged();
    }

    class AsyncCaller extends AsyncTask<Void, Void, String> {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(Void... urls) {
            String resp = "";
            G_DelegateClient delegate =  controller.delegates().pop();
            String ep = G_NetUtils.buildRequest(controller.apiBaseUrl(),delegate.args());
            resp = G_NetUtils.getRequest(ep);
            if(!list_titles.contains(resp.toString()))
                list_titles.add(resp);
            return resp;
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }
            refreshListView();
            progressBar.setVisibility(View.GONE);
            Log.i("INFO", response);
        }
    }
}
