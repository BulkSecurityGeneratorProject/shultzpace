package space.shultz.repository;

import space.shultz.domain.Resume;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Resume entity.
 */
public interface ResumeRepository extends JpaRepository<Resume,Long> {

}
