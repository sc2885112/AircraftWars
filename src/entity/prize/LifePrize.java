package entity.prize;

import entity.FlyingObject;
import enu.PrizeCategory;
import face.Prize;

public class LifePrize extends FlyingObject implements Prize{

	public LifePrize(){
		this.setFlySpeed(3);
		this.setImg("../img/prize/bee.png");
	}
	
	
	@Override
	public PrizeCategory getCategory() {
		// TODO Auto-generated method stub
		return PrizeCategory.Life;
	}
	
}
