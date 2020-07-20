package com.firatkaya.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.firatkaya.model.User;

@Repository
public interface UserRepository  extends JpaRepository<User,String>  {
	
	User findByUserEmail(String email);
	
	User findByUserId(String userId);
	
	@Query(value="SELECT EXISTS(SELECT * FROM user WHERE user_email=:email and user_id = :userId);",nativeQuery = true)
	int existsByUserEmailandUserId(@Param("email") String email,@Param("userId") String userId);
	
	boolean existsByUserEmail(String email);
	
	boolean existsByUserId(String userId);
	
	@Query(value="SELECT * FROM user where "
			+ "user_email LIKE (CONCAT(:keyword,'%')) or "
			+ "user_email LIKE (CONCAT('%',:keyword)) or "
			+ "user_email LIKE (CONCAT('%',:keyword,'%')) or "
			+ "user_name LIKE (CONCAT(:keyword,'%')) or "
			+ "user_name LIKE (CONCAT('%',:keyword)) or "
			+ "user_name LIKE (CONCAT('%',:keyword,'%')) LIMIT 10 ",nativeQuery = true)	
	<T> Collection<T> searchByUsernameAndUseremail(@Param("keyword") String keyword,Class<T> type);
	
	@Modifying
	@Query(value = "UPDATE user SET user_password = :password  WHERE user_email = :email",nativeQuery = true)
	void updateUserPassword(@Param("email") String email,@Param("password") String password);
	
	
}
