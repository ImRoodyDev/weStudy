package classes;

import java.util.ArrayList;
import java.util.List;

public class School {
	private int id;
	private String name;
	private List<StudyProgram> programs;

	public School(int id, String name) {
		this.id = id;
		this.name = name;
		this.programs = new ArrayList<>();
	}

	public void addProgram(StudyProgram program) {
		if (!programs.contains(program)) {
			programs.add(program);
		}
	}

	public boolean hasProgram(StudyProgram program) {
		return programs.contains(program);
	}

	public List<StudyProgram> getPrograms() {
		return new ArrayList<>(programs);  // Return a copy to maintain encapsulation
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
