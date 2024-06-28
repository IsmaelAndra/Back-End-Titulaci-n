package Proyecto.Titulacion.Contact;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
    
@Service
public class ContactService {
    @Autowired
    ContactRepository repository;
    
    public Contact save( Contact entity ){
        return repository.save(entity);
    }
    
    public void deleteById( Long id ){
        repository.deleteById(id);
    }
    
    public Contact findById(Long id){
        return repository.findById(id).orElse(null);
    }
    
    public List<Contact> findAll(){
        return repository.findAll();
    }
    
}
