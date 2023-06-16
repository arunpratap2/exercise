package com.synchrony.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Image")
public class Image {
    private static final long serialVersionUID = 455455672134L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "image_url")
    private String imageURL;

    @ManyToOne
    @JoinColumn(name="user_name", nullable=false)
    private User user;

    private String username;

    public Image(){

    }
    public Image(String username, String imageURL) {
        this.username = username;
        this.imageURL = imageURL;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
