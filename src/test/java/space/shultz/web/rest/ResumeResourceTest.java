package space.shultz.web.rest;

import space.shultz.Application;
import space.shultz.domain.Resume;
import space.shultz.repository.ResumeRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ResumeResource REST controller.
 *
 * @see ResumeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ResumeResourceTest {


    @Inject
    private ResumeRepository resumeRepository;

    private MockMvc restResumeMockMvc;

    private Resume resume;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ResumeResource resumeResource = new ResumeResource();
        ReflectionTestUtils.setField(resumeResource, "resumeRepository", resumeRepository);
        this.restResumeMockMvc = MockMvcBuilders.standaloneSetup(resumeResource).build();
    }

    @Before
    public void initTest() {
        resume = new Resume();
    }

    @Test
    @Transactional
    public void createResume() throws Exception {
        int databaseSizeBeforeCreate = resumeRepository.findAll().size();

        // Create the Resume
        restResumeMockMvc.perform(post("/api/resumes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resume)))
                .andExpect(status().isCreated());

        // Validate the Resume in the database
        List<Resume> resumes = resumeRepository.findAll();
        assertThat(resumes).hasSize(databaseSizeBeforeCreate + 1);
        Resume testResume = resumes.get(resumes.size() - 1);
    }

    @Test
    @Transactional
    public void getAllResumes() throws Exception {
        // Initialize the database
        resumeRepository.saveAndFlush(resume);

        // Get all the resumes
        restResumeMockMvc.perform(get("/api/resumes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(resume.getId().intValue())));
    }

    @Test
    @Transactional
    public void getResume() throws Exception {
        // Initialize the database
        resumeRepository.saveAndFlush(resume);

        // Get the resume
        restResumeMockMvc.perform(get("/api/resumes/{id}", resume.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(resume.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingResume() throws Exception {
        // Get the resume
        restResumeMockMvc.perform(get("/api/resumes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResume() throws Exception {
        // Initialize the database
        resumeRepository.saveAndFlush(resume);
		
		int databaseSizeBeforeUpdate = resumeRepository.findAll().size();

        // Update the resume
        restResumeMockMvc.perform(put("/api/resumes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resume)))
                .andExpect(status().isOk());

        // Validate the Resume in the database
        List<Resume> resumes = resumeRepository.findAll();
        assertThat(resumes).hasSize(databaseSizeBeforeUpdate);
        Resume testResume = resumes.get(resumes.size() - 1);
    }

    @Test
    @Transactional
    public void deleteResume() throws Exception {
        // Initialize the database
        resumeRepository.saveAndFlush(resume);
		
		int databaseSizeBeforeDelete = resumeRepository.findAll().size();

        // Get the resume
        restResumeMockMvc.perform(delete("/api/resumes/{id}", resume.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Resume> resumes = resumeRepository.findAll();
        assertThat(resumes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
