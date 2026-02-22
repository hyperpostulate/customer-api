package org.mesutormanli.customerapi.model.dto;

import java.io.Serializable;

public record CustomerDto(
        Long id,
        String name,
        String surname,
        Integer age,
        String address,
        String telephone,
        String email,
        String nationality,
        String maritalStatus) implements Serializable {

    public CustomerDto() {
        this(null, null, null, null, null, null, null, null, null);
    }

    public static CustomerDtoBuilder builder() {
        return new CustomerDtoBuilder();
    }

    public static class CustomerDtoBuilder {
        private Long id;
        private String name;
        private String surname;
        private Integer age;
        private String address;
        private String telephone;
        private String email;
        private String nationality;
        private String maritalStatus;

        CustomerDtoBuilder() {
        }

        public CustomerDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CustomerDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CustomerDtoBuilder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public CustomerDtoBuilder age(Integer age) {
            this.age = age;
            return this;
        }

        public CustomerDtoBuilder address(String address) {
            this.address = address;
            return this;
        }

        public CustomerDtoBuilder telephone(String telephone) {
            this.telephone = telephone;
            return this;
        }

        public CustomerDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public CustomerDtoBuilder nationality(String nationality) {
            this.nationality = nationality;
            return this;
        }

        public CustomerDtoBuilder maritalStatus(String maritalStatus) {
            this.maritalStatus = maritalStatus;
            return this;
        }

        public CustomerDto build() {
            return new CustomerDto(id, name, surname, age, address, telephone, email, nationality, maritalStatus);
        }
    }
}
