package com.petros.mailapplication.model;

import com.petros.mailapplication.Util.MyCrypting;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity(name="users")
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="first_name",nullable = false)
    private String firstName;
    @Column(name="last_name",nullable = false)
    private String lastName;
    @Column(name="email",nullable = false)
    private String email;
    @Column(name="password",nullable = false)
    private String password;
    @Column(name="emailPassword",nullable=false)
    private String emailPassword;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection < Role > roles;

    @OneToMany(targetEntity = Mail.class,cascade = CascadeType.ALL,mappedBy = "user")
    private List<Mail> mails;

    public User() {}

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User(String firstName, String lastName, String email, String password,String emailPassword, Collection < Role > roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        setEmailPassword(emailPassword);
        this.roles = roles;
    }

    public User(String firstName, String lastName, String email, String password, Collection<Role> roles, List<Mail> mails) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.mails = mails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection < Role > getRoles() {
        return roles;
    }

    public void setRoles(Collection < Role > roles) {
        this.roles = roles;
    }

    public List<Mail> getMails(int tip) {
        return mails.stream().filter(x->x.getTip()==tip).collect(Collectors.toList());
    }

    public void setMails(List<Mail> mails) {
        this.mails = mails;
    }

    public void addMails(Collection<Mail> mails){
        this.mails.addAll(mails);
        Collections.sort(this.mails);
    }

    public String getEmailPassword() {
        try {
            return MyCrypting.decrypt(emailPassword);
        }catch (Exception e){
            e.printStackTrace();
        }
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        try {
            this.emailPassword = MyCrypting.encrypt(emailPassword);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", emailPassword='" + emailPassword + '\'' +
                ", roles=" + roles +
                ", mails=" + mails +
                '}';
    }
}