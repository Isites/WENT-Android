package went.activity;

import java.util.Iterator;
import java.util.List;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.easemob.chat.EMChat;

public class WentApplication extends Application{
	Context appContext;
	final String LOG_TAG = "WentApplication";
	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(LOG_TAG, "ApplicationOnCreate");
		
		//initial the baidu location sdk
		SDKInitializer.initialize(getApplicationContext());
		
		//initial the easemob sdk
		appContext = this;
		int pid = android.os.Process.myPid();
		String processAppName = getAppName(pid);
		// 如果app启用了远程的service，此application:onCreate会被调用2次
		// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
		// 默认的app会在以包名为默认的process name下运行，如果查到的process name不是app的process name就立即返回
	        
		if (processAppName == null ||!processAppName.equalsIgnoreCase("went.activity")) {
		            Log.e(LOG_TAG, "enter the service process!");	            
		            // 则此application::onCreate 是被service 调用的，直接返回
		            return;
		}
	        
		EMChat.getInstance().init(getApplicationContext());
		/**
		 * debugMode == true 时，sdk 会在log里输入调试信息
		 * @param debugMode
		 * 在做代码混淆的时候需要设置成false
		 */
		EMChat.getInstance().setDebugMode(true);
	}
	
	private String getAppName(int pID) {
		String processName = null;
		ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
		List l = am.getRunningAppProcesses();
		Iterator i = l.iterator();
		PackageManager pm = this.getPackageManager();
		while (i.hasNext()) {
			ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
			try {
				if (info.pid == pID) {
					CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
					// Log.d("Process", "Id: "+ info.pid +" ProcessName: "+
					// info.processName +"  Label: "+c.toString());
					// processName = c.toString();
					processName = info.processName;
					return processName;
				}
			} catch (Exception e) {
				// Log.d("Process", "Error>> :"+ e.toString());
			}
		}
		return processName;
	}
}
