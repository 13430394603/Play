package com.play.core;
import java.util.*;

import com.play.model.Point;
/**
 * <b>蛇实体类</b>
 * <p>
 * 描述:<br>
 * 
 * @author 威 
 * <br>2018年5月16日 下午4:55:32 
 * @see
 * @since 1.0
 */
public class Snake {
	private int score			= 0;	//分数
	private List<Point> snakePoints;	//蛇身体所有点数据
	private Point prevPoint;			//上一个尾点Point对象
	public Snake(int pWidth){
		snakePoints = new ArrayList<Point>();
		Point p1 = new Point(0*pWidth, 0);
		Point p2 = new Point(1*pWidth, 0);
		Point p3 = new Point(2*pWidth, 0);
		snakePoints.add(p3);
		snakePoints.add(p2);
		snakePoints.add(p1);
		prevPoint = new Point(p1.x, p1.y);
	}
	/**
	 * 吃子尾自增
	 * <p>	 
	 * void
	 * @see
	 * @since 1.0
	 */
	public void add () {
		score++;	
		snakePoints.add(prevPoint);
	}
	/**
	 * 移动
	 * <p>	 
	 * @param 最新前进点坐标
	 * void
	 * @see
	 * @since 1.0
	 */
	public void move (Point p){
		prevPoint = snakePoints.get(snakePoints.size()-1);
		snakePoints.add(0, p);	
		snakePoints.remove(snakePoints.size()-1);
	}
	/**
	 * 获取蛇身坐标数据	
	 * <p>	 
	 * @return
	 * List<Point>
	 * @see
	 * @since 1.0
	 */
	public List<Point> getData (){
		return snakePoints;
	}  
	/**
	 * 获取头部坐标数据
	 * <p>	 
	 * @return
	 * Point
	 * @see
	 * @since 1.0
	 */
	public Point getHead (){
		return snakePoints.get(0);
	}
	/**
	 * 获取分数
	 * <p>	 
	 * @return
	 * int
	 * @see
	 * @since 1.0
	 */
	public int getScore(){
		return score;
	}
}