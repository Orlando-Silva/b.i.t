package com.app.bit.Helpers;


import android.os.StrictMode;
import android.util.Log;
import android.util.Pair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import com.app.bit.DAL.HttpRequestObjects.SendWithdrawFirstStepRequest;
import com.app.bit.DAL.HttpRequestObjects.SignerRequest;
import com.app.bit.DAL.HttpResponseObjects.WithdrawResponse.SendWithdrawFirstStepResponse;
import com.google.gson.*;

import java.io.InputStreamReader;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class HttpHelpers {

    private static final String REQUEST_METHOD = "GET";
    private static final int READ_TIMEOUT = 15000;
    private static final int CONNECTION_TIMEOUT = 15000;

    public static <T, P, K> T makePostRequest(String urlTo, List<Pair<P, K>> parameters, Class<T> clazz) {
        String inputLine;

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);

            URL url = new URL(urlTo);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));

            if(parameters != null) {
                writer.write(getQuery(parameters));
            }
            writer.flush();
            writer.close();
            os.close();

            conn.connect();

            InputStreamReader streamReader = new
                    InputStreamReader(conn.getInputStream());

            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }

            reader.close();
            streamReader.close();

            String stringResult = stringBuilder.toString();

            return new GsonBuilder().create().fromJson(stringResult, clazz);
        }
        catch (ProtocolException e) {
            Log.d("url", urlTo);
            e.printStackTrace();

        } catch (MalformedURLException e) {
            Log.d("url", urlTo);
            e.printStackTrace();

        } catch (IOException e) {
            Log.d("url", urlTo);
            e.printStackTrace();

        }
        return null;
    }

    public static <T, P, K> T makeWithdrawPostRequest(String urlTo, SendWithdrawFirstStepRequest parameters, Class<T> clazz) throws Exception{
        String inputLine;

        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
            URL url = new URL(urlTo);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            byte[] input =  new GsonBuilder().create().toJson(parameters).getBytes("utf-8");
            os.write(input, 0, input.length);


            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            writer.flush();
            writer.close();
            os.close();

            conn.connect();

            InputStreamReader streamReader = null;

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK
                || conn.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
                streamReader = new InputStreamReader(conn.getInputStream());
            }
            else {
                streamReader = new InputStreamReader(conn.getErrorStream());
            }

            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }

            reader.close();
            streamReader.close();

            String stringResult = stringBuilder.toString();
            Log.e("WithdrawError", stringResult);
            return new GsonBuilder().create().fromJson(stringResult, clazz);
        }
        catch (ProtocolException e) {
            Log.d("url", urlTo);
            e.printStackTrace();
            throw e;

        } catch (MalformedURLException e) {
            Log.d("url", urlTo);
            e.printStackTrace();
            throw e;

        } catch (IOException e) {
            Log.d("url", urlTo);
            e.printStackTrace();
            throw e;

        }
    }

    public static <T, P, K> T makeSignWithdrawPostRequest(String urlTo, SignerRequest parameters, Class<T> clazz) throws Exception {
        String inputLine;

        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
            URL url = new URL(urlTo);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            byte[] input =  new GsonBuilder().create().toJson(parameters).getBytes("utf-8");
            os.write(input, 0, input.length);


            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            writer.flush();
            writer.close();
            os.close();

            conn.connect();

            InputStreamReader streamReader = null;

            if (conn.getResponseCode() == HttpURLConnection.HTTP_CREATED
                    || conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                streamReader = new InputStreamReader(conn.getInputStream());
            }
            else {
                streamReader = new InputStreamReader(conn.getErrorStream());
            }

            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }

            reader.close();
            streamReader.close();

            String stringResult = stringBuilder.toString();
            Log.e("WithdrawError", stringResult);
            return new GsonBuilder().create().fromJson(stringResult, clazz);
        }
        catch (ProtocolException e) {
            Log.d("url", urlTo);
            e.printStackTrace();
            throw e;

        } catch (MalformedURLException e) {
            Log.d("url", urlTo);
            e.printStackTrace();
            throw e;

        } catch (IOException e) {
            Log.d("url", urlTo);
            e.printStackTrace();
            throw e;
        }
    }

    public static <T, P, K> T makeSecondStepWithdrawPostRequest(String urlTo, SendWithdrawFirstStepResponse parameters, Class<T> clazz) throws Exception {
        String inputLine;

        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
            URL url = new URL(urlTo);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            byte[] input =  new GsonBuilder().create().toJson(parameters).getBytes("utf-8");
            os.write(input, 0, input.length);


            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            writer.flush();
            writer.close();
            os.close();

            conn.connect();

            InputStreamReader streamReader = null;

            if (conn.getResponseCode() == HttpURLConnection.HTTP_CREATED
                    || conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                streamReader = new InputStreamReader(conn.getInputStream());
            }
            else {
                streamReader = new InputStreamReader(conn.getErrorStream());
            }

            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }

            reader.close();
            streamReader.close();

            String stringResult = stringBuilder.toString();
            Log.e("WithdrawError", stringResult);
            return new GsonBuilder().create().fromJson(stringResult, clazz);
        }
        catch (ProtocolException e) {
            Log.d("url", urlTo);
            e.printStackTrace();
            throw e;

        } catch (MalformedURLException e) {
            Log.d("url", urlTo);
            e.printStackTrace();
            throw e;

        } catch (IOException e) {
            Log.d("url", urlTo);
            e.printStackTrace();
            throw e;
        }
    }



    private static <P, K> String getQuery(List<Pair<P, K>> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Pair<P, K> pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.first.toString(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.second.toString(), "UTF-8"));
        }

        return result.toString();
    }

    public static <T> T makeGetRequest(String url, Class<T> clazz)
    {

        String inputLine;

        try {
            //Create a URL object holding our url
            URL myUrl = new URL(url);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);

            HttpURLConnection connection =(HttpURLConnection)
                    myUrl.openConnection();

            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            connection.connect();

            InputStreamReader streamReader = new
                    InputStreamReader(connection.getInputStream());

            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }

            reader.close();
            streamReader.close();

            String stringResult = stringBuilder.toString();
            return new GsonBuilder().create().fromJson(stringResult, clazz);

        }
        catch (ProtocolException e) {

            e.printStackTrace();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        return null;
    }

}


