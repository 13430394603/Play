package com.play.enumm;

public enum DownStrategy {
	Right{
		public String getValue(){
			return com.play.core.Core.LOCAT_RIGTH;
		}
	}, Left{
		public String getValue(){
			return com.play.core.Core.LOCAT_LEFT;
		}
	};
	public String getValue(){
		return null;
	}
}
