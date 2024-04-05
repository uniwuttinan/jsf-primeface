package my.example.view;

import lombok.Getter;
import lombok.Setter;
import my.example.model.Employee;
import my.example.service.EmployeeServiceMemory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Setter
@Getter
@ViewScoped
@ManagedBean(name = "employeeBean")
public class EmployeeBean implements Serializable {
    private static final Logger log = Logger.getLogger(EmployeeBean.class.getName());

    private final EmployeeServiceMemory employeeService = new EmployeeServiceMemory();

    private List<Employee> searchResults = new ArrayList<>();

    private Employee employeeForm = new Employee();

    private String crudMode = "read";

    @PostConstruct
    public void init() {
        employeeService.mock();
        startSearch();
    }

    public void resetForm() {
        setEmployeeForm(new Employee());
    }

    public void onEditClicked(Employee employee) {
        setCrudMode("update");
        setEmployeeForm(employee);
    }

    public void gotoCreatePage() {
        resetForm();
        setCrudMode("create");
    }

    public void gotoReadPage() {
        resetForm();
        startSearch();
        setCrudMode("read");
    }

    public void onEmployeeCreate() {
        employeeService.add(getEmployeeForm());
        gotoReadPage();
    }

    public void onEmployeeUpdate() {
        employeeService.update(getEmployeeForm());
        gotoReadPage();
    }

    public void onEmployeeDelete() {
        employeeService.delete(getEmployeeForm().getId());
        gotoReadPage();
    }

    public void startSearch() {
        log.info(
                String.format("Start search: %s %s %s", this.getEmployeeForm().getId(), this.getEmployeeForm().getFirstName(), this.getEmployeeForm().getLastName())
        );
        searchResults = new ArrayList<>();
        searchResults.addAll(
                getEmployeeService().search(getEmployeeForm())
        );
    }
}
