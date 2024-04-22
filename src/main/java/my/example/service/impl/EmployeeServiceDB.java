package my.example.service.impl;

import my.example.logger.MyLogger;
import my.example.model.Employee;
import my.example.qualifier.MyService;
import my.example.service.IEmployeeService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;

@ApplicationScoped
@MyService(MyService.EMPLOYEE_SERVICE_DB)
public class EmployeeServiceDB implements IEmployeeService, Serializable {
    @Inject
    private EntityManager entityManager;

    @Inject
    private MyLogger logger;

    @PostConstruct
    public void mock() {
        // get total number of employees
        // if < 0 then start mocking data
        long count = getCount();
        if (count > 0) return;

        logger.info("Mock data started");

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            add(new Employee("Adam", "Adam", formatter.parse("01-01-2001")));
            add(new Employee("Bob", "Bob", formatter.parse("02-02-2002")));
            add(new Employee("Charlie", "Charlie", formatter.parse("03-03-2003")));
        } catch (ParseException e) {
            logger.log(Level.SEVERE, "Error parsing date", e);
        }

        logger.info("Mock data completed");
    }

    private long getCount() {
        return entityManager.createQuery("SELECT COUNT(e) FROM Employee e", Long.class).getSingleResult();
    }

    @Override
    public void add(Employee employee) {
        // entityManager.persist(employee);

        EntityTransaction tx = entityManager.getTransaction();
        try {
            logger.info("Adding employee" + employee);

            tx.begin();
            entityManager.persist(employee);
            tx.commit();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error adding employee");
            logger.log(Level.SEVERE, e.getMessage());
            if (tx.isActive()) tx.rollback();
            logger.info("Rollback completed");
        }
    }

    @Override
    public int update(Employee employee) {
        entityManager.merge(employee);
        return 1;
    }

    @Override
    public int delete(String id) {
//        Employee employee = getByID(id);
//        if (employee != null) {
//            entityManager.remove(employee);
//            return 1; // Indicate success
//        }
//        return 0; // Indicate failure

        EntityTransaction tx = entityManager.getTransaction();
        try {
            logger.info("Remove employee id " + id);
            tx.begin();
            Employee employee = entityManager.find(Employee.class, id);
            entityManager.remove(employee);
            tx.commit();
            return 1;
        } catch (Exception e) {
            logger.severe("Error removing employee" + e.getMessage());
            if (tx.isActive()) tx.rollback();
            logger.info("Rollback completed");
            return 0;
        }
    }

    @Override
    public List<Employee> search(Employee employee) {
        String jpql = "SELECT e FROM Employee e WHERE e.firstName LIKE :searchText OR e.lastName LIKE :searchText";
        TypedQuery<Employee> query = entityManager.createQuery(jpql, Employee.class);
        query.setParameter("searchText", "%" + employee.getFirstName() + "%");
        return query.getResultList();
    }

    @Override
    public Employee getByID(String id) {
        return entityManager.find(Employee.class, id);
    }
}
