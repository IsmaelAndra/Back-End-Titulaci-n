package Proyecto.Titulacion.AcademicProgram;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
    
@Service
public class AcademicProgramService {
    @Autowired
    AcademicProgramRepository repository;
    
    public AcademicProgram save( AcademicProgram entity ){
        return repository.save(entity);
    }
    
    public void deleteById( Long id ){
        repository.deleteById(id);
    }
    
    public AcademicProgram findById(Long id){
        return repository.findById(id).orElse(null);
    }
    
    public List<AcademicProgram> findAll(){
        return repository.findAll();
    }
    
}
