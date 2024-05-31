package com.project.Retil.til.entity;

import com.project.Retil.userAccount.Entity.User_Information;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TilSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String subjectName;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User_Information user;

    @Column
    private Color color; // 확정 아님

    @Column
    private Long studyTime;

    public TilSubject(String subjectName, User_Information user, Color color, Long studyTime) {
        this.subjectName = subjectName;
        this.user = user;
        this.color = color;
        this.studyTime = studyTime;
    }
}
