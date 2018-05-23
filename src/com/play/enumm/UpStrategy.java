package com.play.enumm;

public enum UpStrategy {
	Left{
		public String getValue(){
			return com.play.core.Core.LOCAT_LEFT;
		}
	}, Right{
		public String getValue(){
			return com.play.core.Core.LOCAT_RIGTH;
		}
	};
	public String getValue(){
		return null;
	}
}
