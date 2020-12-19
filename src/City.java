// City.java - SARMIENTO, KHAN, groupe 5

import java.util.ArrayList;
import java.lang.Comparable;

public class City implements Comparable<City> {
	private String name;
	private boolean hasSchool;
	private ArrayList<City> neighbours;

	/**
	 * Create a city from it's name.
	 * 
	 * @param name Name of the city
	 */
	public City(String name) {
		this.name = name;
		this.hasSchool = false;
		this.neighbours = new ArrayList<City>();
	}

	/**
	 * Add a city to the neighbours list, both ways.
	 * 
	 * @param neighbour A neighbour city
	 */
	public void addNeighbour(City neighbour) {
		if (neighbour == this)
			return;
		
		// add to neighbours
		if (!this.neighbours.contains(neighbour))
			this.neighbours.add(neighbour);
		
		// add this city to the neighbour's list
		if (!neighbour.getNeighbours().contains(this))
			neighbour.addNeighbour(this);
	}

	/**
	 * Get a list of nearby cities that have a school.
	 * 
	 * @return List of cities with schools
	 */
	public ArrayList<City> getNearbySchoolCities() {
		ArrayList<City> nearbySchoolCities = new ArrayList<City>();

		for (City n : this.getNeighbours())
			if (n.getHasSchool())
				nearbySchoolCities.add(n);

		return nearbySchoolCities;
	}

	/**
	 * Check if all requirements are met to remove a school.
	 * 
	 * @return Permission to remove a school
	 */
	public boolean canRemoveSchool() {
		if (!this.hasSchool)
			return false;
		
		boolean answer = true;

		// if there's no nearby school
		if (this.getNearbySchoolCities().size() == 0) {
			answer = false;
		} else {
			for (City n : this.getNeighbours()) {
				if (n.getHasSchool())
					continue;

				// if I am his only nearby school
				if (n.getNearbySchoolCities().size() == 1) {
					answer = false;
					break;
				}
			}
		}

		return answer;
	}

	/**
	 * Get a list of cities that depend on the current city's school.
	 * 
	 * @return A list of dependent cities according to accessibility constraints
	 */
	public ArrayList<City> getDependantCities() {
		ArrayList<City> dependantCities = new ArrayList<City>();

		for (City n : this.getNeighbours())
			if (!n.getHasSchool() && n.getNearbySchoolCities().size() == 1) {
				dependantCities.add(n);
			}

		return dependantCities;
	}

	/**
	 * Get the name of the city.
	 * 
	 * @return Name of the city
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the city.
	 * 
	 * @param name New name of the city
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Does the city have a school or not.
	 * 
	 * @return Has the city a school
	 */
	public boolean getHasSchool() {
		return hasSchool;
	}

	/**
	 * Set if a city has a school or not.
	 * 
	 * @param hasSchool School state
	 */
	public void setHasSchool(boolean hasSchool) {
		this.hasSchool = hasSchool;
	}

	/**
	 * Get a city's list of neighbours.
	 * 
	 * @return List of neighbours
	 */
	public ArrayList<City> getNeighbours() {
		return this.neighbours;
	}

	/**
	 * Set the neighbours a city
	 * 
	 * @param neighbours New neighbours of the city
	 */
	public void setNeighbours(ArrayList<City> neighbours) {
		this.neighbours = neighbours;
	}

	/**
	 * Compare two cities by their neighbour count. Used for sorting an array of
	 * cities by ascending order
	 * 
	 * @param another Another city
	 * @return Relative index of a city according to its neighbours count
	 */
	@Override
	public int compareTo(City another) {
		if (this.neighbours.size() > another.getNeighbours().size())
			return 1;
		else if (this.neighbours.size() == another.getNeighbours().size())
			return 0;
		else
			return -1;
	}

	/**
	 * Returns the name of the city
	 */
	@Override
	public String toString() {
		return this.getName();
	}

}