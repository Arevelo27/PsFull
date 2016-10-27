package com.example.andres_desarrollo2.psfull.Server;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;

/**
 * Created by TSI on 05/05/2016.
 */
public class ServerRequest {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;

    public ServerRequest() {
    }

    public JSONObject getJSONFromUrl(String url, List<NameValuePair> params, int method) {
        try {

            // http client
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;

            // Checking http request method type
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                // adding post params
                if (params != null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                }

                httpResponse = httpClient.execute(httpPost);

            } else if (method == GET) {
                // appending params to url
                if (params != null) {
                    String paramString = URLEncodedUtils
                            .format(params, "utf-8");
                    url += "?" + paramString;
                }
                HttpGet httpGet = new HttpGet(url);

                httpResponse = httpClient.execute(httpGet);

            }
            httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
            Log.e("JSON", json);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }


        return jObj;

    }

    JSONObject jobj;

    public JSONObject getJSON(String url, List<NameValuePair> params, int method) {

        Params param = new Params(url, params, method);
        Request myTask = new Request();
        try {
            jobj = myTask.execute(param).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return jobj;
    }


    private static class Params {
        String url;
        List<NameValuePair> params;
        int method;

        Params(String url, List<NameValuePair> params, int method) {
            this.url = url;
            this.params = params;
            this.method = method;
        }
    }

    private class Request extends AsyncTask<Params, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(Params... args) {

            ServerRequest request = new ServerRequest();
            JSONObject json = request.getJSONFromUrl(args[0].url, args[0].params, args[0].method);

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {

            super.onPostExecute(json);

        }

    }
}