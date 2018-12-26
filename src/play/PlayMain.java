package play;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import entity.Bullet;
import entity.CommonEnemy;
import entity.EnemyThree;
import entity.FirePrize;
import entity.Plane;
import entity.FlyingObject;
import entity.LifePrize;
import entity.Plane;
import entity.ZeroRedHero;
import enu.GameStatus;
import enu.HeroStatus;
import enu.PrizeCategory;
import face.Prize;

@SuppressWarnings("serial")
public class PlayMain extends JPanel {
	// ��Ϸ������ߴ�
	public static final int GAME_WIDTH = 700;
	public static final int GAME_HEIGHT = 900;

	private GameStatus gameStatus; // ��¼��Ϸ״̬
	private HeroStatus heroStatus = HeroStatus.Common; //Ӣ��״̬
	public static BufferedImage backgroundImg; // ��Ϸ���汳��ͼ
	private static int heroImgWidth; // Ӣ�ۻ�ͼƬ�Ŀ��������㻭�ʵĽǶ�
	private static int heroImgHeight; // Ӣ�ۻ�ͼƬ�ĸߣ��������㻭�ʵĽǶ�
	
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>(); // ȫͼ�ӵ�����
	private ArrayList<FlyingObject> flyObjList = new ArrayList<FlyingObject>(); // �����Ｏ��
	
	private Plane hero; // Ӣ�۷ɻ�
	private Timer timer = new Timer(); //��ʱ��
	public static int intervel  = 10; //�����˶���ʱ����� ��λ���� 10�����˶�һ��,�൱��һ��100֡
	
	private int enemyPlanInterver = 0;//���ɵ��˷ɻ���ʱ��
	private int heroLaunchInterver = 1; //Ӣ�۹���ʱ������ʱ��
	private int garbageRecoveryInterver = 1000; //��������ʱ��
	private long heroVirtualStart = 0; //Ӣ���黯״̬�Ŀ�ʼʱ��
	private int heroAttSpeed = 0;//Ӣ�۵Ĺ����ٶ�
	private int score = 0; //�÷�
	
	private Random random = new Random();
	
	private JFrame frame; // ���ڿ��
	private JMenuBar bar; // �˵�
	private JMenu game; // �˵���
	private JMenuItem[] items; // �˵�������
	private JTextArea textArea; // �ұ߿�

	// ��ʼ������ͼ������ص�ʱ��ִ��һ��
	static {
		try {
			backgroundImg = ImageIO.read(PlayMain.class.getResource("../img/backgrand/back2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public PlayMain() throws IOException {
		init();
	}

	/**
	 * ��ʼ����������
	 * 
	 * @throws IOException
	 */
	public void init() throws IOException {
		// ��ʼ��Ӣ�ۻ�
		initHeroPlan();

		// ��ʼ��UI
		initUI();

		// ��Ϸ״̬Ϊ����
		gameStatus = GameStatus.Start;
	}

	/**
	 * ��ʼ��UI���
	 */
	public void initUI() {
		frame = new JFrame("�ɻ���ս���ɰ�"); // ʵ����������Ϸ���ڿ��
		textArea = new JTextArea();
		bar = new JMenuBar(); // �����˵���
		game = new JMenu("�˵�"); // ������Ϊ����Ϸ���Ĳ˵�
		items = new JMenuItem[2]; // game�˵��´���2���Ӳ˵���
		game.add(items[0] = new JMenuItem("���¿�ʼ"));// ��һ���Ӳ˵�Ϊ�����¿�ʼ��
		game.add(items[1] = new JMenuItem("�˳�")); // �ڶ����Ӳ˵�Ϊ���˳���
	}

	/**
	 * ��ʼ��Ӣ�ۻ�����
	 * @throws IOException
	 */
	public void initHeroPlan() throws IOException {
		hero = new ZeroRedHero();
		heroImgWidth = hero.getImgWidth();
		heroImgHeight = hero.getImgHeight();
		heroAttSpeed = (1000 / PlayMain.intervel) / hero.getAttackSpeed();
	}

	/**
	 * ��
	 */
	@Override
	public void paint(Graphics g) {
		try {
			drawMainView(g); // ��������
			drawHero(g); // ��Ӣ�ۻ�
			drawBullet(g); // ���ӵ�
			drawFlyObject(g); // ��������
			drawScore(g); //������
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void drawMainView(Graphics g) throws IOException {
		g.drawImage(backgroundImg, 0, 0, null);
	}

	public void drawHero(Graphics g) {
		
		//�ж��Ƿ����黯״̬
//		if(heroStatus == HeroStatus.Virtual){
//			if(System.currentTimeMillis() - heroVirtualStart < 3000){
//				hero.setImg("../img/hero/ship-Virtual.png");
//			}else{
//				heroStatus = HeroStatus.Common;
//				hero.setImg("../img/hero/ship-Common.png");
//			}
//		}
		
		boolean commen = true;
		
		if(heroStatus != HeroStatus.Common){

			//�ж��Ƿ����黯״̬
			if(heroStatus == HeroStatus.Virtual){
				if(System.currentTimeMillis() - heroVirtualStart < 3000){
					hero.setImg("../img/hero/ship-Virtual.png");
					commen=false;
				}
			}
			
			//�ж��Ƿ����޵�״̬
			if(heroStatus == HeroStatus.Virtual){
				if(System.currentTimeMillis() - heroVirtualStart < 3000){
					hero.setImg("../img/hero/ship-Virtual.png");
					commen=false;
				}
			}
			
		}
		
		if(commen){
			heroStatus = HeroStatus.Common;
			hero.setImg("../img/hero/ship-Common.png");
		}
		g.drawImage(hero.getImg(), hero.getX(), hero.getY(), null);
	}

	public void drawBullet(Graphics g) {
		Bullet bullet;
		for (int i = 0; i < bullets.size(); i++) {
			bullet = bullets.get(i);
			g.drawImage(bullet.getImg(), bullet.getX(), bullet.getY(), null);
		}
	}
	
	public void drawFlyObject(Graphics g){
		FlyingObject flyObj;
		for (int i = 0; i < flyObjList.size(); i++) {
			flyObj = flyObjList.get(i);
			g.drawImage(flyObj.getImg(),flyObj.getX() ,flyObj.getY(), null);
			
//			g.drawLine(x1, y1, x2, y2);
//			g.drawLine(x1, y1, x2, y2);
//			g.drawLine(x1, y1, x2, y2);
//			g.drawLine(x1, y1, x2, y2);
		}
	}
	
	public void drawScore(Graphics g){
		
		int x = 10; // x����
		int y = 25; // y����
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 16); // ����
		g.setColor(Color.RED);
		g.setFont(font); // ��������
		g.drawString("SCORE:" + score, x, y += 20); // ������
		g.drawString("LIFE:" + hero.getLife(), x, y += 20); // ����
		
		//��������
		g.drawString("�����ٶ� :" + 1000 / intervel / heroAttSpeed, x, y += 20); 
		g.drawString("����ֵ :" + hero.getFirepower(), x, y += 20); 
		g.drawString("bullets.size:" +bullets.size(), x, y += 20); 
		g.drawString("flyObjList.size:" + flyObjList.size(), x, y += 20); 
	}
	
	
	public void frameManage() {
		// ����ƶ��¼�
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();

				textArea.setText(null);
				textArea.append("PX: ( X:" + x + ", Y:" + y + ")\n" );
				
				hero.setX(x - heroImgWidth / 2);
				hero.setY(y - heroImgHeight / 2);
				
				repaint();
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				
			}

		});

		// ��رհ�ť�¼�
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				String str = "�Ƿ�Ҫ�˳���Ϸ?";
				// �����Ϣ�Ի���
				if (JOptionPane.showConfirmDialog(null, str, "�˳���Ϸ",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

					System.exit(0); // �˳�
				}
			}
		});

		// ������¿�ʼ�¼�
		items[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = "�Ƿ�Ҫ���¿�ʼ��Ϸ?";
				// �����Ϣ�Ի���
				if (JOptionPane.showConfirmDialog(null, str, "���¿�ʼ",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					repaint(); // �ػ�
				}
			}
		});

		// ����˳��¼�
		items[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = "�Ƿ�Ҫ�˳���Ϸ?";
				// �����Ϣ�Ի���
				if (JOptionPane.showConfirmDialog(null, str, "�˳���Ϸ",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					System.exit(0); // �˳�
				}
			}
		});
		
		//�����˶���ʱ��
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				nextStep(); //��ʼ�˶�
				randomEnemyPlany(); //���ɵл�
				heroShooting(); //Ӣ�����
				garbageRecovery(); //��������
				duangGG();//�ӵ�����ײ���
				isGameOver();//�����Ϸ�Ƿ����
				repaint(); // �ػ棬����paint()����
			}
			
		}, intervel,intervel);
		
		
		
		this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT)); //������Ϸ����Ŀ��

		//�����ұ��ı���
		textArea.setEditable(false); //���ɱ༭
		JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(200, GAME_HEIGHT));
		scrollPane.setVisible(true);

		frame.setLayout(new BorderLayout());// ���ò��ַ�ʽΪ��ʽ����
		frame.add(this, BorderLayout.WEST);
		frame.add(scrollPane, BorderLayout.EAST);
		bar.add(game);
		frame.setJMenuBar(bar);
		frame.pack();
		frame.setLocation(0, 0); // ���Դ��ڵĶ�λ
		frame.setResizable(false);// ���ô˴����Ƿ�����û�������С
		frame.setVisible(true); // ���ô�����ʾ״̬
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	
	
	/**
	 * ������ɷ�����
	 */
	public void randomEnemyPlany(){
		if(enemyPlanInterver % 50 == 0){
			int ran = random.nextInt(10);
			if(ran == 1){
				FlyingObject prize = new LifePrize();
				prize.setX(random.nextInt(GAME_WIDTH - prize.getImgWidth()));
				prize.setY(0);
				flyObjList.add(prize);
			}else if(ran == 2){
				FlyingObject prize = new FirePrize();
				prize.setX(random.nextInt(GAME_WIDTH - prize.getImgWidth()));
				prize.setY(0);
				flyObjList.add(prize);
			}else if(ran > 7){
				EnemyThree eplane = new EnemyThree();
				eplane.setX(random.nextInt(GAME_WIDTH - eplane.getImgWidth()));
				eplane.setY(0);
				flyObjList.add(eplane);
			}else{
				CommonEnemy eplane = new CommonEnemy();
				eplane.setX(random.nextInt(GAME_WIDTH - eplane.getImgWidth()));
				eplane.setY(0);
				flyObjList.add(eplane);
			}
		}
		enemyPlanInterver ++;
	}
	
	/**
	 * �˶�һ��
	 */
	public void nextStep(){
		
		//�ӵ��˶�
		for (int i = 0; i < bullets.size(); i++) bullets.get(i).step();
		
		//�������˶�
		for (int i = 0; i < flyObjList.size(); i++) flyObjList.get(i).step();
	}
	
	
	/**
	 * Ӣ�ۻ����
	 */
	public void heroShooting(){
		
		if(heroLaunchInterver % heroAttSpeed == 0){
			
			bullets.addAll(hero.launch());
		}
		
		heroLaunchInterver++;
	}
	
	
	/**
	 * �Ƴ�Խ��ķ�������ӵ�
	 */
	public void garbageRecovery(){
		
		
		if(garbageRecoveryInterver % (1000 / intervel) == 0){
			ArrayList<Bullet> bulletsBak = new ArrayList<Bullet>();
			ArrayList<FlyingObject> flyObjListBak = new ArrayList<FlyingObject>();
			
			//���������ӵ�
			Bullet bullet;
			for (int i = 0; i < bullets.size(); i++) {
				bullet = bullets.get(i);
				if(!bullet.isTransboundary()) bulletsBak.add(bullet);
			}
			
			//�������з�����
			FlyingObject flyObj;
			for (int i = 0; i < flyObjList.size(); i++) {
				flyObj = flyObjList.get(i);
				if(!flyObj.isTransboundary()) flyObjListBak.add(flyObj);
			}
			
			//�滻����
			bullets = bulletsBak;
			flyObjList = flyObjListBak;
		}
		
		garbageRecoveryInterver++;
	}
	
	/**
	 * �����Ϸ�Ƿ����
	 */
	public boolean isGameOver(){
		/**
		 * �������ͨ״̬�������ײ��⣬��ײ���ɹ���,�������
		 * ���������Ϊ0������黯״̬�����Ϊ0����Ϸ����
		 */
		if(heroStatus == HeroStatus.Common){
			for (int i = 0; i < flyObjList.size(); i++) {
				if(flyObjList.get(i).inspectCollision(hero)){
					if(flyObjList.get(i) instanceof Prize){
						Prize prize = (Prize)flyObjList.get(i);
						setPrize(prize); //���ý���
						
						
//						ArrayList<FlyingObject> flyObjBak = new ArrayList<>();
//						flyObjList.set(i, flyObjList.get(flyObjList.size() - 1));
//						for (int k = 0; k < flyObjList.size() -1; k++) {
//							flyObjBak.add(flyObjList.get(k));
//						}
//						flyObjList = flyObjBak;
						setList(i, flyObjList);//�Ƴ�������
						
					}else{
						if(hero.getLife() == 0){
							return true;
						}else{
							Plane enemy = (Plane)flyObjList.get(i);
							hero.setLife(hero.getLife() - enemy.getBullet().getHurt());
							heroStatus = HeroStatus.Virtual;
							heroVirtualStart = System.currentTimeMillis();
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * �ӵ��������֮�����ײ���
	 */
	public void duangGG(){
		Bullet bullet ;
		Plane enemy;
		Prize prize;
		ArrayList<FlyingObject> flyObjBak ;
		ArrayList<Bullet> bulletsBak ; 
		for (int i = 0; i < bullets.size() ; i++) {
			bullet = bullets.get(i);
			for (int j = 0; j < flyObjList.size(); j++) {
				if(bullet.inspectCollision(flyObjList.get(j))){
					if(flyObjList.get(j) instanceof Prize){//�жϵл����˺��л����ǽ���
						/**
						 * ��ȡ��������
						 * ���ö�Ӧ����
						 */
						flyObjBak = new ArrayList<>();
						prize = (Prize)flyObjList.get(j);
						setPrize(prize);//���ý���
						setList(j, flyObjList);
//						flyObjList.set(j, flyObjList.get(flyObjList.size() - 1));
//						for (int k = 0; k < flyObjList.size() -1; k++) {
//							flyObjBak.add(flyObjList.get(k));
//						}
//						flyObjList = flyObjBak;
					}else{
						/**
						 * ���ӵ����˺�ֵ��л�����ֵ��� 
						 * ����л�����ֵΪ0��ɾ���л�
						 */
						enemy = (Plane)flyObjList.get(j);
						enemy.setLife(enemy.getLife() - bullet.getHurt());
						if(enemy.getLife() <= 0){
							flyObjBak = new ArrayList<>();
							
							setList(j, flyObjList);
//							flyObjList.set(j, flyObjList.get(flyObjList.size() - 1));
//							for (int k = 0; k < flyObjList.size() -1; k++) {
//								flyObjBak.add(flyObjList.get(k));
//							}
//							flyObjList = flyObjBak;
						}
					}
					
					//�Ƴ��ӵ�
					bulletsBak = new ArrayList<>() ;
					if(!bullets.isEmpty()){
						bullets.set(i, bullets.get(bullets.size() -1 ));
						for (int k = 0; k < bullets.size() - 1; k++) {
							bulletsBak.add(bullets.get(k));
						}
						
						bullets = bulletsBak;
						continue;
					}
					
				}
			}
		}
	}
	
	/**
	 * ���ö�Ӧ����
	 */
	public void setPrize(Prize prize){
		
		if(prize.getCategory() == PrizeCategory.Life){
			hero.setLife(hero.getLife() + 1);
		}
		
		if(prize.getCategory() == PrizeCategory.Fire){
			hero.setFirepower(hero.getFirepower() + 1);
		}
		
		if(prize.getCategory() == PrizeCategory.Attackspeed){
			
		}
		
		if(prize.getCategory() == PrizeCategory.Invincible){
			
		}
		
	}
	

	/**
	 * �Ƴ�������
	 */
	public void setList(int index , ArrayList<FlyingObject> list){
		ArrayList<FlyingObject> listBak = new ArrayList<>();
		
		list.set(index, list.get(list.size() - 1));
		
		for (int i = 0; i < list.size() -1 ; i++) listBak.add(list.get(i));
		
		list = listBak;
	}
	
	
	
	public void start() {
		frameManage();
	}

	public static void main(String[] args) throws IOException {
		PlayMain plMain = new PlayMain();
		plMain.start();
	}

}
