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
	private int flySpeed; //�ƶ��ٶ�
	
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
	 * ��һ��
	 * @return
	 */
	public void step(){
		this.setY(this.getY() + this.getFlySpeed());
	}
	
	/**
	 * Խ����
	 */
	public boolean isTransboundary(){
		return this.y > PlayMain.GAME_HEIGHT;
	}
	
	/**
	 * ��ײ���
	 * @return
	 */
	public boolean inspectCollision(FlyingObject flyObj){

		int x1 = flyObj.x - this.getImgWidth()/2;                 //x������С����
		int x2 = flyObj.x + this.getImgWidth()/2 + flyObj.getImgWidth();   //x����������
		int y1 = flyObj.y - this.getImgHeight()/2;                //y������С����
		int y2 = flyObj.y + this.getImgHeight()/2 + flyObj.getImgHeight(); //y����������
	
		int herox = this.x + this.getImgWidth()/2;               //Ӣ�ۻ�x�������ĵ����
		int heroy = this.y + this.getImgHeight()/2;              //Ӣ�ۻ�y�������ĵ����
		return herox>x1 && herox<x2 && heroy>y1 && heroy<y2;   //���䷶Χ��Ϊײ����
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
			System.out.println("��ȡͼƬ����");
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
