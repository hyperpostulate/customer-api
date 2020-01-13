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
    private int age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomerEntity name(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public CustomerEntity age(int age) {
        this.age = age;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerEntity entity = (CustomerEntity) o;
        return age == entity.age &&
                Objects.equals(id, entity.id) &&
                Objects.equals(name, entity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }

    @Override
    public String toString() {
        return "CustomerEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
