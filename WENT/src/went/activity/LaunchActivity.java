package went.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;

public class LaunchActivity extends Activity{
	final int DISPLAY_LENGTH = 1000;
	final String LOG_TAG = "LaunchActivity";
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.launch);

		//easemob login
		EMChatManager.getInstance().login("User1","user1",
				new EMCallBack() {//回调
			@Override
			public void onSuccess() {
				runOnUiThread(new Runnable() {
					public void run() {
						Log.d(LOG_TAG, "登陆聊天服务器成功！");	
						Toast.makeText(getApplicationContext(), "login suc", 
								Toast.LENGTH_SHORT).show();
					}
				});
			}
			
			@Override
			public void onProgress(int progress, String status) {
				
			}
			
			@Override
			public void onError(int code, String message) {
				Log.d(LOG_TAG, "登陆聊天服务器失败！");
			}
		});
		
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