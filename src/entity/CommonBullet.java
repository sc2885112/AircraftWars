package entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
/**
 * ÆÕÍ¨µÄ×Óµ¯
 * @author sand monk
 */
public class CommonBullet extends Bullet{
	
	public CommonBullet(int x, int y, int flySpeed, int hurt, BufferedImage img, boolean type) {
		super(x, y, flySpeed, hurt , img , type);
	}
	
	public CommonBullet(boolean type ,String imagePath,int flySpeed){
		this.setFlySpeed(flySpeed); 
		this.setHurt(1);
		this.setImg(imagePath);
		this.setType(type);
	}
	
	@Override
	public ArrayList<Bullet> getBullet(int x, int y ,int firepower) {
		ArrayList<Bullet> result = new ArrayList<Bullet>();
		
		if(!this.getType()){
			y = y + this.getImgHeight();
		}
		
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
