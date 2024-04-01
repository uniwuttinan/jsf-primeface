package my.example.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import my.example.model.Person;
import my.example.service.NameService;

@ViewScoped
@ManagedBean(name="pfBean", eager=true)
public class PrimefacesBean implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Setter
	@Getter
	private Person person= new Person();

	private NameService service = new NameService();

	@Setter
	@Getter
	private String fullName;

	@PostConstruct
	public void init() {
		this.service = new NameService();
		this.person = new Person();
	}

	public PrimefacesBean() {
		this.service = new NameService();
		this.person = new Person();
	};


	public void submitButtonOnClick() {
		this.fullName = this.service.display(person);
	};

	// on contact preference changed
	// clear phone number if email is selected
	// otherwise, clear email
	public void contactPreferenceChanged() {
		if (this.person.getContactPreference().equals("phone")) {
			this.person.setEmail("");
		} else {
			this.person.setPhoneNumber("");
		}
	}
}
