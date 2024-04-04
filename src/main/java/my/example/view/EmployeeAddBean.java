package my.example.view;

import lombok.Getter;
import lombok.Setter;
import my.example.model.Employee;

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
@ManagedBean(name = "employeeAddBean")
public class EmployeeAddBean implements Serializable {
    private static Logger log = Logger.getLogger(EmployeeAddBean.class.getName());

    @ManagedProperty("#{employeeBean}")
    private EmployeeBean employeeBean;

    @Getter
    private List<Employee> searchResults = new ArrayList<>();

    private Employee employeeForm = new Employee();

    public void onSaveClicked() {
        employeeBean.onEmployeeAdd(employeeForm);
    }
}
