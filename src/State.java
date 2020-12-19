// State.java - SARMIENTO, KHAN, groupe 5

import java.util.ArrayList;
import java.util.HashMap;

public class State implements State_Interface {

	private HashMap<String, City> cities;

	/**
	 * Basic constractor
	 */
	public State() {
		this.cities = new HashMap<String, City>();
	}

	/**
	 * Check that every city in a state respects the accesibily contraints. A city
	 * should have a school or a neighbour school
	 * 
	 * @return Check status
	 */
	public boolean checkAccessibilityConstraints() {
		if (this.cities.isEmpty())
			return false;

		boolean answer = true;

		for (City c : getCityList()) {
			if (c.getHasSchool())
				continue;
			// if no school and no nearby schools
			else if (c.getNearbySchoolCities().isEmpty()) {
				answer = false;
				break;
			}
		}

		return answer;
	}

	/**
	 * Build a school in every city
	 * 
	 * @param hasSchool Has all cities school ?
	 */
	public void setAllSchools(boolean hasSchool) {
		for (City c : getCityList())
			c.setHasSchool(hasSchool);
	}

	/**
	 * Automatically, alter cities of the state so that we have the least count of
	 * schools and every city respects the accessibility constraints
	 */
	public void solve() {
		setAllSchools(true);

		ArrayList<City> cityList = getCityList();

		// Sort by
		cityList.sort(null);

		for (City c : cityList)
			if (c.getHasSchool() && c.canRemoveSchool())
				c.setHasSchool(false);
	}

	/**
	 * Get an HashMap where the key is the name of a city and the value represents
	 * the city as an Object
	 * 
	 * @return An HashMap where city names are the key and the cities are the value
	 */
	public HashMap<String, City> getCities() {
		return this.cities;
	}

	/**
	 * Get a city by it's name from cities hashmap
	 * 
	 * @param cityName Name of the city
	 * @return The requested city
	 */
	public City getCity(String cityName) {
		return this.cities.get(cityName);
	}

	/**
	 * Get an array of cities
	 * 
	 * @return Array of cities
	 */
	public ArrayList<City> getCityList() {
		ArrayList<City> cityList = new ArrayList<City>();

		for (String cityName : cities.keySet())
			cityList.add(cities.get(cityName));

		return cityList;
	}

	/**
	 * Get a list of cities that have a school
	 * 
	 * @return an array list
	 */
	public ArrayList<City> getSchools() {
		ArrayList<City> schools = new ArrayList<City>();

		for (City city : this.cities.values())
			if (city.getHasSchool())
				schools.add(city);

		return schools;
	}

	/**
	 * Add a city to the State's Hashmap.
	 * 
	 * @param city city object
	 */
	public void addCity(City city) {
		this.cities.put(city.getName(), city);
	}

	/**
	 * Create a bi-directional route between two cities
	 * 
	 * @return Success state
	 */
	public boolean createRoute(String cityName, String anotherCityName) {
		if (cityName == null || anotherCityName == null)
			return false;

		if (cityName.equals(anotherCityName))
			return false;

		City city = this.cities.get(cityName);
		City anotherCity = this.cities.get(anotherCityName);

		if (city == null || anotherCity == null)
			return false;

		// mutual neighbours
		city.addNeighbour(anotherCity);

		return true;
	}
}