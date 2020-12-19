// State_Interface.java - SARMIENTO, KHAN, groupe 5

import java.util.regex.Pattern;

public interface State_Interface {
	/**
	 * Minimum and maximum count of the cities in a state.
	 * It represents the letter count in the alphabet
	 */
	final int MIN_CITY_COUNT = 1;
	final int MAX_CITY_COUNT = 26;
	
	/**
	 * The cost of a single school
	 */
	final int SCHOOL_UNIT_COST = 1000;
	
	/**
	 * Pattterns and Regex to filter lines in a .ca file
	 */
	final String CITY_REGEX = "ville\\((\\w+)\\).*";
	final Pattern CITY_PATTERN = Pattern.compile(CITY_REGEX);

	final String ROUTE_REGEX = "route\\((\\w+),(\\w+)\\).*";
	final Pattern ROUTE_PATTERN = Pattern.compile(ROUTE_REGEX);

	final String SCHOOL_REGEX = "ecole\\((\\w+)\\).*";
	final Pattern SCHOOL_PATTERN = Pattern.compile(SCHOOL_REGEX);
}
