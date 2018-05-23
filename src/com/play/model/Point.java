package com.play.model;
/**
 * <b>坐标实体类</b>
 * @author 威 
 * <br>2018年5月16日 下午4:57:37 
 * @since 1.0
 */
public class Point implements Comparable{
	public int x;
	public int y;
	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	/**
	 * 约定坐标的位置相等放回ture
	 * <p>	 
	 * @param p
	 * @return
	 * boolean
	 * @see
	 * @since 1.0
	 */
	public boolean equals (Point p) {
		if(p.x == this.x && p.y == this.y)
			return true;
		return false;
	}
	public String toString(){
		return "("+x+", "+y+")";
	}
	@Override
	public int compareTo(Object o) {
		if(((Point) o).x == this.x && ((Point) o).y == this.y)
			return 1;
		if(((Point) o).x == this.x || ((Point) o).y == this.y)
			return 0;
		return -1;
	}
}