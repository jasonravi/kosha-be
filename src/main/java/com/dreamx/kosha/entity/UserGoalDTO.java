package com.dreamx.kosha.entity;


import com.dreamx.kosha.constants.Priority;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "goal")
public class UserGoalDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "target_amount", nullable = false)
    private Double targetAmount;

    @Column(name = "achive_amount")
    private Double achiveAmount;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "priority", nullable = false)
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(name = "logo")
    private String logo;

    @Column(name = "description")
    private String description;

    public UserGoalDTO(Long id, String name, Double targetAmount, Double achieveAmount, int duration, String logo, Priority priority, String description) {
        this.id = id;
        this.name = name;
        this.targetAmount = targetAmount;
        this.achiveAmount = achieveAmount;
        this.duration = duration;
        this.logo = logo;
        this.priority = priority;
        this.description = description;
    }


}
