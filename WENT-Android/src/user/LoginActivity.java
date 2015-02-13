package user;

import qq.login.BaseActivity;
import went.activity.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends BaseActivity {
	
	private Button loginBtn;
	private EditText usernameEdit;
	private EditText pwdEdit;
	
	public Button qqLogin;
	public Button qqCancel;
	public TextView qqloginMes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		loginBtn = (Button)findViewById(R.id.signin_btn);
		usernameEdit = (EditText)findViewById(R.id.username_edit);
		pwdEdit = (EditText)findViewById(R.id.password_edit);
		loginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(logging())
					LoginActivity.this.finish();
				else
					;
			}
		});
		
		
		
		qqLogin =(Button)findViewById(R.id.qqLogin);
		qqCancel=(Button)findViewById(R.id.qqCancel);
		qqloginMes=(TextView)findViewById(R.id.message);
		
		qqLogin.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				initTencent();
				login();
			}
		});
		
		qqCancel.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				logout();
			}
		});
		
		
	}
	private boolean logging() {
		return true;
	}
	
	public void showMessage(String msg) {
		// TODO Auto-generated method stub
		qqloginMes.setText(msg);
	}
}
