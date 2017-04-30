package org.group2.webapp.web.rest.admin;

import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.group2.webapp.entity.Claim;
import org.group2.webapp.service.ClaimService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class ClaimAPI {

    private final Logger log = LoggerFactory.getLogger(ClaimAPI.class);

    private static final String ENTITY_NAME = "claim";

    private final ClaimService claimService;

    public ClaimAPI(ClaimService claimService) {
        this.claimService = claimService;
    }


    @PostMapping("/claims")
    public ResponseEntity<Claim> createClaim(@Valid @RequestBody Claim claim) throws URISyntaxException {
        log.debug("REST request to save Claim : {}", claim);
        if (claim.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        Claim result = claimService.create(claim);
        return ResponseEntity.ok(result);
    }


    @PutMapping("/claims")
    public ResponseEntity<Claim> updateClaim(@Valid @RequestBody Claim claim) throws URISyntaxException {
        log.debug("REST request to update Claim : {}", claim);
        if (claim.getId() == null) {
            return createClaim(claim);
        }
        Claim result = claimService.update(claim);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/claims")
    public ResponseEntity<List<Claim>> getAllClaims() {
        log.debug("REST request to get all Claims");
        return ResponseEntity.ok(claimService.findAll());
    }

    @GetMapping("/claims/year")
    public ResponseEntity<List<Claim>> getAllClaimsByYear(@RequestParam String year) {
        log.debug("REST request to get all Claims by year");
        if( year == null || !StringUtils.isNumeric(year))
            return ResponseEntity.badRequest().build();

        Integer cYear = Integer.parseInt(year);
        return ResponseEntity.ok(claimService.findClaimsByYear(cYear));
    }


    @GetMapping("/claims/{id}")
    public ResponseEntity<Claim> getClaim(@PathVariable Long id) {
        log.debug("REST request to get Claim : {}", id);
        Claim claim = claimService.findOne(id);
        if(claim == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(claim);
    }

    @DeleteMapping("/claims/{id}")
    public ResponseEntity<Void> deleteClaim(@PathVariable Long id) {
        log.debug("REST request to delete Claim : {}", id);
        claimService.delete(id);
        return ResponseEntity.ok().build();
    }

}
