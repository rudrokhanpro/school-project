// Main.java - SARMIENTO, KHAN, groupe 5

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.InputMismatchException;

public class Main {
	static State CA;
	static Scanner sc;

	public static void main(String[] args) {
		UI.printTitle();

		// intialization
		CA = new State();
		sc = new Scanner(System.in);
		
		// check if a file was passed as an argument
		boolean hasInputFile = args.length == 1;

		// if there is an input file
		if (hasInputFile) {
			try {
				// Load state information (cities, routes and schools)
				loadStateFromFile(args[0]);

				// if there an accessibility problem
				if (!CA.checkAccessibilityConstraints())
					// put a school in every city
					CA.setAllSchools(true);

			}
			// if the file was not found
			catch (FileNotFoundException e) {
				hasInputFile = false;
			}
		} else {
			UI.printNoInputFileMessage();
		}

		// if there is no input file
		if (!hasInputFile) {
			// create and cities to state
			createCities();

			// manage routes between cities
			manageRoutes();

			// add schools to every city in the state
			CA.setAllSchools(true);
		}

		// solution options and it conservation
		solveProblem();

		sc.close();
	} // main

	/**
	 * Create cities in a state
	 */
	static void createCities() {
		System.out.println("+------------------------------------------------+");
		System.out.println("| CREATION DES VILLES                            |");
		System.out.println("+================================================+");
		System.out.println();

		int cityCount = State.MIN_CITY_COUNT - 1;

		while (cityCount < State.MIN_CITY_COUNT || cityCount > State.MAX_CITY_COUNT) {
			try {
				System.out.printf("Nombre de villes à représenter > ");
				cityCount = sc.nextInt();

				if (cityCount < State.MIN_CITY_COUNT || cityCount > State.MAX_CITY_COUNT) {
					UI.printInvalidValue(State.MIN_CITY_COUNT, State.MAX_CITY_COUNT);
				}
			}

			// in case the user's input is different from an integer
			catch (InputMismatchException e) {
				UI.printInputMismatchMessage();
				sc.nextLine();
			}
			System.out.println();
		}

		System.out.println("+------------------------------------------------+");
		System.out.println("| (i) La saisie automatique est activé !         |");
		System.out.println("+------------------------------------------------+");
		System.out.println();

		// create a city and add it to the state
		for (int i = 0; i < cityCount; i++) {
			// city name
			int letterASCII = 'A' + i;
			String name = Character.toString((char) letterASCII);
			System.out.println("Nom de la ville n° " + (i + 1) + " > " + name);

			// create new City with the given name
			CA.addCity(new City(name));

			System.out.println("==> La ville " + name + " a bien été créé !");

			System.out.println();
		}
	} // createCities

	/**
	 * Create and remove routes between cities
	 */
	static void manageRoutes() {
		System.out.println("+------------------------------------------------+");
		System.out.println("| REPRESENTATION DES VILLES                      |");
		System.out.println("+================================================+");
		System.out.println();

		int choice = UI.OPTS_UNDEFINED;

		while (choice != UI.ROUTE_OPTS_QUIT) {
			// show menu
			UI.printRoutesMenu();

			try {
				System.out.print("Votre choix > ");
				choice = sc.nextInt();
				sc.nextLine();
				System.out.println();

				switch (choice) {
				case UI.ROUTES_OPTS_CREATE:
					addRoute();
					break;

				case UI.ROUTE_OPTS_QUIT:
					break;

				default:
					UI.printInvalidOptionMessage();
					break;
				}

			}
			// in case the user's input is different from an integer
			catch (InputMismatchException e) {
				UI.printInputMismatchMessage();
				sc.nextLine();
			}
		}
	} // manageRoutes

	/**
	 * Input a city and his neighbours then add a route between them
	 */
	static void addRoute() {
		System.out.println("1. Ajouter une route");
		System.out.println("--------------------");
		System.out.println();

		System.out.printf("Ville de départ > ");
		String fromCityName = sc.nextLine(); // Starting City to create a route with

		if (CA.getCity(fromCityName) == null) {
			System.out.println("+------------------------------------------------+");
			System.out.println("|  /!\\ Ville inconnue !                          |");
			System.out.println("+------------------------------------------------+");

			return;
		}

		System.out.printf("Villes voisines : (ex: B, C) > ");
		String neighboursName = sc.nextLine(); // Endpoint Cities to create a route with

		// Accepted formats: "A, B, C", "A,B,C", "a, b, c"
		final String regex = ",[ ]*";
		String[] neighboursList = neighboursName.split(regex);
		int routeCount = neighboursList.length;

		System.out.println("==> Création de " + routeCount + (routeCount == 1 ? " route" : " routes") + " :");

		for (String neighbour : neighboursList) {
			boolean wasSuccessful = CA.createRoute(fromCityName, neighbour);

			if (wasSuccessful) {
				System.out.println("\t- " + fromCityName + " <-> " + neighbour);
			} else {
				System.out.println("+------------------------------------------------+");
				System.out.println("|  /!\\ Route sans fin !                          |");
				System.out.println("|      La route vers " + neighbour + " a été ignoré.            |");
				System.out.println("+------------------------------------------------+");
			}
		}

		System.out.println();
	} // addRoute

	/**
	 * School management menu
	 */
	static void manageSchools() {
		System.out.println("+------------------------------------------------+");
		System.out.println("| GESTION DES ECOLES                             |");
		System.out.println("+================================================+");
		System.out.println();

		int choice = UI.OPTS_UNDEFINED;

		while (choice != UI.SCHOOLS_OPTS_QUIT) {
			UI.printSchoolsMenu(CA.getSchools().size());

			try {
				System.out.print("Votre choix > ");
				choice = sc.nextInt();
				sc.nextLine();
				System.out.println();

				switch (choice) {
				case UI.SCHOOLS_OPTS_CREATE:
					addSchool();
					break;

				case UI.SCHOOLS_OPTS_REMOVE:
					removeSchool();
					break;

				case UI.SCHOOLS_OPTS_SHOW:
					showSchools();
					break;

				case UI.SCHOOLS_OPTS_QUIT:
					break;

				default:
					UI.printInvalidOptionMessage();
					break;
				}
			} // try

			catch (InputMismatchException e) {
				UI.printInputMismatchMessage();
				sc.nextLine();
			}
		} // while Representation des écoles

		// show currrent project schools setup
		conclusion();
	} // manageSchools

	/**
	 * Input a city name and add a school to it
	 */
	static void addSchool() {
		System.out.println("1. Ajouter une école");
		System.out.println("--------------------");

		System.out.print("Ville concernée > ");
		String cityName = sc.nextLine();
		System.out.println();

		// if the city doesn't exist
		if (CA.getCity(cityName) == null) {
			System.out.println("+------------------------------------------------+");
			System.out.println("|  /!\\ Ville inconnue !                          |");
			System.out.println("+------------------------------------------------+");

			return;
		}

		City city = CA.getCity(cityName);
		city.setHasSchool(true);

		System.out.println("==> L'école dans la ville " + cityName + " a bien été créé.\n");
	} // addSchools

	/**
	 * Input a city and remove it's school
	 */
	static void removeSchool() {
		System.out.println("2. Supprimer une école");
		System.out.println("----------------------");

		System.out.printf("Ville concernée > ");
		String cityName = sc.nextLine();
		System.out.println();

		// if the city doesn't exist
		if (CA.getCity(cityName) == null) {
			System.out.println("+------------------------------------------------+");
			System.out.println("|  /!\\ Ville inconnue !                          |");
			System.out.println("+------------------------------------------------+");

			return;
		}

		City city = CA.getCity(cityName);
		boolean isPossible = city.canRemoveSchool();

		if (isPossible) {
			city.setHasSchool(false);

			System.out.println("==> L'école de la ville " + cityName + " a bien été retirée.");
		} else {
			System.out.println("+------------------------------------------------+");
			System.out.println("| /!\\ Impossible de supprimer cette école !      |");
			System.out.println("+------------------------------------------------+");

			// list of dependants cities according to accessibility constraints
			ArrayList<City> dependants = city.getDependantCities();

			System.out.println("Contrainte d'accessibilité non respectée.");

			if (!dependants.isEmpty()) {
				System.out.println("Les villes suivantes dépendent de cette école :");

				for (City n : dependants) {
					System.out.println("\t- " + n.getName());
				}
			}

			System.out.println();
		}
	} // removeSchool

	/**
	 * Show cities that have a school
	 */
	static void showSchools() {
		System.out.println("3. Afficher écoles");
		System.out.println("------------------");

		System.out.println("Ecoles actuelles: " + CA.getSchools());
		System.out.println();
	} // showSchools

	/**
	 * Open a file and load it's state informarmation <br/>
	 * Extension: .ca <br/>
	 * Format: multiple lines ending with '.' <br/>
	 * Line format: <br/>
	 * 
	 * <pre>
	 * ville(A).
	 * route(A,B).
	 * ecole(A).
	 * </pre>
	 * 
	 * @param filePath Relative or absolute file path
	 * @throws FileNotFoundException
	 */
	static void loadStateFromFile(String filePath) throws FileNotFoundException {
		System.out.println("+------------------------------------------------+");
		System.out.println("| (i) Chargement de la CA depuis un fichier...   |");
		System.out.println("+------------------------------------------------+");
		System.out.println("Ouverture du fichier : " + filePath);
		System.out.println();

		int cityCount = 0;
		int routeCount = 0;
		int schoolCount = 0;

		try {
			File file = new File(filePath);
			Scanner reader = new Scanner(file);
			String line;
			Matcher m;

			while (reader.hasNextLine()) {
				line = reader.nextLine();

				// ex: ville(A)
				if (line.matches(State.CITY_REGEX)) {
					m = State.CITY_PATTERN.matcher(line);

					if (m.find()) {
						String cityName = m.group(1);

						CA.addCity(new City(cityName));

						cityCount++;
					}
				}

				// ex: route(A,B)
				if (line.matches(State.ROUTE_REGEX)) {
					m = State.ROUTE_PATTERN.matcher(line);

					if (m.find()) {
						String nameA = m.group(1);
						String nameB = m.group(2);

						if (CA.createRoute(nameA, nameB))
							routeCount++;
					}
				}

				// ex: ecole(A)
				if (line.matches(State.SCHOOL_REGEX)) {
					m = State.SCHOOL_PATTERN.matcher(line);

					if (m.find()) {
						City c = CA.getCity(m.group(1));
						
						if (c != null) {
							c.setHasSchool(true);
							schoolCount++;
						}
					}
				}
			} // while

			reader.close();

			System.out.println("+------------------------------------------------+");
			System.out.println("| (i) Chargement terminé !                       |");
			System.out.println("+------------------------------------------------+");
			System.out.println("==> Villes créés : " + cityCount);
			System.out.println("==> Routes créés : " + routeCount + " (x2)");
			System.out.println("==> Ecoles créés : " + schoolCount);

			// if no city was loaded
			if (CA.getCities().size() == 0) {
				System.out.println("+------------------------------------------------+");
				System.out.println("| (i) Aucun ville créé !                         |");
				System.out.println("+------------------------------------------------+");
			}

			System.out.println();
		}

		// if given file was not found
		catch (FileNotFoundException e) {
			UI.printFileNotFound();

			throw new FileNotFoundException();
		}

	} // loadStateFromFile

	/**
	 * Solution menu
	 */
	static void solveProblem() {
		System.out.println("+------------------------------------------------+");
		System.out.println("| RESOLUTION DU PROBLEME                         |");
		System.out.println("+================================================+");
		System.out.println();

		int choice = UI.OPTS_UNDEFINED;

		while (choice != UI.SOLUTION_OPTS_QUIT) {
			UI.printSolutionsMenu();

			try {
				System.out.print("Votre choix > ");
				choice = sc.nextInt();
				sc.nextLine();
				System.out.println();

				switch (choice) {
				case UI.SOLUTION_OPTS_MANUALLY:
					manageSchools();
					break;

				case UI.SOLUTION_OPTS_AUTOMATIC:
					CA.solve();
					conclusion();
					break;

				case UI.SOLUTION_OPTS_SAVE:
					saveStateToFile();
					break;

				case UI.SOLUTION_OPTS_QUIT:
					break;

				default:
					UI.printInvalidOptionMessage();
					break;
				}
			}

			// in case the user's input is different from an integer
			catch (InputMismatchException e) {
				UI.printInputMismatchMessage();
				sc.nextLine();
			}
		}
	} // solveProblem

	/**
	 * Input a file path and save state information on it
	 */
	static void saveStateToFile() {
		System.out.println("3. Sauvegarder");
		System.out.println("--------------");

		System.out.print("Nom du fichier > ");
		String filePath = sc.nextLine();
		System.out.println();

		try {
			File file = new File(filePath);

			System.out.println("+------------------------------------------------+");
			System.out.println("| (i) Si le fichier existe, il sera remplacé.    |");
			System.out.println("+------------------------------------------------+");

			file.createNewFile();

			FileWriter writer = new FileWriter(file);

			// track visited cities to not duplicate routes
			HashMap<String, Boolean> visited = new HashMap<String, Boolean>();

			// Write every city
			for (String cityName : CA.getCities().keySet()) {
				String line = "ville(" + cityName + ").\n";
				writer.write(line);

				// visited default value
				visited.put(cityName, false);
			}

			// Write every route
			for (City c : CA.getCityList()) {
				for (City neighbour : c.getNeighbours()) {
					if (!visited.get(neighbour.getName())) {
						String line = "route(" + c.getName() + "," + neighbour.getName() + ").\n";
						writer.write(line);
					}
				}

				// make the city visited to not duplicate route
				visited.put(c.getName(), true);
			}

			// Write every city that has a school
			for (City c : CA.getSchools()) {
				String line = "ecole(" + c.getName() + ").\n";
				writer.write(line);
			}

			writer.close();

			System.out.println("==> Sauvegarde terminé !");
			System.out.println();

		}

		// in case there was an IO exception (ex: couldn't open file, file deleted, etc)
		catch (IOException e) {
			UI.printIOMessage();
		}
	} // saveStateToFile

	/**
	 * Project result. Show how many schools were built.
	 */
	static void conclusion() {
		ArrayList<City> schools = CA.getSchools();
		int schoolCount = schools.size();

		System.out.println("+------------------------------------------------+");
		System.out.println("| CONCLUSION DU PROJET                           |");
		System.out.println("+================================================+");
		System.out.println("==> Nombre écoles construites : " + schoolCount);
		System.out.println("==> Ecoles construites : " + schools);
		System.out.println("==> Coût total : " + (schoolCount * CA.SCHOOL_UNIT_COST) + " €");
		System.out.println();
	}
}