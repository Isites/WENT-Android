package presenter;

import model.IModel;
import model.Model;
import view.IMapActivity;
import went.util.Constant;
import android.util.Log;

import com.baidu.mapapi.model.LatLng;
import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;

public class MapActivityPresenter implements IMapActivityPresenter{
	private IMapActivity view;
	private IModel model;
	final String LOG_TAG = "MapActivityPresenter";
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
	
	public void sendMessage(String content, String username){
		//获取到与聊天人的会话对象。参数username为聊天人的userid或者groupid，后文中的username皆是如此
		EMConversation conversation = EMChatManager.getInstance().getConversation(username);
		//创建一条文本消息
		EMMessage message = EMMessage.createSendMessage(EMMessage.Type.TXT);
		//如果是群聊，设置chattype,默认是单聊
		//message.setChatType(ChatType.GroupChat);
		//设置消息body
		TextMessageBody txtBody = new TextMessageBody(content);
		message.addBody(txtBody);
		//设置接收人
		message.setReceipt(username);
		//把消息加入到此会话对象中
		conversation.addMessage(message);
		//发送消息
		EMChatManager.getInstance().sendMessage(message, new EMCallBack(){
			@Override
			public void onError(int arg0, String arg1) {
				//view.toast("error");
			}
			@Override
			public void onProgress(int arg0, String arg1) {
				
			}
			@Override
			public void onSuccess() {
				Log.i(LOG_TAG, "sendMessageSuccess");
				//view.toast("success");
			}});
	}
	//IMapActivityPresenter's overrides.
	@Override
	public void udpateMP(int mp) {
		view.updateMP(mp);
	}	
}
