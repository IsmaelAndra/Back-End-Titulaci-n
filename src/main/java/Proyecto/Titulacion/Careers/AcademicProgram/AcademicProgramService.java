package Proyecto.Titulacion.Careers.AcademicProgram;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
    
@Service
public class AcademicProgramService {
    @Autowired
    AcademicProgramRepository repository;
    
    public AcademicProgram save( AcademicProgram entity ){
        return repository.save(entity);
    }
    
    public void deleteById( Long idAcademicProgram ){
        repository.deleteById(idAcademicProgram);
    }
    
    public AcademicProgram findById(Long idAcademicProgram){
        return repository.findById(idAcademicProgram).orElse(null);
    }
    
    public List<AcademicProgram> findAll(){
        return repository.findAll();
    }
    
    public Page<AcademicProgram> findPaginated(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findAll(pageable);
    }

    public Page<AcademicProgram> findByNameAcademicProgram(String nameAcademicProgram, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findByNameAcademicProgramContaining(nameAcademicProgram, pageable);
    }
}
