package com.project.retil.user.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.ArrayList;

public interface UserRepository extends JpaRepository<User_Information, Long>{
	@Override
	ArrayList<User_Information> findAll();
	boolean existsByEmail(String email);

	User_Information findByEmail(String email);
}
