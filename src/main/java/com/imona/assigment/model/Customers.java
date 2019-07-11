package com.imona.assigment.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Customers implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String Name;
    private String Surname;
    private Boolean Gender;
    private Date birthDate;
    private String birthCity;
    private String Channels;
    private Boolean isActive;

    public Customers(String name, String surname, Boolean gender, Date birthDate, String birthCity, String channels, Boolean isActive) {
        Name = name;
        Surname = surname;
        Gender = gender;
        this.birthDate = birthDate;
        this.birthCity = birthCity;
        Channels = channels;
        this.isActive = isActive;
    }

    public Customers() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public Boolean getGender() {
        return Gender;
    }

    public void setGender(Boolean gender) {
        Gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthCity() {
        return birthCity;
    }

    public void setBirthCity(String birthCity) {
        this.birthCity = birthCity;
    }

    public String getChannels() {
        return Channels;
    }

    public void setChannels(String channels) {
        Channels = channels;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
