package entity;

import face.Enemy;
/**
 * ��ͨ�ĵл� 0�Ż�
 * @author lenovo
 */
public class CommonEnemy extends Plane implements Enemy{
	public CommonEnemy(){
		this.setBullet(new CommonBullet(false));
		this.setAttackSpeed(1);
		this.setFlySpeed(3);
		this.setLife(1);
		this.setFirepower(1);
		this.setImg("../img/enemy/enemy11.png");
	}

	@Override
	public int getScore() {
		return 1;
	}
	
}
