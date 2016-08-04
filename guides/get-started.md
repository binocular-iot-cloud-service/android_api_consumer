[//]: # (title: Get started with Android)
[//]: # (description: Quick guide to get started using the Binocular API through the Android SDK)
[//]: # (env: android)

# Getting started
* Start by cloning the Android repository and import it into Android studio
* Open the Credentials.java file and set your API key and client secret.
* Make sure your device have Internet connection
* Run the app

At this point you should have some JSON data presented in your GUI comming from Binocular.

### Using the endpoints
In order to make an API call you need three things:

* HTTPCommunication - Inherits AsyncTask so that the GUI don't freeze (or even crash because od network on main thread)
* Endpoints - This is a static class that you can use in order to get the available endpoints from Binocular. All the public methods returns an Endpoint needed in the HTTPCommunication class.
* OnDone - It's just an interface callback that will get your data when the network activities are done.

### Example of an API call
Get device types:
```java
HTTPCommunication http = new HTTPCommunication(Endpoints.getDevicetypes(), new OnDone() {
      @Override
      public void onHttpResponse(String data) {
           txt_myJson.setText(data);
      }
});
http.execute();
```
Set some flag on a device:
```java
HTTPCommunication http2 = new HTTPCommunication(Endpoints.setFlags("[myDeviceId]", "{\"power_off\":\"0\"}"), new OnDone() {
     @Override
     public void onHttpResponse(String data) {
           String myData = data;
     } 
});
http2.execute();
```
Data format
In order to keep a general and simple solution all returned data will be strings. The same goes for posting. So in order to post JSON to the API you will insert JSON as a String into the HTTPCommunication class.

Also:
* Data must conform to definied datamodel in the Binocular cloud
* Flags must conform to definied flagmodels in the Binocular cloud

Don't forget to escape special characters in the string `{ "power_off":"0" }` becomes `String myJsonData = "{\"power_off\":\"0\"}";`

### Assumptions
In order to use networking internet permission have been added in the manifest
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```
Screen orientation have been set to portrait in the manifest
```
android:screenOrientation="portrait"
```
### Debugging
A good place to start debugging is in the HTTPCommunication class. Start by placing a breakpoint in the doInBackground. Follow along to see any status codes and what the variable responseString is. This should provide you with a good indication on what might be wrong.

Checklist:

* Check Internet connectivity
* Check correct credentials in the Credentials.java file
* If post: make sure the posted data is in correct format
* Make sure that the posted data conforms to the definied datamodels definied in Binocular cloud
* Start with an enpoint that doesn't require any parameters (like getAllUserDevices()) and see if you can get any data