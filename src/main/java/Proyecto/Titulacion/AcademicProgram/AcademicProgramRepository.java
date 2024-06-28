package Proyecto.Titulacion.AcademicProgram;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
    
public interface AcademicProgramRepository extends CrudRepository<AcademicProgram, Long>{
    List<AcademicProgram> findAll();
}
