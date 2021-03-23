import java.util.Scanner;
//import java.util.ArrayList;

public class YearGenerator {
	
//	private static int[] yearsG = sharedYears(1979, 8); //Mon
//	private static int[] yearsF = sharedYears(1985, 8); //Tue
//	private static int[] yearsE = sharedYears(1986, 7); //Wed
//	private static int[] yearsD = sharedYears(1981, 8); //Thur
//	private static int[] yearsC = sharedYears(1982, 8); //Fri
//	private static int[] yearsB = sharedYears(1977, 9); //Sat
//	private static int[] yearsA = sharedYears(1978, 9); //Sun
//	private static int[] yearsGF = sharedYears(1996, 3); //Mon
//	private static int[] yearsFE = sharedYears(1980, 3); //Tue
//	private static int[] yearsED = sharedYears(1992, 3); //Wed
//	private static int[] yearsDC = sharedYears(1976, 3); //Thur
//	private static int[] yearsCB = sharedYears(1988, 3); //Fri
//	private static int[] yearsBA = sharedYears(1972, 3); //Sat
//	private static int[] yearsAG = sharedYears(1984, 3); //Sun
	
	public static boolean isLeapYear(int year) {
		if (year % 400 == 0)
			return true;
		else if (year % 100 == 0)
			return false;
		else if (year % 4 == 0)
			return true;
		else return false;
	}
	
	public static boolean isSpecialCommonYear(int year) {
		if (year % 100 == 0 && !isLeapYear(year))
			return true;
		else return false;
	}
	
	public static int distFromNextSpecialCommonYear(int year) {
		int hundreds = year / 100;
		int tensAndOnes = year % 100;
		int dist = (100 - tensAndOnes) % 100;
		if ((hundreds + 1) % 4 == 0)
			dist = dist + 100;
		return dist;
	}
	
	public static int distFromLastSpecialCommonYear(int year) {
		int hundreds = year / 100;
		int dist = year % 100;
		if ((hundreds) % 4 == 0)
			dist = dist + 100;
		return dist;
	}
	
	public static void printList(int[] list) {
		for (int k = 0; k < list.length; k++) 
			System.out.println(list[k]);
	}
	
	public static boolean contains(int[] list, int num) {
		for (int k = 0; k < list.length; k++) {
			if (list[k] == num)
				return true;
		}
		return false;
	}
	
	public static int getStartYear(String weekday, boolean leapYear) {
		weekday = weekday.toLowerCase();
		if (!leapYear) {
			if (weekday.equals("sunday")) 
				return 1995;
			else if (weekday.equals("monday"))
				return 1990;
			else if (weekday.equals("tuesday"))
				return 1991;
			else if (weekday.equals("wednesday"))
				return 1997;
			else if (weekday.equals("thursday"))
				return 1998;
			else if (weekday.equals("friday"))
				return 1999;
			else
				return 1994;
		}
		else {
			if (weekday.equals("sunday")) 
				return 1984;
			else if (weekday.equals("monday"))
				return 1996;
			else if (weekday.equals("tuesday"))
				return 1980;
			else if (weekday.equals("wednesday"))
				return 1992;
			else if (weekday.equals("thursday"))
				return 1976;
			else if (weekday.equals("friday"))
				return 1988;
			else
				return 1972;
		}
	}
	
	//fix leap years to account for special common years
	public static int[] sharedYears(int startYear, int length) {
		int[] list = new int[length];
		list[0] = startYear;
		
		if (isLeapYear(startYear)) {
			for (int k = 1; k < length; k++) {
				int prevYear = list[k-1];
				int dist = distFromNextSpecialCommonYear(prevYear);
				
				if (dist > 28) 
					list[k] = prevYear + 28;
				else {
					if (dist > 8)
						list[k] = prevYear + 40;
					else list[k] = prevYear + 12;
				}
			}
		}

		else {
			for (int k = 1; k < length; k++) {
				int prevYear = list[k-1];
				
				int dist = distFromNextSpecialCommonYear(prevYear);
				
				int distFrom94 = Math.abs(dist - 6);
				int distFrom97 = Math.abs(dist - 3);
				int plusEleven = prevYear + 11;
				
				if (distFrom94 == 4 || distFrom94 == 3) 
					list[k] = prevYear + 12;
				else if (distFrom97 == 3 || distFrom97 == 2 || isLeapYear(plusEleven)) 
					list[k] = prevYear + 6;
				else list[k] = plusEleven;
			}
		}
		return list;
	}
	
	public static void getListByYear() {
		
		System.out.println("What year would you like to get a list of shared-calendar-years for?");
	
		Scanner kboard = new Scanner(System.in);
		int year = kboard.nextInt();
		
		int startYear = 0;
		
		
		if (isLeapYear(year)) {
			
			int tensAndOnes = year % 100;
			int dist = distFromLastSpecialCommonYear(year);
			int distFrom34 = Math.abs(tensAndOnes - 34);
			
//			(tensAndOnes == 4 || tensAndOnes == 8
//					|| tensAndOnes == 32 || tensAndOnes == 36
//					|| tensAndOnes == 60 || tensAndOnes == 64)
			if (dist >= 28 * 3)
				startYear = year - (28*3);
			else if (distFrom34 == 30 || distFrom34 == 28 || distFrom34 == 2) 
				startYear = year - (28 *2 + 12);
			else 
				startYear = year - (28 * 2 + 40);
		}
		
		else {
			int dist = distFromLastSpecialCommonYear(year);
			double distFrom14andHalf = Math.abs(dist - 14.5);
			int distFrom17 = Math.abs(dist - 17);
			
			//dist == 9 || dist == 13 || dist == 21 || dist == 25
			
			if (dist > 28)
				startYear = year - 28;
			else if (distFrom17 == 4 || distFrom17 == 8)
				startYear = year - 34;
			else if(distFrom14andHalf == 0.5 || distFrom14andHalf == 4.5 || distFrom14andHalf == 11.5 || distFrom14andHalf == 12.5)
				startYear = year - 29;
			else if (dist == 7 || dist == 11)
				startYear = year - 18;
			else
				startYear = year - 23;
		}
		
		int[]sharedCalYears = sharedYears(startYear, 7);
		
		int[] years = new int[6];
		for (int k = 0; k < 3; k++) 
			years[k] = sharedCalYears[k];
		for (int k = 5; k > 2; k--)
			years[k] = sharedCalYears[k+1];
		
		System.out.println("Here are the years that have matching calendars with " + year + ": ");
		printList(years);
		kboard.close();
	}
	
	public static void getListByWeekday() {
	
		System.out.println("What weekday do you like to get years for?");
		
		Scanner kboard1 = new Scanner(System.in);
		String weekday = kboard1.nextLine();
		
		System.out.println("Would you like these to be leap years?");
		System.out.println("Type either \"Yes\" or \"No\".");
		
		Scanner kboard2 = new Scanner(System.in);
		String answer = kboard2.nextLine();
		
		boolean leapYear = false;
		if (answer.toLowerCase().equals("yes"))
			leapYear = true;
		
		int startYear = getStartYear(weekday, leapYear);
		int[] years = sharedYears(startYear, 6);
		printList(years);
		
		kboard1.close();
		kboard2.close();
	}
	
	//need to find a way to make this a dictionary
	public static String labelToWeekday(String label) {
		String answer = "";
		if (label.equals("G") || label.equals("GF"))
			answer = "Monday";
		else if (label.equals("F") || label.equals("FE"))
			answer = "Tuesday";
		else
			answer = "Sunday";
		return answer;
	}
	
	public static String getLabel(int year) {
		
		int round1 = year % 400;
		int hundreds = round1 / 100;
		
		if (isLeapYear(year)) {
			int round2 = (year % 28) / 4;
			
			if ((hundreds == 0 && round2 == 0) || (hundreds == 1 && round2 == 3) || (hundreds == 2 && round2 == 6) || (hundreds == 3 && round2 == 2)) 
				return "GF";
			else if ((hundreds == 0 && round2 == 3) || (hundreds == 1 && round2 == 6) || (hundreds == 2 && round2 == 2) || (hundreds == 3 && round2 == 5))
				return "FE";
			else if ((hundreds == 0 && round2 == 6) || (hundreds == 1 && round2 == 2) || (hundreds == 2 && round2 == 5) || (hundreds == 3 && round2 == 1))
				return "ED";
			else if ((hundreds == 0 && round2 == 2) || (hundreds == 1 && round2 == 5) || (hundreds == 2 && round2 == 1) || (hundreds == 3 && round2 == 4))
				return "DC";
			else if ((hundreds == 0 && round2 == 5) || (hundreds == 1 && round2 == 1) || (hundreds == 2 && round2 == 4) || (hundreds == 3 && round2 == 0))
				return "CB";
			else if ((hundreds == 0 && round2 == 1) || (hundreds == 1 && round2 == 4) || (hundreds == 2 && round2 == 0) || (hundreds == 3 && round2 == 3))
				return "BA";
			else 
				//((hundreds == 0 && round2 == 4) || (hundreds == 1 && round2 == 0) || (hundreds == 2 && round2 == 3) || (hundreds == 3 && round2 == 6))
				return "AG";
		}
		
		else {
			if (isSpecialCommonYear(year)) {
				if (hundreds == 1)
					return "C";
				else if (hundreds == 2)
					return "E";
				else 
					//(if hundreds == 3)
					return "G";
			}
			
			int round2 = round1 % 28;
			if ( (hundreds == 0 && (round2 == 1 || round2 == 7 || round2 == 18)) || (hundreds == 1 && (round2 == 2 || round2 == 13 || round2 == 19)) || (hundreds == 2 && (round2 == 3 || round2 == 14 || round2 == 25)) || (hundreds == 3 && (round2 == 9 || round2 == 15 || round2 == 26)) )
				return "G";
			else if ( (hundreds == 3 && (round2 == 10 || round2 == 21 || round2 == 27)) || (hundreds == 0 && (round2 == 2 || round2 == 13 || round2 == 19)) || (hundreds == 1 && (round2 == 3 || round2 == 14 || round2 == 25)) || (hundreds == 2 && (round2 == 9 || round2 == 15 || round2 == 26)) )
				return "F";
			else if ( (hundreds == 2 && (round2 == 10 || round2 == 21 || round2 == 27)) || (hundreds == 3 && (round2 == 5 || round2 == 11 || round2 == 22)) || (hundreds == 0 && (round2 == 3 || round2 == 14 || round2 == 25)) || (hundreds == 1 && (round2 == 9 || round2 == 15 || round2 == 26)) )
				return "E";
			else if ( (hundreds == 1 && (round2 == 10 || round2 == 21 || round2 == 27)) || (hundreds == 2 && (round2 == 5 || round2 == 11 || round2 == 22)) || (hundreds == 3 && (round2 == 6 || round2 == 17 || round2 == 23)) || (hundreds == 0 && (round2 == 9 || round2 == 15 || round2 == 26)) )
				return "D";
			else if ( (hundreds == 0 && (round2 == 10 || round2 == 21 || round2 == 27)) || (hundreds == 1 && (round2 == 5 || round2 == 11 || round2 == 22)) || (hundreds == 2 && (round2 == 6 || round2 == 17 || round2 == 23)) || (hundreds == 3 && (round2 == 1 || round2 == 7 || round2 == 18)) )
				return "C";
			else if ( (hundreds == 3 && (round2 == 2 || round2 == 13 || round2 == 19)) || (hundreds == 0 && (round2 == 5 || round2 == 11 || round2 == 22)) || (hundreds == 1 && (round2 == 6 || round2 == 17 || round2 == 23)) || (hundreds == 2 && (round2 == 1 || round2 == 7 || round2 == 18)) )
				return "B";
			else
			//( (hundreds == 2 && (round2 == 2 || round2 == 13 || round2 == 19)) || (hundreds == 3 && (round2 == 3 || round2 == 14 || round2 == 25)) || (hundreds == 0 && (round2 == 6 || round2 == 17 || round2 == 23)) || (hundreds == 1 && (round2 == 1 || round2 == 7 || round2 == 18)) )
				return "A";
		}
		
	}
	
	public static int testthis(int year) {
		return year % 400;
	}
	
	public static void main(String args[]) {
		
		System.out.println("Would you like to get years that share the same calendar");
		System.out.println("by a specific year or a weekday?");
		Scanner k = new Scanner(System.in);
		String answer = k.nextLine();
		answer = answer.toLowerCase();
		if (answer.contentEquals("year"))
			getListByYear();
		if (answer.equals("weekday"))
			getListByWeekday();
		k.close();

//		for (int k = 1976; k <= 2052; k++) 
//			System.out.println(k + "\t" + getLabel(k, true));
		
		
//		for (int k = 1600; k < 1999; k++) 
//			System.out.println(k + "\t" + getLabel(k));
				



		
	}

}
