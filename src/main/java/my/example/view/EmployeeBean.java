package my.example.view;

import lombok.Getter;
import lombok.Setter;
import my.example.model.Employee;
import my.example.service.EmployeeServiceMemory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.time.Duration;
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

    private Employee selectedEmployee = new Employee();

    private String crudMode = "read";

    @PostConstruct
    public void init() {
        employeeService.mock();
        startSearch();
    }

    public void resetForm() {
        setEmployeeForm(new Employee());
    }

    public void resetFormToSelectedEmployee() {
        setEmployeeForm(getSelectedEmployee().clone());
    }

    public void onEditClicked(Employee employee) {
        setCrudMode("update");
        setSelectedEmployee(employee.clone());
        setEmployeeForm(employee.clone());
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
        Duration duration = getEmployeeForm().getAgeDiffToNow();
        long diffYears = duration.toDays() / 365;
        // log.info("diff " + diffYears);

        // age must > 2 years
        if (diffYears < 2) {
            showMessageError("Age is invalid");
            return;
        }

        employeeService.add(getEmployeeForm());
        showMessage("บันทึกข้อมูลเรียบร้อย (CREATE)");
        gotoReadPage();
    }

    public void onEmployeeUpdate() {
        employeeService.update(getEmployeeForm());
        showMessage("บันทึกข้อมูลเรียบร้อย (UPDATE)");
        gotoReadPage();
    }

    public void onEmployeeDelete() {
        employeeService.delete(getSelectedEmployee().getId());
        gotoReadPage();
    }

    public void startSearch() {
//        log.info(
//                String.format("Start search: %s %s %s", this.getEmployeeForm().getId(), this.getEmployeeForm().getFirstName(), this.getEmployeeForm().getLastName())
//        );
        searchResults = new ArrayList<>();
        searchResults.addAll(getEmployeeService().search(getEmployeeForm()));
    }

    public void onResetClick() {
        if (getCrudMode().equals("read")) {
            resetForm();
            startSearch();
        } else if (getCrudMode().equals("update")) {
            resetFormToSelectedEmployee();
        }
    }

    public int getPanelGridColumnCount() {
        if (getCrudMode().equals("read")) {
            return 2;
        }
        return 3;
    }

    public void showMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", message));
    }

    public void showMessageError(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", message));
    }
}
