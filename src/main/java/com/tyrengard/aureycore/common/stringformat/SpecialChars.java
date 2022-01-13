package com.tyrengard.aureycore.common.stringformat;

public enum SpecialChars {
	BULLET;
	
	@Override
	public String toString() {
		return switch (this) {
			case BULLET -> "\u2022";
			default -> null;
		};
	}
}
