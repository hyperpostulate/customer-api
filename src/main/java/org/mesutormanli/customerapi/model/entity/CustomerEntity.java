package org.mesutormanli.customerapi.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "CUSTOMER")
@SQLDelete(sql = "UPDATE CUSTOMER SET deleted = true WHERE id=?")
@SQLRestriction("status <> 'DELETED'")
public final class CustomerEntity {

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

    @Column
    private boolean deleted = Boolean.FALSE;

    public CustomerEntity() {
    }

    public CustomerEntity(Long id, String name, String surname, Integer age, String address, String telephone,
            String email, String nationality, String maritalStatus, boolean deleted) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
        this.nationality = nationality;
        this.maritalStatus = maritalStatus;
        this.deleted = deleted;
    }

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        CustomerEntity that = (CustomerEntity) o;

        if (deleted != that.deleted)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null)
            return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null)
            return false;
        if (age != null ? !age.equals(that.age) : that.age != null)
            return false;
        if (address != null ? !address.equals(that.address) : that.address != null)
            return false;
        if (telephone != null ? !telephone.equals(that.telephone) : that.telephone != null)
            return false;
        if (email != null ? !email.equals(that.email) : that.email != null)
            return false;
        if (nationality != null ? !nationality.equals(that.nationality) : that.nationality != null)
            return false;
        return maritalStatus != null ? maritalStatus.equals(that.maritalStatus) : that.maritalStatus == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (nationality != null ? nationality.hashCode() : 0);
        result = 31 * result + (maritalStatus != null ? maritalStatus.hashCode() : 0);
        result = 31 * result + (deleted ? 1 : 0);
        return result;
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
                ", deleted=" + deleted +
                '}';
    }

    public static CustomerEntityBuilder builder() {
        return new CustomerEntityBuilder();
    }

    public static class CustomerEntityBuilder {
        private Long id;
        private String name;
        private String surname;
        private Integer age;
        private String address;
        private String telephone;
        private String email;
        private String nationality;
        private String maritalStatus;
        private boolean deleted;

        CustomerEntityBuilder() {
        }

        public CustomerEntityBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CustomerEntityBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CustomerEntityBuilder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public CustomerEntityBuilder age(Integer age) {
            this.age = age;
            return this;
        }

        public CustomerEntityBuilder address(String address) {
            this.address = address;
            return this;
        }

        public CustomerEntityBuilder telephone(String telephone) {
            this.telephone = telephone;
            return this;
        }

        public CustomerEntityBuilder email(String email) {
            this.email = email;
            return this;
        }

        public CustomerEntityBuilder nationality(String nationality) {
            this.nationality = nationality;
            return this;
        }

        public CustomerEntityBuilder maritalStatus(String maritalStatus) {
            this.maritalStatus = maritalStatus;
            return this;
        }

        public CustomerEntityBuilder deleted(boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        public CustomerEntity build() {
            return new CustomerEntity(id, name, surname, age, address, telephone, email, nationality, maritalStatus,
                    deleted);
        }
    }
}
