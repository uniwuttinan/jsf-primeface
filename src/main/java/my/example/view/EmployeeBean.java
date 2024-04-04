package my.example.view;

import lombok.Getter;
import lombok.Setter;
import my.example.model.Employee;
import my.example.service.EmployeeServiceMemory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Setter
@Getter
@SessionScoped
@ManagedBean(name = "employeeBean")
public class EmployeeBean implements Serializable {
    private static final Logger log = Logger.getLogger(EmployeeBean.class.getName());

    private final EmployeeServiceMemory employeeService = new EmployeeServiceMemory();

    private static ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    @PostConstruct
    public void init() {
        employeeService.mock();
    }

    private void redirect(String url) {
        try {
            ExternalContext ec = getExternalContext();
            ec.redirect(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onEditClicked(Employee employee) {
        // save the selected employee to session
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        sessionMap.put("selectingEmployee", employee);
        // redirect to employee-edit.xhtml
        redirect("employee-edit.xhtml");
    }

    public void onEmployeeAddButtonClicked() {
        redirect("employee-add.xhtml");
    }

    public void onBackClicked() {
        redirect("employee-search.xhtml");
    }

    public void onEmployeeAdd(Employee employee) {
        employeeService.add(employee);
        redirect("employee-search.xhtml");
    }

    public void onEmployeeUpdate(Employee employee) {
        employeeService.update(employee);
        redirect("employee-search.xhtml");
    }

    public void onEmployeeDelete(Employee employee) {
        employeeService.delete(employee.getId());
        redirect("employee-search.xhtml");
    }

    public List<Employee> searchEmployee(Employee query) {
        return getEmployeeService().search(query);
    }
}
