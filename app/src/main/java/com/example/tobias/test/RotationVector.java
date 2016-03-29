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

public class RotationVector extends AppCompatActivity implements SensorEventListener{
    private SensorManager mSensorManager;
    private Sensor rotationVector;
    private int azimut;
    private TextView degrees;
    private TextView direction;
    private ImageView image;
    float[] orientation = new float[3];
    float[] rotationMat = new float[9];
    private RotateAnimation rotate;
    float rotationDegree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation_vector);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Compass with rotation vector");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        rotationVector = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        degrees = (TextView) findViewById(R.id.azimut2);
        direction = (TextView) findViewById(R.id.direction2);
        image = (ImageView) findViewById(R.id.compassimage);

    }
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, rotationVector, SensorManager.SENSOR_DELAY_UI);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType()==Sensor.TYPE_ROTATION_VECTOR){
            SensorManager.getRotationMatrixFromVector( rotationMat, event.values );
            azimut = (int) Math.toDegrees(SensorManager.getOrientation(rotationMat,orientation)[0]);
            if ( azimut<0){
                azimut+=360;
            }
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
        degrees.setText(String.valueOf(azimut)+(char)176);

        rotate = new RotateAnimation(rotationDegree,-azimut, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotate.setDuration(210);
        rotate.setFillAfter(true);
        image.startAnimation(rotate);
        rotationDegree = -azimut;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
