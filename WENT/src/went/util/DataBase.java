package went.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

public class DataBase {
	private SQLiteDatabase db;
    public static String DBURI = Environment.getExternalStorageDirectory().toString() + "/WENT/" 
    		+ DataBaseHelper.DB_NAME;
    public DataBase(Context context, String createTable) {
        db = context.openOrCreateDatabase(DBURI,Context.MODE_PRIVATE, null);
        db.execSQL(createTable);
    }

    public void saveData(HashMap<String, Object> listItem, int tableflag) {
    	String insert_command = "";
    	String[] tags;
    	if(tableflag == DataBaseHelper.PLAYER_TABLE_FLAG) {
    		tags = DataBaseHelper.PLAYER_TAGS;
    		insert_command = DataBaseHelper.INSERT_PLAYER;
    	}else if(tableflag == DataBaseHelper.BASE_TABLE_FLAG) {
    		tags = DataBaseHelper.BASE_TAGS;
    		insert_command = DataBaseHelper.INSERT_BASE;
    	}else
    		return;
    	Object[] object = new Object[tags.length];
		for(int i = 0; i < object.length; i++)
			object[i] = listItem.get(tags[i]);
		db.execSQL(insert_command, object);
		
    }

    public void updateData(int id, String tag, int value, int tableflag){
        String table_name = "";
        if(tableflag == DataBaseHelper.PLAYER_TABLE_FLAG)
        	table_name = DataBaseHelper.PLAYER_TABlE;
        else if(tableflag == DataBaseHelper.BASE_TABLE_FLAG)
        	table_name = DataBaseHelper.BASE_TABLE;
        else
        	return;
        db.execSQL("UPDATE "+ table_name +" SET " +
                "id = \"" + id + "\"" +
                " WHERE " + tag + " = \"" + value + "\"");
    }
    
    public void deleteData(int id, int tableflag){
        String table_name = "";
        if(tableflag == DataBaseHelper.PLAYER_TABLE_FLAG)
        	table_name = DataBaseHelper.PLAYER_TABlE;
        else if(tableflag == DataBaseHelper.BASE_TABLE_FLAG)
        	table_name = DataBaseHelper.BASE_TABLE;
        else
        	return;
        db.execSQL("delete from "+ table_name +
        		"  WHERE id = \"" + id + "\"");
    }
    public ArrayList<HashMap<String, Object>> getData(int tableflag) {
        String table_name = "";
        String[] tags;
        int i, length;
    	if(tableflag == DataBaseHelper.PLAYER_TABLE_FLAG) {
    		tags = DataBaseHelper.PLAYER_TAGS;
    		table_name = DataBaseHelper.PLAYER_TABlE;
    	}else if(tableflag == DataBaseHelper.BASE_TABLE_FLAG) {
    		tags = DataBaseHelper.BASE_TAGS;
    		table_name = DataBaseHelper.BASE_TABLE;
    	}else
    		return null;
        
        ArrayList<HashMap<String, Object>> listItem =
        		new ArrayList<HashMap<String, Object>>();
        //db.execSQL(DataBaseHelper.CREATE_BASE_TABLE);
        Cursor c = db.rawQuery("SELECT * from "+ table_name + " ORDER BY " +
        		DataBaseHelper.KEY_ID + " DESC LIMIT 999", null);
        
        length = tags.length;
        while (c.moveToNext()) {
        	HashMap<String, Object> map = new HashMap<String, Object>();
        	for(i = 0; i < length; i++)
        		map.put(tags[i], c.getString(c.getColumnIndex(tags[i])));
            listItem.add(map);
        }
        /*HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(tags[0], "0");
        map.put(tags[1], "94.3");
        map.put(tags[2], "114.3");
        map.put(tags[3], "100");
        listItem.add(map);*/
        c.close();
        Collections.reverse(listItem);
        return listItem;
    }

    public void close() {
        if (db != null)
            db.close();
    }
}