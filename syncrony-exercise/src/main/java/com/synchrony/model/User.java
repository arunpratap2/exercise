package com.synchrony.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

  private static final long serialVersionUID = 4455672134L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "first_name")
  private String firstName;


  @Column(name = "last_name")
  private String lastName;
  @Column(name = "user_name")
  private String userName;

  @Column(name = "password")
  private String password;


  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "image_url",referencedColumnName = "id")
  private List<Image> imageDetails = new ArrayList<>();
  public User() {

  }

  public User(String firstName, String lastName, String userName, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
    this.password = password;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Image> getImageDetails() {
    return imageDetails;
  }
  public void setImageDetails(List<Image> imageDetails) {
     this.imageDetails = imageDetails;
  }

}
