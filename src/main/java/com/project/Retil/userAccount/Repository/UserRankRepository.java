package com.project.Retil.userAccount.Repository;

import com.project.Retil.userAccount.Entity.User_Information;
import com.project.Retil.userAccount.Entity.User_Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRankRepository extends JpaRepository<User_Rank, Long> {
    User_Rank findByUser(User_Information user);
}
