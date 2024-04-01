package my.example.model;

import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.UUID;

public class Person implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;

	@Getter
	@Setter
	private String firstName;

	@Getter
	@Setter
	private String lastName;

	@Getter
	@Setter
	private String title;

	@Getter
	@Setter
	private String sex;

	@Getter
	@Setter
	private String contactPreference = "phone";

	@Getter
	@Setter
	private String phoneNumber;

	@Getter
	@Setter
	private String email;


	public Person() {

		id = UUID.randomUUID().toString();
		this.firstName = "";
		this.lastName = "";
		this.title = "";
		this.sex = "";
		this.contactPreference = "phone";
		this.phoneNumber = "";
		this.email = "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
