package presenter;

import model.IModel;
import model.Model;
import view.IMapActivity;
import went.util.Constant;

import com.baidu.mapapi.model.LatLng;

public class MapActivityPresenter implements IMapActivityPresenter{
	private IMapActivity view;
	private IModel model;
	
	public MapActivityPresenter(IMapActivity view){
		this.view = view;
		model = new Model(this);
	}
	
	public void setPedestrianLatlng(LatLng latlng){
		model.setPedestrianLatlng(latlng);
	}
	
	public void attack(LatLng target){
		model.attack(target);
		view.attack(target);
	}
	
	public void collectMP(){
		view.collectMP(model.getCollectMP());
	}
	
	public void showTowerInfo(LatLng latlng){	
		view.showTowerInfo(model.getTowerInfo(latlng));
	}
	
	public void showPedestrianInfo(){
		view.showPedestrianInfo(model.getPedestrian());
	}
	
	public void refreshButtonByDistance(){
		//distance     neutral    friend|enemy
		boolean[] result = model.getNearBy(); 
		
		if(result[0]){
			if(result[1]){
				view.refreshButton(Constant.judge[1]);
			}else if(result[2]){
				view.refreshButton(Constant.judge[0]);
			}else{
				view.refreshButton(Constant.judge[2]);
			}
		}else{
			view.refreshButton(Constant.judge[3]);
		}
		view.refreshButton(Constant.judge[4]);
	}
	
	public void initAllMarker(){
		LatLng[] latlng = model.getMarkerLatlng();
		int[] camp = model.getMarkerCamp();
		for(int i=0; i<camp.length; i++){
			view.initMarker(latlng[i], Constant.CAMPS_DRAWABLES[camp[i]]);
		}
	}
	
	public void placeBeacon(){
		view.placeBeacon(model.placeBeacon());
	}
	
	public void activiteBeacon(){
		view.activiteBeacon(model.activiteBeacon());
	}

	//IMapActivityPresenter's overrides.
	@Override
	public void udpateMP(int mp) {
		view.updateMP(mp);
	}
}
