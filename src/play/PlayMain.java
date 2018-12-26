package play;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
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
import entity.Enemy;
import entity.EnemyThree;
import entity.FirePrize;
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
	// 游戏主界面尺寸
	public static final int GAME_WIDTH = 700;
	public static final int GAME_HEIGHT = 900;

	private GameStatus gameStatus; // 记录游戏状态
	private HeroStatus heroStatus = HeroStatus.Common; // 英雄状态
	public static BufferedImage backgroundImg; // 游戏界面背景图
	public static BufferedImage pushImg; // 游戏界面背景图
	public static BufferedImage gameOverImg; // 游戏界面背景图
	
	private static int heroImgWidth; // 英雄机图片的宽，用来计算画笔的角度
	private static int heroImgHeight; // 英雄机图片的高，用来计算画笔的角度

	private ArrayList<Bullet> bullets ; // 全图子弹集合
	private ArrayList<FlyingObject> flyObjList ; // 飞行物集合

	private Plane hero; // 英雄飞机
	private Timer timer = new Timer(); // 定时器
	public static int intervel = 10; // 界面运动定时器间隔 单位毫秒 10毫秒运动一次,相当于一秒100帧

	private int enemyPlanInterver = 0;// 生成敌人飞机定时器
	private int launchInterver = 1; // 射击间隔定时器
	private int garbageRecoveryInterver = 1000; // 垃圾清理定时器
	private long heroVirtualStart = 0; // 英雄虚化状态的开始时间
	private int heroAttSpeed = 0;// 英雄的攻击速度
	private int score = 0; // 得分

	private Random random = new Random();
	private JFrame frame; // 窗口框架
	private JMenuBar bar; // 菜单
	private JMenu game; // 菜单项
	private JMenuItem[] items; // 菜单下拉项
	private JTextArea textArea; // 右边框

	// 初始化背景图在类加载的时候执行一次
	static {
		try {
			backgroundImg = ImageIO.read(PlayMain.class.getResource("../img/backgrand/back2.jpg"));
			pushImg = ImageIO.read(PlayMain.class.getResource("../img/status/push.png"));
			gameOverImg = ImageIO.read(PlayMain.class.getResource("../img/status/gameOver.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public PlayMain() throws IOException {
		init();
	}

	/**
	 * 初始化所有数据
	 * @throws IOException
	 */
	public void init() throws IOException {
		// 初始化英雄机
		initHeroPlan();

		// 初始化UI
		initUI();

		// 游戏状态为启动
		gameStatus = GameStatus.Runing;
		
		bullets = new ArrayList<Bullet>();
		
		flyObjList = new ArrayList<FlyingObject>();
		
		score = 0;
	}

	/**
	 * 初始化UI组件
	 */
	public void initUI() {
		frame = new JFrame("飞机大战怀旧版"); // 实现窗口框架
		textArea = new JTextArea();
		bar = new JMenuBar(); // 建立菜单栏
		game = new JMenu("菜单"); // 建立名为“游戏”的菜单
		items = new JMenuItem[2]; // game菜单下创建2个子菜单项
		game.add(items[0] = new JMenuItem("重新开始"));// 第一个子菜单为“重新开始”
		game.add(items[1] = new JMenuItem("退出")); // 第二个子菜单为“退出”
	}

	/**
	 * 初始化英雄机参数
	 * @throws IOException
	 */
	public void initHeroPlan() throws IOException {
		hero = new ZeroRedHero();
		heroImgWidth = hero.getImgWidth();
		heroImgHeight = hero.getImgHeight();
		heroAttSpeed = hero.getAttackSpeed();
	}

	/**
	 * 画
	 */
	@Override
	public void paint(Graphics g) {
		try {
			drawMainView(g); // 画主界面
			drawHero(g); // 画英雄机
			drawBullet(g); // 画子弹
			drawFlyObject(g); // 画飞行物
			drawScore(g); // 画分数
			drawGameStatus(g);//画状态
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void drawMainView(Graphics g) throws IOException {
		g.drawImage(backgroundImg, 0, 0, null);
	}
	
	public void drawGameStatus(Graphics g){
		
		if(gameStatus == GameStatus.End){
			g.drawImage(gameOverImg, 0, 0, null);
		}
		
		if(gameStatus == GameStatus.Suspend){
			g.drawImage(pushImg, 0, 0, null);
		}
	}
	

	public void drawHero(Graphics g) {

		// 判断是否是虚化状态
		boolean commen = true;

		if (heroStatus != HeroStatus.Common) {

			// 判断是否是虚化状态
			if (heroStatus == HeroStatus.Virtual) {
				//判断时间有没有超过三秒
				if (System.currentTimeMillis() - heroVirtualStart < 3000) {
					hero.setImg("../img/hero/ship-Virtual.png");
					commen = false;
				}
			}

			// 判断是否是无敌状态
			if (heroStatus == HeroStatus.Virtual) {
				if (System.currentTimeMillis() - heroVirtualStart < 3000) {
					hero.setImg("../img/hero/ship-Virtual.png");
					commen = false;
				}
			}
		}

		
		if (commen) {
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

	public void drawFlyObject(Graphics g) {
		FlyingObject flyObj;
		for (int i = 0; i < flyObjList.size(); i++) {
			flyObj = flyObjList.get(i);
			g.drawImage(flyObj.getImg(), flyObj.getX(), flyObj.getY(), null);
		}
	}

	public void drawScore(Graphics g) {
		int x = 10; // x坐标
		int y = 25; // y坐标
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 16); // 字体
		g.setColor(Color.RED);
		g.setFont(font); // 设置字体
		g.drawString("得分:" + score, x, y += 20); // 画分数
		g.drawString("生命:" + hero.getLife(), x, y += 20); // 画命
		g.drawString("攻击速度 :" + heroAttSpeed , x, y += 20);
		g.drawString("火力值 :" + hero.getFirepower(), x, y += 20);
	}

	public void startFrame() {
		// 鼠标移动事件
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();

				textArea.setText(null);
				textArea.append("PX: ( X:" + x + ", Y:" + y + ")\n");

				hero.setX(x - heroImgWidth / 2);
				hero.setY(y - heroImgHeight / 2);

				repaint();
			}

			@Override
			public void mouseDragged(MouseEvent e) {

			}

		});
		
		//鼠标移入移出事件
		addMouseListener(new MouseAdapter() {
			
			@Override
            public void mouseEntered(MouseEvent e) { // 鼠标进入
				if(gameStatus != GameStatus.End){
					gameStatus = GameStatus.Runing;
				}
            }

            @Override
            public void mouseExited(MouseEvent e) { // 鼠标退出
            	if(gameStatus != GameStatus.End){
            		gameStatus = GameStatus.Suspend;
            	}
            }
		});

		// 点关闭按钮事件
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				String str = "是否要退出游戏?";
				// 添加消息对话框
				if (JOptionPane.showConfirmDialog(null, str, "退出游戏",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					System.exit(0); // 退出
				}
			}
		});

		// 点击重新开始事件
		items[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = "是否要重新开始游戏?";
				// 添加消息对话框
				if (JOptionPane.showConfirmDialog(null, str, "重新开始",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					try {
						init();
					} catch (IOException e1) {
						e1.printStackTrace();
					} 
				}
			}
		});

		// 点击退出事件
		items[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = "是否要退出游戏?";
				// 添加消息对话框
				if (JOptionPane.showConfirmDialog(null, str, "退出游戏",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					System.exit(0); // 退出
				}
			}
		});

		// 界面运动定时器
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				nextStep(); // 开始运动
				randomEnemyPlany(); // 生成敌机
				heroShooting(); // 英雄射击
				garbageRecovery(); // 垃圾清理
				duangGG();// 子弹的碰撞检查
				isGameOver();// 检查游戏是否结束
				repaint(); // 重绘，调用paint()方法
			}

		}, intervel, intervel);

		this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT)); // 设置游戏界面的宽高

		// 设置右边文本框
		textArea.setEditable(false); // 不可编辑
		JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(200, GAME_HEIGHT));
		scrollPane.setVisible(true);

		frame.setLayout(new BorderLayout());// 设置布局方式为流式布局
		frame.add(this, BorderLayout.WEST);
		frame.add(scrollPane, BorderLayout.EAST);
		bar.add(game);
		frame.setJMenuBar(bar);
		frame.pack();
		frame.setLocation(0, 0); // 电脑窗口的定位
		frame.setResizable(false);// 设置此窗口是否可由用户调整大小
		frame.setVisible(true); // 设置窗口显示状态
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	/**
	 * 随机生成飞行物
	 */
	public void randomEnemyPlany() {
		if(gameStatus != GameStatus.Runing) return;
		
		if (enemyPlanInterver % 50 == 0) {
			int ran = random.nextInt(20);
			if (ran == 1) {
				FlyingObject prize = new LifePrize();
				prize.setX(random.nextInt(GAME_WIDTH - prize.getImgWidth()));
				prize.setY(0);
				flyObjList.add(prize);
			} else if (ran == 2) {
				FlyingObject prize = new FirePrize();
				prize.setX(random.nextInt(GAME_WIDTH - prize.getImgWidth()));
				prize.setY(0);
				flyObjList.add(prize);
			} else if (ran > 17) {
				EnemyThree eplane = new EnemyThree();
				eplane.setX(random.nextInt(GAME_WIDTH - eplane.getImgWidth()));
				eplane.setY(0);
				flyObjList.add(eplane);
			} else {
				CommonEnemy eplane = new CommonEnemy();
				eplane.setX(random.nextInt(GAME_WIDTH - eplane.getImgWidth()));
				eplane.setY(0);
				flyObjList.add(eplane);
			}
		}
		enemyPlanInterver++;
	}

	/**
	 * 运动一步
	 */
	public void nextStep() {
		if(gameStatus != GameStatus.Runing) return;
		
		// 子弹运动
		for (int i = 0; i < bullets.size(); i++) bullets.get(i).step();

		// 飞行物运动
		for (int i = 0; i < flyObjList.size(); i++) flyObjList.get(i).step();
	}

	/**
	 * 射击
	 */
	public void heroShooting() {
		if(gameStatus != GameStatus.Runing) return;
		/**
		 * 英雄机射击
		 */
		if (launchInterver % ((1000 / PlayMain.intervel) / heroAttSpeed) == 0) {

			bullets.addAll(hero.launch());
		}

		/**
		 * 敌机射击
		 */
		Enemy enemy;

		for (int i = 0; i < flyObjList.size(); i++) {
			if (flyObjList.get(i) instanceof Enemy) {
				enemy = (Enemy) flyObjList.get(i);
				if (launchInterver % ((1000 / PlayMain.intervel) / enemy.getAttackSpeed()) == 0) {
					bullets.addAll(enemy.launch());
				}
			}
		}

		launchInterver++;
	}

	/**
	 * 移除越界的飞行物和子弹
	 */
	public void garbageRecovery() {

		if (garbageRecoveryInterver % (1000 / intervel) == 0) {
			ArrayList<Bullet> bulletsBak = new ArrayList<Bullet>();
			ArrayList<FlyingObject> flyObjListBak = new ArrayList<FlyingObject>();

			// 遍历所有子弹
			Bullet bullet;
			for (int i = 0; i < bullets.size(); i++) {
				bullet = bullets.get(i);
				if (!bullet.isTransboundary())
					bulletsBak.add(bullet);
			}

			// 遍历所有飞行物
			FlyingObject flyObj;
			for (int i = 0; i < flyObjList.size(); i++) {
				flyObj = flyObjList.get(i);
				if (!flyObj.isTransboundary())
					flyObjListBak.add(flyObj);
			}

			// 替换集合
			bullets = bulletsBak;
			flyObjList = flyObjListBak;
		}

		garbageRecoveryInterver++;
	}
	
	/**
	 * 检查游戏是否结束
	 */
	public void isGameOver(){
		
		if(collision())
			gameStatus = GameStatus.End;
		
	}
	
	
	/**
	 * 英雄机碰撞检测
	 * 如果命数为
	 * @return
	 */
	public boolean collision() {
		/**
		 * 如果是普通状态则进行碰撞检测，碰撞检测成功后,检查命数 如果命数不为0则进入虚化状态，如果为0则游戏结束
		 */
		if (heroStatus == HeroStatus.Common) {
			//与敌机和奖励的碰撞检查
			for (int i = 0; i < flyObjList.size(); i++) {
				if (flyObjList.get(i).inspectCollision(hero)) {
					if (flyObjList.get(i) instanceof Prize) {
						Prize prize = (Prize) flyObjList.get(i);
						setPrize(prize); // 设置奖励
						
						// 移除飞行物
						removeEnemy(i);
					} else {
						if (hero.getLife() == 1) {
							return true;
						} else {
							Plane enemy = (Plane) flyObjList.get(i);
							hero.setLife(hero.getLife() - enemy.getBullet().getHurt());
							heroStatus = HeroStatus.Virtual;
							heroVirtualStart = System.currentTimeMillis();
						}
					}
				}
			}
			
			//与子弹的碰撞检查
			for (int i = 0; i < bullets.size(); i++) {
				if(!bullets.get(i).getType()){
					if (bullets.get(i).inspectCollision(hero)) {
						if (hero.getLife() == 1) {
							return true;
						} else {
							hero.setLife(hero.getLife() - bullets.get(i).getHurt());
							heroStatus = HeroStatus.Virtual;
							heroVirtualStart = System.currentTimeMillis();
							removeBullet(i);
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * 子弹与敌机的碰撞检查
	 */
	public void duangGG() {
		Bullet bullet;
		Enemy enemy;
		Prize prize;
		// ArrayList<FlyingObject> flyObjBak;
		// ArrayList<Bullet> bulletsBak;

		if (!bullets.isEmpty()) {
			for (int i = 0; i < bullets.size(); i++) {
				bullet = bullets.get(i);
				if (!flyObjList.isEmpty()) {
					flyObj: for (int j = 0; j < flyObjList.size(); j++) {

						// 如果子弹是英雄机发出
						if (bullet.getType()) {

							if (bullet.inspectCollision(flyObjList.get(j))) {
								if (flyObjList.get(j) instanceof Prize) {// 判断敌机是伤害敌机还是奖励
									/**
									 * 获取奖励内容 设置对应奖励
									 */
									prize = (Prize) flyObjList.get(j);
									setPrize(prize);// 设置奖励
									removeEnemy(j);
								} else {
									/**
									 * 用子弹的伤害值与敌机生命值相减 如果敌机生命值为0，删除敌机
									 */
									enemy = (Enemy) flyObjList.get(j);
									enemy.setLife(enemy.getLife() - bullet.getHurt());
									if (enemy.getLife() <= 0) {
										removeEnemy(j);
										score += enemy.getScope();
									}
								}

								// 移除子弹
								removeBullet(i);
								break flyObj;
							}
						}

					}
				}

			}
		}
	}

	/**
	 * 设置对应奖励
	 */
	public void setPrize(Prize prize) {

		if (prize.getCategory() == PrizeCategory.Life) {
			hero.setLife(hero.getLife() + 1);
		}

		if (prize.getCategory() == PrizeCategory.Fire) {
			hero.setFirepower(hero.getFirepower() + 1);
		}

		if (prize.getCategory() == PrizeCategory.Attackspeed) {

		}

		if (prize.getCategory() == PrizeCategory.Invincible) {

		}

	}

	/**
	 * 移除飞行物
	 */
	public void removeEnemy(int index) {
		ArrayList<FlyingObject> listBak = new ArrayList<>();

		flyObjList.set(index, flyObjList.get(flyObjList.size() - 1));

		for (int i = 0; i < flyObjList.size() - 1; i++)
			listBak.add(flyObjList.get(i));

		flyObjList = listBak;
	}
	
	/**
	 * 移除子弹
	 */
	public void removeBullet(int index){
		ArrayList<Bullet> listBak = new ArrayList<>();

		bullets.set(index, bullets.get(bullets.size() - 1));

		for (int i = 0; i < bullets.size() - 1; i++)
			listBak.add(bullets.get(i));

		bullets = listBak;
	}


	public static void main(String[] args) throws IOException {
		PlayMain plMain = new PlayMain();
		plMain.startFrame();
	}

}
