package qq.login;

import android.app.Activity;
import android.content.Intent;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public abstract class BaseActivity extends Activity{
	public static final String AppID="222222";
	protected Tencent mTencent;
	
	public void initTencent(){
		mTencent=Tencent.createInstance(AppID, this);
	}
	
	public void login(){
		mTencent.login(this, "all", new IUiListener() {
			
			@Override
			public void onError(UiError arg0) {
				showMessage("登录出错");
			}
			
			@Override
			public void onComplete(Object arg0) {
				showMessage("成功登陆");
			}
			
			@Override
			public void onCancel() {
				showMessage("取消登录");
			}
		});
	}
	
	public void logout(){
	  if(mTencent!=null){
		   mTencent.logout(this.getApplicationContext());
		   showMessage("注销登录");
	  }
	}
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     super.onActivityResult(requestCode, resultCode, data);
	     if(mTencent!=null){
			 mTencent.onActivityResult(requestCode, resultCode, data); 
	     }
	}
	 
	//须在具体的activity中重写
	public abstract void showMessage(String string);
	

}
