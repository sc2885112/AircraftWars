package entity;

import face.Enemy;

/**
 * µÐ»úÈýºÅ
 * @author lenovo
 *
 */
public class EnemyThree extends Plane implements Enemy{
	public EnemyThree(){
		this.setBullet(new CommonBullet(false));
		this.setAttackSpeed(1);
		this.setFlySpeed(1);
		this.setLife(20);
		this.setFirepower(1);
		this.setImg("../img/enemy/enemy3.png");
	}

	@Override
	public int getScore() {
		return 1;
	}
}
