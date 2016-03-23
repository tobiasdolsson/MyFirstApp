package com.example.tobias.test;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class StartAcceleratorActivity extends AppCompatActivity implements SensorEventListener {
    //testpush
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    TextView text0;
    TextView text1;
    TextView text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_accelerator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        text0 = (TextView) findViewById(R.id.value0);
        text1 = (TextView) findViewById(R.id.value1);
        text2 = (TextView) findViewById(R.id.value2);


    }


    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }

    public void onSensorChanged(SensorEvent event){
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        text0.setText("X: "+ String.format("%.2f",x));
        text1.setText("Y: "+ String.format("%.2f",y));
        text2.setText("Z: "+ String.format("%.2f",z));

    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    }
