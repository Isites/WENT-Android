package user;

import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {

	//@ViewById(R.id.tabhost)
	private TabHost tabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		setupTabs();
	}

	private void setupTabs() {
		tabHost = getTabHost();
		//LayoutInflater inflater = LayoutInflater.from(this);
		//inflater.inflate(R.layout.home, tabHost.getTabContentView());

		//tabHost.addTab(tabHost.newTabSpec("Status")
		//		.setIndicator("״̬")
			//	.setContent(R.id.home));
		addTab("Status", "状态", GameStatusActivity.class);
		addTab("Stats", "数据", GameStatsActivity.class);
		addTab("Hidden", "Mine", MineActivity.class);
		addTab("Setting", "设置", SettingActivity.class);
		
	}
	private void addTab(String strId, String cnId, Class<?> c) {
		tabHost.addTab(tabHost.newTabSpec(strId)
				.setIndicator(cnId, null)
				.setContent(new Intent(MainActivity.this, c)));
		/*TabSpec ts = tabHost.newTabSpec(strId);
		ts.setIndicator(cnId, null);
		ts.setContent(new Intent(MainActivity.this, c));
		tabHost.addTab(ts);*/
	}
}
