package com.StreamCatch.app.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Content {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid" , strategy = "uuid2")
	private String id;
	private String name;
	@OneToOne
	private Platform idPlatform;
	@Lob
	@Column(columnDefinition="MEDIUMBLOB")
	private String image;

	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
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
	
	public Platform getIdPlatform() {
		return idPlatform;
	}
	public void setIdPlatform(Platform idPlatform) {
		this.idPlatform = idPlatform;
	}
	
	

}
