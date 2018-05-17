package com.play.frame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.awt.annotation.Control;
import com.awt.control.AbstractControl;
import com.play.core.Core;
import com.play.model.Point;
/**
 * <b>贪吃蛇游戏界面</b>
 * <p>
 * 描述:<br>
 * @author 威 
 * <br>2018年5月16日 下午3:58:24 
 * @see
 * @since 1.0
 */
@Control
public class PlayFrame extends AbstractControl{
	private Thread currentPlay;
	private Core c;
	private PlayWin panel;
	private JLabel show;
	boolean canClick;
	JLayeredPane jLayeredPane;
	public PlayFrame(){
	}
	@Override
	protected void execute() {
		c = new Core(120, 70, 4);
		panel = new PlayWin();//自定义动画类
		//获取容器对象
		jLayeredPane = (JLayeredPane) getComponentByName("mainFrame&Layer");
		show =  (JLabel) getComponentByName("showInfor");
		if(jLayeredPane == null)
			throw new NullPointerException();
		panel.setSize(c.getWidth(), c.getHeight());
		panel.setBorder(BorderFactory.createLineBorder(java.awt.Color.CYAN, 1));
		panel.setLocation(0, 38);
		panel.setData(c.getData());
		jLayeredPane.add(panel, JLayeredPane.DRAG_LAYER);
		
		panel.requestFocus();//请求焦点，否则监听无效
		panel.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == 40){
					c.keyPress(Core.LOCAT_DOWM);
				}
				else if(e.getKeyCode() == 38){
					c.keyPress(Core.LOCAT_UP);
				}
				else if(e.getKeyCode() == 37){
					c.keyPress(Core.LOCAT_LEFT);
				}
				else if(e.getKeyCode() == 39){
					c.keyPress(Core.LOCAT_RIGTH);
				}
			}
		});
	}
	//点击事件
	public void personClick(MouseEvent e){
		if(currentPlay != null) currentPlay.interrupt();
		c = new Core(120, 70, 4);
		currentPlay = new PersonThread(panel, c, show);
		currentPlay.start();
	}
	public void wisdomClick(MouseEvent e){
		if(currentPlay != null) currentPlay.interrupt();
		c = new Core(120, 70, 4);
		currentPlay = new WisdomThread(panel, c, show);
		currentPlay.start();
	}
}
/**
 * <b>开启线程处理游戏逻辑和画动画</b>
 * <p>
 * @author 威 
 * <br>2018年5月16日 下午3:58:19 
 * @see
 * @since 1.0
 */
class WisdomThread extends Thread{
	PlayWin panel;
	Core c;
	JLabel show;
	public boolean exit = true;
	public WisdomThread(PlayWin panel, Core c, JLabel show){
		this.panel = panel;
		this.c = c;
		this.show = show;
	}
	public void run(){
		while(!c.getOver() && exit){
			c.intelligencePattern();
			panel.setData(c.getData());
			show.setText("正在进行AI模式：" + c.getScore());
			panel.repaint();
			try{
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		show.setText("游戏结束！得分为：" + c.getScore());
	}
	@Override
	public void interrupt(){
		exit = false;
	}
}
class PersonThread extends Thread{
	PlayWin panel;
	Core c;
	JLabel show;
	public boolean exit = true;
	public PersonThread(PlayWin panel, Core c, JLabel show){
		this.panel = panel;
		this.c = c;
		this.show = show;
	}
	public void run(){
		while(!c.getOver() && exit){
			c.artificialPattern();
			panel.setData(c.getData());
			show.setText("正在进行游戏：" + c.getScore());
			panel.repaint();
			try{
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		show.setText("游戏结束！得分为：" + c.getScore());
	}
	@Override
	public void interrupt(){
		exit = false;
	}
}
/**
 * <b>自定义动画类</b>
 * <p>
 * 描述:<br>
 * setData 设置坐标数据
 * @author 威 
 * <br>2018年5月16日 下午3:59:10 
 * @see
 * @since 1.0
 */
class PlayWin extends JPanel{
	private static final long serialVersionUID = 1L;
	private List<Point> data;
	public PlayWin(){
		this.setLayout(null);
		this.setVisible(true);
	}
	public void setData(List<Point> data){
		this.data = data;
	}
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		if(data != null){
			g2d.setColor(java.awt.Color.WHITE);
			g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
			g2d.setColor(java.awt.Color.GRAY);
			for(Point p : data){
				g2d.fillRect(p.x, p.y, 4, 4);
			}
		}
	}
	
	
}
