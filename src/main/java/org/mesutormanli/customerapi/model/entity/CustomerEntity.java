package org.mesutormanli.customerapi.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CUSTOMER")
@SQLDelete(sql = "UPDATE CUSTOMER SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
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

    @Column
    private boolean deleted = Boolean.FALSE;

}
