package com.example.tony.firstapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private G_ControlClient controller = G_ControlClient.instance();
    AsyncCaller background_task;
    Button zip_button;
    EditText zip_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        background_task = new AsyncCaller();
        zip_button = (Button) findViewById(R.id.queryButton);
        zip_button.setOnClickListener(queryHandler);
        zip_text = (EditText) findViewById(R.id.cityText);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    View.OnClickListener queryHandler = new View.OnClickListener() {
        public void onClick(View v) {
            // it was the 1st button
            int zip = (int) Integer.valueOf(zip_text.getText().toString());
            controller.addZipDelegate(zip);
            new AsyncCaller().execute();
        }
    };

    class AsyncCaller extends AsyncTask<Void, Void, String> {
        private Exception exception;

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        TextView responseView = (TextView) findViewById(R.id.responseView);

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            responseView.setText("");
//            controller.addZipDelegate(60130);
        }

        protected String doInBackground(Void... urls) {
            String resp = "nil";
            for(G_DelegateClient delegate : controller.delegates())
            {
                String ep = G_NetUtils.buildRequest(controller.apiBaseUrl(),delegate.args());
                resp = G_NetUtils.getRequest(ep);
            }
            return resp;
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }
            progressBar.setVisibility(View.GONE);
            Log.i("INFO", response);
            responseView.setText(response);
        }
    }
}
