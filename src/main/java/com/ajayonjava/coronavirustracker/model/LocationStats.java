package com.ajayonjava.coronavirustracker.model;

import java.util.Date;

public class LocationStats {
	
	private String state;
	private String country;
	private int latestTotalCases;
	private int diffFromPreviousDay;
	private int totalDeath;
	private int totalDeathFromPreviousDay;
	private int totalRecovered;
	private int totalRecoveredFromPrivousDay;
	private int totalActiveCases;
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getLatestTotalCases() {
		return latestTotalCases;
	}
	public void setLatestTotalCases(int latestTotalCases) {
		this.latestTotalCases = latestTotalCases;
	}
	public int getDiffFromPreviousDay() {
		return diffFromPreviousDay;
	}
	public void setDiffFromPreviousDay(int diffFromPreviousDay) {
		this.diffFromPreviousDay = diffFromPreviousDay;
	}
	public int getTotalDeath() {
		return totalDeath;
	}
	public void setTotalDeath(int totalDeath) {
		this.totalDeath = totalDeath;
	}
	public int getTotalDeathFromPreviousDay() {
		return totalDeathFromPreviousDay;
	}
	public void setTotalDeathFromPreviousDay(int totalDeathFromPreviousDay) {
		this.totalDeathFromPreviousDay = totalDeathFromPreviousDay;
	}
	public int getTotalRecovered() {
		return totalRecovered;
	}
	public void setTotalRecovered(int totalRecovered) {
		this.totalRecovered = totalRecovered;
	}
	public int getTotalRecoveredFromPrivousDay() {
		return totalRecoveredFromPrivousDay;
	}
	public void setTotalRecoveredFromPrivousDay(int totalRecoveredFromPrivousDay) {
		this.totalRecoveredFromPrivousDay = totalRecoveredFromPrivousDay;
	}
	public int getTotalActiveCases() {
		return totalActiveCases;
	}
	public void setTotalActiveCases(int totalActiveCases) {
		this.totalActiveCases = totalActiveCases;
	}
	@Override
	public String toString() {
		return "LocationStats [state=" + state + ", country=" + country + ", latestTotalCases=" + latestTotalCases
				+ ", diffFromPreviousDay=" + diffFromPreviousDay + "]";
	}
	
	
}
