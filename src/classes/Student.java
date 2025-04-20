package classes;

import base.User;

public class Student extends User {
	private School selectedSchool;
	private StudyProgram selectedProgram;

	// Constructor to initialize a Student object
	public Student(String username, String password) {
		super(username, password);
	}

	// Let student select an school
	public boolean selectSchool(School school) {
		if (school != null) {
			this.selectedSchool = school;
			return true;
		}
		return false;
	}

	// Let student select a study program inside the school
	public boolean selectStudyProgram(StudyProgram program) {
		if (program != null && (selectedSchool == null || selectedSchool.hasProgram(program))) {
			this.selectedProgram = program;
			return true;
		}
		return false;
	}

	@Override
	public boolean authenticate(String password) {
		return super.authenticate(password);
	}

	public School getSelectedSchool() {
		return selectedSchool;
	}

	public StudyProgram getSelectedProgram() {
		return selectedProgram;
	}
}
