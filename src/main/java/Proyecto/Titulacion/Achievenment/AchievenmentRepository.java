package Proyecto.Titulacion.Achievenment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
    
public interface AchievenmentRepository extends JpaRepository<Achievenment, Long>{
    Page<Achievenment> findByNameAchievementContaining(String nameAchieviement, Pageable pageable);
}
