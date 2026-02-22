package org.mesutormanli.customerapi.model.request;

import java.io.Serializable;

public record CustomerRequest(
        String name,
        String surname,
        Integer age,
        String address,
        String telephone,
        String email,
        String nationality,
        String maritalStatus) implements Serializable {

    public CustomerRequest() {
        this(null, null, null, null, null, null, null, null);
    }

    public static CustomerRequestBuilder builder() {
        return new CustomerRequestBuilder();
    }

    public static class CustomerRequestBuilder {
        private String name;
        private String surname;
        private Integer age;
        private String address;
        private String telephone;
        private String email;
        private String nationality;
        private String maritalStatus;

        CustomerRequestBuilder() {
        }

        public CustomerRequestBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CustomerRequestBuilder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public CustomerRequestBuilder age(Integer age) {
            this.age = age;
            return this;
        }

        public CustomerRequestBuilder address(String address) {
            this.address = address;
            return this;
        }

        public CustomerRequestBuilder telephone(String telephone) {
            this.telephone = telephone;
            return this;
        }

        public CustomerRequestBuilder email(String email) {
            this.email = email;
            return this;
        }

        public CustomerRequestBuilder nationality(String nationality) {
            this.nationality = nationality;
            return this;
        }

        public CustomerRequestBuilder maritalStatus(String maritalStatus) {
            this.maritalStatus = maritalStatus;
            return this;
        }

        public CustomerRequest build() {
            return new CustomerRequest(name, surname, age, address, telephone, email, nationality, maritalStatus);
        }
    }
}
