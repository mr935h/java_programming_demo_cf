package com.programmingDemonstration;

/**
 * This is a class to hold tool information
 * @author michael.ryden
 */
public class Tool {
	public double dailyCharge;
	public boolean weekdayCharge;
	public boolean weekendCharge;
	public boolean holidayCharge;
	public String brand;
	public String toolType;
	public String toolCode;

	/**
	 * Tool Constructor
	 * @param toolCode tool code for tool being checked out
	 * @throws CheckoutCustomException 
	 */
	public Tool(String toolCode) {
		switch(toolCode) {
			case "CHNS":
				getCHNS();
				break;
			case "JAKD":
				getJAKD();
				break;
			case "JAKR":
				getJAKR();
				break;
			case "LADW":
				getLADW();
				break;
		}
	}

	
	/**
	 * instantiates new tool with CHNS attributes
	 */
	private void getCHNS() {
		dailyCharge = 1.49;
		weekdayCharge = true;
		weekendCharge = false;
		holidayCharge = true;
		brand = "Stihl";
		toolType = "Chainsaw";
		toolCode = "CHNS";
	}
	
	/**
	 * instantiates new tool with JAKD attributes
	 */
	private void getJAKD() {
		dailyCharge = 2.99;
		weekdayCharge = true;
		weekendCharge = false;
		holidayCharge = false;
		brand = "DeWalt";
		toolType = "Jackhammer";
		toolCode = "JAKD";
	}
	
	/**
	 * instantiates new tool with JAKR attributes
	 */
	private void getJAKR() {
		dailyCharge = 2.99;
		weekdayCharge = true;
		weekendCharge = false;
		holidayCharge = false;
		brand = "Ridgid";
		toolType = "Jackhammer";
		toolCode = "JAKR";
	}
	
	/**
	 * instantiates new tool with LADW attributes
	 */
	private void getLADW() {
		dailyCharge = 1.99;
		weekdayCharge = true;
		weekendCharge = true;
		holidayCharge = false;
		brand = "Werner";
		toolType = "Ladder";
		toolCode = "LADW";
	}

}
