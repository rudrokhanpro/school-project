# The Schools Project

A Java program to manage cities in a state and minimize the amount of schools in that state.

## Authors

- Rudro KHAN
- Daniel SARMIENTO 

## Goal

Build the least amount of schools in a state while granting to every city the access to a school.

## Constraints

A **state** has to respect the following constraints:

- **accessibility** constraint: every city must have an access to a school or a nearby school.
- **economic** constraint: a state has to build the least amount school to reduce expenses.

## States

A **state** is a Java class that has a collection of cities.
The state can access cities by their unique _name_, make routes between different cities and manage their school.
A *state* has the following properties:

- `HashMap<String,City>`: An Hash Map where a **city's _name_ is the key** and the **city _object_ is the value**.

## Cities

A **city** is a Java class that has following properties:

- `String name`: Name of the city
- `boolean hasSchool`: Bolean to know if a city has a school
- `ArrayList<City> neighbours`: A list of nearby cities

# Compilation & Run

- With **Eclipse IDE**: Open projects from File System... > Select Project Directory > Run
- With the **Command line**: `cd src && javac *.java -d ../bin` then `cd ../bin && java Main`
