package my.example.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
public class Employee implements Serializable {
    private String id;

    private String firstName;

    private String lastName;

    private Date birthDate;

    public Employee(String firstName, String lastName, Date birthDate) {
        this.id = "0";
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public Employee() {
        this.id = "";
        this.firstName = "";
        this.lastName = "";
        this.birthDate = new Date();
    }
}
