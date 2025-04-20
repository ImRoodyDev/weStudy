package classes;

public class StudyProgram {
	private int id;
	private String name;
	private School school;

	public StudyProgram(int id, String name, School school) {
		this.id = id;
		this.name = name;
		this.school = school;

		// Add this program to the school's list of programs
		if (school != null) {
			school.addProgram(this);
		}
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public School getSchool() {
		return school;
	}
}
