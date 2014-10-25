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
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pedir_turno_acitivy);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		TextView titulo = (TextView) findViewById(R.id.txtPedirDep);

		try {
			nombreDep = bean.getNombreDependencia();
			nombreEmp = bean.getNombreEmpresa();
		} catch (Exception e) {

		}

		titulo.setText(nombreDep);

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

			// Add your data
			// nameValuePairs = new ArrayList<NameValuePair>(2);
			//
			//
			// nameValuePairs.add(new BasicNameValuePair("name", nombreDep));
			// nameValuePairs.add(new BasicNameValuePair("last", last));
			// nameValuePairs.add(new BasicNameValuePair("idUser", id));
			// nameValuePairs.add(new BasicNameValuePair("tel", telefono));
			// nameValuePairs.add(new BasicNameValuePair("carrera", carrera));
			// nameValuePairs.add(new BasicNameValuePair("genero",genero));
			// nameValuePairs.add(new BasicNameValuePair("email", email));
			// nameValuePairs.add(new BasicNameValuePair("idEvent", idevent));
			// nameValuePairs.add(new BasicNameValuePair("inTime",
			// convertUnix(Long.toString(Calendar.getInstance().getTimeInMillis()))));
			// nameValuePairs.add(new BasicNameValuePair("outTime",
			// Long.toString(0)));
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
		String Turno = turno[1];
		String Cod = turno[0];

		ParseInstallation installation = ParseInstallation
				.getCurrentInstallation();

		EditText turnosEsperaET = (EditText) findViewById(R.id.txtNumerosTurnosEspers);

		turnosEspera = turnosEsperaET.getText().toString();

		installation.put("device_id", Cod);
		// PushService.subscribe(this, "dep4", PedirTurnoAcitivy.class);
		installation.put("turnos_espera", turnosEspera);
		installation.saveInBackground();

		/* escribe un archivo cpon el tunro y el cod */
		SharedPreferences preferencias = getSharedPreferences("datos",
				Context.MODE_PRIVATE);
		preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
		Editor editor = preferencias.edit();
		editor.putString("turno", Cod + "-" + Turno);
		editor.commit();

		Button btn = (Button)findViewById(R.id.btnPedirTurno);
		btn.setVisibility(View.INVISIBLE);
		
		TextView txtTuTurno = (TextView) findViewById(R.id.txtTuTurno);
		txtTuTurno.setVisibility(View.VISIBLE);
		
		TextView txtCod = (TextView) findViewById(R.id.txtCodigo);
		txtCod.setText(Cod);
		txtCod.setVisibility(View.VISIBLE);

		TextView txtTurno = (TextView) findViewById(R.id.txtTurno);
		txtTurno.setText(Turno);
		txtTurno.setVisibility(View.VISIBLE);

	}
}
