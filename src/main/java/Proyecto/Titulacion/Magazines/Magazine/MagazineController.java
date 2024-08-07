package Proyecto.Titulacion.Magazines.Magazine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import Proyecto.Titulacion.Careers.Career.Career;
import Proyecto.Titulacion.Careers.Career.CareerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/magazine")
@CrossOrigin({ "*" })
@Tag(name = "Controller Magazine (Revista)", description = "Table Magazine")
public class MagazineController {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    CareerService careerService;

    @Autowired
    MagazineService service;

    @Operation(summary = "Gets an magazine for your idMagazine")
    @GetMapping("/{idMagazine}/")
    public ResponseEntity<Magazine> getMagazineEntity(@PathVariable Long idMagazine) {
        Optional<Magazine> magazine = service.findById(idMagazine);
        return magazine.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Gets all magazine")
    @GetMapping("/")
    public List<Magazine> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Save a magazine with an image, requires hasAnyRole(ADMIN)")
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Magazine> saveMagazine(
            @RequestParam("nameMagazine") String nameMagazine,
            @RequestParam("descriptionMagazine") String descriptionMagazine,
            @RequestParam("idCareer") Long idCareer,
            @RequestParam("image") MultipartFile image) {

        Magazine magazine = new Magazine();
        magazine.setNameMagazine(nameMagazine);
        magazine.setDescriptionMagazine(descriptionMagazine);

        Optional<Career> careerOptional = careerService.findById(idCareer);
        if (careerOptional.isPresent()) {
            magazine.setCareer(careerOptional.get());
        } else {
            return ResponseEntity.badRequest().body(null);
        }

        if (!image.isEmpty()) {
            String imageUrl = saveImage(image);
            magazine.setUrlImageMagazine(imageUrl);
        }

        Magazine savedMagazine = service.save(magazine);
        return ResponseEntity.ok(savedMagazine);
    }

    @Operation(summary = "Updates a magazine by its idMagazine, requires hasAnyRole(ADMIN)")
    @PutMapping("/{idMagazine}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Magazine> updateMagazine(
            @PathVariable long idMagazine,
            @RequestParam("nameMagazine") String nameMagazine,
            @RequestParam("descriptionMagazine") String descriptionMagazine,
            @RequestParam("idCareer") Long idCareer,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        Optional<Magazine> optionalMagazine = service.findById(idMagazine);
        if (optionalMagazine.isPresent()) {
            Magazine existingMagazine = optionalMagazine.get();
            existingMagazine.setNameMagazine(nameMagazine);
            existingMagazine.setDescriptionMagazine(descriptionMagazine);

            Optional<Career> careerOptional = careerService.findById(idCareer);
            if (careerOptional.isPresent()) {
                existingMagazine.setCareer(careerOptional.get());
            } else {
                return ResponseEntity.badRequest().body(null);
            }

            if (image != null && !image.isEmpty()) {
                String imageUrl = saveImage(image);
                existingMagazine.setUrlImageMagazine(imageUrl);
            }

            Magazine updatedMagazine = service.save(existingMagazine);
            return ResponseEntity.ok(updatedMagazine);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Removes an magazine by its idMagazine, requires hasAnyRole(ADMIN)")
    @DeleteMapping("/{idMagazine}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deleteById(@PathVariable long idMagazine) {
        service.deleteById(idMagazine);
    }

    @Operation(summary = "Partial updates an magazine by its idMagazine, requires hasAnyRole(ADMIN)")
    @PatchMapping("/{idMagazine}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Magazine> partialUpdate(@PathVariable long idMagazine,
            @RequestBody Map<String, Object> fields) {
        Optional<Magazine> optionalMagazine = service.findById(idMagazine);

        if (!optionalMagazine.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Magazine entity = optionalMagazine.get();

        for (Map.Entry<String, Object> field : fields.entrySet()) {
            String fieldName = field.getKey();
            Object fieldValue = field.getValue();

            try {
                Field campoEntidad = Magazine.class.getDeclaredField(fieldName);
                campoEntidad.setAccessible(true);
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                campoEntidad.set(entity, mapper.convertValue(fieldValue, campoEntidad.getType()));
            } catch (NoSuchFieldException ex) {
                throw new IllegalArgumentException("Campo '" + fieldName + "' no encontrado en la entidad Magazine",
                        ex);
            } catch (IllegalAccessException ex) {
                throw new IllegalStateException("No se puede acceder o establecer el campo '" + fieldName + "'", ex);
            } catch (Exception ex) {
                throw new RuntimeException("Error al actualizar el campo '" + fieldName + "'", ex);
            }
        }

        Magazine updatedMagazine = service.save(entity);
        return ResponseEntity.ok(updatedMagazine);
    }

    @Operation(summary = "Magazine Pagination")
    @GetMapping("/paginated")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Page<Magazine> findPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idMagazine") String sortBy) {
        return service.findPaginated(page, size, sortBy);
    }

    @Operation(summary = "Search for Magazine")
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Page<Magazine> findByNameMagazine(
            @RequestParam String nameMagazine,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idMagazine") String sortBy) {
        return service.findByNameMagazine(nameMagazine, page, size, sortBy);
    }

    @Operation(summary = "Daily, monthly and yearly Magazine statistics.")
    @GetMapping("/stats")
    public Map<String, Long> getMagazineStats(@RequestParam String period) {
        return service.getMagazineStats(period);
    }

    private String saveImage(MultipartFile image) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path copyLocation = Paths.get(uploadPath + File.separator + fileName);
            Files.copy(image.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            return "uploads/" + fileName; // Guarda la ruta relativa
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al guardar el archivo de imagen");
        }
    }
}
