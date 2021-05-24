package com.eCom.eCom.entities;

import javax.annotation.Nonnull;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class WebsiteUser {

  @Id
  private String name;
  private String email;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public WebsiteUser() {
    this.name = "";
    this.email = "";
  }

  public WebsiteUser(String name) {
    this.name = name;
    this.email = "";
  }

  public WebsiteUser(String name, String email) {
    this.name = name;
    this.email = email;
  }

  @Override
  public String toString() {
    return "WebsiteUser [email=" + email + ", name=" + name + "]";
  }
}
