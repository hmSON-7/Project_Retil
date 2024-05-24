package com.project.Retil.til.entity;

import com.project.Retil.userAccount.Entity.User_Information;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TilSubject {

    @Id
    private String subjectId;

    @Column
    private int subjectNum = 0;

    @Column
    private String subjectName;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User_Information user;

    public TilSubject(String subjectName, User_Information user) {
        this.subjectNum++;
        this.subjectId = user.getNickname() + "-" + subjectNum;
        this.subjectName = subjectName;
        this.user = user;
    }
}
