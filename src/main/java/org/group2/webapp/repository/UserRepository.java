package org.group2.webapp.repository;

import java.util.List;
import java.util.Optional;

import org.group2.webapp.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByEmail(String email);

    Optional<User> findOneByUsername(String username);

    @EntityGraph(attributePaths = "authorities")
    User findOneWithAuthoritiesById(Long id);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByUsername(String username);

    @Query("select u from User u join u.authorities au where name=?1")
    List<User> findAllUserByAuthority(String authoritiesConstants);
    
    @Query("select u from User u join u.authorities au where name=? and u.faculty.id=?")
    List<User> findAllUserByAuthorityAndFacultyId(String authoritiesConstants, long facultyId);
}
