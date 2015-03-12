package view;

import com.baidu.mapapi.model.LatLng;

import data.Pedestrian;
import data.Tower;

public interface IMapActivity {
	void refreshButton(int[] x);
	void attack(LatLng latlng);
	void showTowerInfo(Tower x);
	void showPedestrianInfo(Pedestrian pedestrian);
	void initMarker(LatLng latlng, int pic_id);
	void updateMP(int x);
	void placeBeacon(LatLng x);
	void collectMP(int x);
	void activiteBeacon(boolean x);
	void toast(String x);
}
