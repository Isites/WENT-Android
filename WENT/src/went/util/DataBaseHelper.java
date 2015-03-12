package went.util;

import java.util.HashMap;

public class DataBaseHelper {
	public final static String DB_NAME = "went.db";
	public final static String KEY_ID = "id";
	public final static int PLAYER_TABLE_FLAG = 1;
	public final static int BASE_TABLE_FLAG = 2;
	
	public final static String PLAYER_TABlE = "player_table";
	public final static String CREATE_PLAYER_TABLE = "CREATE table IF NOT EXISTS "+
	DataBaseHelper.PLAYER_TABlE  + " (" + DataBaseHelper.KEY_ID +
	" INTEGER PRIMARY KEY AUTOINCREMENT," + "CAMP INTEGER, " +
	"LONGTITUDE INTEGER, LATITUDE INTEGER, HP INTEGER, MP INTEGER," +
	" LV INTEGER, EXP INTEGER, ATK INTEGER, DEF INTEGER)";
	public final static String[] PLAYER_TAGS = new String[]{"id", "camp", 
		"latlon", "lv", "exp", "hp", "mp", "atk", "def", "view"};
	public final static String INSERT_PLAYER = 
			"insert into " + DataBaseHelper.PLAYER_TABlE
			+ "  values(NULL,?,?,?,?,?,?,?,?,?,?)";
	
	public final static String BASE_TABLE = "base_table";
	public final static String CREATE_BASE_TABLE = "CREATE table IF NOT EXISTS "+
			DataBaseHelper.BASE_TABLE  + " (" + DataBaseHelper.KEY_ID +
			" INTEGER PRIMARY KEY AUTOINCREMENT," + "id VARCHAR(300)," +
			"camp VARCHAR(300), latlon VARCHAR(300), lv VARCHAR(300)," +
			"exp VARCHAR(300)," + "hp VARCHAR(300)," + ")";
	public final static String[] BASE_TAGS = new String[]{"id", "camp", 
		"latlon", "lv", "exp", "hp", "mp", "atk", "def", "atr"};
	public final static String INSERT_BASE = 
			"insert into " + DataBaseHelper.BASE_TABLE
			+ "  values(NULL,?,?,?,?,?,?,?,?,?,?)";
	
	public static HashMap<String, Object> PLAYER  =
			new HashMap<String, Object>();
	public static HashMap<String, Object> BASE = 
			new HashMap<String, Object>();
	
	public static String CAMP_STYLE = DataBaseHelper.SXZ;
	public final static String SXZ = "sxz", KXZ = "kxz", YXZ = "yxz";
}