package org.group2.webapp.repository;

import java.util.List;

import org.group2.webapp.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@SuppressWarnings("unused")
public interface ClaimRepository extends JpaRepository<Claim, Long> {

    @Query("select c from Claim c, User u, Faculty f where c.user.id = u.id and u.faculty.id = f.id and f.id = ?1")
    List<Claim> findAllByFacultyId(Long facultyId);

    @Query("select c from Claim c where YEAR(c.created_time) = ?1")
    List<Claim> findAllByYear(int year);

    @Query("select c from Claim c, User u, Faculty f where c.processed_time IS NOT  NULL and c.user.id = u.id and u.faculty.id = f.id and f.id = ?1")
    List<Claim> findAllProcessedByFacultyId(Long facultyId);

    @Query("select c from Claim c join c.circumstances cir where cir.id = ?1")
    List<Claim> findAllByCircumstanceId(Long circumstanceId);


    @Query("select c from Claim c where MONTH(c.created_time) = ?1 and YEAR(c.created_time) = ?2")
    List<Claim> findAllByThisMonth(int month,int year);

    @Query("select c from Claim c where WEEK(c.created_time) = ?1 and MONTH(c.created_time) = ?2 and YEAR(c.created_time) = ?3")
    List<Claim> findAllByThisWeek(int week,int month,int year);

    @Query("select c from Claim c where c.user.id=?1")
    List<Claim> findAllByUserId(Long userId);

    @Query("select c from Claim c where c.status=1")
    List<Claim> findAllNotBeProcessed();
    
    @Query("select c from Claim c where c.canUploadMoreEvidence=true and c.status=1")
    List<Claim> findAllNotBeOverUploadEvidence();
}
