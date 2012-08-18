package com.shatteringstone.gnosis;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.Duration;


public class Card {
	/** The fields that make up this face */
	private Set fields;
	
	/** TODO the template that tells a card how to display */
	
	/** The cards learning style */
	private LearningStyle learningStyle;
	
	private boolean learning = true;
	
	/** when the card was added to the deck */
	private DateTime dateAdded;
	
	/** when the card was first reviewed */
	private DateTime dateFirstReviewed;
	
	/** when the card is due for review */
	private DateTime dateDue;
	
	/** Date the card was last due */
	private DateTime dateLastDue;
	
	/** the current interval of the card (time between reviews) in milliseconds*/
	private long interval;
	
	/** The card's last interval in milliseconds*/
	private long lastInterval;
	
	/** The date the card was last see */
	private DateTime dateLastReviewed;
	
	/** The last answer */
	private Answer lastReviewAnswer;
	
	/**
	 * A numeric representation of the card's ease.  
	 * Smaller is harder. If the ease is 2.5, and you choose 'good', 
	 * the next interval will be about 2.5x greater than the last interval.
	 */
	private double ease;
	
	/** The card's last ease */
	private double lastEase;
	
	/** The number of time this card has been answered correctly */
	private int numCorrect;
	
	/** The total number of reviews. */
	private int numReviews;

	/**  The cur number of successive correct answers without failure. */
	private int curRun;
	
	/**  The max number of successive correct answers without failure. */
	private int maxRun;

	/** Average time it takes to answer this card in milliseconds*/
	private long avgTime;
	
	/** Total time spent reviewing this card in milliseconds*/
	private long totalTime;

	// BEING GETTERS/SETTERS
	public LearningStyle getLearningStyle() {
		return learningStyle;
	}

	public double getEase() {
		return ease;
	}
	
	public DateTime getDateLastDue() {
		return dateLastDue;
	}
	
	public boolean isLearning() {
		return this.learning;
	}
	// END GETTERS/SETTERS

	private static double calcEase(Answer ansQuality, Card card) {
		double eFactor = card.getEase();
		int q=ansQuality.ordinal();
		eFactor = eFactor-0.8+0.28*q-0.02*q*q; //Reduced
		//eFactor = eFactor+(0.1-(5-quality)*(0.08+(5-quality)*0.02)); //Non-reduced
		eFactor = Math.max(card.getLearningStyle().getMinEase(), eFactor);
		eFactor = Math.min(eFactor, card.getLearningStyle().getMaxEase());
		return eFactor;
	}
	
	public void saveReview(Answer ansQuality, long ansTime) {
		this.update(ansQuality, ansTime);
	}
	
	private void update(Answer ansQuality, long ansTime) {
		double ease = calcEase(ansQuality, this);
		
		this.lastEase = this.ease;
		this.lastInterval = this.interval;
		this.dateLastReviewed = DateTime.now();
		this.dateLastDue = this.dateDue;
		
		this.ease = ease;
		//this.interval = calcInterval(ease, this);
		long newInterval = (long)(interval * ease);
		Duration dur = new Duration(dateLastDue, DateTime.now());
		if (learning) {
//			if (dur.getMillis() > microIntervalsInitial.get(microIntervalsInitial.size() - 1)) {
//			}
		}
		if (ansQuality.resets()) {
			interval = this.learningStyle.getInitialInterval(learning);
		}
		
		this.dateDue = this.dateDue.plus(this.interval);
		this.numReviews++;
		if (ansQuality.passed()) {
			this.numCorrect++;
			if (this.lastReviewAnswer.passed()) {
				this.curRun++;
				if (this.curRun > this.maxRun) {
					this.maxRun = this.curRun;
				}
			}
		} else if (!ansQuality.passed()) {
			this.curRun = 0;
		}
		
		this.totalTime += ansTime;
		this.avgTime = this.totalTime/this.numReviews;
		
		if (this.checkGraduated()) {
			this.learning = false;
		}
		
		this.sanityCheck();
	}
	
	private boolean checkGraduated() {
		// TODO write this function
		return true;
	}
	
	private void sanityCheck() {
		//TODO check all numbers are greater than 0
	}
}
