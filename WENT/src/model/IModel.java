package model;

import com.baidu.mapapi.model.LatLng;

import data.Pedestrian;
import data.Tower;

public interface IModel {
	boolean[] getNearBy();
	Pedestrian getPedestrian();
	void setPedestrianLatlng(LatLng latlng);
	Tower getTowerInfo(LatLng latlng);
	LatLng[] getMarkerLatlng();
	int[] getMarkerCamp();
	void attack(LatLng latlng);
	int getCollectMP();
	LatLng placeBeacon();
	boolean activiteBeacon();
}
