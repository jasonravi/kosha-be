package com.dreamx.kosha.entity;


import com.dreamx.kosha.constants.EmploymentType;
import com.dreamx.kosha.constants.FinancialProfile;
import com.dreamx.kosha.constants.Gender;
import com.dreamx.kosha.constants.MaritalStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status", nullable = true)
    private MaritalStatus maritalStatus;

    private short age;

    @Column(name = "number_of_dependents", nullable = true)
    private short numberOfDependents;

    // One user can have many financial instruments
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<UserFinancialInstrument> userFinancialInstruments;

    @Column(name = "mobile_number", nullable = false, unique = true)
    private String mobileNumber;

    @Column(name = "employment_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    private short status;

    @Column(name = "financial_profile")
    @Enumerated(EnumType.STRING)
    private FinancialProfile financialProfile;

    @Column(name = "financial_health_score")
    private Double financialHealthScore;

    @Column(name = "demographic_health_score")
    private Double demographicHealthScore;


    public User(Long id) {
        this.id = id;
    }
}