package org.group2.webapp.repository;

import org.group2.webapp.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@SuppressWarnings("unused")
public interface ClaimRepository extends JpaRepository<Claim, Long> {

    @Query("select c from Claim c, User u, Faculty f where c.user.id = u.id and u.faculty.id = f.id and f.id = ?1")
    List<Claim> findAllByFacultyId(Long facultyId);

    @Query("select c from Claim c where YEAR(c.created_time) = ?1")
    List<Claim> findAllByYear(int year);

    @Query("select c from Claim c, User u, Faculty f where c.processed_time != NULL and c.user.id = u.id and u.faculty.id = f.id and f.id = ?1")
    List<Claim> findAllProcessedByFacultyId(Long facultyId);

    @Query("select c from Claim c join c.circumstances cir where cir.id = ?1")
    List<Claim> findAllByCircumstanceId(Long circumstanceId);
}
