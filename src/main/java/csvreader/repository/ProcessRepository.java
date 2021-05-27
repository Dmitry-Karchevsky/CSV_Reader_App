package csvreader.repository;

import csvreader.entity.BusinessProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessRepository extends JpaRepository<BusinessProcess, Long> {
}
