package test;

import classes.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.Assert.*;

public class WeStudyJTest {
	@Test
	public void testStudentRegistration() {
		Authentication auth = new Authentication();
		Student student = new Student("testUser", "password123");


		assertTrue(auth.registerUser(student));
		assertNotNull(auth.getUser("testUser"));
	}

	@Test
	public void testSchoolSearch() {
		SchoolFinder finder = new SchoolFinder();
		School school1 = new School(1, "De Haagse Hogeschool");
		School school2 = new School(2, "Universiteit Leiden");

		finder.addSchool(school1);
		finder.addSchool(school2);

		List<School> results = finder.findSchoolsByName("haagse");
		assertEquals(1, results.size());
		assertEquals("De Haagse Hogeschool", results.get(0).getName());
	}

	@Test
	public void testStudyProgramSelection() {
		School school = new School(1, "De Haagse Hogeschool");
		StudyProgram program = new StudyProgram(101, "HBO-ICT", school);
		Student student = new Student("studentName", "password");

		assertTrue(student.selectSchool(school));
		assertTrue(student.selectStudyProgram(program));
		assertEquals(program, student.getSelectedProgram());
	}
}
