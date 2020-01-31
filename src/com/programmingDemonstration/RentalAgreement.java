package com.programmingDemonstration;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This is a class to hold rental agreement information
 * @author michael.ryden
 * @see com.programmingDemonstration.RentalAgreement#RentalAgreement
 */
public class RentalAgreement extends Checkout{
	public String toolType;
	public String toolBrand;
	public Date dueDate;
	public double dailyCharge;
	public int chargeDays;
	public double prediscountCharge;
	public double discountAmount;
	public double finalCharge;
	
	
	/**
	 * RentalAgreement Constructor
	 * @param checkout Checkout instance
	 * @see com.programmingDemonstration.Checkout
	 */
	public RentalAgreement(Checkout checkout) {
		super(checkout.toolCode, checkout.rentDayCount, checkout.discountPercent, checkout.strCheckOutDate);
		Tool tool = new Tool(this.toolCode);
		this.toolType = tool.toolType;
		this.toolBrand = tool.brand;
		
		this.dailyCharge = tool.dailyCharge;
		this.dueDate = getDueDate(this.checkOutDate, this.rentDayCount);
		this.chargeDays = getChargeDays(tool.weekdayCharge, tool.weekendCharge, tool.holidayCharge, this.rentDayCount);
		this.prediscountCharge = getPrediscountCharge(tool.dailyCharge, this.chargeDays);
		this.discountAmount = getDiscountAmount(this.discountPercent, this.prediscountCharge);
		this.finalCharge = getFinalCharge(this.prediscountCharge, this.discountAmount);		
	}
	
	
	/**
	 * calculates the due date based on the checkout date and number of rental days requested.
	 * @param lcCheckOutDate date the tool is being checked out
	 * @param lcRentDayCount count of days needing to rent the tool
	 * @return the startDate plus the number of rental days or the dueDate
	 */
	private Date getDueDate(Date lcCheckOutDate, int lcRentDayCount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(lcCheckOutDate);
		cal.add(Calendar.DAY_OF_MONTH, lcRentDayCount);
		Date endDate = cal.getTime();
		return endDate;
	}
	
	/**
	 * calculates the chargeable days 
	 * @param isWeekdayCharged will weekdays be charged
	 * @param isWeekendCharged will weekends be charged
	 * @param isHolidayCharged will holidays be charged
	 * @param lcRentDayCount count of days needing to rent the tool
	 * @return amount of days chargeable based on tool based on tool being checked out
	 */
	private int getChargeDays(boolean isWeekdayCharged, boolean isWeekendCharged, boolean isHolidayCharged, int lcRentDayCount) {
		int days = lcRentDayCount;
		
		if(!isWeekendCharged) {
			days -= getWeekendOffset();
		}
		
		if(!isHolidayCharged) {
			days -= getHolidayOffset();
		}
		
		return days;
	}
	
	/**
	 * Get count of holiday days that are not charged
	 * @return count of holiday days that are not charged
	 */
	private int getHolidayOffset() {
		int holidayOffset = 0;
		int laborDayOfMonth = 0;
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(this.checkOutDate);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(this.dueDate);
		Calendar calDueDate = Calendar.getInstance();
		calDueDate.setTime(this.dueDate);
		
		//increment calendar objects by one day since clock starts day after checkout
		cal1.add(Calendar.DATE, 1);
		cal2.add(Calendar.DATE, 1);
		
		while(cal1.before(cal2)) {
			//account for July 4th weekend 
			if(cal1.get(Calendar.MONTH)==Calendar.JULY && cal1.get(Calendar.DAY_OF_MONTH)==4) {
				if(cal1.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY && cal1.before(calDueDate)) {
					holidayOffset++;
				} else if (cal1.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY && cal1.before(calDueDate)){
					holidayOffset++;
				} else {
					holidayOffset++;
				}
			}
			
			//account for Labor Day
			if(cal1.get(Calendar.MONTH) == Calendar.SEPTEMBER) {
				//only call method once to get labor day of month
				if(laborDayOfMonth == 0) {
					laborDayOfMonth = getLaborDayOfMonth(cal1.get(Calendar.YEAR));
				}
				
				if(cal1.get(Calendar.DAY_OF_MONTH) == laborDayOfMonth) {
					holidayOffset++;
				}
			}
			
			cal1.add(Calendar.DATE, 1);
		}
		
		return holidayOffset;
	}
	
	/**
	 * Finds the first Monday in September (Labor Day) for given year
	 * @param year the year for which Labor Day needs to be found
	 * @return day of month being the first Monday in September (Labor Day) 
	 */
	private int getLaborDayOfMonth(int year) {
		int laborDayOfMonth = 0;
		Calendar cal1 = Calendar.getInstance();
		cal1.set(Calendar.MONTH, 8);
		cal1.set(Calendar.DATE, 1);
		cal1.set(Calendar.YEAR, year);
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.MONTH, 8);
		cal2.set(Calendar.DATE, 15);
		cal2.set(Calendar.YEAR, year);
		
		
		while(cal1.before(cal2)) {
			if(cal1.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
				laborDayOfMonth = cal1.get(Calendar.DAY_OF_MONTH);
				break;
			}
			cal1.add(Calendar.DATE, 1);
		}
		
		return laborDayOfMonth;
	}
	
	/**
	 * Get count of weekend days that are not charged
	 * @return count of weekend days that are not charged
	 */
	private int getWeekendOffset() {
		int dayOffset = 0;
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(this.checkOutDate);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(this.dueDate);
		
		//increment calendar objects by one day since clock starts day after checkout
		cal1.add(Calendar.DATE, 1);
		cal2.add(Calendar.DATE, 1);
		
		while(cal1.before(cal2)) {
			if(cal1.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || cal1.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY) {
				dayOffset++;
			}
			cal1.add(Calendar.DATE, 1);
		}
		return dayOffset;
	}
	
	/**
	 * Provides sub-total charge before discount
	 * @param lcDailyCharge daily charge attribute of tool being checked out
	 * @param lcChargeDays amount of days the tool will be charged for
	 * @return sub-total charge as dailyCharge * chargeDays rounded to 2 decimal places
	 */
	private double getPrediscountCharge(double lcDailyCharge, int lcChargeDays) {
		double preDiscountCharge = lcDailyCharge * lcChargeDays;
		preDiscountCharge = (double) Math.round(preDiscountCharge * 100) / 100;
		return preDiscountCharge;
	}
	
	/**
	 * Provides discount amount 
	 * @param lcDiscountPercent discount of final bill as an int
	 * @param lcPrediscountCharge sub-total of bill before discount is applied
	 * @return discountAmount as prediscountCharge * discount rounded to 2 decimal places
	 */
	private double getDiscountAmount(int lcDiscountPercent, double lcPrediscountCharge) {
		double discount = (double) lcDiscountPercent / 100;
		double discountAmount = lcPrediscountCharge * discount;
		discountAmount = (double) Math.round(discountAmount * 100) / 100;
		return discountAmount;
	}
	
	/**
	 * Provides rental agreement final charge
	 * @param lcPrediscountCharge sub-total of bill before discount is applied
	 * @param lcDiscountAmount prediscountCharge * discount 
	 * @return final charge as prediscountCharge - discountAmount rounded to 2 decimal places
	 */
	private double getFinalCharge(double lcPrediscountCharge, double lcDiscountAmount) {
		double finalCharge = lcPrediscountCharge - lcDiscountAmount;
		finalCharge = (double) Math.round(finalCharge * 100) / 100;
		return finalCharge;
	}
	
	/**
	 * Prints rental agreement
	 * @return void
	 */
	public void printRentalAgreement() {
		NumberFormat curFormatter = NumberFormat.getCurrencyInstance();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yy");
		System.out.println("Rental Agreement");
		System.out.println("");
		System.out.println("Tool code: " + toolCode);
		System.out.println("Tool type: " + toolType);
		System.out.println("Tool brand: " + toolBrand);
		System.out.println("Rental days: " + rentDayCount);
		System.out.println("Check out date: " + dateFormatter.format(checkOutDate));
		System.out.println("Due date: " + dateFormatter.format(dueDate));
		System.out.println("Daily rental charge: " + curFormatter.format(dailyCharge));
		System.out.println("Charge days: " + chargeDays);
		System.out.println("Pre-discount charge: " + curFormatter.format(prediscountCharge));
		System.out.println("Discount percent: " + discountPercent + "%");
		System.out.println("Discount amount: " + curFormatter.format(discountAmount));
		System.out.println("Final charge: " + curFormatter.format(finalCharge));
	}
	
	/**
	 * Prints Checkout terms and rental agreement values
	 * @return void
	 */
	public void printRentalAgreementTestFormat() {
		NumberFormat curFormatter = NumberFormat.getCurrencyInstance();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("M/d/yy");
		System.out.println("Checkout terms");
		System.out.println("Tool code: " + toolCode);
		System.out.println("Check out date: " + dateFormatter.format(checkOutDate));
		System.out.println("Rental days: " + rentDayCount);
		System.out.println("Discount: " + discountPercent + "%");
		System.out.println("");
		
		System.out.println("Rental Agreement expected values");		
		System.out.println("Due date: " + dateFormatter.format(dueDate));
		System.out.println("Daily charge: " + curFormatter.format(dailyCharge));
		System.out.println("Charge days: " + chargeDays);
		System.out.println("Pre-discount charge: " + curFormatter.format(prediscountCharge));
		System.out.println("Discount %: " + discountPercent + "%");
		System.out.println("Discount amount: " + curFormatter.format(discountAmount));
		System.out.println("Final charge: " + curFormatter.format(finalCharge));
	}
	
}
