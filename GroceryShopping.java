package com.ford;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/*
 * GroceryShopping 
 * @Author Govindaiah
 * 
 * This class is to calculate total price of purchased products
 */
public class GroceryShopping {

	public static void main(String args[]) {

		Scanner input = new Scanner(System.in);

		LocalDate currentDate = LocalDate.now();

		int choice = 1;
		double subtotal = 0;
		double price = 0;
		int soupQuantity = 0;

		LocalDate purchaseDate = null;
		boolean flag = false;
		System.out.println("Enter the date of puchasing and date formate is yyyy-MM-dd");

		do {
			String date = input.next();
			try {
				purchaseDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy[-MM[-dd]]"));
				flag = false;
			} catch (DateTimeParseException e) {
				System.out.println("Date formate is incorrect please Enter correct formate");
				flag = true;
			}
		} while (flag);

		do {
			System.out.println("Welcome to GroceryStore-Billing");
			System.out.println();
			System.out.println("Product     unit      cost");
			System.out.println("==========================");
			System.out.println("1.soup      tin       0.65");
			System.out.println("2.bread     loaf      0.80");
			System.out.println("3.milk      bottle    1.30");
			System.out.println("4.apples    single    0.10");
			System.out.println();
			System.out.println("0. Quit");
			System.out.println("");

			System.out.println("Please Select Product Number or Enter 0 to exit");
			choice = input.nextInt();

			if (choice == 0)
				break;

			System.out.println("Enter Quantity?");
			int qty = input.nextInt();
			switch (choice) {
			case 1:
				soupQuantity = qty;
				price = 0.65;
				subtotal = getSubTotal(subtotal, price, qty);
				break;
			case 2:
				price = 0.80;
				LocalDate discountStartDate = currentDate.minusDays(1);
				LocalDate discountEndDate = discountStartDate.plusDays(7);
				if (soupQuantity > 2 && validateDiscountDate(purchaseDate, discountStartDate, discountEndDate)) {
					int breadDiscount = (int) Math.floor(soupQuantity / 2);
					if (qty > breadDiscount) {
						subtotal = subtotal + (((qty - breadDiscount) * price) + (breadDiscount * 0.40));
					}
					if (qty < breadDiscount) {
						subtotal = subtotal + (qty * 0.40);
					}
				} else {
					subtotal = getSubTotal(subtotal, price, qty);
				}
				break;
			case 3:
				price = 1.30;
				subtotal = getSubTotal(subtotal, price, qty);
				break;
			case 4:
				price = 0.10;
				LocalDate appleDicountStartDate = currentDate.plusDays(3);
				LocalDate appleDicountEndtDate = YearMonth.from(appleDicountStartDate.plusMonths(1)).atEndOfMonth();
				if (validateDiscountDate(purchaseDate, appleDicountStartDate, appleDicountEndtDate)) {
					subtotal = subtotal + (qty * (price - (price * 10 / 100)));
				} else {
					subtotal = getSubTotal(subtotal, price, qty);
				}
				break;

			}
		}

		while (choice > 0);
		System.out.println("Total price : " + subtotal);

	}

	/*
	 * Method to calculate subtotal of purchased item
	 */
	private static double getSubTotal(double subtotal, double price, int qty) {
		subtotal = subtotal + (qty * price);
		return subtotal;
	}

	/*
	 * Method to validate discount price based on purchase date
	 */
	private static boolean validateDiscountDate(LocalDate purchaseDate, LocalDate appleDicountStartDate,
			LocalDate appleDicountEndtDate) {
		return (purchaseDate.equals(appleDicountStartDate) || purchaseDate.equals(appleDicountEndtDate))
				|| (purchaseDate.isAfter(appleDicountStartDate) && purchaseDate.isBefore(appleDicountEndtDate));
	}
}
