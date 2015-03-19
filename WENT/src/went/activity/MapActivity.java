package went.activity;

import javax.microedition.khronos.opengles.GL10;

import presenter.MapActivityPresenter;
import user.MainActivity;
import view.IMapActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapDrawFrameCallback;
import com.baidu.mapapi.map.BaiduMap.OnMapLoadedCallback;
import com.baidu.mapapi.map.BaiduMap.OnMapLongClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;

import data.Pedestrian;
import data.Tower;

public class MapActivity extends Activity 
	implements IMapActivity{
	final String LOG_TAG = "MapActivity";
	MapActivityPresenter presenter;
	//location 
	LocationClient locClient;
	public LocationListener locationListener = new LocationListener();
	private LocationMode currentMode;
	BitmapDescriptor currentMarker;

	MapView mapView;
	BaiduMap baiduMap;
	
	ImageButton backBtn, attackBtn, buildBtn, collectBtn, userBtn, send;
	EditText sendMessage;
	RelativeLayout opeDetailRellay;
	RelativeLayout userWindow;
	//int userWindow_x, userWindow_y, userWindow_w, userWindow_h;
	
	boolean isFirstLoc = true, isFirstGetBase = true, isAttack = false;
	//save the user current latlng and the base's latlng need to operate
	LatLng curPoint;
	MapStatus mapStatus;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		initMap();
		initButton();
		setMarkerClick();
		setMapGestureLis();
		
		presenter = new MapActivityPresenter(this);
	}
	
	/*
	 * initial the map view
	 */
	@SuppressLint({ "InflateParams", "NewApi" })
	void initMap(){
		//set the map parameter
		BaiduMapOptions mapOptions = new BaiduMapOptions();
		mapOptions.zoomControlsEnabled(false);	
		mapView = new MapView(this, mapOptions);
		
		setContentView(mapView);
		//add the XML View
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
				(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		View view = LayoutInflater.from(this).inflate(R.layout.activity_location,null);
		this.addContentView(view, params);
		// initial map data
		baiduMap = mapView.getMap();
		baiduMap.setMaxAndMinZoomLevel((float)19.0, (float)16.0);
		toast(baiduMap.getMaxZoomLevel()+"");
		//set the mapView 
		currentMode = LocationMode.NORMAL;
		baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
				currentMode, true, currentMarker));
		//set the compass
		baiduMap.getUiSettings().setCompassEnabled(true);
		baiduMap.getUiSettings().setCompassPosition(new Point(50, 50));
		//set buildings shadow fade
		baiduMap.setBuildingsEnabled(false);
		// open the location function
		baiduMap.setMyLocationEnabled(true);
		
		//zoom map to 20m
		MapStatusUpdate u1 = MapStatusUpdateFactory.zoomBy(20);
		baiduMap.animateMapStatus(u1);
		//initial location function 
		locClient = new LocationClient(this);
		locClient.registerLocationListener(locationListener);	
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// support the GPS location
		option.setCoorType("bd09ll"); // coordinate type
		option.setScanSpan(2000);// location frequency
		locClient.setLocOption(option);
		locClient.start();
		
         //mUiSettings = mBaiduMap.getUiSettings();
         mapStatus = new MapStatus.Builder().overlook(-45).build();
         MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(mapStatus);
         baiduMap.animateMapStatus(u, 1000);
         
         userWindow = (RelativeLayout ) findViewById(R.id.user_tiny_window);
         userWindow.setAlpha((float) 0.7);
         
         userWindow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				presenter.showPedestrianInfo();
			}
         });
         /*
        // get height & width
		int wm = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int hm = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		userWindow.measure(wm, hm);
		userWindow_w = userWindow.getMeasuredWidth();
		userWindow_h = userWindow.getMeasuredHeight();
		
		// new api...
		userWindow_x = (int) userWindow.getX();
		userWindow_y = (int) userWindow.getY();
			
         // set blur background, when map get loaded
         baiduMap.setOnMapLoadedCallback(new OnMapLoadedCallback() {
			@SuppressLint("NewApi")
			@Override
			public void onMapLoaded() {
				baiduMap.snapshot(blurifyWindow);
			}
         });
         
         baiduMap.setOnMapStatusChangeListener(new OnMapStatusChangeListener() {
			@Override
			public void onMapStatusChange(MapStatus arg0) {
				//baiduMap.snapshot(blurifyWindow);
			}

			@Override
			public void onMapStatusChangeFinish(MapStatus arg0) {
				baiduMap.snapshot(blurifyWindow);
			}

			@Override
			public void onMapStatusChangeStart(MapStatus arg0) {
				//baiduMap.snapshot(blurifyWindow);
			}
         });*/
	}
	
	/*
	SnapshotReadyCallback blurifyWindow = new SnapshotReadyCallback() {
		@SuppressLint("NewApi")
		@Override
		public void onSnapshotReady(Bitmap origin) {
			Bitmap background = BlurUtil.fastblur(MapActivity.this,
					Bitmap.createBitmap(origin,
							userWindow_x, userWindow_y,
							userWindow_w, userWindow_h),
					25);//0-25模糊值
			userWindow.setBackground(new BitmapDrawable(background));
		}
	};*/
	
	/*
	 * get the button, set the button click listener
	 */
	void initButton(){
		opeDetailRellay = (RelativeLayout) findViewById(R.id.ope_detail);
		buildBtn = (ImageButton) findViewById(R.id.build);
		attackBtn = (ImageButton) findViewById(R.id.attack);
		collectBtn = (ImageButton) findViewById(R.id.collect);
		send = (ImageButton) findViewById(R.id.send);
		sendMessage = (EditText) findViewById(R.id.sendMessage);
		//goto the user Activity
		userBtn = (ImageButton) findViewById(R.id.user);
		userBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MapActivity.this, MainActivity.class);
				MapActivity.this.startActivity(intent);
				/*presenter.showPedestrianInfo();*/
			}
		});
		buildBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				toast("build");
			}
		});
		attackBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				toast("attac");
				isAttack = true;
			}
		});
		collectBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				toast("collect");
				presenter.collectMP();
			}
		});
		//return to the self location (the map center)
		backBtn = (ImageButton) findViewById(R.id.back_center);
		backBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
					backToCenter();
			}
		});
		//send message
		send.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				presenter.sendMessage("This is went", "User2");
			}
		});
	}
	
	
	/*
	 * set the marker click listener
	 */
	void setMarkerClick(){
		
		baiduMap.setOnMarkerClickListener(new OnMarkerClickListener(){
			@Override
			public boolean onMarkerClick(final Marker arg0) {
				//Point p = mBaiduMap.getProjection().toScreenLocation(arg0.getPosition());  
				toast(isAttack+"");
				if(isAttack){
					presenter.attack(arg0.getPosition());
					isAttack = false;
				}else{
					//创建InfoWindow展示的view  
			        Button button = new Button(getApplicationContext());
			        button.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// placeBeacon(arg)
							presenter.placeBeacon();
							
							//构建用户绘制园形的Option对象  
							OverlayOptions circleOptions = new CircleOptions()  
							    .center(arg0.getPosition())
							    .fillColor(0xAAFFFF00);  
							//在地图上添加多边形Option，用于显示  
							baiduMap.addOverlay(circleOptions);
						}
			        });
			        //button.setBackgroundResource(R.drawable.popup);  
			        //定义用于显示该InfoWindow的坐标点  
			        //LatLng pt = new LatLng(39.86923, 116.397428);  
			        //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量 
			        InfoWindow mInfoWindow = new InfoWindow(
			        		button, arg0.getPosition(), -47);  
			        //显示InfoWindow  
			        baiduMap.showInfoWindow(mInfoWindow);
					//presenter.showTowerInfo(arg0.getPosition());
				}
				return false;
			}
		});		
	}
	
	
	/*
	 * map gesture listener, include:long click; click; map loaded call back
	 */
	void setMapGestureLis(){
		baiduMap.setOnMapLongClickListener(new OnMapLongClickListener(){
			@Override
			public void onMapLongClick(LatLng arg0) {
				toast("longc");
			}
		});
		baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				return false;
			}
			@Override
			public void onMapClick(LatLng arg0) {
				toast("mapclick");
			}
		});
		baiduMap.setOnMapLoadedCallback(new OnMapLoadedCallback(){
			@Override
			public void onMapLoaded() {
				toast("loaded");
			}
		});
	}
			
	/*location listener
	 * when locating is over, this function is invoked
	 */
	public class LocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			// judge if the map is null
			if (location == null || mapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// locating the user direction
					.direction(0).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			baiduMap.setMyLocationData(locData);
			
			curPoint = new LatLng(location.getLatitude()
					,location.getLongitude());  
			//toast(location.getTime());
			presenter.setPedestrianLatlng(curPoint);
			if (isFirstLoc) {
				isFirstLoc = false;
				presenter.initAllMarker();
				backToCenter();
			}
		}
		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	void backToCenter(){
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(curPoint);
		baiduMap.animateMapStatus(u);
	}
	/*
	 * get the distance
	 */
	double getDistance(LatLng cur, LatLng ot){
		return DistanceUtil.getDistance(cur, ot);
	}
 
	/*
	 * double click the back, exit the app
	 */
	private long exitTime =   0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
                if((System.currentTimeMillis()-exitTime) > 2000){
                    Toast.makeText(getApplicationContext(), "再按一次退出！", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    System.exit(0);
                    return true;
                }
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
	@Override
	protected void onPause() {
		mapView.onPause();
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		mapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// close the locating
		locClient.stop();
		//close the location map
		baiduMap.setMyLocationEnabled(false);
		mapView.onDestroy();
		super.onDestroy();
	}

	//IMapActivity's Overrides.
	@Override
	public void refreshButton(int[] x) {
		attackBtn.setVisibility(x[0]);
		buildBtn.setVisibility(x[1]);
		collectBtn.setVisibility(x[2]);
	}

	@Override
	public void attack(LatLng latlng) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showTowerInfo(Tower x) {
		userWindow.setVisibility(View.GONE);
		
		final Context contxt = MapActivity.this;
		AlertDialog.Builder builder = new AlertDialog.Builder(contxt);
		LayoutInflater inflater =
				(LayoutInflater) contxt.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.user_menu_window,
				(ViewGroup) findViewById(R.id.dialogRootView));
		layout.getBackground().setAlpha(179);
		builder.setView(layout);
		
		builder.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				userWindow.setVisibility(View.VISIBLE);
			}
		});
		builder.show();
	}

	@Override
	public void showPedestrianInfo(Pedestrian pedestrian) {
		userWindow.setVisibility(View.GONE);
		
		final Context contxt = MapActivity.this;
		AlertDialog.Builder builder = new AlertDialog.Builder(contxt);
		LayoutInflater inflater =
				(LayoutInflater) contxt.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.user_menu_window,
				(ViewGroup) findViewById(R.id.dialogRootView));
		layout.getBackground().setAlpha(179);
		builder.setView(layout);
		
		builder.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				userWindow.setVisibility(View.VISIBLE);
			}
		});
		builder.show();
	}

	@Override
	public void updateMP(int x) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void placeBeacon(LatLng x) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collectMP(int x) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void activiteBeacon(boolean x) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initMarker(LatLng point, int pic_id){ 
		//marker drawable 
		BitmapDescriptor bitmap = BitmapDescriptorFactory  
		    .fromResource(pic_id);  
		//markerOption
		OverlayOptions option = new MarkerOptions()
		    .position(point)  
		    .icon(bitmap)
		    .title(point.toString());  

		//add to map
		baiduMap.addOverlay(option);
	}
	
	@Override
	public void toast(String x){
		Toast.makeText(getApplicationContext(), x, Toast.LENGTH_SHORT).show();
	}
}