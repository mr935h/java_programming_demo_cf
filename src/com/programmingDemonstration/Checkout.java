package com.programmingDemonstration;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * This is a class to hold checkout information
 * @author michael.ryden
 * @see com.programmingDemonstration.Checkout#Checkout
 */
public class Checkout{
	public String toolCode;
	public int rentDayCount;
	public int discountPercent;
	public Date checkOutDate;
	public String strCheckOutDate;
	
	/**
	 * Checkout Constructor
	 * @param toolCode tool code for tool being checked out
	 * @param rentDayCount how many days needed to rent the tool
	 * @param discountPercent percent taken off final bill in integer format
	 * @param checkOutDate date checking out tool in M/d/yy format
	 * @throws CheckoutCustomException in case of invalid value 
	 */
	public Checkout(String toolCode, int rentDayCount, int discountPercent, String checkOutDate) throws IllegalArgumentException {
		this.toolCode = toolCode;
		if(rentDayCount < 1) {
			throw new IllegalArgumentException ("Provide days needing the tool for rental greater than 1.");
		}else {
			this.rentDayCount = rentDayCount;
		}
		
		if(discountPercent > 100 || discountPercent < 0) {
			throw new IllegalArgumentException ("Provide a valid discount percent between 0 and 100.");
		} else {
			this.discountPercent = discountPercent;
		}
		
		this.strCheckOutDate = checkOutDate;
		try {
			this.checkOutDate = new SimpleDateFormat("M/d/yy").parse(checkOutDate);
		} catch (ParseException e) {
			throw new IllegalArgumentException ("Provide a valid Checkout date as a String in M/d/yy format.");
		}
		
	}
	
	/**
	 * Creates a new Rental Agreement instance
	 * @return RentalAgreement instance
	 * @see com.programmingDemonstration.RentalAgreement
	 */
	public RentalAgreement getRentalAgreement() {
		RentalAgreement rentalAgreement = new RentalAgreement(this);
		return rentalAgreement;
	}
	

}