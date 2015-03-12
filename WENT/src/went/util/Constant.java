package went.util;

import went.activity.R;
import android.view.View;

public class Constant {
	public final static String MODEL_LOGTAG = "model.Model",
			MAPACTIVITYPRESENTER_LOGTAG = "presenter.MapActivityPresenter";
	public final static int KXZ = 0, SXZ = 1, YXZ = 2, NULL = 3;
	
	//set the button's visibility by this array
	public final static int[][] judge = new int[][]{
			{ View.GONE, View.GONE, View.VISIBLE},//friend
			{ View.GONE, View.VISIBLE, View.GONE},//neutral
			{ View.VISIBLE, View.GONE, View.GONE},//enemy
			{ View.GONE, View.GONE, View.GONE},//out of distance
			{ View.VISIBLE, View.VISIBLE, View.VISIBLE},
	};
	
	//marker drawable array
	public final static int[] CAMPS_DRAWABLES = { R.drawable.point_kxz, 
			R.drawable.point_sxz, R.drawable.point_yxz, R.drawable.ic_launcher};
	
}
