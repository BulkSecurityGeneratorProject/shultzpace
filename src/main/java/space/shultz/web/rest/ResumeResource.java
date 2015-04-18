package space.shultz.web.rest;

import com.codahale.metrics.annotation.Timed;
import space.shultz.domain.Resume;
import space.shultz.repository.ResumeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Resume.
 */
@RestController
@RequestMapping("/api")
public class ResumeResource {

    private final Logger log = LoggerFactory.getLogger(ResumeResource.class);

    @Inject
    private ResumeRepository resumeRepository;

    /**
     * POST  /resumes -> Create a new resume.
     */
    @RequestMapping(value = "/resumes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Resume resume) throws URISyntaxException {
        log.debug("REST request to save Resume : {}", resume);
        if (resume.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new resume cannot already have an ID").build();
        }
        resumeRepository.save(resume);
        return ResponseEntity.created(new URI("/api/resumes/" + resume.getId())).build();
    }

    /**
     * PUT  /resumes -> Updates an existing resume.
     */
    @RequestMapping(value = "/resumes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Resume resume) throws URISyntaxException {
        log.debug("REST request to update Resume : {}", resume);
        if (resume.getId() == null) {
            return create(resume);
        }
        resumeRepository.save(resume);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /resumes -> get all the resumes.
     */
    @RequestMapping(value = "/resumes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Resume> getAll() {
        log.debug("REST request to get all Resumes");
        return resumeRepository.findAll();
    }

    /**
     * GET  /resumes/:id -> get the "id" resume.
     */
    @RequestMapping(value = "/resumes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Resume> get(@PathVariable Long id) {
        log.debug("REST request to get Resume : {}", id);
        return Optional.ofNullable(resumeRepository.findOne(id))
            .map(resume -> new ResponseEntity<>(
                resume,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /resumes/:id -> delete the "id" resume.
     */
    @RequestMapping(value = "/resumes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Resume : {}", id);
        resumeRepository.delete(id);
    }
}
