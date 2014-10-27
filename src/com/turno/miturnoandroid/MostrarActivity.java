package com.turno.miturnoandroid;

import com.example.miturnoandroid.R;
import com.example.miturnoandroid.R.id;
import com.example.miturnoandroid.R.layout;
import com.example.miturnoandroid.R.menu;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class MostrarActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mostrar);
		
		/* preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
		Editor editor = preferencias.edit();
		editor.putString("turno","");
		editor.commit();*/
		
		mostrar();

	}
	
	public void mostrar(){
		
		SharedPreferences preferencias=getSharedPreferences("datos",Context.MODE_PRIVATE);
		preferencias=getSharedPreferences("datos",Context.MODE_PRIVATE);  	    
			    
		String strTurno = preferencias.getString("turno", "");
        
		String[] turno = strTurno.split("-");
		String Turno = "Turno : " + turno[1];
		String Cod = "Codigo : " + turno[0];
				
		TextView txtCod = (TextView) findViewById(R.id.txtCodigo);
		txtCod.setText(Cod);

		TextView txtTurnoPedido = (TextView) findViewById(R.id.txtTurno);
		txtTurnoPedido.setText(Turno);
	}
	
	


}
