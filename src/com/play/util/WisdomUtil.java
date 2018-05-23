package com.play.util;

import java.util.List;

import com.play.core.Core;
import com.play.core.Snake;
import com.play.enumm.DownStrategy;
import com.play.enumm.LeftStrategy;
import com.play.enumm.RightStrategy;
import com.play.enumm.UpStrategy;
import com.play.model.Point;

public class WisdomUtil {
	private String secoLoca; //一个将就的方向
	private int pWidth;
	private int winHeight;
	private int winWidth;
	public void init(int winWidth, int winHeight, int pWidth){
		this.winWidth 	= winWidth;
		this.winHeight 	= winHeight;
		this.pWidth 	= pWidth;
	}
	public String wisdom(String origLocaltion, Snake snake, Point food){
		String isRightLocat = wisdom1(origLocaltion, snake, food);
		if(isRightLocat == null) isRightLocat = wisdom2(origLocaltion, snake, food);
		return isRightLocat == null ? secoLoca : isRightLocat;
	}
	//初始方向
	public String wisdom1(String origLocaltion, Snake snake, Point food){
		if(isSafa(origLocaltion, snake)){
			secoLoca = origLocaltion;
			System.out.print("安全");
			if(isComing(origLocaltion, snake, food)){
				System.out.print("靠近；");
				return origLocaltion;
			}
			System.out.print("不靠近；");
		}System.out.print("初始方向："+origLocaltion+"，换方向；");
		return null;
	}
	//其他方向
	public String wisdom2(String origLocaltion, Snake snake, Point food){
		System.out.println("判断其他方向！");
		String isRight = null;
		switch(origLocaltion){
			case Core.LOCAT_UP : 
				for(UpStrategy enumType : UpStrategy.values()){
					isRight = wisdom1(enumType.getValue(), snake, food);
					if(isRight != null) return isRight;
				}
				break;
			case Core.LOCAT_DOWM : 
				for(DownStrategy enumType : DownStrategy.values()){
					isRight = wisdom1(enumType.getValue(), snake, food);
					if(isRight != null) return isRight;
				}
				break;
			case Core.LOCAT_RIGTH : 
				for(RightStrategy enumType : RightStrategy.values()){
					isRight = wisdom1(enumType.getValue(), snake, food);
					if(isRight != null) return isRight;
				}
				break;
			case Core.LOCAT_LEFT : 
				for(LeftStrategy enumType : LeftStrategy.values()){
					isRight = wisdom1(enumType.getValue(), snake, food);
					if(isRight != null) return isRight;
				}
				break;
		}
		return isRight;
	}
	public boolean isSafa(String origLocaltion, Snake snake){
		Point nextPoint = getNextPoint(origLocaltion, snake.getHead());
		int safaVal = 0, dataLen = snake.getData().size();
		List<Point> points = snake.getData();
		for(int i = 0; i < dataLen; i++){
			Point p = points.get(i);
			int num = nextPoint.compareTo(p);
			if(num == 0){
				safaVal = getMarginTargetVal(origLocaltion, p, nextPoint);
				if(safaVal < dataLen - i) return false;
			}else if(num == 1) return false;
		}
		return true;
	}
	
	public boolean isComing(String origLocaltion, Snake snake, Point food){
		int origVal = getMarginTargetVal2(origLocaltion, snake.getHead(), food);
		int nextVal = getMarginTargetVal2(origLocaltion,
				getNextPoint(origLocaltion, snake.getHead()),
				food);
		return nextVal >= origVal ? false : true;
	}
	public int getMarginTargetVal2(String locat, Point orig, Point next){
		int val = 0;
		switch(locat){
			case Core.LOCAT_LEFT : case Core.LOCAT_RIGTH :
				val = next.x - orig.x;
				break;
			case Core.LOCAT_UP : case Core.LOCAT_DOWM :
				val = next.y - orig.y;
				break;
		}
		if(val < 0)
			val = Math.abs(val);
		return val;
	}
	/**
	 * 在某一方向上与目标距离值
	 * <p>	 
	 * @return
	 * int
	 * @see
	 * @since 1.0
	 */
	public int getMarginTargetVal(String locat, Point orig, Point next){
		int val = 0;
		switch(locat){
			case Core.LOCAT_LEFT :
				if(next.x > orig.x)
					val = next.x - orig.x;
				else 
					val = (winWidth*pWidth-next.x) + (winWidth*pWidth-orig.x);
				break;
			case Core.LOCAT_RIGTH :
				if(next.x > orig.x)
					val = (winWidth*pWidth-next.x) + (winWidth*pWidth-orig.x);
				else 
					val = next.x - orig.x;
				break;
			case Core.LOCAT_UP : 
				if(next.y > orig.y)
					val = next.y - orig.y;
				else 
					val = (winHeight*pWidth-next.y) + (winHeight*pWidth-orig.y);
				break;
			case Core.LOCAT_DOWM :
				if(next.y > orig.y)
					val = (winHeight*pWidth-next.y) + (winHeight*pWidth-orig.y);
				else 
					val = next.y - orig.y;
				break;
		}
		if(val < 0)
			val = Math.abs(val);
		return val;
	}
	
	protected Point getNextPoint(String locat, Point headPoint) {
		Point currentPoint;
		int x = 0, y = 0; 		
		if(locat.equals(Core.LOCAT_UP)){
			x = headPoint.x;
			y = headPoint.y == 0 
				? this.winHeight*pWidth-pWidth 
				: headPoint.y-pWidth;//撞上墙--穿过到下
		}
		else if(locat.equals(Core.LOCAT_DOWM)){
			x = headPoint.x;
			y = headPoint.y == this.winHeight*pWidth 
				? 0 
				: headPoint.y+pWidth;//撞下墙--穿过到下
		}
		else if(locat.equals(Core.LOCAT_RIGTH)){
			y = headPoint.y;
			x = headPoint.x == this.winWidth*pWidth 
				? 0 
				: headPoint.x+pWidth;
		}
		else if(locat.equals(Core.LOCAT_LEFT)){
			y = headPoint.y;
			x = headPoint.x == 0 
				? this.winWidth*pWidth-pWidth
				: headPoint.x-pWidth;
		}	
		currentPoint = new Point(x, y);
		return currentPoint;
	}
}
