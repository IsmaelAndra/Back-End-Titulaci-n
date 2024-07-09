package Proyecto.Titulacion.Contact;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
    
@Service
public class ContactService {
    @Autowired
    ContactRepository repository;
    
    public Contact save( Contact entity ){
        return repository.save(entity);
    }
    
    public void deleteById( Long idContact ){
        repository.deleteById(idContact);
    }
    
    public Contact findById(Long idContact){
        return repository.findById(idContact).orElse(null);
    }
    
    public List<Contact> findAll(){
        return repository.findAll();
    }
    
    public Page<Contact> findPaginated(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findAll(pageable);
    }

    public Page<Contact> findByNameContact(String nameContact, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findByNameContactContaining(nameContact, pageable);
    }
}
