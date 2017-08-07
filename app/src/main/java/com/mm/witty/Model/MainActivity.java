package com.mm.witty.Model;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mm.witty.R;
import com.mm.witty.Request.JsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class MainActivity extends AppCompatActivity  implements SensorEventListener{

LayoutInflater inflater;
    TextView textView;
    TextToSpeech textToSpeech;
    Button saveButton1;
    Button previousJoke;

    private Button nextJoke;
    private Button btnTextToSpeech;
    private SensorManager sensorManager;
    private View view;
    private long lastUpdate;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inflater = MainActivity.this.getLayoutInflater();
        nextJoke = (Button)findViewById(R.id.nextjke);
        btnTextToSpeech = (Button)findViewById(R.id.textTOSpeech);
        textView = (TextView) findViewById(R.id.textView);
        previousJoke = (Button)findViewById(R.id.previousjke);

        setImageName();
        view = findViewById(R.id.textView);
        // Create an object of Sensor manager
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();

        nextJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AsyncJsonFeed().execute("http://api.icndb.com/jokes/random");  //executes the asnyctask using the url
                count++;

                if(count == 3) {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Here comes new features.");
                    alertDialog.setMessage("Shake it ... Shake it... if you wana next");
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Okay :( ",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }
        });


        previousJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AsyncJsonFeed().execute("http://api.icndb.com/jokes/random");  //executes the asnyctask using the url
                count--;

                if(count == 3) {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Here comes new features.");
                    alertDialog.setMessage("Shake it ... Shake it... if you wana next");
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Okay :( ",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }
        });

        btnTextToSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String convertTextToSpeech = textView.getText().toString();
                textToSpeech= new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
                    @Override
                    public void onInit(int status) {
                        if(status != TextToSpeech.ERROR){
                            textToSpeech.setPitch(0.4f);
                            textToSpeech.setSpeechRate(1);
                            textToSpeech.setLanguage(Locale.US);
                            textToSpeech.speak(convertTextToSpeech, TextToSpeech.QUEUE_FLUSH, null, null);
                        }
                    }
                });
            }
        });


    }
    @Override
    public void onDestroy()
    {
        if ( textToSpeech != null )
        {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }



    public void setImageName()
    {
        // Create a new instance of ALertDialog box
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        // inflate from save_picture.xml
        View mView = inflater.inflate(R.layout.send_alert, null);
        // We can use savePicture editText id from save_picture.xml now


        saveButton1 = mView.findViewById(R.id.saveButton);
        builder.setView(mView);
        // dialog box
        final AlertDialog dialog = builder.create();
        dialog.show();
        // setting onClick listener
        saveButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();


            }
        });

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            getAcceleromter(event);
        }
    }

    private void getAcceleromter(SensorEvent event){
        float[] values = event.values;
        float x = values[0];
        float y = values[1];
        float z = values[2];
        float delta = (x*x+y*y+z*z)/(SensorManager.GRAVITY_EARTH*SensorManager.GRAVITY_EARTH);
        long actualTime=event.timestamp;
        if(delta >=6)
        {
            if(actualTime - lastUpdate < 200) return ;
            lastUpdate = actualTime;
            Toast.makeText(this,"Device was shuffled", Toast.LENGTH_SHORT).show();
            new AsyncJsonFeed().execute("http://api.icndb.com/jokes/random");

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onResume() {
        super.onResume();

        // we are registering the sensor
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onPause()
    {
        super.onPause();
        sensorManager.unregisterListener(this);

    }

    class AsyncJsonFeed extends AsyncTask<String, Void, String>
    {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        protected String doInBackground(String... urls){
            return new JsonRequest().readJSONFeed(urls[0]);
        }

        protected void onPostExecute(String json)
        {
            try {
                JSONObject jo = new JSONObject(json);
                JSONObject value = jo.getJSONObject("value");

                textView.setText(value .getString("joke"));
                //textView.setText(value.getString("number") + value.getString("proverb")); // will pick these objects
            } catch (JSONException ex)
            {
                Log.d("JSONArray", ex.getLocalizedMessage());
            }
        }
    }
}