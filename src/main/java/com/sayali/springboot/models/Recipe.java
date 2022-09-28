package com.sayali.springboot.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Recipe {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int servings;
	private int userID;
	private String name;
	private String instructions;
	private String ingredients;
	private int veg;
	
	public int getVeg() {
		return veg;
	}
	public void setVeg(int veg) {
		this.veg = veg;
	}
	/*
	 * @Column(name = "isVeg", columnDefinition = "BOOLEAN") private boolean isVeg;
	 * 
	 * public boolean isVeg() { return isVeg; } public void setVeg(boolean isVeg) {
	 * this.isVeg = isVeg; }
	 */
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getServings() {
		return servings;
	}
	public void setServings(int servings) {
		this.servings = servings;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInstructions() {
		return instructions;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	public String getIngredients() {
		return ingredients;
	}
	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

}
