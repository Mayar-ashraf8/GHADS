/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author hp
 */
@Entity
@Table(name = "user")
public class User {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "user_id")
    private int userId;
     
    @Column(unique = true)
    private String username;
    
    private String password;
    @Column(name = "full_name")
    private String fullName;
    
    private String email;
    private String role;
    
     @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;
     
     
    //private int orgId;

    public User() {
    }

    public User(int userId, String username, String password, String fullName, String email, String role, Organization organization) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.organization = organization;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

  
    /*@Override
    public String toString() {
        return "User{" + "userId=" + userId + ", username=" + username + ", password=" + password + ", fullName=" + fullName + ", email=" + email + ", role=" + role + ", organization=" + organization + '}';
    }*/

    @Override
    public String toString() {
        //return String.valueOf(userId);
        return username;
    }
    

    
}
