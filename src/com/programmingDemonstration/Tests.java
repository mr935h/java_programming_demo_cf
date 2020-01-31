package com.programmingDemonstration;

import static org.junit.jupiter.api.Assertions.*;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;

class Tests {
	/* date and currency formatters
	 * testing date format is different than requested
	 * date format in RentalAgreement class
	 */
	SimpleDateFormat dateFormatter = new SimpleDateFormat("M/d/yy");
	NumberFormat curFormatter = NumberFormat.getCurrencyInstance();

	@Test
	void test1() {
		try {
			Tool tool = new Tool("JAKR");
			@SuppressWarnings("unused")
			Checkout checkout = new Checkout(tool.toolCode, 5, 101, "9/3/15");
		} catch (IllegalArgumentException  e) {
			assertEquals("Provide a valid discount percent between 0 and 100.", e.getMessage());
		}
	}
	
	@Test
	void test2() {
		Tool tool = new Tool("LADW");
		Checkout checkout = new Checkout(tool.toolCode, 3, 10, "7/2/20");
		RentalAgreement rentalAgreement = checkout.getRentalAgreement();
		
		assertEquals("7/5/20", dateFormatter.format(rentalAgreement.dueDate));
		assertEquals("$1.99", curFormatter.format(rentalAgreement.dailyCharge));
		assertEquals(2, rentalAgreement.chargeDays);
		assertEquals("$3.98", curFormatter.format(rentalAgreement.prediscountCharge));
		assertEquals(10, rentalAgreement.discountPercent);
		assertEquals("$0.40", curFormatter.format(rentalAgreement.discountAmount));
		assertEquals("$3.58", curFormatter.format(rentalAgreement.finalCharge));
	}
	
	@Test
	void test3() {
		Tool tool = new Tool("CHNS");
		Checkout checkout = new Checkout(tool.toolCode, 5, 25, "7/2/15");
		RentalAgreement rentalAgreement = checkout.getRentalAgreement();
		
		assertEquals("7/7/15", dateFormatter.format(rentalAgreement.dueDate));
		assertEquals("$1.49", curFormatter.format(rentalAgreement.dailyCharge));
		assertEquals(3, rentalAgreement.chargeDays);
		assertEquals("$4.47", curFormatter.format(rentalAgreement.prediscountCharge));
		assertEquals(25, rentalAgreement.discountPercent);
		assertEquals("$1.12", curFormatter.format(rentalAgreement.discountAmount));
		assertEquals("$3.35", curFormatter.format(rentalAgreement.finalCharge));
	}
	
	@Test
	void test4() {
		Tool tool = new Tool("JAKD");
		Checkout checkout = new Checkout(tool.toolCode, 6, 0, "9/3/15");
		RentalAgreement rentalAgreement = checkout.getRentalAgreement();
		
		assertEquals("9/9/15", dateFormatter.format(rentalAgreement.dueDate));
		assertEquals("$2.99", curFormatter.format(rentalAgreement.dailyCharge));
		assertEquals(3, rentalAgreement.chargeDays);
		assertEquals("$8.97", curFormatter.format(rentalAgreement.prediscountCharge));
		assertEquals(0, rentalAgreement.discountPercent);
		assertEquals("$0.00", curFormatter.format(rentalAgreement.discountAmount));
		assertEquals("$8.97", curFormatter.format(rentalAgreement.finalCharge));
	}
	
	@Test
	void test5() {
		Tool tool = new Tool("JAKR");
		Checkout checkout = new Checkout(tool.toolCode, 9, 0, "7/2/15");
		RentalAgreement rentalAgreement = checkout.getRentalAgreement();
		
		assertEquals("7/11/15", dateFormatter.format(rentalAgreement.dueDate));
		assertEquals("$2.99", curFormatter.format(rentalAgreement.dailyCharge));
		assertEquals(5, rentalAgreement.chargeDays);
		assertEquals("$14.95", curFormatter.format(rentalAgreement.prediscountCharge));
		assertEquals(0, rentalAgreement.discountPercent);
		assertEquals("$0.00", curFormatter.format(rentalAgreement.discountAmount));
		assertEquals("$14.95", curFormatter.format(rentalAgreement.finalCharge));
	}
	
	@Test
	void test6() {
		Tool tool = new Tool("JAKR");
		Checkout checkout = new Checkout(tool.toolCode, 4, 50, "7/2/20");
		RentalAgreement rentalAgreement = checkout.getRentalAgreement();
		
		assertEquals("7/6/20", dateFormatter.format(rentalAgreement.dueDate));
		assertEquals("$2.99", curFormatter.format(rentalAgreement.dailyCharge));
		assertEquals(1, rentalAgreement.chargeDays);
		assertEquals("$2.99", curFormatter.format(rentalAgreement.prediscountCharge));
		assertEquals(50, rentalAgreement.discountPercent);
		assertEquals("$1.50", curFormatter.format(rentalAgreement.discountAmount));
		assertEquals("$1.49", curFormatter.format(rentalAgreement.finalCharge));
	}
}
