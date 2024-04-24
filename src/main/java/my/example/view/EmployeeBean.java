package my.example.view;

import lombok.Getter;
import lombok.Setter;
import my.example.logger.MyLogger;
import my.example.model.Employee;
import my.example.qualifier.MyService;
import my.example.service.IEmployeeService;
import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ViewScoped
@Named("employeeBean")
public class EmployeeBean implements Serializable {
    @Inject
    private MyLogger logger;

    @Inject
    @MyService(MyService.EMPLOYEE_SERVICE_DB)
    private IEmployeeService employeeService;

    private List<Employee> searchResults = new ArrayList<>();

    private Employee employeeForm = new Employee();

    private Employee selectedEmployee = new Employee();

    private String crudMode = "read";

    @PostConstruct
    public void init() {
        startSearch();
        logger.info("EmployeeBean is initialized");
    }

    public void resetForm() {
        setEmployeeForm(new Employee());
    }

    public void resetFormToSelectedEmployee() {
        setEmployeeForm(getSelectedEmployee().clone());
    }

    public void startEdit(Employee employee) {
        this.selectedEmployee = employee.clone();
        this.employeeForm = employee.clone();
        setCrudMode("update");
    }

    public void setSelectedEmployee(Employee employee) {
        if (employee == null) return;
        startEdit(employee);
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
        // log.info(
        // String.format("Start search: %s %s %s", this.getEmployeeForm().getId(), this.getEmployeeForm().getFirstName(), this.getEmployeeForm().getLastName())
        // ;
        searchResults = new ArrayList<>();
        searchResults.addAll(getEmployeeService().search(getEmployeeForm()));
        setCrudMode("read");
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
        return getCrudMode().equals("read") ? 2 : 3;
    }

    public void showMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", message));
    }

    public void showMessageError(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", message));
    }

    public void onRowSelect(SelectEvent<Employee> event) {
        // log.info("Selected: " + event.getObject());
        Employee employee = event.getObject();
        if (employee == null) {
            return;
        }

        startEdit(employee);
    }
}
