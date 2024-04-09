package my.example.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
public class Employee implements Serializable, Cloneable {
    private String id;

    private String firstName;

    private String lastName;

    private Date birthDate;

    private String age;

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

    public static String getTimeDifference(Date startDate, Date endDate) {
        long millisecondsDifference = endDate.getTime() - startDate.getTime();

        // Convert milliseconds to years, months, and days
        long millisecondsInDay = 1000 * 60 * 60 * 24;
        long daysDifference = millisecondsDifference / millisecondsInDay;
        long years = daysDifference / 365;
        long remainingDays = daysDifference % 365;
        long months = remainingDays / 30;
        long remainingDaysInMonth = remainingDays % 30;

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

    public String getAge() {
        return getTimeDifference(this.birthDate, new Date());
    }

    @Override
    public Employee clone() {
        try {
            Employee clone = (Employee) super.clone();
            clone.id = this.id;
            clone.firstName = this.firstName;
            clone.lastName = this.lastName;
            clone.birthDate = this.birthDate;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
