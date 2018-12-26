package entity.hero;

import entity.Plane;
import entity.bullet.CommonBullet;
import play.PlayMain;

/**
 * ��ź�ɫ��
 * @author sand monk
 *
 */
public class ZeroRedHero extends Plane{
	
	/**
	 * ��ʼ����Ż�
	 */
	public ZeroRedHero(){
		this.setImg("../img/hero/ship-Common.png");
		this.setLife(3); // ��������ֵ
		this.setFlySpeed(999); // ���÷����ٶ�
		this.setAttackSpeed(5);// ���ù����ٶ�
		this.setFirepower(1); // ���û���ֵ ����Ϊ3
		this.setBullet(new CommonBullet(true,"../img/bullet/bullet.png",3)); // ��������
		this.setX(PlayMain.GAME_WIDTH / 2 / this.getImgWidth() * this.getImgWidth());
		this.setY(PlayMain.GAME_HEIGHT - this.getImgHeight());
	}
}
