package went.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.baidu.mapapi.SDKInitializer;

public class LaunchActivity extends Activity{
	final int DISPLAY_LENGTH = 1000;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.launch);
		//initial the baidu location sdk
		SDKInitializer.initialize(getApplicationContext());

		//start the LocationDemo Activity
		new Handler().postDelayed(new Runnable() {  
				public void run() {  
					Intent intent = new Intent(LaunchActivity.this, MapActivity.class);
					LaunchActivity.this.startActivity(intent);  
					LaunchActivity.this.finish();  
				}  
		}, DISPLAY_LENGTH);
	}
}