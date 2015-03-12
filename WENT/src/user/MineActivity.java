package user;

import java.util.ArrayList;

import went.activity.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class MineActivity extends Activity {
	
	private ImageButton addBtn;
	private ListView lstView;
	private ArrayAdapter<String> adapter;
	private ArrayList<String> lstostuff;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine);
		
		lstostuff = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, lstostuff);
		lstView = (ListView)findViewById(R.id.lst4stuff);
		lstView.setAdapter(adapter);
		
		addBtn = (ImageButton)findViewById(R.id.add_btn);
		addBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final EditText inputServer = new EditText(MineActivity.this);
				AlertDialog.Builder adBuilder = new AlertDialog.Builder(MineActivity.this);
				adBuilder.setTitle("Stuff")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setView(inputServer)
					.setNegativeButton("Cancel", null)
					.setPositiveButton("Ok", new DialogInterface.OnClickListener() {			
						@Override
						public void onClick(DialogInterface dialog, int which) {
							lstostuff.add(inputServer.getText().toString());
							adapter.notifyDataSetChanged();
						}
					})
					.show();
			}
		});
	}
}
