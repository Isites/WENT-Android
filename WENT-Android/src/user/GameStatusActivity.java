package user;

//import org.androidannotations.annotations.EActivity;
//import org.androidannotations.annotations.WindowFeature;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//@WindowFeature({Window.FEATURE_NO_TITLE})
//@EActivity(R.layout.activity_gamestatus)
public class GameStatusActivity extends Activity {
	
	private ListView lstView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		lstView = new ListView(this);
		lstView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getData()));
		setContentView(lstView);
		//setContentView(R.layout.activity_gamestatus);
		//lstView = (ListView)findViewById(R.id.lstView);
	}
	
	private List<String> getData() {
		List<String> data = new ArrayList<String>();
		data.add("K.Perkins");
		data.add("K.Garnett");
		data.add("P.Pierce");
		data.add("R.Allen");
		data.add("R.Rondo");
		
		return data;
	}
}
