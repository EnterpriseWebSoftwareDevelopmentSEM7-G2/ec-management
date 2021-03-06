package org.group2.webapp.web.rest.admin;

import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.group2.webapp.entity.Faculty;
import org.group2.webapp.service.FacultyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class FacultyAPI {

    private final Logger log = LoggerFactory.getLogger(FacultyAPI.class);

    private static final String ENTITY_NAME = "faculty";

    private final FacultyService facultyService;

    public FacultyAPI(FacultyService facultyService) {
        this.facultyService = facultyService;
    }


    @PostMapping("/faculties")
    public ResponseEntity<Faculty> createFaculty(@Valid @RequestBody Faculty faculty) throws URISyntaxException {
        log.debug("REST request to save Faculty : {}", faculty);
        if (faculty.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        Faculty result = facultyService.create(faculty);
        return ResponseEntity.ok(result);
    }


    @PutMapping("/faculties")
    public ResponseEntity<Faculty> updateFaculty(@Valid @RequestBody Faculty faculty) throws URISyntaxException {
        log.debug("REST request to update Faculty : {}", faculty);
        if (faculty.getId() == null) {
            return createFaculty(faculty);
        }
        Faculty result = facultyService.update(faculty);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/faculties")
    public List<Faculty> getAllFacultys() {
        log.debug("REST request to get all Facultys");
        return facultyService.findAll();
    }


    @GetMapping("/faculties/{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        log.debug("REST request to get Faculty : {}", id);
        Faculty faculty = facultyService.findOne(id);
        if(faculty == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(faculty);
    }

    @DeleteMapping("/faculties/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        log.debug("REST request to delete Faculty : {}", id);
        facultyService.delete(id);
        return ResponseEntity.ok().build();
    }

}
