package com.bamoya.whatsapp_clone.repository;

import com.bamoya.whatsapp_clone.model.user.User;
import com.bamoya.whatsapp_clone.model.user.UserConstants;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  @Query(name = UserConstants.FIND_USER_BY_EMAIL)
  Optional<User> findByEmail(String email);

  @Query(name = UserConstants.FIND_USER_BY_PUBLIC_ID)
  Optional<User> findUserByPublicId(@Param("publicId") String senderId);

  @Query(name = UserConstants.FIND_ALL_USERS_EXCEPT_SELF)
  List<User> findAllUsersExceptSelf(String publicId);
}
