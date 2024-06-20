package com.project.Retil.til.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TilProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false, name = "til_id")
    private Til til;

    @Column
    private Boolean aDay;

    @Column
    private Boolean threeDays;

    @Column
    private Boolean aWeek;

    @Column
    private Boolean fifteenDays;

    @Column
    private Boolean aMonth;

    @Column
    private Boolean twoMonths;

    @Column
    private Boolean sixMonths;

    public TilProgress(Til til) {
        this.til = til;
        this.aDay = false;
        this.threeDays = false;
        this.aWeek = false;
        this.fifteenDays = false;
        this.aMonth = false;
        this.twoMonths = false;
        this.sixMonths = false;
    }

    public void changeADay() {
        this.aDay = true;
    }

    public void changeThreeDays() {
        this.threeDays = true;
    }

    public void changeAWeek() {
        this.aWeek = true;
    }

    public void changeFifteenDays() {
        this.fifteenDays = true;
    }

    public void changeAMonth() {
        this.aMonth = true;
    }

    public void changeTwoMonths() {
        this.twoMonths = true;
    }

    public void changeSixMonths() {
        this.sixMonths = true;
    }
}