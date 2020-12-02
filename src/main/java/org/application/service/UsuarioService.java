package org.application.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.transaction.Transactional;

import org.application.domain.Usuario;

@Named
public class UsuarioService extends BaseService<Usuario> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public UsuarioService(){
        super(Usuario.class);
    }
    
    @Transactional
    public List<Usuario> findAllUsuarioEntities() {
        
        return entityManager.createQuery("SELECT o FROM Usuario o ", Usuario.class).getResultList();
    }
    
    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Usuario o", Long.class).getSingleResult();
    }
    
    @Override
    protected void handleDependenciesBeforeDelete(Usuario productExample) {

        /* This is called before a Usuario is deleted. Place here all the
           steps to cut dependencies to other entities */
        
    }

}
