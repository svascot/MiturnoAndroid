package com.turno.miturnoandroid;

import com.example.miturnoandroid.R;
import com.parse.ParseAnalytics;
import com.turno.bean.bean;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}
	
	
	public void callPedirTurno(View v){
		
		SharedPreferences preferencias=getSharedPreferences("datos",Context.MODE_PRIVATE);
		preferencias=getSharedPreferences("datos",Context.MODE_PRIVATE);  
			    
			    
		String empresa = preferencias.getString("Empresa", "");
		
		if(empresa.equals("")){
			Intent myIntent = new Intent(MainActivity.this,
					ListEmpActivity.class);
			MainActivity.this.startActivity(myIntent);
		}else{
			
			bean.setNombreEmpresa(empresa);
			
			Intent myIntent = new Intent(MainActivity.this,
					ListDepActivity.class);
			MainActivity.this.startActivity(myIntent);
		}
		
		

	
		
	}
	public void callCancelar(View v){
		Intent myIntent = new Intent(MainActivity.this,
				CanelarActivity.class);

		MainActivity.this.startActivity(myIntent);
		
		
	}

}
