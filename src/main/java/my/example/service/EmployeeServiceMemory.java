package my.example.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import my.example.model.Employee;

public class EmployeeServiceMemory  {

	public static HashMap<String, Employee> employeeMap = new HashMap<String, Employee>();

	public void add(Employee employee) {
		employeeMap.put(employee.getId(), employee);
	}

	public int update(Employee employee) {
		if (employeeMap.containsKey(employee.getId())) {
			employeeMap.put(employee.getId(),employee);
			return 1;
		}else {
			return 0;
		}
	}

	public List<Employee> search(Employee employee) {
		List<Employee> employeeList = new ArrayList<Employee>();
		for (Map.Entry<String, Employee> entry : employeeMap.entrySet()) {
			employeeList.add(entry.getValue());
		}
		return employeeList;
	}

	public int delete(String id) {
		if (employeeMap.containsKey(id)) {
			employeeMap.remove(id);
			return 1;
		}else {
			return 0;
		}
	}

}
