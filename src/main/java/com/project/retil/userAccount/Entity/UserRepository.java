package com.project.Retil.userAccount.Entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface UserRepository extends JpaRepository<User_Information, Long>{
	@Override
	ArrayList<User_Information> findAll();
	boolean existsByEmail(String email);

	User_Information findByEmail(String email);
}
