package com.play.enumm;

public enum RightStrategy {
	Top{
		public String getValue(){
			return com.play.core.Core.LOCAT_UP;
		}
	}, Bom{
		public String getValue(){
			return com.play.core.Core.LOCAT_DOWM;
		}
	};
	public String getValue(){
		return null;
	}
}
