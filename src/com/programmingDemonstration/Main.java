package com.programmingDemonstration;

/**
 * Demonstrate the RentalAgreement.printRentalAgreement() method 
 * capability and formatting is correct
 * @author michael.ryden
 *
 */
public class Main {

	public static void main(String[] args) {
		printRentalAgreement("LADW", 3, 10, "7/2/20");
		printRentalAgreement("JAKD", 6, 0, "9/3/15");
		printRentalAgreement("JAKR", 9, 0, "7/2/15");
		printRentalAgreement("JAKR", 4, 50, "7/2/20");
	}
	
	/**
	 * Prints Rental Agreement
	 * @param toolCode tool code for tool being checked out
	 * @param rentDayCount how many days needed to rent the tool
	 * @param discountPercent percent taken off final bill in integer format
	 * @param checkOutDate date checking out tool in M/d/yy format
	 * @return void
	 */
	private static void printRentalAgreement(String toolCode, int rentDayCount, int discountPercent, String checkOutDate) {
		Checkout checkout = new Checkout(toolCode, rentDayCount, discountPercent, checkOutDate);
		RentalAgreement rentalAgreement = checkout.getRentalAgreement();
		rentalAgreement.printRentalAgreement();
		System.out.println("");
	}
	
}
