package org.mesutormanli.crudapi.model.entity;


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
        CustomerEntity entity = (CustomerEntity) o;
        return Objects.equals(id, entity.id) &&
                Objects.equals(name, entity.name) &&
                Objects.equals(age, entity.age) &&
                Objects.equals(address, entity.address) &&
                Objects.equals(telephone, entity.telephone) &&
                Objects.equals(email, entity.email) &&
                Objects.equals(nationality, entity.nationality) &&
                Objects.equals(maritalStatus, entity.maritalStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, address, telephone, email, nationality, maritalStatus);
    }

    @Override
    public String toString() {
        return "CustomerEntity{" +
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
