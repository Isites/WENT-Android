package model;

import java.util.ArrayList;

import presenter.IMapActivityPresenter;
import presenter.MapActivityPresenter;
import went.util.Constant;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;

import data.Beacon;
import data.Pedestrian;
import data.Tower;

public class Model implements IModel{
	Beacon[] beacon;
	Pedestrian pedestrian;
	Tower[] tower;
	Tower targetTower;
	boolean[] buttonVisibility;
	IMapActivityPresenter presenterCallback;
	public Model(MapActivityPresenter ipresenter){
		this.presenterCallback = ipresenter;
		pedestrian = new Pedestrian("110", new LatLng(30.530675, 114.360119), Constant.YXZ);
		tower = new Tower[]{
				new Tower("574338264", new LatLng(30.530675, 114.360119), Constant.SXZ),
				new Tower("601887553", new LatLng(30.534109, 114.362675), Constant.YXZ),
				new Tower("601854032", new LatLng(30.534822, 114.361129), Constant.NULL),
		};
		beacon = new Beacon[]{
				
		};
		
	}
	//presenter.udpateMPByTime(mp);
	@Override
	public Pedestrian getPedestrian() {
		return this.pedestrian;
	}

	@Override
	public void setPedestrianLatlng(LatLng latlng) {
		//get Tower and Pedestrian data from server, set targetTower, 
		//buttonVisibility.
		pedestrian.setLatlng(latlng);
	}

	@Override
	public Tower getTowerInfo(LatLng latlng) {
		for(int i=0; i<tower.length; i++){
			if(latlng.equals(tower[i].getLatlng())){
				return tower[i];
			}
		}
		return null;
	}

	@Override
	public boolean[] getNearBy() {
		//return buttonVisibility;
		return new boolean[]{ true, false, false };
	}

	@Override
	public LatLng[] getMarkerLatlng() {
		LatLng[] latlng = new LatLng[tower.length+1];
		latlng[0] = pedestrian.getLatlng();
		for(int i=0; i<tower.length; i++)
			latlng[i+1] = tower[i].getLatlng();
		return latlng;
	}

	@Override
	public int[] getMarkerCamp() {
		int[] camp = new int[tower.length+1];
		camp[0] = pedestrian.getCamp();
		for(int i=0; i<tower.length; i++)
			camp[i+1] = tower[i].getCamp();
		return camp;
	}

	@Override
	public void attack(LatLng latlng) {
		int _mp = pedestrian.getMP();
		int _atkMp = pedestrian.getAtkMp();
		int _camp = pedestrian.getCamp();
		
		// is attack valid?
		if(_mp < _atkMp) {
			// mp not enough
			return;
		}
		
		Tower target = null;
		for(Tower t: tower) {
			if(t.getLatlng().equals(latlng)) {
				target = t;
				break;
			}
		}
		if(target == null) {
			// some sorts of error
			return;
		}
		if(target.getCamp() == _camp) {
			// same camp
			return;
		}
		
		pedestrian.setMP(_mp - _atkMp);
		target.shiftHp(-pedestrian.getATK());
		ArrayList<Beacon> beacons = target.getBeacons();
		for(Beacon _beacon: beacons) {
			// is beacon hit?
			if(Math.random() <= _beacon.getHitProbability()) {
				_beacon.setHP(_beacon.getHP() - pedestrian.getATK());
			}
		}
		if(!target.isAlive()) {
			target.reset(_camp);
		}

		//update view
		presenterCallback.udpateMP(_mp - _atkMp);
	}

	@Override
	public int getCollectMP() {
		//get nearby tower， and get the mp in the tower.
		int totalMP = 0;
		for(Tower tower: this.tower)
			if(DistanceUtil.getDistance(tower.getLatlng(), 
					pedestrian.getLatlng()) < 500){
				for(Beacon beacon: tower.getBeacons()){
					if(pedestrian.getMP() + totalMP + beacon.getMP()
							> pedestrian.getTopMP())
						break;
					else{
						totalMP = totalMP + beacon.getMP();
						beacon.setMP(0);
					}
				}
			}
		pedestrian.setMP(pedestrian.getMP() + totalMP);
		return 0;
	}

	@Override
	public LatLng placeBeacon() {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean activiteBeacon() {
		if(pedestrian.getMP() > 20){
			pedestrian.setMP(pedestrian.getMP() - 20);
			
			return true;
		}else
			return false;
	}
}
