package user;

//import org.androidannotations.annotations.EActivity;
//import org.androidannotations.annotations.WindowFeature;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import went.activity.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

//@WindowFeature({Window.FEATURE_NO_TITLE})
//@EActivity(R.layout.activity_gamestats)
public class GameStatsActivity extends Activity {
	
	private TextView statsTxt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gamestats);
		statsTxt = (TextView)findViewById(R.id.stats_txt);
		statsTxt.setText(getCPUInfos());
	}
	
	private static String getCPUInfos() {
		String path = "/proc/cpuinfo";
		String tmp = "";
		StringBuilder strbuilder = new StringBuilder();
		String retstr = null;
		
		try {
			FileReader fr = new FileReader(path);
			BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
			while ((tmp = localBufferedReader.readLine()) != null) {
				strbuilder.append(tmp + "\n");
			}
			if (strbuilder != null) {
				retstr = strbuilder.toString();
				return retstr;
			}
	
			} catch (IOException e) {
				e.printStackTrace();
			}
		return retstr;
	}
}
