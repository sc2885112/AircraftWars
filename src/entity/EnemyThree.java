package entity;


/**
 * µÐ»úÈýºÅ
 * @author lenovo
 *
 */
public class EnemyThree extends Enemy{
	public EnemyThree(){
		this.setBullet(new CommonBullet(false,"../img/bullet/bullet2.png",6));
		this.setAttackSpeed(1);
		this.setFlySpeed(1);
		this.setLife(20);
		this.setFirepower(2);
		this.setImg("../img/enemy/enemy3.png");
	}

	@Override
	public int getScope() {
		
		return 100;
	}

}
