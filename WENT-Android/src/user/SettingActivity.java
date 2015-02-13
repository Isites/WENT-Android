package user;

//import org.androidannotations.annotations.EActivity;
//import org.androidannotations.annotations.WindowFeature;

import went.activity.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

//@WindowFeature({Window.FEATURE_NO_TITLE})
//@EActivity(R.layout.activity_setting)
public class SettingActivity extends Activity implements OnClickListener{
	
	private static final int LOGINACTIVITY_CODE = 100;
	private Button aboutBtn;
	private Button loginBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_off);
		aboutBtn = (Button)findViewById(R.id.about_btn);
		loginBtn = (Button)findViewById(R.id.login_btn);
		aboutBtn.setOnClickListener(this);
		loginBtn.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.login_btn:
			Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
			startActivityForResult(intent, 100);
			break;
		case R.id.about_btn:
			startActivity(new Intent(SettingActivity.this, AboutActivity.class));
			break;
			default:
				break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == 0) {
			
		}
	}
}
