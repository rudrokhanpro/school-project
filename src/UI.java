// UI.java - SARMIENTO, KHAN, groupe 5

public interface UI {
	/** Default option. */
	final int OPTS_UNDEFINED = -1;
	
	/** Project title and authors */
	static void printTitle() {
		System.out.println("+------------------------------------------------+");
		System.out.println("| PROJET ECOLES                                  |");
		System.out.println("+================================================+");
		System.out.println("| Auteurs: Daniel SARMIENTO, Rudro KHAN          |");
		System.out.println("| Groupe: 5                                      |");
		System.out.println("+------------------------------------------------+");
	}
	
	/////////////////////////////////////////////////////////////////////////////
	// Menus
	/////////////////////////////////////////////////////////////////////////////

	// ---- Routes

	/** Prints a routes management menu. */
	static void printRoutesMenu() {
		System.out.println("+------------------------------------------------+");
		System.out.println("| MENU: REPRESENTATION DES VILLES                |");
		System.out.println("|                                                |");
		System.out.println("|   1) Ajouter une route                         |");
		System.out.println("|   2) Fin                                       |");
		System.out.println("+------------------------------------------------+");
	}

	/** Option number to create a route, according to routes management menu. */
	final int ROUTES_OPTS_CREATE = 1;
	/** Option number to quit routes management menu. */
	final int ROUTE_OPTS_QUIT = 2;

	// ---- Schools

	/**
	 * Prints a school management menu and the current school count in a state.
	 * 
	 * @param count Current school count
	 */
	static void printSchoolsMenu(int count) {
		System.out.println("+------------------------------------------------+");
		System.out.println("| MENU: GESTION DES ECOLES                       |");
		System.out.println("|                                                |");
		System.out.println("|   1) Ajouter une école                         |");
		System.out.println("|   2) Retirer une école                         |");
		System.out.println("|   3) Afficher écoles                           |");
		System.out.println("|   4) Fin                                       |");
		System.out.println("|------------------------------------------------|");
		System.out.printf("| Nombre d'écoles actuelles: %2d                  |\n", count);
		System.out.println("+------------------------------------------------+");
	}

	/** Option number to create a school, according to school management menu. */
	final int SCHOOLS_OPTS_CREATE = 1;
	/** Option number to remove a school, according to school management menu. */
	final int SCHOOLS_OPTS_REMOVE = 2;
	/**
	 * Option number to show current schools, according to school management menu.
	 */
	final int SCHOOLS_OPTS_SHOW = 3;
	/** Option number to quit school management menu. */
	final int SCHOOLS_OPTS_QUIT = 4;

	// ---- Solutions

	/** Prints a menu to solve the problem and save a solution. */
	static void printSolutionsMenu() {
		System.out.println("+------------------------------------------------+");
		System.out.println("| MENU: RESOLUTION DU PROBLEME                   |");
		System.out.println("|                                                |");
		System.out.println("|   1) Résoudre manuellement                     |");
		System.out.println("|   2) Résoudre automatiquement                  |");
		System.out.println("|   3) Sauvegarder                               |");
		System.out.println("|   4) Fin                                       |");
		System.out.println("+------------------------------------------------+");
	}

	/** Option number to manually solve the problem, according to solutions menu. */
	final int SOLUTION_OPTS_MANUALLY = 1;
	/**
	 * Option number to automatically solve the problem, according to solutions
	 * menu.
	 */
	final int SOLUTION_OPTS_AUTOMATIC = 2;
	/** Option number to save current solution, according to solutions menu. */
	final int SOLUTION_OPTS_SAVE = 3;
	/** Option number to quit solutions menu. */
	final int SOLUTION_OPTS_QUIT = 4;

	/////////////////////////////////////////////////////////////////////////////
	// Error Messages
	/////////////////////////////////////////////////////////////////////////////
	/** Prints an error message to describe an FileNotFound exception. */
	static void printFileNotFound() {
		System.out.println("+------------------------------------------------+");
		System.out.println("| /!\\ Fichier non trouvé !                       |");
		System.out.println("|     Vérifiez que le fichier existe.            |");
		System.out.println("+------------------------------------------------+");
	}

	/**
	 * Prints an error message to describe an InputMismatch exception. ex: A
	 * character was input instead of an integer.
	 */
	static void printInputMismatchMessage() {
		System.out.println("+------------------------------------------------+");
		System.out.println("| /!\\ Saisie invalide !                          |");
		System.out.println("|     Merci de saisir un nombre entier.          |");
		System.out.println("+------------------------------------------------+");
	}

	/** Prints an error message to describe an IO exception. */
	static void printIOMessage() {
		System.out.println("+------------------------------------------------+");
		System.out.println("| /!\\ Erreur d'entrée et sortie !                |");
		System.out.println("|     L'opération d'écriture a échoué.           |");
		System.out.println("+------------------------------------------------+");
	}

	/** Prints an error message to describe that an option is invalid. */
	static void printInvalidOptionMessage() {
		System.out.println("+------------------------------------------------+");
		System.out.println("| /!\\ Saisie invalide !                          |");
		System.out.println("|     Veuillez saisir à nouveau votre choix.     |");
		System.out.println("+------------------------------------------------+");
	}

	/**
	 * Prints an error message to describe that a value is out of bounds.
	 * 
	 * @param min Minimum value
	 * @param max Maximim value
	 */
	static void printInvalidValue(int min, int max) {
		System.out.println("+------------------------------------------------+");
		System.out.println("| /!\\ Valeur incorrecte !                       |");
		System.out.printf("|     Maximum %2d et minimum %2d.                |\n", min, max);
		System.out.println("+------------------------------------------------+");
	}

	/**
	 * Prints a warning message that no .ca file was loaded at launch
	 */
	static void printNoInputFileMessage() {
		System.out.println("+------------------------------------------------+");
		System.out.println("| (i) Pensez à utiliser un fichier CA            |");
		System.out.println("|     ex: programme.exe <nom_fichier>.ca         |");
		System.out.println("+------------------------------------------------+");
	}
}
