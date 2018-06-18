package com.coffee.coffeehouse.dto;

/**
 * This class is the response class which display the daily report
 * 
 * @author Akhil
 *
 */
public class DailyReport {
	private String coffeeType;
	private int totalServingPerDay;
	private int totalServingSold;

	public String getCoffeeType() {
		return coffeeType;
	}

	public void setCoffeeType(String coffeeType) {
		this.coffeeType = coffeeType;
	}

	public int getTotalServingPerDay() {
		return totalServingPerDay;
	}

	public void setTotalServingPerDay(int totalServingPerDay) {
		this.totalServingPerDay = totalServingPerDay;
	}

	public int getTotalServingSold() {
		return totalServingSold;
	}

	public void setTotalServingSold(int totalServingSold) {
		this.totalServingSold = totalServingSold;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null || !(obj instanceof DailyReport)) {
			return false;
		}
		final DailyReport coffee = (DailyReport) obj;
		if (coffee.getCoffeeType().equalsIgnoreCase(this.coffeeType)
				&& coffee.getTotalServingPerDay() == this.totalServingPerDay)
				 {
			return true;
		} else {
			return false;
		}
	}
}
