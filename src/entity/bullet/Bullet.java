package entity.bullet;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entity.FlyingObject;
import play.PlayMain;

/**
 * 武器总类
 * 依赖在飞机类的属性上
 * @author sand monk
 */
public abstract class Bullet extends FlyingObject{
	private int hurt;  //伤害值
	private boolean type; //武器的类别，是敌机发出还是英雄机发出
	
	
	public Bullet(int x, int y, int flySpeed,int hurt,BufferedImage img, boolean type) {
		super(x, y, flySpeed, img);
		this.hurt = hurt;
		this.type = type;
	}
	
	public Bullet(int x, int y, int flySpeed,int hurt,boolean type) {
		super(x, y, flySpeed);
		this.hurt = hurt;
		this.type = type;
	}
	
	public Bullet() {}
	
	/**
	 * 生成子弹
	 * 留给子类覆盖
	 * @return
	 */
	public abstract ArrayList<Bullet> getBullet(int x , int y ,int firepower);

	
	@Override
	public void step() {
		if(this.getType()) 
			this.setY(this.getY() - this.getFlySpeed());
		else
			this.setY(this.getY() + this.getFlySpeed());
	};
	
	@Override
	public boolean isTransboundary() {
		if(type)
			return this.getY() < 0;
		else
			return this.getY() > PlayMain.GAME_HEIGHT;
	};
	
	public int getHurt() {
		return hurt;
	}

	public void setHurt(int hurt) {
		this.hurt = hurt;
	}

	public boolean getType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
	}
}
