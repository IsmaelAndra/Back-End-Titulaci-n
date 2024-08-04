package Proyecto.Titulacion.Careers.StudyPlan;

import java.util.List;
import java.util.Map;
import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/studyPlan")
@CrossOrigin({ "*" })
@Tag(name = "Controller Study plan (Plan de Estudios)", description = "Tabla Study Plan")
public class StudyPlanController {

    @Autowired
    StudyPlanService service;

    @Operation(summary = "Gets an study plan for your idStudyPlan")
    @GetMapping("/{idStudyPlan}/")
    public StudyPlan findById(@PathVariable long idStudyPlan) {
        return service.findById(idStudyPlan);
    }

    @Operation(summary = "Gets all study plans")
    @GetMapping("/")
    public List<StudyPlan> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Save an study plan, requires hasAnyRole(ADMIN)")
    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public StudyPlan save(@RequestBody StudyPlan entitiy) {
        return service.save(entitiy);
    }

    @Operation(summary = "Updates an study plan by its idStudyPlan, requires hasAnyRole(ADMIN)")
    @PutMapping("/{idStudyPlan}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public StudyPlan update(@RequestBody StudyPlan entity) {
        return service.save(entity);
    }

    @Operation(summary = "Removes an study plan by its idStudyPlan, requires hasAnyRole(ADMIN)")
    @DeleteMapping("/{idStudyPlan}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deleteById(@PathVariable long idStudyPlan) {
        service.deleteById(idStudyPlan);
    }

    @Operation(summary = "Partial updates an study plan by its idStudyPlan, requires hasAnyRole(ADMIN)")
    @PatchMapping("/{idStudyPlan}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public StudyPlan partialUpdate(@PathVariable long idStudyPlan, @RequestBody Map<String, Object> fields) {

        StudyPlan entity = findById(idStudyPlan);

        for (Map.Entry<String, Object> field : fields.entrySet()) {
            String fieldName = field.getKey();
            Object fieldValue = field.getValue();

            try {
                Field campoEntidad = StudyPlan.class.getDeclaredField(fieldName);
                campoEntidad.setAccessible(true);
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                campoEntidad.set(entity, mapper.convertValue(fieldValue, campoEntidad.getType()));
            } catch (NoSuchFieldException ex) {
                throw new IllegalArgumentException(
                        "Campo '" + fieldName + "' no encontrado en la entidad StudyPlan", ex);
            } catch (IllegalAccessException ex) {
                throw new IllegalStateException("No se puede acceder o establecer el campo '" + fieldName + "'", ex);
            } catch (Exception ex) {
                throw new RuntimeException("Error al actualizar el campo '" + fieldName + "'", ex);
            }
        }
        return update(entity);
    }

    @Operation(summary = "Study Plan Pagination")
    @GetMapping("/paginated")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Page<StudyPlan> findPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idStudyPlan") String sortBy) {
        return service.findPaginated(page, size, sortBy);
    }

    @Operation(summary = "Search for Study Plan")
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Page<StudyPlan> findByNameStudyPlan(
            @RequestParam String nameStudyPlan,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idStudyPlan") String sortBy) {
        return service.findByNameStudyPlan(nameStudyPlan, page, size, sortBy);
    }
}
