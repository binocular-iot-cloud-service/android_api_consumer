package com.binocular.binocularsdk.web;

import android.util.Base64;
import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Class for all the endpoint. Spend your time learning these
 */
public class Endpoints {

    private static String BASE_URL = "http://api.binocular.se/v1/";

    /**
    * Sets the header with authentication and JSON
     * @param[in] req The Http request that will gets its headers
     * @return The HttpUriRequest with the headers set
    */
    private static HttpUriRequest defineHeader(HttpUriRequest req) {
       // req.setHeader("Content-Type", "application/json");
        String auth = Credentials.getApiKey() + ":" + Credentials.getClientSecret();

        String base64EncodedCredentials = Base64.encodeToString(auth.getBytes(), Base64.NO_WRAP);
        req.addHeader("Authorization", "Basic " + base64EncodedCredentials);
        req.setHeader("Accept", "application/json");
        req.setHeader("Content-Type", "application/json;charset=UTF-8");

        return req;
    }

    /**
     * Converts a string array to a JSON string array and returns is as a StringEntity for the HttpPost
     * @param[in] data String array that is to be converted
     * @return The converted StringEntity
    */
    private static StringEntity getAsEntities(String[] data){

        String str = "[";
        for(int i = 0; i < data.length; i++){
            str += "\""+data[i]+"\"";
            if(i < data.length -1){
                str += ",";
            }
        }

        str += "]";

        try {
            return new StringEntity(str);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    //////////////////////////////////
    ///// Endpoints starts here //////
    //////////////////////////////////

    /**
     * Gets the devicetypes
     * */
    public static IEndpoint getDevicetypes(){
        return new IEndpoint() {
            @Override
            public HttpUriRequest getHttpRequest() {
                return defineHeader(new HttpGet(BASE_URL + "devicetypes"));
            }
        };
    }

    /**
     * Gets all the devices from the devicetypeId
     */
    public static IEndpoint getDevices(final String devicetypeId){
        return new IEndpoint() {
            @Override
            public HttpUriRequest getHttpRequest() {
                return defineHeader(new HttpGet(BASE_URL + "devicetypes/"+devicetypeId+"/devices"));
            }
        };
    }

    /***
     * Gets a device from a given deviceId
     * @param[in] deviceId The id of the device
    */
    public static IEndpoint getDevice(final String deviceId){
        return new IEndpoint() {
            @Override
            public HttpUriRequest getHttpRequest() {
                return defineHeader(new HttpGet(BASE_URL + "devices/"+deviceId));
            }
        };
    }

    /**
     * Gets a given data entry by id
     * @param[in] dataEntryId The id of the specific data entry
     */
    public static IEndpoint getDataEntry(final String dataEntryId){
        return new IEndpoint() {
            @Override
            public HttpUriRequest getHttpRequest() {
                return defineHeader(new HttpGet(BASE_URL + "data/"+dataEntryId));
            }
        };
    }

    /**
     * Gets data from a device id
     * @param[in] deviceId The id of the device
     */
    public static IEndpoint getData(final String deviceId){
        return new IEndpoint() {
            @Override
            public HttpUriRequest getHttpRequest() {
                return defineHeader(new HttpGet(BASE_URL + "devices/"+deviceId+"/data"));
            }
        };
    }

    /**
     * Gets data from multiple devices.
     * @param[in] devices String array id device ids to fetch from.
     * */
    public static IEndpoint getDataFromMany(final String[] devices){
        return new IEndpoint() {
            @Override
            public HttpUriRequest getHttpRequest() {

                HttpPost post = new HttpPost(BASE_URL + "devices/data");
                post.setEntity(getAsEntities(devices));

                return defineHeader(post);
            }
        };
    }

    /**
     * Get all user devices
     * */
    public static IEndpoint getAllUserDevices(){
        return new IEndpoint() {
            @Override
            public HttpUriRequest getHttpRequest() {
                return defineHeader(new HttpGet(BASE_URL + "devices"));
            }
        };
    }

    /**
     * Sends data to the data. It is important that the json data conforms to the sepcified data modell.
     * @param[in] deviceId The id of the device that the data belongs to
     * @param[in] json The json data, as a string, that is to be pushed to the cloud.
     * */
    public static IEndpoint sendData(final String deviceId, final String json){
        return new IEndpoint() {
            @Override
            public HttpUriRequest getHttpRequest() {

                HttpPost post = new HttpPost(BASE_URL + "devices/"+deviceId+"/data");
                try {
                    post.setEntity(new StringEntity(json));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                return defineHeader(post);
            }
        };
    }

    /**
     * Gets flags from a device
     * @param[in] deviceId The id of the device to get the flags from
     * */
    public static IEndpoint getFlags(final String deviceId){
        return new IEndpoint() {
            @Override
            public HttpUriRequest getHttpRequest() {
                return defineHeader(new HttpGet(BASE_URL + "devices/"+deviceId+"/flags"));
            }
        };
    }

    /**
     * Set the flags on a device. It is important that the json data conforms to the sepcified data modell.
     * @param[in] deviceId The id of the device to set the flags on
     * @param[in] json The json data, as a string, that is to be pushed to the cloud.
     * */
    public static IEndpoint setFlags(final String deviceId, final String json){
        return new IEndpoint() {
            @Override
            public HttpUriRequest getHttpRequest() {

                HttpPost post = new HttpPost(BASE_URL + "devices/"+deviceId+"/flags");
                try {
                    post.setEntity(new StringEntity(json));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                return defineHeader(post);
            }
        };
    }


    /**
     * Gets a heartbeat on a device
     * @param[in] deviceId The id of the device to get the heartbeat from
     * */
    public static IEndpoint getHeartbeat(final String deviceId){
        return new IEndpoint() {
            @Override
            public HttpUriRequest getHttpRequest() {
                return defineHeader(new HttpGet(BASE_URL + "devices/"+deviceId+"/heartbeat"));
            }
        };
    }

    /**
     * Activate a device.
     * @param[in] deviceTypeId The id of the devicetype that the device belongs to
     * */
    public static IEndpoint activateDevice(final String deviceTypeId){
        return new IEndpoint() {
            @Override
            public HttpUriRequest getHttpRequest() {

                HttpPost post = new HttpPost(BASE_URL + "devicetypes/"+deviceTypeId+"/activate_device");
                return defineHeader(post);
            }
        };
    }
}
