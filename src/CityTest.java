// CityTest.java - SARMIENTO, KHAN, groupe 5

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CityTest {

	@Test
	void testAddNeighbour() {
		City a = new City("A");
		City b = new City("B");

		// no self reference
		a.addNeighbour(a);
		assertEquals(a.getNeighbours().size(), 0);

		// neigbours count
		a.addNeighbour(b);
		assertFalse(a.getNeighbours().size() == 0);
		assertEquals(a.getNeighbours().size(), 1);
		assertFalse(b.getNeighbours().size() == 0);
		assertEquals(b.getNeighbours().size(), 1);

		// mutuality
		assertTrue(a.getNeighbours().contains(b));
		assertTrue(b.getNeighbours().contains(a));
	}

	@Test
	void testGetNearbySchoolCities() {
		City a = new City("A");
		City b = new City("B");
		City c = new City("C");

		a.addNeighbour(b);
		b.addNeighbour(c);

		// default value
		assertFalse(a.getHasSchool());
		assertEquals(a.getNearbySchoolCities().size(), 0);

		// nearby school count
		b.setHasSchool(true);
		assertEquals(a.getNearbySchoolCities().size(), 1);
		assertEquals(b.getNearbySchoolCities().size(), 0);
		assertEquals(c.getNearbySchoolCities().size(), 1);
	}

	@Test
	void testCanRemoveSchool() {
		City a = new City("A");
		City b = new City("B");

		// default value
		assertFalse(a.canRemoveSchool());

		// accessibility constraint check: no nearby schools
		a.setHasSchool(true);
		assertFalse(a.canRemoveSchool());

		// accessibility constraint check: nearby schools
		a.addNeighbour(b);
		b.setHasSchool(true);
		assertTrue(a.canRemoveSchool());

		// dependent city check
		a.setHasSchool(false);
		assertFalse(b.canRemoveSchool());
	}

	@Test
	void testGetDependantCities() {
		City a = new City("A");
		City b = new City("B");
		City c = new City("C");

		// default value
		assertEquals(a.getDependantCities().size(), 0);

		// dependent city
		a.setHasSchool(true);
		assertTrue(b.getDependantCities().contains(a));
	}

}
