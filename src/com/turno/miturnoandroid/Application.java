package com.turno.miturnoandroid;
import android.net.ParseException;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParsePush;
import com.parse.PushService;
import com.parse.SaveCallback;

public class Application extends android.app.Application {

  public Application() {
  }

  @Override
  public void onCreate() {
    super.onCreate();

	// Initialize the Parse SDK.
    Parse.initialize(this, "30RmLKXYaKqfDn68xP747xkZJOD2tyiiUvT56qQo", "DdVfVgWX2Rsrr0IEuL7eyZAzl0SNMF1z6YMkitZ2");
       
//    ParseInstallation installation = ParseInstallation.getCurrentInstallation();
////    installation.put("device_id", );
//    installation.saveInBackground();
    // Specify an Activity to handle all pushes by default.
    
    PushService.subscribe(this, "a", MostrarActivity.class);
	PushService.setDefaultPushCallback(this, MostrarActivity.class);
	
	
  }
  
}