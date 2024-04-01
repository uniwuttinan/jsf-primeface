package my.example.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.example.model.Employee;
import my.example.service.EmployeeServiceMemory;

@ManagedBean
@ViewScoped
public class CrudBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String mode;
	private Employee employeeCriteria;
	private Employee employeeEdit;
	private Employee selectedMember;
	private List<Employee> employeeList;

	private EmployeeServiceMemory service = new EmployeeServiceMemory();
	
	@PostConstruct
	public void init() {
		mode = "R";
		employeeCriteria = new Employee(); 
	}
	
	public void searchBtnOnclick() {
		employeeList = service.search(employeeCriteria);
	}

	public void addBtnOnclick() {
		mode = "C";
		employeeEdit = new Employee();
	}

	public void editBtnOnclick(Employee p) {
		mode = "U";
		employeeEdit = p;
	}

	public void saveBtnOnclick() {
		service.add(employeeEdit);
		mode = "U";
	}

	public void updateBtnOnclick() {
		service.update(employeeEdit);
	}

	public void deleteBtnOnclick() {
		service.delete(employeeEdit.getId());
	}

	public void backBtnOnclick() {
		mode = "R";
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Employee getEmployeeCriteria() {
		return employeeCriteria;
	}

	public void setEmployeeCriteria(Employee employeeCriteria) {
		this.employeeCriteria = employeeCriteria;
	}

	public Employee getEmployeeEdit() {
		return employeeEdit;
	}

	public void setEmployeeEdit(Employee employeeEdit) {
		this.employeeEdit = employeeEdit;
	}

	public Employee getSelectedMember() {
		return selectedMember;
	}

	public void setSelectedMember(Employee selectedMember) {
		this.selectedMember = selectedMember;
	}

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}
 
}
