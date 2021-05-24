package com.eCom.eCom.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {

  @Id
  String name;
  String category;
  float price;
  String publishedOn;
  int isOnSale;
  int isNew;
  int isInStock;

  public int getIsOnSale() {
    return isOnSale;
  }

  public void setIsOnSale(int isOnSale) {
    this.isOnSale = isOnSale;
  }

  public int getIsNew() {
    return isNew;
  }

  public void setIsNew(int isNew) {
    this.isNew = isNew;
  }

  public int getIsInStock() {
    return isInStock;
  }

  public void setIsInStock(int isInStock) {
    this.isInStock = isInStock;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public String getPublishedOn() {
    return publishedOn;
  }

  public void setPublishedOn(String publishedOn) {
    this.publishedOn = publishedOn;
  }

}
