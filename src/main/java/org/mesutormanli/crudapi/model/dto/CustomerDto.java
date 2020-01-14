package org.mesutormanli.crudapi.model.dto;

import java.io.Serializable;
import java.util.Objects;

public class CustomerDto implements Serializable {
    private Long id;
    private String name;
    private Integer age;
    private String address;
    private String telephone;
    private String email;
    private String nationality;
    private String maritalStatus;

    public CustomerDto id(Long id) {
        this.id = id;
        return this;
    }

    public CustomerDto name(String name) {
        this.name = name;
        return this;
    }

    public CustomerDto age(Integer age) {
        this.age = age;
        return this;
    }


    public CustomerDto address(String address) {
        this.address = address;
        return this;
    }

    public CustomerDto telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public CustomerDto email(String email) {
        this.email = email;
        return this;
    }

    public CustomerDto nationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public CustomerDto maritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
        return this;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public String getNationality() {
        return nationality;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDto that = (CustomerDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(age, that.age) &&
                Objects.equals(address, that.address) &&
                Objects.equals(telephone, that.telephone) &&
                Objects.equals(email, that.email) &&
                Objects.equals(nationality, that.nationality) &&
                Objects.equals(maritalStatus, that.maritalStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, address, telephone, email, nationality, maritalStatus);
    }

    @Override
    public String toString() {
        return "CustomerDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", nationality='" + nationality + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                '}';
    }
}
