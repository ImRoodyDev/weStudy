import base.User;
import classes.Authentication;
import classes.School;
import classes.SchoolFinder;
import classes.Student;
import classes.StudyProgram;
import interfaces.AuthenticationListener;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    private static Authentication auth;
    private static Scanner scanner;
    private static User currentUser;
    private static SchoolFinder schoolFinder;
    
    public static void main(String[] args) {
        // Initialize components
        auth = new Authentication();
        auth.setListener(new AuthenticationListener() {
	        @Override
	        public void onUserRegistered(String username) {
		        System.out.println("✅ User '" + username + "' has been successfully registered!");
	        }

	        @Override
	        public void onUserLoggedIn(String username) {
		        System.out.println("🔓 User '" + username + "' has successfully logged in!");
	        }

	        @Override
	        public void alreadyExists(String username) {
		        System.out.println("❌ Registration failed: Username '" + username + "' already exists!");
	        }

	        @Override
	        public void invalidCredentials(String username) {
		        System.out.println("❌ Login failed: Invalid credentials for '" + username + "'!");
	        }
        });
        scanner = new Scanner(System.in);
        schoolFinder = new SchoolFinder();
        
        // Setup some initial data
        setupInitialData();
        
        boolean running = true;
        
        System.out.println("======================================");
        System.out.println("🎓 Welcome to WeStudy Application 🎓");
        System.out.println("======================================");
        
        while (running) {
            displayMainMenu();
            int choice = getUserChoice();
            
            switch (choice) {
                case 1:
                    registerNewUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    if (currentUser != null) {
                        showUserProfile();
                    } else {
                        System.out.println("❌ You need to login first!");
                    }
                    break;
                case 4:
                    if (currentUser != null) {
                        logoutUser();
                    } else {
                        System.out.println("❌ You are not logged in!");
                    }
                    break;
                case 5:
                    searchSchools();
                    break;
                case 6:
                    if (currentUser != null && currentUser instanceof Student) {
                        Student student = (Student) currentUser;
                        if (student.getSelectedSchool() != null) {
                            selectStudyProgram(student, student.getSelectedSchool());
                        } else {
                            System.out.println("❌ You need to select a school first!");
                        }
                    } else {
                        System.out.println("❌ You need to login as a student first!");
                    }
                    break;
                case 0:
                    System.out.println("👋 Thank you for using WeStudy. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("⚠️ Invalid choice. Please try again.");
            }
            
            System.out.println(); // Empty line for readability
        }
        
        scanner.close();
    }
    
    private static void setupInitialData() {
        // Add some schools
        School haagseHogeschool = new School(1, "De Haagse Hogeschool");
        School universityLeiden = new School(2, "Universiteit Leiden");
        School universityUtrecht = new School(3, "Universiteit Utrecht");
        
        // Add study programs to schools
        StudyProgram informatica = new StudyProgram(101, "HBO-ICT", haagseHogeschool);
        StudyProgram bedrijfskunde = new StudyProgram(102, "Bedrijfskunde", haagseHogeschool);
        StudyProgram rechten = new StudyProgram(201, "Rechten", universityLeiden);
        StudyProgram geneeskunde = new StudyProgram(202, "Geneeskunde", universityLeiden);
        StudyProgram psychologie = new StudyProgram(301, "Psychologie", universityUtrecht);
        
        // Add programs to schools (assuming School has a method to add programs)
        haagseHogeschool.addProgram(informatica);
        haagseHogeschool.addProgram(bedrijfskunde);
        universityLeiden.addProgram(rechten);
        universityLeiden.addProgram(geneeskunde);
        universityUtrecht.addProgram(psychologie);
        
        schoolFinder.addSchool(haagseHogeschool);
        schoolFinder.addSchool(universityLeiden);
        schoolFinder.addSchool(universityUtrecht);
    }
    
    private static void displayMainMenu() {
        System.out.println("--------------------------------------");
        if (currentUser != null) {
            System.out.println("Logged in as: " + currentUser.getUsername());
        } else {
            System.out.println("Not logged in");
        }
        System.out.println("--------------------------------------");
        System.out.println("1. Register new user");
        System.out.println("2. Login");
        System.out.println("3. View profile");
        System.out.println("4. Logout");
        System.out.println("5. Search schools");
        System.out.println("6. Select study program");
        System.out.println("0. Exit");
        System.out.println("--------------------------------------");
        System.out.print("Enter your choice: ");
    }
    
    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static void registerNewUser() {
        System.out.println("\n📝 REGISTER NEW USER");
        System.out.println("--------------------------------------");
        
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        Student newStudent = new Student(username, password);
        boolean registered = auth.registerUser(newStudent);
        
        if (registered) {
            // Automatically log in the user after successful registration
            currentUser = newStudent;
            currentUser.login(password);
            System.out.println("You are now logged in as " + username);
        }
    }
    
    private static void loginUser() {
        System.out.println("\n🔑 LOGIN");
        System.out.println("--------------------------------------");
        
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        User user = auth.loginUser(username, password);
        if (user != null) {
            currentUser = user;
        }
    }
    
    private static void showUserProfile() {
        System.out.println("\n👤 USER PROFILE");
        System.out.println("--------------------------------------");
        System.out.println("Username: " + currentUser.getUsername());
        System.out.println("Status: " + (currentUser.isLoggedIn() ? "Logged in" : "Not logged in"));
        
        if (currentUser instanceof Student) {
            Student student = (Student) currentUser;
            System.out.println("User type: Student");
            
            if (student.getSelectedSchool() != null) {
                System.out.println("Selected school: " + student.getSelectedSchool().getName());
                
                if (student.getSelectedProgram() != null) {
                    System.out.println("Selected program: " + student.getSelectedProgram().getName());
                } else {
                    System.out.println("No study program selected");
                }
            } else {
                System.out.println("No school selected");
            }
        }
    }
    
    private static void logoutUser() {
        currentUser.logout();
        System.out.println("🔒 You have been logged out.");
        currentUser = null;
    }
    
    private static void searchSchools() {
        System.out.println("\n🔍 SEARCH SCHOOLS");
        System.out.println("--------------------------------------");
        
        System.out.print("Enter search term: ");
        String searchTerm = scanner.nextLine();
        
        List<School> results = schoolFinder.findSchoolsByName(searchTerm);
        
        System.out.println("\nSearch results for '" + searchTerm + "':");
        if (results.isEmpty()) {
            System.out.println("No schools found matching your search.");
        } else {
            for (int i = 0; i < results.size(); i++) {
                School school = results.get(i);
                System.out.println((i+1) + ". " + school.getName() + " (ID: " + school.getId() + ")");
            }
            
            if (currentUser instanceof Student && currentUser.isLoggedIn()) {
                System.out.print("\nSelect a school by number (or 0 to cancel): ");
                int selection = getUserChoice();
                
                if (selection > 0 && selection <= results.size()) {
                    Student student = (Student) currentUser;
                    School selectedSchool = results.get(selection - 1);
                    
                    if (student.selectSchool(selectedSchool)) {
                        System.out.println("✅ School '" + selectedSchool.getName() + "' has been selected.");
                        
                        // After selecting a school, offer to select a study program
                        selectStudyProgram(student, selectedSchool);
                    } else {
                        System.out.println("❌ Failed to select school.");
                    }
                }
            }
        }
    }
    
    private static void selectStudyProgram(Student student, School school) {
        List<StudyProgram> programs = school.getPrograms();
        
        if (programs == null || programs.isEmpty()) {
            System.out.println("No study programs available for this school.");
            return;
        }
        
        System.out.println("\n📚 SELECT STUDY PROGRAM");
        System.out.println("--------------------------------------");
        System.out.println("Available programs at " + school.getName() + ":");
        
        for (int i = 0; i < programs.size(); i++) {
            StudyProgram program = programs.get(i);
            System.out.println((i+1) + ". " + program.getName() + " (ID: " + program.getId() + ")");
        }
        
        System.out.print("\nSelect a program by number (or 0 to cancel): ");
        int selection = getUserChoice();
        
        if (selection > 0 && selection <= programs.size()) {
            StudyProgram selectedProgram = programs.get(selection - 1);
            
            if (student.selectStudyProgram(selectedProgram)) {
                System.out.println("✅ Program '" + selectedProgram.getName() + "' has been selected.");
            } else {
                System.out.println("❌ Failed to select program.");
            }
        }
    }
}
