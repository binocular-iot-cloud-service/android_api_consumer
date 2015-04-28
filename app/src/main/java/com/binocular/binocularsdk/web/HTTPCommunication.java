package com.binocular.binocularsdk.web;

import android.os.AsyncTask;
import android.util.Base64;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Async task fot http communication. Create a new instance for each Http request
 */
public class HTTPCommunication extends AsyncTask<Void, Void, Void>{

    OnDone done;
    String result;
    IEndpoint endpoint;

    public HTTPCommunication(IEndpoint endpoint, OnDone done){
        this.done = done;
        this.endpoint = endpoint;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;

        try {
            response = httpclient.execute(endpoint.getHttpRequest());
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){

                responseString =getStringResponse(response);
            } else{
                responseString =getStringResponse(response);

                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        result = responseString;

        return null;
    }

    /**
     * Gets the HttpResonse as a string
     * @param[in] res The HttpRepons to fetch the string from
     * */
    private String getStringResponse(HttpResponse res){
        String result = "";
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            res.getEntity().writeTo(out);
            result = out.toString();
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);


        /// Callback when done.
        done.onHttpResponse(result);
    }
}
