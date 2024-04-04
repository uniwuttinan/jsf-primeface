package my.example.view;

import lombok.Getter;
import lombok.Setter;
import my.example.model.Employee;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "employeeSearchBean")
public class EmployeeSearchBean implements Serializable {
    private static Logger log = Logger.getLogger(EmployeeSearchBean.class.getName());

    @ManagedProperty("#{employeeBean}")
    private EmployeeBean employeeBean;

    @Getter
    private List<Employee> searchResults = new ArrayList<>();

    private Employee employeeForm = new Employee();

    @PostConstruct
    public void init() {
        clearSearchResults();
        startSearch();
    }

    public void clearSearchResults() {
        searchResults = new ArrayList<>();
    }

    public void startSearch() {
        clearSearchResults();
        searchResults.addAll(employeeBean.searchEmployee(getEmployeeForm()));
    }
}
