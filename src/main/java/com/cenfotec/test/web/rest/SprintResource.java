package com.cenfotec.test.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cenfotec.test.domain.Sprint;
import com.cenfotec.test.repository.SprintRepository;
import com.cenfotec.test.web.rest.errors.BadRequestAlertException;
import com.cenfotec.test.web.rest.util.HeaderUtil;
import com.cenfotec.test.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Sprint.
 */
@RestController
@RequestMapping("/api")
public class SprintResource {

    private final Logger log = LoggerFactory.getLogger(SprintResource.class);

    private static final String ENTITY_NAME = "sprint";

    private final SprintRepository sprintRepository;

    public SprintResource(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }

    /**
     * POST  /sprints : Create a new sprint.
     *
     * @param sprint the sprint to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sprint, or with status 400 (Bad Request) if the sprint has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sprints")
    @Timed
    public ResponseEntity<Sprint> createSprint(@RequestBody Sprint sprint) throws URISyntaxException {
        log.debug("REST request to save Sprint : {}", sprint);
        if (sprint.getId() != null) {
            throw new BadRequestAlertException("A new sprint cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sprint result = sprintRepository.save(sprint);
        return ResponseEntity.created(new URI("/api/sprints/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sprints : Updates an existing sprint.
     *
     * @param sprint the sprint to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sprint,
     * or with status 400 (Bad Request) if the sprint is not valid,
     * or with status 500 (Internal Server Error) if the sprint couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sprints")
    @Timed
    public ResponseEntity<Sprint> updateSprint(@RequestBody Sprint sprint) throws URISyntaxException {
        log.debug("REST request to update Sprint : {}", sprint);
        if (sprint.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Sprint result = sprintRepository.save(sprint);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sprint.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sprints : get all the sprints.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sprints in body
     */
    @GetMapping("/sprints")
    @Timed
    public ResponseEntity<List<Sprint>> getAllSprints(Pageable pageable) {
        log.debug("REST request to get a page of Sprints");
        Page<Sprint> page = sprintRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sprints");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /sprints/:id : get the "id" sprint.
     *
     * @param id the id of the sprint to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sprint, or with status 404 (Not Found)
     */
    @GetMapping("/sprints/{id}")
    @Timed
    public ResponseEntity<Sprint> getSprint(@PathVariable Long id) {
        log.debug("REST request to get Sprint : {}", id);
        Optional<Sprint> sprint = sprintRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sprint);
    }

    /**
     * DELETE  /sprints/:id : delete the "id" sprint.
     *
     * @param id the id of the sprint to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sprints/{id}")
    @Timed
    public ResponseEntity<Void> deleteSprint(@PathVariable Long id) {
        log.debug("REST request to delete Sprint : {}", id);

        sprintRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
