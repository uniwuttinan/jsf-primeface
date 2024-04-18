package my.example.qualifier;

import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
public @interface MyService {
    String EMPLOYEE_SERVICE_MEMORY = "EMPLOYEE_SERVICE_MEMORY";
    String EMPLOYEE_SERVICE_DB = "EMPLOYEE_SERVICE_DB";


    String value();
}