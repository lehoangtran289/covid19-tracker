package com.example.covidreport.models;

public class LocationStats implements Comparable<LocationStats> {
	private String state;
	private String country;
	private int totalCases;
	private int totalDeaths;
	private int totalRecovered;
	private int diff;

	public int getDiff() {
		return diff;
	}

	public void setDiff(int diff) {
		this.diff = diff;
	}

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

	public int getTotalCases() {
		return totalCases;
	}

	public void setTotalCases(int totalCases) {
		this.totalCases = totalCases;
	}

	public int getTotalDeaths() {
		return totalDeaths;
	}

	public void setTotalDeaths(int totalDeaths) {
		this.totalDeaths = totalDeaths;
	}

	public int getTotalRecovered() {
		return totalRecovered;
	}

	public void setTotalRecovered(int totalRecovered) {
		this.totalRecovered = totalRecovered;
	}

	@Override
	public String toString() {
		return "LocationStats [state=" + state + ", country=" + country + ", totalCases=" + totalCases
				+ ", totalDeaths=" + totalDeaths + ", totalRecovered=" + totalRecovered + "]";
	}

	@Override
	public int compareTo(LocationStats o) {
		return Integer.compare(this.getTotalCases(), o.getTotalCases());
	}
}
