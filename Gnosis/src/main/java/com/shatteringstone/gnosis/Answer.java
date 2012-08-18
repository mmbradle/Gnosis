package com.shatteringstone.gnosis;

public enum Answer {
	NULL(false, true), 
	BAD(false, true), 
	FAIL(false, false), 
	PASS(true, false), 
	GOOD(true, false), 
	BRIGHT(true, false);
 
	private final boolean passed;
	private final boolean resets;
 
	private Answer(boolean passed, boolean resets) {
		this.passed = passed;
		this.resets = resets;
	}
 
	public boolean passed() {
		return this.passed;
	}
	
	public boolean resets() {
		return this.resets;
	}
}
