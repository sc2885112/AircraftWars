package entity;

import play.PlayMain;

/**
 * 零号红色机
 * @author lenovo
 *
 */
public class ZeroRedHero extends Plane{
	
	/**
	 * 初始化零号机
	 */
	public ZeroRedHero(){
		this.setImg("../img/hero/ship-Common.png");
		this.setLife(3); // 设置生命值
		this.setFlySpeed(999); // 设置飞行速度
		this.setAttackSpeed(5);// 设置攻击速度
		this.setFirepower(1); // 设置火力值 上限为3
		this.setBullet(new CommonBullet(true,"../img/bullet/bullet.png",3)); // 设置武器
		this.setX(PlayMain.GAME_WIDTH / 2 / this.getImgWidth() * this.getImgWidth());
		this.setY(PlayMain.GAME_HEIGHT - this.getImgHeight());
	}
}
