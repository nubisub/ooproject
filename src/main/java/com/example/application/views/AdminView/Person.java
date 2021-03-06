package com.example.application.views.AdminView;

import org.apache.tomcat.jni.Address;
import java.util.Date;
import javax.annotation.Nonnull;


// tag::snippet[]
public class Person {

  @Nonnull
  private String firstName;

  @Nonnull
  private String lastName;

  @Nonnull
  private String email;

  @Nonnull
  private Date birthday;

  @Nonnull
  private String id;

  @Nonnull
  private boolean subscriber;

  @Nonnull
  private String membership;

  @Nonnull
  private String pictureUrl;

  @Nonnull
  private String profession;

  @Nonnull
  private Address address;

  private Integer managerId;

  @Nonnull
  private boolean manager;

  @Nonnull
  private String status;

  private int prioritas;

  public Person(String nicolaus_copernicus, String i, String status, int prioritas) {
    this.firstName = nicolaus_copernicus;
    this.id = i;
    this.status = status;
    this.prioritas = prioritas;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFullName() {
    return firstName + " " + lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public boolean isSubscriber() {
    return subscriber;
  }

  public void setSubscriber(boolean subscriber) {
    this.subscriber = subscriber;
  }

  public String getMembership() {
    return membership;
  }

  public void setMembership(String membership) {
    this.membership = membership;
  }

  public String getPictureUrl() {
    return pictureUrl;
  }

  public void setPictureUrl(String pictureUrl) {
    this.pictureUrl = pictureUrl;
  }

  public String getProfession() {
    return profession;
  }

  public void setProfession(String profession) {
    this.profession = profession;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }



  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Person)) {
      return false;
    }
    Person other = (Person) obj;
    return id == other.id;
  }

  public Integer getManagerId() {
    return managerId;
  }

  public void setManagerId(Integer managerId) {
    this.managerId = managerId;
  }

  public boolean isManager() {
    return manager;
  }

  public void setManager(boolean manager) {
    this.manager = manager;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

    public int getPrioritas() {
        return prioritas;
    }

  public void setPrioritas(int prioritas) {
    this.prioritas = prioritas;
  }
}
// end::snippet[]
