package my.example.service;

import my.example.model.Person;

import java.io.Serializable;

public class NameService implements Serializable {
	public String display(Person person) {
//		return String.format("%s %s %s", person.getTitle(), person.getFirstName(), person.getLastName());
//		return "xxxxxxxxxxxxxxxxxxxxxx";
		return  String.format("%s %s %s", person.getTitle(), person.getFirstName(), person.getLastName());
	}
}
