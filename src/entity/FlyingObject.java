package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import face.Flying;
import play.PlayMain;

public abstract class FlyingObject implements Flying{
	
	private int x;
	private int y;
	private BufferedImage img;
	private int imgWidth;
	private int imgHeight;
	private int flySpeed; //移动速度
	
	public FlyingObject(int x, int y, int flySpeed,BufferedImage img) {
		this.x = x;
		this.y = y;
		this.flySpeed = flySpeed;
		this.img = img;
	}
	
	public FlyingObject(int x, int y, int flySpeed) {
		this.x = x;
		this.y = y;
		this.flySpeed = flySpeed;
	}
	
	public FlyingObject() {}
	
	@Override
	public void fly(int x,int y) {
		this.setX(x);
		this.setY(y);
	}
	
	/**
	 * 走一步
	 * @return
	 */
	public void step(){
		this.setY(this.getY() + this.getFlySpeed());
	}
	
	/**
	 * 越界检查
	 */
	public boolean isTransboundary(){
		return this.y > PlayMain.GAME_HEIGHT;
	}
	
	/**
	 * 碰撞检查
	 * @return
	 */
	public boolean inspectCollision(FlyingObject flyObj){

		int x1 = flyObj.x - this.getImgWidth()/2;                 //x坐标最小距离
		int x2 = flyObj.x + this.getImgWidth()/2 + flyObj.getImgWidth();   //x坐标最大距离
		int y1 = flyObj.y - this.getImgHeight()/2;                //y坐标最小距离
		int y2 = flyObj.y + this.getImgHeight()/2 + flyObj.getImgHeight(); //y坐标最大距离
	
		int herox = this.x + this.getImgWidth()/2;               //英雄机x坐标中心点距离
		int heroy = this.y + this.getImgHeight()/2;              //英雄机y坐标中心点距离
		return herox>x1 && herox<x2 && heroy>y1 && heroy<y2;   //区间范围内为撞上了
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(String imgPath){
		try {
			this.img = ImageIO.read(FlyingObject.class.getResource(imgPath));
			this.imgWidth = this.getImg().getWidth();
			this.imgHeight = this.getImg().getHeight();
		} catch (IOException e) {
			System.out.println("读取图片出错！");
			e.printStackTrace();
		}
	}

	public int getFlySpeed() {
		return flySpeed;
	}

	public void setFlySpeed(int flySpeed) {
		this.flySpeed = flySpeed;
	}
	
	public int getImgWidth() {
		return imgWidth;
	}

	public void setImgWidth(int imgWidth) {
		this.imgWidth = imgWidth;
	}

	public int getImgHeight() {
		return imgHeight;
	}

	public void setImgHeight(int imgHeight) {
		this.imgHeight = imgHeight;
	}
	
}
