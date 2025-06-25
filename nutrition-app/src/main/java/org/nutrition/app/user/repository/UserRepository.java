package org.nutrition.app.user.repository;

import jakarta.transaction.Transactional;
import org.nutrition.app.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @NonNull
    Page<User> findAll(@NonNull final Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "delete from User u where u.id=:id")
    Integer deleteByIdReturning(final UUID id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "update User u set u.password=:newPassword where u.id=:id ")
    Integer updateUserPassword(final UUID id, final String newPassword);
}

