package com.play.core;
import java.util.*;


import com.play.model.Point;
/**
 * <b>贪吃蛇游戏逻辑核心类</b>
 * 
 * @author 威 
 * <br>2018年5月16日 下午4:55:01 
 * @see
 * @since 1.0
 */
public class Core {
	//方向常量
	public static final String LOCAT_UP 	= "up";
	public static final String LOCAT_DOWM 	= "dowm";
	public static final String LOCAT_RIGTH 	= "right";
	public static final String LOCAT_LEFT	= "left";
	//反方向map
	Map<String, String> map 				= new HashMap<String, String>();
	//可以表示当前的方向，相对于新的方向可以表示前一方向
	private String prevLocat 				= LOCAT_RIGTH;
	//上一步操作完为true
	public boolean allowFlag 				= true;
	//游戏结束为ture 
	public boolean over						= false;
	//每一格的宽
	private int pWidth;
	//窗口的大小
	private int winWidth;
	private int winHeight;
	/**
	 * 食点
	 */
	private Point foodPoint;
	/**
	 * 蛇实体类
	 */
	private Snake snake;
	
	public Core(){
		this(100, 100, 2);
	}
	//获取结束布尔值
	public boolean getOver(){
		return over;
	}
	//设置结束布尔值
	public void setOver(boolean over){
		this.over = over;
	}
	/**
	 * 一句话描述该构造方法
	 * @param winWidth		这个不是实际宽度		
	 * @param winHeight		这个不是实际高度		
	 * @param pWidth		这是每一格的宽
	 */
	public Core(int winWidth, int winHeight, int pWidth){
		if(pWidth <= 0)
			throw new IllegalArgumentException("每一格的宽不能为负数或者0：pWidth=" + pWidth);
		snake = new Snake(pWidth);
		this.pWidth = pWidth;
		this.winWidth = winWidth;
		this.winHeight = winHeight;
		foodPoint = ranP ();
		map.put(LOCAT_UP, LOCAT_DOWM);
		map.put(LOCAT_DOWM, LOCAT_UP);
		map.put(LOCAT_RIGTH, LOCAT_LEFT);
		map.put(LOCAT_LEFT, LOCAT_RIGTH);
	}
	/**
	 * 获取窗口的实际宽度
	 * <p>	 
	 * @return
	 * int
	 * @see
	 * @since 1.0
	 */
	public int getWidth(){
		return this.winWidth*this.pWidth;
	}
	/**
	 * 获取窗口的实际高度
	 * <p>	 
	 * @return
	 * int
	 * @see
	 * @since 1.0
	 */
	public int getHeight(){
		return this.winHeight*this.pWidth;
	}
	
	/**
	 * 智能吃子模式
	 * <p>	 
	 * void
	 * @see
	 * @since 1.0
	 */
	public void intelligencePattern(){
		allowFlag = true;
		Point headPoint = snake.getHead();
		if(headPoint.x < foodPoint.x && headPoint.y < foodPoint.y){
			switch(prevLocat){
				case LOCAT_UP : 	prevLocat = getRightLocat(LOCAT_RIGTH); break;
				case LOCAT_LEFT : 	prevLocat = getRightLocat(LOCAT_DOWM);  break;
			}
		}else if(headPoint.x > foodPoint.x && headPoint.y > foodPoint.y){
			switch(prevLocat){
				case LOCAT_DOWM : 	prevLocat = getRightLocat(LOCAT_RIGTH); break;
				case LOCAT_RIGTH : 	prevLocat = getRightLocat(LOCAT_DOWM);  break;	
			}
		}else if(headPoint.x > foodPoint.x && headPoint.y < foodPoint.y){
			switch(prevLocat){
				case LOCAT_UP : 	prevLocat = getRightLocat(LOCAT_RIGTH); break;
				case LOCAT_RIGTH : 	prevLocat = getRightLocat(LOCAT_DOWM);  break;	
			}
		}else if(headPoint.x < foodPoint.x && headPoint.y > foodPoint.y){
			switch(prevLocat){
				case LOCAT_DOWM : 	prevLocat = getRightLocat(LOCAT_LEFT); break;
				case LOCAT_LEFT : 	prevLocat = getRightLocat(LOCAT_UP);   break;	
			}
		}else if(headPoint.x == foodPoint.x){
			if(headPoint.y < foodPoint.y){
				if(prevLocat.equals(LOCAT_UP))
					prevLocat = isRightLocat(LOCAT_RIGTH) ? LOCAT_RIGTH : prevLocat;
				else
					prevLocat = isRightLocat(LOCAT_DOWM) ? LOCAT_DOWM : prevLocat;
			}else {
				if(prevLocat.equals(LOCAT_DOWM))
					prevLocat = isRightLocat(LOCAT_RIGTH) ? LOCAT_RIGTH : prevLocat;
				else
					prevLocat = isRightLocat(LOCAT_UP) ? LOCAT_UP : prevLocat;
			}
		}else if(headPoint.y == foodPoint.y){
			if(headPoint.x < foodPoint.x){
				if(prevLocat.equals(LOCAT_LEFT))
					prevLocat = isRightLocat(LOCAT_UP) ? LOCAT_UP : prevLocat;
				else
					prevLocat = isRightLocat(LOCAT_RIGTH) ? LOCAT_RIGTH : prevLocat;
			}else {
				if(prevLocat.equals(LOCAT_RIGTH))
					prevLocat = isRightLocat(LOCAT_UP) ? LOCAT_UP : prevLocat;
				else
					prevLocat = isRightLocat(LOCAT_LEFT) ? LOCAT_LEFT : prevLocat;
			}
		}
		paintCore ();
	}
	/**
	 * 人模式
	 * <p>	 
	 * void
	 * @see
	 * @since 1.0
	 */
	public void artificialPattern(){
		allowFlag = true;
		paintCore ();
	}
	
	//获取子坐标
	public Point getFoodPoint(){
		return foodPoint;
	}
	
	/**
	 * 所有需要画点的数据
	 * <p>	
	 * 重画前的准备
	 * 确认身体数据 
	 * 确认子数据 
	 * void
	 * @since 1.0
	 */
	public void paintCore (){
		Point currentPoint = null;
		//currentPoint = new Point(headPoint.x+pWidth, headPoint.y);
		currentPoint = getNextPoint(snake.getHead(), prevLocat);		
		if(currentPoint == null)
			throw new NullPointerException();
		if(currentPoint.equals(foodPoint)){
			snake.add();
			foodPoint = ranP ();
		}
		for(Point p : snake.getData())
			if(currentPoint.equals(p))
				over = true;
		if(!over) snake.move(currentPoint);
	}
	
	/**
	 * 获取所有要画的点的数据
	 * <p>	 
	 * @return
	 * List<Point>
	 * @see	com.play.core.Core.snake
	 * @since 1.0
	 */
	public List<Point> getData(){
		List<Point> data = new ArrayList<Point>();
		data.addAll(snake.getData());
		data.add(foodPoint);
		return data;
	}
	/**
	 * 按键
	 * <p>	 
	 * @param locat
	 * void
	 * @see
	 * @since 1.0
	 */
	public void keyPress (String locat) {
		if(locat.equals(LOCAT_LEFT)){
			if(!prevLocat.equals(LOCAT_RIGTH) && allowFlag){
				allowFlag = false;
				prevLocat = LOCAT_LEFT;
			}
		}
		else if (locat.equals(LOCAT_RIGTH)) {
			if(!prevLocat.equals(LOCAT_LEFT) && allowFlag){
				allowFlag = false;
				prevLocat = LOCAT_RIGTH;
			}
		}
		else if (locat.equals(LOCAT_UP)) {
			if(!prevLocat.equals(LOCAT_DOWM) && allowFlag)	{
				allowFlag = false;
				prevLocat = LOCAT_UP;
			}
		}
		else if (locat.equals(LOCAT_DOWM)) {
			if(!prevLocat.equals(LOCAT_UP) && allowFlag){
				allowFlag = false;
				prevLocat = LOCAT_DOWM;
			}
		}
	}
	/**
	 * 获取结束游戏分数
	 * <p>	 
	 * @return
	 * int
	 * @see com.play.core.Core.snake
	 * @since 1.0
	 */
	public int getScore(){
		return snake.getScore();
	}
	/**
	 * 以字符串的形式输出所有点
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		String str = "{";
		for(Point p : getData())
			str += p.toString()+" ";
		return str.substring(0, str.length()-1) + "}";
	}
	/**
	 * 根据头坐标获取下一个坐标
	 * <p>	 
	 * @param headPoint	头坐标
	 * @param locat		下一个方向
	 * @return
	 * Point
	 * @since 1.0
	 */
	protected Point getNextPoint(Point headPoint, String locat) {
		Point currentPoint;
		int x = 0, y = 0; 		
		if(locat.equals(LOCAT_UP)){
			x = headPoint.x;
			y = headPoint.y == 0 
				? this.winHeight*pWidth-pWidth 
				: headPoint.y-pWidth;//撞上墙--穿过到下
		}
		else if(locat.equals(LOCAT_DOWM)){
			x = headPoint.x;
			y = headPoint.y == this.winHeight*pWidth 
				? 0 
				: headPoint.y+pWidth;//撞下墙--穿过到下
		}
		else if(locat.equals(LOCAT_RIGTH)){
			y = headPoint.y;
			x = headPoint.x == this.winWidth*pWidth 
				? 0 
				: headPoint.x+pWidth;
		}
		else if(locat.equals(LOCAT_LEFT)){
			y = headPoint.y;
			x = headPoint.x == 0 
				? this.winWidth*pWidth-pWidth
				: headPoint.x-pWidth;
		}	
		currentPoint = new Point(x, y);
		return currentPoint;
	}
	/**
	 * 获取随机点
	 * <p>	 
	 * @return
	 * Point
	 * @see
	 * @since 1.0
	 */
	protected Point ranP (){
		int x = (int) (Math.random()*this.winWidth)*pWidth;
		int y = (int) (Math.random()*this.winHeight)*pWidth;
		Point randomPoint = new Point(x, y);
		for(Point point : snake.getData()){
			if(randomPoint.equals(point))
				return ranP ();	
		}
		return randomPoint;
	}
	/**
	 * 判断正确方向
	 * <p>	 
	 * @param wantLocat		预计会转向的方向
	 * @return
	 * String		转向有误则返方向转
	 * @see
	 * @since 1.0
	 */
	protected String getRightLocat(String wantLocat){
		Point temp = getNextPoint(snake.getHead(), wantLocat);
		for(Point p : snake.getData())
			if(temp.equals(p))
				return wantLocat;
		return map.get(wantLocat);
	}
	/**
	 * 判断下一步是否会碰到自身
	 * <p>	 
	 * @param wantLocat 	预计会转向的方向
	 * @return
	 * boolean
	 * @see
	 * @since 1.0
	 */
	protected boolean isRightLocat(String wantLocat){
		Point temp = getNextPoint(snake.getHead(), wantLocat);
		for(Point p : snake.getData())
			if(temp.equals(p))
				return false;
		return true;
	}
}