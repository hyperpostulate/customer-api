package org.mesutormanli.customerapi.model.entity;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "CUSTOMER")
public class CustomerEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private Integer age;

    @Column
    private String address;

    @Column
    private String telephone;

    @Column
    private String email;

    @Column
    private String nationality;

    @Column
    private String maritalStatus;

    public Long getId() {
        return id;
    }

    public CustomerEntity id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CustomerEntity name(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public CustomerEntity surname(String surname) {
        this.surname = surname;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public CustomerEntity age(Integer age) {
        this.age = age;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public CustomerEntity address(String address) {
        this.address = address;
        return this;
    }

    public String getTelephone() {
        return telephone;
    }

    public CustomerEntity telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public CustomerEntity email(String email) {
        this.email = email;
        return this;
    }

    public String getNationality() {
        return nationality;
    }

    public CustomerEntity nationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public CustomerEntity maritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerEntity that = (CustomerEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(age, that.age) &&
                Objects.equals(address, that.address) &&
                Objects.equals(telephone, that.telephone) &&
                Objects.equals(email, that.email) &&
                Objects.equals(nationality, that.nationality) &&
                Objects.equals(maritalStatus, that.maritalStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, age, address, telephone, email, nationality, maritalStatus);
    }

    @Override
    public String toString() {
        return "CustomerEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", nationality='" + nationality + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                '}';
    }
}
