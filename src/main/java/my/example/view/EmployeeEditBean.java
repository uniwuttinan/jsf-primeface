package my.example.view;

import lombok.Getter;
import lombok.Setter;
import my.example.model.Employee;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "employeeEditBean")
public class EmployeeEditBean implements Serializable {
    private static Logger log = Logger.getLogger(EmployeeEditBean.class.getName());

    @ManagedProperty("#{employeeBean}")
    private EmployeeBean employeeBean;

    @Getter
    private List<Employee> searchResults = new ArrayList<>();

    private Employee employeeForm = new Employee();

    @PostConstruct
    public void init() {
        // get the employee from the context
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();

        Employee selectingEmployee = (Employee) sessionMap.get("selectingEmployee");

        if (selectingEmployee != null) {
            employeeForm = selectingEmployee;
        }
    }

    public void onUpdateClicked() {
        employeeBean.onEmployeeUpdate(employeeForm);
    }

    public void onDeleteClicked() {
        employeeBean.onEmployeeDelete(employeeForm);
    }
}
