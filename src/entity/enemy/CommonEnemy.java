package entity.enemy;

import entity.bullet.CommonBullet;

/**
 * 普通的敌机 0号机
 * @author sand monk
 */
public class CommonEnemy extends Enemy{
	public CommonEnemy(){
		this.setBullet(new CommonBullet(false,"../img/bullet/bullet2.png",6));
		this.setAttackSpeed(1);
		this.setFlySpeed(3);
		this.setLife(1);
		this.setFirepower(1);
		this.setImg("../img/enemy/enemy11.png");
	}

	@Override
	public int getScope() {
		// TODO Auto-generated method stub
		return 10;
	}

}
