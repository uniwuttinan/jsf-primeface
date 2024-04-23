package my.example.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Duration;
import java.util.Date;


@Entity
@Table(name = "employees")
@Data
@ToString
public class Employee implements Serializable, Cloneable {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY) // for SQLite
    // @GeneratedValue(strategy = GenerationType.SEQUENCE) // for MySQL (NOT WORK)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "birthdate")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Transient
    private String age;

    public Employee(String firstName, String lastName, Date birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public Employee() {
        this.firstName = "";
        this.lastName = "";
        this.birthDate = new Date();
    }

    public static String timeDiffToHumanReadable(Duration diff) {
        long years = diff.toDays() / 365;
        long months = (diff.toDays() % 365) / 30;
        long remainingDaysInMonth = (diff.toDays() % 365) % 30;

        // Construct human-readable string
        StringBuilder result = new StringBuilder();
        if (years > 0) {
            result.append(years).append(" year").append(years > 1 ? "s" : "").append(" ");
        }
        if (months > 0) {
            result.append(months).append(" month").append(months > 1 ? "s" : "").append(" ");
        }
        if (remainingDaysInMonth > 0) {
            result.append(remainingDaysInMonth).append(" day").append(remainingDaysInMonth > 1 ? "s" : "");
        }

        return result.toString().trim();
    }

    public Duration getAgeDiffToNow() {
        return Duration.between(birthDate.toInstant(), new Date().toInstant());
    }

    public String getAge() {
        Duration diff = this.getAgeDiffToNow();
        return timeDiffToHumanReadable(diff);
    }

    @Override
    public Employee clone() {
        try {
            Employee clone = (Employee) super.clone();
            clone.id = this.id;
            clone.firstName = this.firstName;
            clone.lastName = this.lastName;
            clone.birthDate = (Date) this.birthDate.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
