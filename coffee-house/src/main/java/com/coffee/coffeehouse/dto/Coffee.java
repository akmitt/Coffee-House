package com.coffee.coffeehouse.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Coffee {
    @NotNull
    @NotEmpty
	public String name;
	private String description;
	private int servingPerDay;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getServingPerDay() {
		return servingPerDay;
	}

	public void setServingPerDay(int servingPerDay) {
		this.servingPerDay = servingPerDay;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null || !(obj instanceof Coffee)) {
			return false;
		}
		final Coffee coffee = (Coffee) obj;
		if (coffee.name == null || coffee.name.isEmpty() || !name.equals(this.name))
		{
			return false;
		}
		else {
			return true;
		}

	}
}
