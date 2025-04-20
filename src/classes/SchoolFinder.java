package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SchoolFinder {
	private List<School> availableSchools;

	public SchoolFinder() {
		this.availableSchools = new ArrayList<>();
	}

	public void addSchool(School school) {
		if (!availableSchools.contains(school)) {
			availableSchools.add(school);
		}
	}

	public List<School> findSchoolsByName(String searchTerm) {
		if (searchTerm == null || searchTerm.isEmpty()) {
			return new ArrayList<>();
		}

		String lowerCaseSearchTerm = searchTerm.toLowerCase();

		return availableSchools.stream()
				.filter(school -> school.getName().toLowerCase().contains(lowerCaseSearchTerm))
				.collect(Collectors.toList());
	}

	public List<School> getAllSchools() {
		return new ArrayList<>(availableSchools);
	}
}
