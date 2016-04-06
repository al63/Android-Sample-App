package com.sharethrough.sample;

import android.os.AsyncTask;
import com.sharethrough.sdk.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class ContentRequest {

    private ArrayList<OnCompleteListener> listeners = new ArrayList<OnCompleteListener>();

    public void doRequest() {
        SendHttpRequestTask task = new SendHttpRequestTask();
        task.execute(new String[]{"http://messy-truth-api.herokuapp.com/"});
    }

    private void responseReceived(String response) {
        for (OnCompleteListener listener : listeners) {
            listener.onComplete(response);
        }
    }
    public interface OnCompleteListener {
        void onComplete(String response);
    }

    public void addOnCompleteListener(OnCompleteListener onCompleteListener) {
        listeners.add(onCompleteListener);
    }

    private class SendHttpRequestTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);

                // Starts the query
                conn.connect();
                InputStream is = conn.getInputStream();

                // Convert the InputStream into a string
                BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    stringBuilder.append(s);
                }
            } catch (ProtocolException e) {
                Logger.w(e.getMessage());
            } catch (IOException e) {
                Logger.w(e.getMessage());
            }

            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String response) {
            responseReceived(response);
        }
    }
}
