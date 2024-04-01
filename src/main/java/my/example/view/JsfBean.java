package my.example.view;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import my.example.model.Person;
import my.example.service.NameService;

@SessionScoped
@ManagedBean
public class JsfBean  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Person person= new Person();
	private NameService service = new NameService();
	private String fullName;
	
	public String returnPage() {
		this.fullName = service.display(person);
		return "form-04-result";
	}
	
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public String getFullName() {
		return this.fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
}
