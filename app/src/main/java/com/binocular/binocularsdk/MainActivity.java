package com.binocular.binocularsdk;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.binocular.binocularsdk.web.Endpoints;
import com.binocular.binocularsdk.web.HTTPCommunication;
import com.binocular.binocularsdk.web.OnDone;

/**
 * MainActivity for the application
 * */
public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView txt_myJson = (TextView)findViewById(R.id.txt_myJson);

        //TODO: Set your credentials in the Credentials.java file

        HTTPCommunication http = new HTTPCommunication(Endpoints.getDevicetypes(), new OnDone() {
            @Override
            public void onHttpResponse(String data) {
                txt_myJson.setText(data);
            }
        });
        http.execute();

        /*
        //Example of a post.
        //Replace with a correct device id and the json must conform to the definied flag modell.
        HTTPCommunication http2 = new HTTPCommunication(Endpoints.setFlags("[myDeviceId]", "{\"power_off\":\"0\"}"), new OnDone() {
            @Override
            public void onHttpResponse(String data) {
                String myData = data;
            }
        });
        http2.execute();
        */
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
