package data;

import java.util.ArrayList;

import com.baidu.mapapi.model.LatLng;



public class Tower{
	private LatLng latlng;
	private int camp;
	private String id;
	private int hp;
	private boolean isAlive = true;
	private ArrayList<Beacon> beacons;
	
	public Tower(){
		
	}
	
	public Tower(String id, LatLng latlng, int camp){
		this.id = id;
		this.latlng = latlng;
		this.camp = camp;
	}
	
	public String getID(){
		return this.id;
	}
	public LatLng getLatlng(){
		return this.latlng;
	}
	
	public int getCamp(){
		return this.camp;
	}
	public int getHp() {
		return this.hp;
	}
	
	public void setID(String id){
		this.id = id;
	}
	public void setLatlng(LatLng latlng){
		this.latlng = latlng;
	}
	
	public void setCamp(int camp){
		this.camp = camp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public void shiftHp(int shift) {
		this.hp += shift;
		if(this.hp <= 0){
			this.isAlive = false;
			this.hp = 0;
		}
	}
	public boolean isAlive() {
		return this.isAlive;
	}
	public void reset(int _camp) {
		this.hp = 1000 / 2;
		this.camp = _camp;
	}
	public void placeBeacon(Beacon beacon) {
		beacons.add(beacon);
	}
	public ArrayList<Beacon> getBeacons() {
		return beacons;
	}
}
