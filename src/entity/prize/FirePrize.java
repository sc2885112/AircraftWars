package entity.prize;

import entity.FlyingObject;
import enu.PrizeCategory;
import face.Prize;

public class FirePrize extends FlyingObject implements Prize{
	
	public FirePrize(){
		this.setFlySpeed(3);
		this.setImg("../img/prize/fire.png");
	}
	
	@Override
	public PrizeCategory getCategory() {
		// TODO Auto-generated method stub
		return PrizeCategory.Fire;
	}

}
