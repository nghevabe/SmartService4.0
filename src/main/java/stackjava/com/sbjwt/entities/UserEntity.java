package stackjava.com.sbjwt.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = { "roles", "authorities" })
@Entity
@Table(name = "user_account")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "user_name")
	private String username;

	@Column(name = "pass_word")
	private String password;

	@Column(name = "full_name")
	private String fullname;

	@Column(name = "phone")
	private String phone;

	@Column(name = "mail")
	private String mail;

	@Column(name = "address")
	private String address;

	@Column(name = "role")
	private String role;

	public UserEntity() {
	}

	public UserEntity(int id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public UserEntity(int id, String username, String password, String fullname,
					  String phone, String mail, String address, String role) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.phone = phone;
		this.mail = mail;
		this.address = address;
		this.role = role;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() { return fullname; }

	public void setFullname(String fullname) { this.fullname = fullname; }

	public String getPhone() { return phone; }

	public void setPhone(String phone) { this.phone = phone; }

	public String getMail() { return mail; }

	public void setMail(String mail) { this.mail = mail; }

	public String getAddress() { return address; }

	public void setAddress(String address) { this.address = address; }

	public String getRole() { return role; }

	public void setRole(String role) { this.role = role; }
}
