package com.StreamCatch.app.Entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


import org.hibernate.annotations.GenericGenerator;

@Entity
public class Platform {
	
    @Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid" , strategy = "uuid2")
    private String id;
    private String name;
    private double price;

   
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
    


}
