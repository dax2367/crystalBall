/*=======================================================================================
	Author:			Holly Eaton

	Dev Envi:		Android Studio, eclipse Android Development Tools and Notepad++

	Program:		Crystal Ball App (Magic Eight ball game)

	Description:	This file is the main file of the program that imports all the dependencies, defines variables and
						controls all the activity of the program.
 
 ========================================================================================*/

package com.example.crystalball;

import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crystalball.ShakeDetector.OnShakeListener;

public class MainActivity extends ActionBarActivity
{
	public static final String TAG = MainActivity.class.getSimpleName();
	
	private CrystalBall mCrystalBall = new CrystalBall();
	private TextView mAnswerLabel;
	private Button mGetAnswerButton;	//BUTTON CODE for use in emulator REMOVE in the final version.
	private ImageView mCrystalBallImage;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private ShakeDetector mShakeDetector;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Assign the Views from the layout file
        mAnswerLabel = (TextView) findViewById(R.id.textView1);
        mGetAnswerButton = (Button) findViewById(R.id.button1);	//BUTTON CODE for use in emulator REMOVE in the final version.
    	mCrystalBallImage = (ImageView) findViewById(R.id.imageView1);

    	//Access services and sensors
    	mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    	mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    	mShakeDetector = new ShakeDetector(new OnShakeListener()
    	{			
			@Override
			public void onShake()
			{
				handleNewAnswer();
			}
		});
        
    	//BUTTON CODE for use in emulator that can't be shook, it will be REMOVED in the final version.
        mGetAnswerButton.setOnClickListener(new View.OnClickListener()
        {			
			@Override
			public void onClick(View v)
			{
				handleNewAnswer();
			}
		});
        
        // Exploring the Android Log
        Log.d(TAG, "We're logging from the onCreate() method!");
        Log.d(TAG, mAnswerLabel.toString());        
    }
    
    @Override	//Resumes paused activity when higher a priority process is finished.
    public void onResume()
    {
    	super.onResume();
    	mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }
    
    /*Pauses activity when not in use. This saves the battery by unregistering the listener so that
     * it isn't constantly checking the sensors.*/
    @Override
    public void onPause()
    {
    	super.onPause();
    	mSensorManager.unregisterListener(mShakeDetector);
    }
    
    private void animateCrystalBall()
    {
    	mCrystalBallImage.setImageResource(R.drawable.ball_animation);
    	AnimationDrawable ballAnimation = (AnimationDrawable) mCrystalBallImage.getDrawable();
    	if(ballAnimation.isRunning())
    	  {
    		ballAnimation.stop();
    	  }
    	ballAnimation.start();
    }
    
    private void animateAnswer()
    {
	    AlphaAnimation fadeInAnimation = new AlphaAnimation(0, 1);
	    fadeInAnimation.setDuration(6000);
	    fadeInAnimation.setFillAfter(true);
	    
	    mAnswerLabel.setAnimation(fadeInAnimation);
    }
    
    private void playSound()
    {
    	MediaPlayer player = MediaPlayer.create(this, R.raw.crystal_ball);
    	player.start();
    	player.setOnCompletionListener(new OnCompletionListener()
    	{
			
			@Override
			public void onCompletion(MediaPlayer mp)
			{
				mp.release();				
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /* Handle action bar item clicks here.
     * The action bar will automatically handle clicks on the Home/Up button,
     * so long as you specify a parent activity in AndroidManifest.xml.*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings)
          {
            return true;
          }
        return super.onOptionsItemSelected(item);
    }
    
    //Get the next answer
	private void handleNewAnswer()
	{
		String answer = mCrystalBall.getAnAnswer();				
		
		//Update the label with our dynamic result				
		mAnswerLabel.setText(answer);
		
		animateCrystalBall();
		animateAnswer();
		playSound();
	}
}