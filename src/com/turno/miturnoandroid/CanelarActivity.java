package com.turno.miturnoandroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miturnoandroid.R;
import com.parse.ParseInstallation;
import com.turno.bean.bean;

public class CanelarActivity extends Activity {
	
	String Cod;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_canelar);
		
		SharedPreferences preferencias=getSharedPreferences("datos",Context.MODE_PRIVATE);
		preferencias=getSharedPreferences("datos",Context.MODE_PRIVATE);  
			    
			    
		String strTurno = preferencias.getString("turno", "");
        
		String[] turno = strTurno.split("-");
		String Turno = turno[1];
		 Cod = turno[0];
		
		TextView txtTurno = (TextView)findViewById(R.id.txtTurnoCancelar);
		txtTurno.setText("Deseas cancelar tu turno? codigo: " + Cod + " - Turno: " + Turno);
	
	}
	
	public void cancelar(View v) {
		HttpPost httppost;

		HttpResponse response;
		HttpClient httpclient;

		String htmlResponse = null;
		try {
			httpclient = new DefaultHttpClient();
			httppost = new HttpPost(
					"http://mvvtech.com/svascot/MiTurno/miturno/index.php/site/EliminarTurno");

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("codigo", Cod));
			
			

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
//			 Execute HTTP Post Request
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
		
		SharedPreferences preferencias = getSharedPreferences("datos",
				Context.MODE_PRIVATE);
		
		Toast.makeText(getBaseContext(), "Turno cancelado!", Toast.LENGTH_SHORT)
		.show();

		preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);

		Editor editor = preferencias.edit();
		editor.putString("turno","");
		editor.commit();

	}
	
}