package my.example.view;

import lombok.Getter;
import lombok.Setter;
import my.example.model.Person;
import my.example.service.impl.NameService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

@ViewScoped
@ManagedBean(name = "pfBean", eager = true)
public class PrimefacesBean implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Setter
    @Getter
    private Person person = new Person();

    private NameService service = new NameService();

    @Setter
    @Getter
    private String fullName;

    public PrimefacesBean() {
        this.service = new NameService();
        this.person = new Person();
    }

    @PostConstruct
    public void init() {
        this.service = new NameService();
        this.person = new Person();
    }

    public void submitButtonOnClick() {
        this.fullName = this.service.display(person);
    }

    // on contact preference changed
    public void contactPreferenceChanged() {
        this.person.setEmail("");
        this.person.setPhoneNumber("");
    }

    public String personSexFull() {
        switch (this.person.getSex()) {
            case "M":
                return "Male";
            case "F":
                return "Female";
        }
        return "Unknown";
    }
}
