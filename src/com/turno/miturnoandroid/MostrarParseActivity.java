package com.turno.miturnoandroid;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.example.miturnoandroid.R;

public class MostrarParseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Quita la barra de titulo
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mostrar_parse);
		
		
//		 preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
//		Editor editor = preferencias.edit();
//		editor.putString("turno","");
//		editor.commit();
		
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
		
		int contador = preferencias.getInt("contador", 0);
		
		if(contador==1){
			Editor editor = preferencias.edit();
			editor.putString("turno","");
			editor.commit();
		}else{
			contador++;
			Editor editor = preferencias.edit();
			editor.putInt("contador",contador);
			editor.commit();
		}
		
		
	}

}
