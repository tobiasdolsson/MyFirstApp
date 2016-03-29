package com.example.tobias.test;

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
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class CompassActivity extends AppCompatActivity implements SensorEventListener{

    double azimut;
    private SensorManager mSensorManager;
    Sensor accelerometer;
    Sensor magnetometer;
    float[] mGravity = new float[3];
    float[] mGeomagnetic = new float[3];
    float Ro[];
    float I[];
    private TextView degrees;
    private TextView direction;
    private ImageView image;
    private RotateAnimation rotate;
    float rotationDegree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Compass");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Ro = new float[9];
        I = new float[9];
        degrees = (TextView) findViewById(R.id.azimut);
        direction = (TextView) findViewById(R.id.direction);
        image = (ImageView) findViewById(R.id.compassimage);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            final float alpha = 0.8f;
            mGravity[0] = alpha * mGravity[0] + (1 - alpha) * event.values[0];
            mGravity[1] = alpha * mGravity[1] + (1 - alpha) * event.values[1];
            mGravity[2] = alpha * mGravity[2] + (1 - alpha) * event.values[2];
        }

        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            final float alpha = 0.8f;
            mGeomagnetic[0] = alpha * mGeomagnetic[0] + (1 - alpha) * event.values[0];
            mGeomagnetic[1] = alpha * mGeomagnetic[1] + (1 - alpha) * event.values[1];
            mGeomagnetic[2] = alpha * mGeomagnetic[2] + (1 - alpha) * event.values[2];
        }

        if(mGravity != null && mGeomagnetic != null){

            boolean success = SensorManager.getRotationMatrix(Ro, I, mGravity, mGeomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(Ro, orientation);
                //rotationDegree = orientation[0];
                azimut = Math.toDegrees(orientation[0]);
                float fazimut = (float) azimut;
                if (azimut < 0) {
                    azimut += 360;
                }
                int range = (int) (azimut / (360f / 16f));
                if (range == 15 || range == 0)
                    direction.setText("NORTH");
                if (range == 1 || range == 2)
                    direction.setText("NORTHEAST");
                if (range == 3 || range == 4)
                    direction.setText("EAST");
                if (range == 5 || range == 6)
                    direction.setText("SOUTHEAST");
                if (range == 7 || range == 8)
                    direction.setText("SOUTH");
                if (range == 9 || range == 10)
                    direction.setText("SOUTHWEST");
                if (range == 11 || range == 12)
                    direction.setText("WEST");
                if (range == 13 || range == 14)
                    direction.setText("NORTHWEST");
                degrees.setText(String.format("%.0f", azimut)+((char) 176));

                rotate = new RotateAnimation(rotationDegree,-fazimut, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                rotate.setDuration(210);
                rotate.setFillAfter(true);
                image.startAnimation(rotate);
                rotationDegree = -fazimut;
            }

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
