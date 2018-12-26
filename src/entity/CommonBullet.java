package entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
/**
 * ÆÕÍ¨µÄ×Óµ¯
 * @author lenovo
 */
public class CommonBullet extends Bullet{
	
	public CommonBullet(int x, int y, int flySpeed, int hurt, BufferedImage img, boolean type) {
		super(x, y, flySpeed, hurt , img , type);
	}
	
	public CommonBullet(boolean type){
		this.setFlySpeed(3); 
		this.setHurt(1);
		this.setImg("../img/bullet/bullet.png");
		this.setType(type);
	}
	
	@Override
	public ArrayList<Bullet> getBullet(int x, int y ,int firepower) {
		ArrayList<Bullet> result = new ArrayList<Bullet>();
			if( firepower >= 3){
				result.add(new CommonBullet((x - 15),y,this.getFlySpeed(),this.getHurt(),this.getImg(),this.getType()));
				result.add(new CommonBullet((x),y,this.getFlySpeed(),this.getHurt(),this.getImg(),this.getType()));
				result.add(new CommonBullet((x + 15),y,this.getFlySpeed(),this.getHurt(),this.getImg(),this.getType()));
			}
			if( firepower == 2){
				result.add(new CommonBullet((x + 15),y,this.getFlySpeed(),this.getHurt(),this.getImg(),this.getType()));
				result.add(new CommonBullet((x - 15),y,this.getFlySpeed(),this.getHurt(),this.getImg(),this.getType()));
			}
			if(firepower == 1){
				result.add(new CommonBullet(x,y,this.getFlySpeed(),this.getHurt(),this.getImg(),this.getType()));
			}
		
		return result;
	}
}
