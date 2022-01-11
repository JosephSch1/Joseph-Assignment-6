package com.coderscampus;

import java.time.YearMonth;

public class Car {
	private YearMonth date;
	private Integer sale;
	
	public Car (YearMonth date, Integer sale) {
		this.date = date;
		this.sale = sale;
	}

	public YearMonth getDate() {
		return date;
	}

	public void setDate(YearMonth date) {
		this.date = date;
	}

	public Integer getSale() {
		return sale;
	}

	public void setSale(Integer sale) {
		this.sale = sale;
	}
	
	
}
