package data;


public class Beacon{
	private String id, ownerID, placedTowerID;
	private int camp, hp, mp, agility, hpRecoveryRate, mpProduceRate;
	private double hitProbability;
	private boolean isAlive = true;

	public Beacon(String id, int camp){
		this.id = id;
		this.camp = camp;
	}
	
	public Beacon(String id, String ownerID, String placedTowerID){
		this.id = id;
		this.ownerID = ownerID;
		this.placedTowerID = placedTowerID;
	}

	public String getID() {
		return this.id;
	}

	public int getCamp() {
		return this.camp;
	}

	public int getHP() {
		return this.hp;
	}

	public int getMP() {
		return this.mp;
	}

	public int getAgility() {
		return this.agility;
	}
	public double getHitProbability() {
		return this.hitProbability;
	}

	public String getOwnerID() {
		return this.ownerID;
	}

	public String getPlacedTowerID() {
		return this.placedTowerID;
	}

	public int getHpRecoveryRate() {
		return this.hpRecoveryRate;
	}

	public int getMpProduceRate() {
		return this.mpProduceRate;
	}

	public boolean isAlive(){
		return this.isAlive;
	}
	
	public void setID(String id) {
		this.id = id;
	}

	public void setOwnerID(String id) {
		this.ownerID = id;
	}

	public void setPlacedTowerID(String id) {
		this.placedTowerID = id;
	}

	public void setCamp(int camp) {
		this.camp = camp;
	}
	
	public void setHP(int hp){
		this.hp = hp;
		if(hp <= 0)
			this.setIsAlive(false);
	}
	
	public void setMP(int mp){
		this.mp = mp;
	}
	
	public void setAgility(int agility){
		this.agility = agility;
		this.hitProbability = 1.0 / agility;
	}
	
	public void setHpRecoveryRate(int recoveryRate){
		this.hpRecoveryRate = recoveryRate;
	}
	
	public void setMpProduceRate(int produceRate){
		this.mpProduceRate = produceRate;
	}
	
	public void setIsAlive(boolean x){
		this.isAlive = x;
	}
}

