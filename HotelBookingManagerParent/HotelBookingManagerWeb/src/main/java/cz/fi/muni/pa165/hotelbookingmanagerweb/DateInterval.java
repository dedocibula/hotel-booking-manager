package cz.fi.muni.pa165.hotelbookingmanagerweb;

/**
 *
 * @author Marian Rusnak
 */
public class DateInterval {

	private int dateFrom;
	private int monthFrom;
	private int yearFrom;
	private int dateTo;
	private int monthTo;
	private int yearTo;

	public int getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(int dateFrom) {
		this.dateFrom = dateFrom;
	}

	public int getMonthFrom() {
		return monthFrom;
	}

	public void setMonthFrom(int monthFrom) {
		this.monthFrom = monthFrom;
	}

	public int getYearFrom() {
		return yearFrom;
	}

	public void setYearFrom(int yearFrom) {
		this.yearFrom = yearFrom;
	}

	public int getDateTo() {
		return dateTo;
	}

	public void setDateTo(int dateTo) {
		this.dateTo = dateTo;
	}

	public int getMonthTo() {
		return monthTo;
	}

	public void setMonthTo(int monthTo) {
		this.monthTo = monthTo;
	}

	public int getYearTo() {
		return yearTo;
	}

	public void setYearTo(int yearTo) {
		this.yearTo = yearTo;
	}
}
