package com.shatteringstone.gnosis;
import java.util.List;


public class LearningStyle {
	private final double maxEase = 2.5;
	private final double minEase = 1.3;
	private final double initialEase = 2.5;
	
	/** The initial steps to support micro-intervals */
	private List<Long> microIntervalsInitial;
	
	private List<Long> microIntervalsReview;
	
	public double getMaxEase() {
		return maxEase;
	}
	public double getMinEase() {
		return minEase;
	}
	public double getInitialEase() {
		return initialEase;
	}
	public long getInitialInterval(boolean isLearning) {
		List<Long> microInterval = isLearning ? microIntervalsInitial : microIntervalsReview;
		return microInterval.get(0);
	}
}
