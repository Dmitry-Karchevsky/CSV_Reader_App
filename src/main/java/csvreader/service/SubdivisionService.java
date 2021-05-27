package csvreader.service;

import csvreader.entity.Subdivision;
import csvreader.repository.SubdivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubdivisionService {

    @Autowired
    private SubdivisionRepository subdivisionRepository;

    public void saveSubdivision(Subdivision subdivision) {
        Subdivision subdivisionFromDb = subdivisionRepository.findByName(subdivision.getName());
        if (subdivisionFromDb == null) {
            subdivisionRepository.save(subdivision);
        }
        else {
            subdivision.setId(subdivisionFromDb.getId());
        }
    }
}
