package com.travelapp.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "properties_dbs")
public class Properties {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;
	@Column(name = "Title")
	private String title;
	@Column(name = "Type")
    private String type;
	@Column(name = "Address")
    private String address;
	@Column(name = "Description")
    private String description;
	@Column(name = "PicPath")
    private String picPath;
	@Column(name = "Price")
    private int price;
	@Column(name = "Bed")
    private int bed;
	@Column(name = "Bath")
    private int bath;
	@Column(name = "Area")
    private int area;
	@Column(name = "Garage")
    private boolean garage;
	@Column(name = "Score")
    private double score;
	@Column(name = "IsFavorite")
	private boolean isFav;

	public boolean isFav() {
		return isFav;
	}
	public void setFav(boolean isFav) {
		this.isFav = isFav;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getBed() {
		return bed;
	}
	public void setBed(int bed) {
		this.bed = bed;
	}
	public int getBath() {
		return bath;
	}
	public void setBath(int bath) {
		this.bath = bath;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public boolean isGarage() {
		return garage;
	}
	public void setGarage(boolean garage) {
		this.garage = garage;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	@Override
	public String toString() {
		return "Properties [id=" + id + ", title=" + title + ", type=" + type + ", address=" + address
				+ ", description=" + description + ", picPath=" + picPath + ", price=" + price + ", bed=" + bed
				+ ", bath=" + bath + ", area=" + area + ", garage=" + garage + ", score=" + score + ", isFav=" + isFav
				+ "]";
	}
	
	
}
