package com.turno.miturnoandroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.miturnoandroid.R;
import com.turno.bean.bean;

public class ListEmpActivity extends Activity {
	private String jsonResult;
	private String url = "http://mvvtech.com/svascot/MiTurno/miturno/index.php/site/listEmp";
	private LinearLayout contenedor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Quita la barra de titulo
				requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_list_emp);

		contenedor = (LinearLayout) findViewById(R.id.conteneder2);

		accessWebService();

	}

	public void callListDep() {
		Intent myIntent = new Intent(ListEmpActivity.this,
				ListDepActivity.class);

		ListEmpActivity.this.startActivity(myIntent);

	}

	// Async Task to access the web
	private class JsonReadTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(params[0]);
			try {
				//
				// List<NameValuePair> parametros = new
				// ArrayList<NameValuePair>();
				// parametros.add(new BasicNameValuePair("nombreDep",
				// bean.getNombreEmpresa()));
				//
				// httppost.setEntity(new UrlEncodedFormEntity(parametros));

				HttpResponse response = httpclient.execute(httppost);
				jsonResult = inputStreamToString(
						response.getEntity().getContent()).toString();
			}

			catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		private StringBuilder inputStreamToString(InputStream is) {
			String rLine = "";
			StringBuilder answer = new StringBuilder();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));

			try {
				while ((rLine = rd.readLine()) != null) {
					answer.append(rLine);
				}
			}

			catch (IOException e) {
				// e.printStackTrace();
				Toast.makeText(getApplicationContext(),
						"Error..." + e.toString(), Toast.LENGTH_LONG).show();
			}
			return answer;
		}

		@Override
		protected void onPostExecute(String result) {
			ListDrwaer();
		}
	}// end async task

	public void accessWebService() {
		JsonReadTask task = new JsonReadTask();
		// passes values for the urls string array
		task.execute(new String[] { url });
	}

	// build hash set for list view
	@SuppressWarnings("deprecation")
	public void ListDrwaer() {

		try {
			JSONObject jsonResponse = new JSONObject(jsonResult);
			JSONArray jsonMainNode = jsonResponse.optJSONArray("listDep");

			for (int i = 0; i < jsonMainNode.length(); i++) {

				JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
				String name = jsonChildNode.optString("Empresa");
				String id = jsonChildNode.optString("id");

				String outPut = name;

				Button boton = new Button(this);

				boton.setLayoutParams(new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT, 1));
				
				boton.setTextSize(30);
				boton.setTextColor(Color.parseColor("#ffa800"));
				
			
				if (i % 2 == 0) {
					boton.setBackgroundColor(Color.parseColor("#282828"));
				} else {
					boton.setBackgroundColor(Color.parseColor("#464646"));
				}

				boton.setText(outPut.toUpperCase());

				final String pasarId = id;
				final String pasarNombre = name;
				boton.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						
						bean.setIdEmpresa(pasarId);
						SharedPreferences preferencias = getSharedPreferences("datos",
								Context.MODE_PRIVATE);
						
						preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
						Editor editor = preferencias.edit();
						editor.putString("Empresa",pasarId);
						editor.putString("nombreEmpresa", pasarNombre);
						editor.commit();
						
						callListDep();
					}
				});
				contenedor.addView(boton);

			}
		} catch (JSONException e) {
			Toast.makeText(getApplicationContext(), "Error" + e.toString(),
					Toast.LENGTH_SHORT).show();
		}

	}

}
