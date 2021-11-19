package com.StreamCatch.app.Entity;




import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import com.StreamCatch.app.rol.Rol;




@Entity
public class Users {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid" , strategy = "uuid2")
	private String id;
	private String name;
	private String surname;
	private String email;
	private String password;
	private Boolean status;
	
	@Enumerated(EnumType.STRING)
	private Rol rol;
	
//	@OneToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	private List<Platform> platforms = new ArrayList<>();
	
	@ManyToOne
	private Content content;

	
//	
//	public List<Platform> getPlatforms() {
//		return platforms;
//	}
//	public void setPlatforms(List<Platform> platforms) {
//		this.platforms = platforms;
//	}

		public Users() {
		super();
		
	}
	
	public Users(String id, String name, String surname, String email, String password, Boolean status, Rol rol,
		Content content) {
	super();
	this.id = id;
	this.name = name;
	this.surname = surname;
	this.email = email;
	this.password = password;
	this.status = status;
	this.rol = rol;
	this.content = content;
}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Content getContent() {
		return content;
	}
	public void setContent(Content content) {
		this.content = content;
	}




}
