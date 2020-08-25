package converter;

import java.io.Serializable;
import java.util.Date;

public class StudentdDto implements Serializable {

	String firstname;
	String email;
	String lastname;
	Integer age;
	Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Override
	public String toString() {
		return "StudentdDto [firstname=" + firstname + ", email=" + email + ", lastname=" + lastname + ", age=" + age
				+ ", date=" + date + "]";
	}

}
