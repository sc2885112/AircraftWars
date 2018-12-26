package entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import play.PlayMain;

/**
 * �ӵ�����
 * @author lenovo
 */
public class Bullet extends FlyingObject{
	private int hurt;  //�˺�ֵ
	private boolean type; //�ӵ�������ǵл���������Ӣ�ۻ�����
	
	
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
	 * �����ӵ�
	 * �������า��
	 * @return
	 */
	public ArrayList<Bullet> getBullet(int x , int y ,int firepower){return null;}

	
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