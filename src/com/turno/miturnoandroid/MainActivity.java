package com.turno.miturnoandroid;

import com.example.miturnoandroid.R;
import com.parse.ParseAnalytics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}
	
	
	public void callPedirTurno(View v){
		Intent myIntent = new Intent(MainActivity.this,
				ListDepActivity.class);

		MainActivity.this.startActivity(myIntent);
		
	}
	public void callListDep(View v){
		
		
	}

}
