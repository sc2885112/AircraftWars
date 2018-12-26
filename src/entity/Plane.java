package entity;

import java.util.ArrayList;

import entity.bullet.Bullet;
import face.Attack;

public class Plane extends FlyingObject implements Attack{
	
	private int attackSpeed; //�����ٶ�
	private int life; //����ֵ
	private Bullet bullet; //װ�ص�����
	private int firepower; //����ֵ

	public Plane() {}

	@Override
	public ArrayList<Bullet> launch() {
		return bullet.getBullet(this.getX() + this.getImgWidth() / 2 - 8 ,this.getY() - 10,firepower);
	}
	
	public int getAttackSpeed() {
		return attackSpeed;
	}

	public void setAttackSpeed(int attackSpeed) {
		this.attackSpeed = attackSpeed;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public Bullet getBullet() {
		return bullet;
	}

	public void setBullet(Bullet bullet) {
		this.bullet = bullet;
	}

	public int getFirepower() {
		return firepower;
	}

	public void setFirepower(int firepower) {
		this.firepower = firepower;
	}
	
}
