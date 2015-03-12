package data;

import com.baidu.mapapi.model.LatLng;


public class Pedestrian{
	private String id;
	private LatLng latlng;
	private Beacon[] beacons;
	private StringBuilder occupiedTowerID;
	private int camp, lv, mp, topMP, atk, atkSpeed, atkMp, atkFactor, view;
	
	public Pedestrian(){
		
	}
	
	public Pedestrian(String id, LatLng latlng, int camp){
		this.id = id;
		this.latlng = latlng;
		this.camp = camp;
		
		this.occupiedTowerID = new StringBuilder();
	}
	
	public int getTopMP(){
		return this.topMP;
	}
	public int getCamp(){
		return this.camp;
	}
	public LatLng getLatlng(){
		return this.latlng;
	}
	
	public String getID(){
		return this.id;
	}
	
	public Beacon[]  getBeacon(){
		return this.beacons;
	}
	
	public String getOccupiedTowerID(){
		return this.occupiedTowerID.toString();
	}
	
	public int getLV(){
		return this.lv;
	}
	
	public int getMP(){
		return this.mp;
	}
	
	public int getATK(){
		return this.atk;
	}
	
	public int getAtkSpeed(){
		return this.atkSpeed;
	}
	
	public int getAtkMp(){
		return this.atkMp;
	}
	
	public int getAtkFactor(){
		return this.atkFactor;
	}
	
	public int getView(){
		return this.view;
	}
	
	public void setCamp(int x){
		this.camp = x;
	}
	public void setID(String id){
		this.id = id;
	}
	
	public void setOwnedBeaconID(String id){
		//this.ownedBeaconID.append(id);
	}
	
	public void setOccupiedTowerID(String id){
		this.occupiedTowerID.append(id);
	}
	
	public void setLV(int lv){
		this.lv = lv;
	}
	
	public void setMP(int mp){
		this.mp = mp;
	}
	
	public void setATK(){
		
	}
	
	public void setAtkSpeed(){
		
	}
	
	public void setAtkMp(){
		
	}
	
	public void setAtkFactor(){
		
	}
	
	public void setView(){
		
	}
	
	public void setLatlng(LatLng latlng){
		this.latlng = latlng;
	}
	
	public String toString(){
		return this.id + this.camp + this.latlng;
	}
}
