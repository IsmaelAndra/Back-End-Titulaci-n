package Proyecto.Titulacion.User.Rol;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolService {
    @Autowired
    private RolRepository rolRepository;

    //Crear
    public Rol save(Rol entity)
    {
        return rolRepository.save(entity);
    }

    //Leer
    public Rol findById(long id_rol)
    {
        return rolRepository.findById(id_rol).orElse(null);
    }

    //Eliminar
    public void deleteByID (long id_rol)
    {
        rolRepository.deleteById(id_rol);
    }
    
    //Seleccionar Todo
    public List <Rol> findAll()
    {
        return rolRepository.findAll();
    }
}
