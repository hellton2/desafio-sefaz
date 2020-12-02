package org.application.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import org.application.domain.Telefone;
import org.application.domain.TelefoneDto;
import org.application.domain.Usuario;
import org.application.service.TelefoneService;
import org.application.service.UsuarioService;
import org.application.web.util.MessageFactory;

@Named("usuarioBean")
@ViewScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(UsuarioBean.class.getName());

	private List<Usuario> usuarioList;

	private Usuario usuario;
	
	private Telefone telefone;
    
    private List<Telefone> telefones;
    
    private List<Telefone> telefonesAux;
     
    @PostConstruct
    public void init() {
    	
        telefone = new Telefone();
        telefones = new ArrayList<Telefone>();
        telefonesAux = new ArrayList<Telefone>();
        
        
       System.out.println("Resetou telefones");
         
    }
     
    public void createNew() {
        if(telefones.contains(telefone)) {
            FacesMessage msg = new FacesMessage("Duplicado", "Este telefone já foi adicionado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } 
        else {
        	System.out.println(telefone);
        	System.out.println("Criou");
        	telefones.add(new Telefone(telefone.getDdd(), telefone.getNumero(), null));
        	System.out.println(telefones);

            telefone = new Telefone();
        }
    }
     
    public String reinit() {
    	System.out.println("Reinit");
    	System.out.println(telefones);
    	System.out.println(telefone);

        telefone = new Telefone();
         
        return null;
    }
    
//	private List<Telefone> telefones;

	@Inject
	private UsuarioService usuarioService;
	
	@Inject
    private TelefoneService telefoneService;



	public void prepareNewUsuario() {
		reset();
		this.usuario = new Usuario();
		// set any default values now, if you need
		// Example: this.usuario.setAnything("test");
	}
	
	public void prepareNewTelefone() {
		resetTelefones();
		this.telefone = new Telefone();
		// set any default values now, if you need
		// Example: this.usuario.setAnything("test");
	}
	
	public String persistTelefone() {
		
		return "";
	}

	public String persist() {

		String message;

		try {

			if (usuario.getId() != null) {
				
				usuario = usuarioService.update(usuario);
				List<Telefone> tels = telefoneService.findAllTelefones(usuario.getId());
				tels.forEach(x -> telefoneService.delete(x));
				
				for (Telefone telefone : telefones) {
					telefone.setUsuario(usuario);
					telefoneService.save(telefone);
					System.out.println("Salvando Telefone");
				}
				
				message = "message_successfully_updated";
			} else {
				System.out.println("Persist");
				System.out.println(telefones);
				
				System.out.println("Telefones Aux");

				System.out.println(telefonesAux);
				
				
				usuario = usuarioService.save(usuario);				
				List<Telefone> tels = telefoneService.findAllTelefones(usuario.getId());
				tels.forEach(x -> telefoneService.delete(x));
				
				for (Telefone telefone : telefones) {
					telefone.setUsuario(usuario);
					telefoneService.save(telefone);
					System.out.println("Salvando Telefone");
				}
				System.out.println(usuario);
				

				System.out.println("Finally Persist");

				message = "message_successfully_created";
			}
		} catch (OptimisticLockException e) {
			logger.log(Level.SEVERE, "Error occured", e);
			message = "message_optimistic_locking_exception";
			// Set validationFailed to keep the dialog open
			FacesContext.getCurrentInstance().validationFailed();
		} catch (PersistenceException e) {
			logger.log(Level.SEVERE, "Error occured", e);
			message = "message_save_exception";
			// Set validationFailed to keep the dialog open
			FacesContext.getCurrentInstance().validationFailed();
		}

		usuarioList = null;

		FacesMessage facesMessage = MessageFactory.getMessage(message);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);

		return null;
	}
	
	public String deleteTelefone() {
		
		return "";
	}

	public String delete() {

		String message;

		try {
			usuarioService.delete(usuario);
			message = "message_successfully_deleted";
			reset();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error occured", e);
			message = "message_delete_exception";
			// Set validationFailed to keep the dialog open
			FacesContext.getCurrentInstance().validationFailed();
		}
		FacesContext.getCurrentInstance().addMessage(null, MessageFactory.getMessage(message));

		return null;
	}

	public void onDialogOpen(Usuario usuario) {
		reset();
		this.usuario = usuario;
	}

	public void reset() {
		usuario = null;
		usuarioList = null;

	}
	
	public void resetTelefones() {
		telefone = null;
		setTelefones(null);

	}

	public Usuario getUsuario() {
		// Need to check for null, because some strange behaviour of f:viewParam
		// Sometimes it is setting to null
		if (this.usuario == null) {
			prepareNewUsuario();
		}
		
		if (this.usuario != null && usuario.getId() != null && usuario.getId().longValue() != 0) {
			List<Telefone> tels = telefoneService.findAllTelefones(usuario.getId());
			telefones = tels;
			System.out.println();
			for (Telefone telefone : tels) {
				usuario.getTelefoneDtos().add(new TelefoneDto(telefone.getDdd(), telefone.getNumero()));
			}
		}
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		if (usuario != null) {
			this.usuario = usuario;
		}
	}

	public List<Usuario> getUsuarioList() {
		if (usuarioList == null) {
			usuarioList = usuarioService.findAllUsuarioEntities();
		}
		return usuarioList;
	}
	

	public void setUsuarioList(List<Usuario> usuarioList) {
		this.usuarioList = usuarioList;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	

}
