package com.turno.miturnoandroid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miturnoandroid.R;
import com.parse.ParseInstallation;
import com.turno.bean.bean;

public class PedirTurnoAcitivy extends Activity {

	String turnosEspera;
	String nombreDep;
	String nombreEmp;
	Integer maxEspera;
	EditText turnosEsperaET;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Quita la barra de titulo
				requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pedir_turno_acitivy);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		TextView titulo = (TextView) findViewById(R.id.txtPedirDep);
		turnosEsperaET = (EditText) findViewById(R.id.txtNumerosTurnosEspers);
		
		try {
			nombreDep = bean.getNombreDependencia();
			nombreEmp = bean.getIdEmpresa();
			maxEspera = bean.getMaxEspera();
			
		} catch (Exception e) {

		}

		titulo.setText(nombreDep);
		turnosEsperaET.setHint("Ingresa un numero en 3 y "+ maxEspera+".");
		
	}

	public void pedir(View v) {

		SharedPreferences preferencias = getSharedPreferences("datos",
				Context.MODE_PRIVATE);
		preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);

		String turno = preferencias.getString("turno", "");

		if (turno.equals("")) {
			peticionPedir();
		} else {
			Toast.makeText(getApplicationContext(),
					"Ya pediste un turno, debes cancelarlo o ser atendido.",
					Toast.LENGTH_LONG).show();
		}
	}

	public void peticionPedir() {
		HttpPost httppost;

		HttpResponse response;
		HttpClient httpclient;

		String htmlResponse = null;
		try {
			httpclient = new DefaultHttpClient();
			httppost = new HttpPost(
					"http://mvvtech.com/svascot/MiTurno/miturno/index.php/site/pedirTurno");

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("nombreDep", nombreDep));
			params.add(new BasicNameValuePair("nombreEmp", nombreEmp));
			
			httppost.setEntity(new UrlEncodedFormEntity(params));
			// Execute HTTP Post Request
			response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			htmlResponse = EntityUtils.toString(entity);
		} catch (ClientProtocolException e) {
			Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT)
					.show();
		} catch (IOException e) {
			Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT)
					.show();
		}

		String[] turno = htmlResponse.split("-");
		String sTurnoPedido = turno[1];
		String Cod = turno[0];
		String sTurnoActual = turno[2];

		ParseInstallation installation = ParseInstallation
				.getCurrentInstallation();


		turnosEspera = turnosEsperaET.getText().toString();

		int iTurnoActual = Integer.parseInt(sTurnoActual);
		int iTurnoPedido = Integer.parseInt(sTurnoPedido);
		
		installation.put("device_id", Cod);
		installation.put("turnos_espera", (iTurnoPedido - iTurnoActual)-Integer.parseInt(turnosEspera)); //algoritmo de aviso
		installation.saveInBackground();

		/* escribe un archivo cpon el tunro y el cod */
		SharedPreferences preferencias = getSharedPreferences("datos",
				Context.MODE_PRIVATE);
		preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
		Editor editor = preferencias.edit();
		editor.putString("turno", Cod + "-" + sTurnoPedido);
		editor.putInt("contador", 0);
		editor.commit();
		
		callMostrar();
		
	}
	
	public void callMostrar(){
		Intent myIntent = new Intent(PedirTurnoAcitivy.this,
				MostrarActivity.class);

		PedirTurnoAcitivy.this.startActivity(myIntent);
		finish();
	}
	
	public void onBackPressed() {
		Intent myIntent = new Intent(PedirTurnoAcitivy.this,
				ListDepActivity.class);
		PedirTurnoAcitivy.this.startActivity(myIntent);
		finish();
	}
	
}
