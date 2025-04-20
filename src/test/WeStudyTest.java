package test;

import base.User;
import classes.*;

import java.util.List;

public class WeStudyTest {

	public static void main(String[] args) {
		Authentication auth = new Authentication();

		School haagseHogeschool = new School(1, "De Haagse Hogeschool");
		School universityLeiden = new School(2, "Universiteit Leiden");

		StudyProgram informatica = new StudyProgram(101, "HBO-ICT", haagseHogeschool);
		StudyProgram bedrijfskunde = new StudyProgram(102, "Bedrijfskunde", haagseHogeschool);
		StudyProgram rechten = new StudyProgram(201, "Rechten", universityLeiden);

		SchoolFinder finder = new SchoolFinder();
		finder.addSchool(haagseHogeschool);
		finder.addSchool(universityLeiden);

		Student student1 = new Student("roody", "veiligWachtwoord123");
		boolean isRegistered = auth.registerUser(student1);
		System.out.println("Student geregistreerd: " + isRegistered);

		User loggedInUser = auth.loginUser("roody", "veiligWachtwoord123");
		System.out.println("Inloggen succesvol: " + (loggedInUser != null));

		List<School> searchResults = finder.findSchoolsByName("haagse");
		System.out.println("Zoekresultaten voor 'haagse': " + searchResults.size());

		for (School school : searchResults) {
			System.out.println("Gevonden school: " + school.getName());


			if (loggedInUser instanceof Student) {
				Student student = (Student) loggedInUser;
				boolean schoolSelected = student.selectSchool(haagseHogeschool);
				System.out.println("School geselecteerd: " + schoolSelected);

				boolean programSelected = student.selectStudyProgram(informatica);
				System.out.println("Opleiding geselecteerd: " + programSelected);

				System.out.println("Geselecteerde school: " + student.getSelectedSchool().getName());
				System.out.println("Geselecteerde opleiding: " + student.getSelectedProgram().getName());
			}
		}
	}
}
