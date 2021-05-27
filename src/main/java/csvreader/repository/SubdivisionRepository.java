package csvreader.repository;

import csvreader.entity.Subdivision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubdivisionRepository extends JpaRepository<Subdivision, Long> {
    Subdivision findByName(String name);
}
