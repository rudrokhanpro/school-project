// StateTest.java - SARMIENTO, KHAN, groupe 5

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class StateTest {

	@Test
	void testCheckAccessibilityConstraints() {
		State CA = new State();
		City a = new City("A");
		City b = new City("B");

		// default
		assertFalse(CA.checkAccessibilityConstraints());

		// no schools
		CA.addCity(a);
		CA.addCity(b);

		assertFalse(CA.checkAccessibilityConstraints());

		// 1 school but no route
		a.setHasSchool(true);
		assertFalse(CA.checkAccessibilityConstraints());

		// 2 schools but no route
		b.setHasSchool(true);
		assertTrue(CA.checkAccessibilityConstraints());

		// 1 school with route
		CA.createRoute("A", "B");
		assertTrue(CA.checkAccessibilityConstraints());
	}

	@Test
	void testSolve() {
		State CA = new State();
		int schoolCount;

		// empty
		CA.solve();
		// count schools
		schoolCount = 0;
		for (City city : CA.getCityList())
			if (city.getHasSchool())
				schoolCount++;

		assertEquals(0, schoolCount);

		// no routes
		City a = new City("A");
		CA.addCity(a);
		City b = new City("B");
		CA.addCity(b);
		City c = new City("C");
		CA.addCity(c);
		City d = new City("D");
		CA.addCity(d);
		// count schools
		schoolCount = 0;
		for (City city : CA.getCityList())
			if (city.getHasSchool())
				schoolCount++;
		assertEquals(0, schoolCount);

		// solution check accessibility constraints
		CA.createRoute("A", "B");
		CA.createRoute("B", "C");
		CA.createRoute("C", "D");

		// before solve
		assertFalse(CA.checkAccessibilityConstraints());

		// after solve
		CA.solve();
		assertTrue(CA.checkAccessibilityConstraints());

		// economic constraint
		// count schools
		schoolCount = 0;
		for (City city : CA.getCityList())
			if (city.getHasSchool())
				schoolCount++;
		assertEquals(2, schoolCount);
	}

	@Test
	void testGetCityList() {
		State CA = new State();

		// empty
		assertEquals(new ArrayList<City>(), CA.getCityList());

		// count check
		City a = new City("A");
		City b = new City("B");
		CA.addCity(a);
		CA.addCity(b);

		assertEquals(2, CA.getCityList().size());

		// content check
		assertTrue(CA.getCityList().contains(a));
	}

	@Test
	void testGetSchools() {
		State CA = new State();

		// empty
		assertEquals(new ArrayList<City>(), CA.getSchools());

		// count check
		City a = new City("A");
		City b = new City("B");
		CA.addCity(a);
		CA.addCity(b);

		a.setHasSchool(true);
		assertEquals(1, CA.getSchools().size());

		// content check
		assertTrue(CA.getSchools().contains(a));
		assertFalse(CA.getSchools().contains(b));
	}

	@Test
	void testAddCity() {
		State CA = new State();

		// empty check
		assertTrue(CA.getCities().isEmpty());

		// city count
		City a = new City("A");
		City b = new City("B");
		CA.addCity(a);
		CA.addCity(b);

		assertEquals(CA.getCities().size(), 2);

		// duplicate, erase old copy
		City c1 = new City("C");
		City c2 = new City("C");
		CA.addCity(c1);
		CA.addCity(c2);

		assertNotEquals(CA.getCities().get("C"), c1);
		assertEquals(CA.getCities().get("C"), c2);
	}

	@Test
	void testCreateRoute() {
		State CA = new State();

		
		// null check
		System.out.println("test create route");
		assertFalse(CA.createRoute(null, null));

		// unknown city
		assertFalse(CA.createRoute("Z", "Q"));

		// content check
		City a = new City("A"); CA.addCity(a);
		City b = new City("B"); CA.addCity(b);
		City c = new City("C"); CA.addCity(c);
		

		assertTrue(CA.createRoute("A", "B"));
		assertTrue(CA.createRoute("B", "C"));

		assertTrue(a.getNeighbours().contains(b) && b.getNeighbours().contains(a));
		assertTrue(c.getNeighbours().contains(b) && b.getNeighbours().contains(c));
		assertFalse(a.getNeighbours().contains(c) || c.getNeighbours().contains(a));
	}

}
