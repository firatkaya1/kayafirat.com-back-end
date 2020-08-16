package com.firatkaya.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.firatkaya.entity.User;
import com.firatkaya.entity.UserPermissions;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByUserEmail(String email);

    User findByUserId(String userId);

    User findByUserName(String userName);

    boolean existsByUserName(String username);

    boolean existsByUserEmail(String email);

    boolean existsByUserId(String userId);

    @Query(value = "SELECT * FROM user where "
            + "user_email LIKE (CONCAT(:keyword,'%')) or "
            + "user_email LIKE (CONCAT('%',:keyword)) or "
            + "user_email LIKE (CONCAT('%',:keyword,'%')) or "
            + "user_name LIKE (CONCAT(:keyword,'%')) or "
            + "user_name LIKE (CONCAT('%',:keyword)) or "
            + "user_name LIKE (CONCAT('%',:keyword,'%')) LIMIT 10 ", nativeQuery = true)
    <T> Collection<T> searchByUsernameAndUseremail(@Param("keyword") String keyword, Class<T> type);

    @Modifying
    @Query(value = "UPDATE user SET user_password = :password  WHERE user_email = :email", nativeQuery = true)
    void updateUserPassword(@Param("email") String email, @Param("password") String password);

    @Modifying
    @Query(value = "UPDATE user_permissions SET "
            + "is_aboutme_show = :#{#user.aboutmeShow},"
            + "is_all_comment_show = :#{#user.allCommentShow},"
            + "is_all_fav_show = :#{#user.allFavShow},"
            + "is_birthdate_show = :#{#user.birthdateShow},"
            + "is_contact_show = :#{#user.contactShow},"
            + "is_github_show = :#{#user.githubShow},"
            + "is_last_seen_show = :#{#user.lastSeenShow},"
            + "is_linkedin_show = :#{#user.linkedinShow},"
            + "is_name_show = :#{#user.nameShow},"
            + "is_registerdate_show = :#{#user.registerdateShow} "
            + "WHERE user_email = :#{#user.userEmail} ", nativeQuery = true)
    void updateUserPermissions(@Param("user") UserPermissions userPermissions);

    @Query(value = "SELECT EXISTS(SELECT * FROM user WHERE user_email=:email and user_id = :userId and is_verification = 0);", nativeQuery = true)
    int existsByUserEmailandUserId(@Param("email") String email, @Param("userId") String userId);

    @Modifying
    @Query(value = "UPDATE user SET user_birthday_date = :birthdate  WHERE user_email = :email", nativeQuery = true)
    void updateUserBirthDate(@Param("email") String email, @Param("birthdate") String birthdate);

    @Modifying
    @Query(value = "UPDATE user SET user_name = :username  WHERE user_email = :email", nativeQuery = true)
    void updateUserUsernameOnUser(@Param("email") String email, @Param("username") String username);

    @Modifying
    @Query(value = "UPDATE comment SET user_name = :username  WHERE user_name = :username", nativeQuery = true)
    void updateUserUsernameOnComment(@Param("username") String username);

    @Modifying
    @Query(value = "UPDATE user_profile SET user_github = :username  WHERE user_email = :email", nativeQuery = true)
    void updateGithubAddress(@Param("email") String email, @Param("username") String githubAddress);

    @Modifying
    @Query(value = "UPDATE user_profile SET user_linkedin = :userlinkedin  WHERE user_email = :email", nativeQuery = true)
    void updateLinkedinAddress(@Param("email") String email, @Param("userlinkedin") String userlinkedin);

    @Modifying
    @Query(value = "UPDATE user SET user_profile_photo = :path  WHERE user_id = :userId", nativeQuery = true)
    void updateUserPhoto(@Param("userId") String userId, @Param("path") String path);



    @Query(value = "SELECT user_email, user_password FROM user where user_email=:email", nativeQuery = true)
    public List<Object[]> findUser(@Param("email") String email);


}
