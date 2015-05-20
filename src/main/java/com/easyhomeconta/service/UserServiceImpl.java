package com.easyhomeconta.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.easyhomeconta.dao.UserDao;
import com.easyhomeconta.model.Producto;
import com.easyhomeconta.model.Rol;
import com.easyhomeconta.model.User;
import com.easyhomeconta.utils.MyUtils;
/**
 * 
 * @author Alberto
 *
 */
@Named
public class UserServiceImpl implements UserService {

	private final Logger log = Logger.getLogger(UserServiceImpl.class);
	
	@Inject
	private UserDao userDao;
	
	@Inject
	private FamiliaService familiaService;
		
	@Override
	@PreAuthorize("hasAnyRole('Administrador')")
	public List<User> findAllUsers() {
		List<User> lstUsers=userDao.findAll();
		return lstUsers;			
	}
	
	@Override
	@Transactional
	public void createUser(User user) {
		//Encriptacion de contraseña
		user.setPassword(MyUtils.codificarPasswordBcrypt(user.getPassword()));
		
		//Le insertamos fecha de creacion
		user.setFechaCreacion(new Date());
		
		//Por defecto, creamos al usuario con los atributos a true
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setEnabled(true);
		user.setCredentialsNonExpired(true);
		userDao.create(user);
	}

	
	@Override
	@Transactional
	public void saveUser(User user, Integer selectedFamilia, Boolean cambiarFoto, UploadedFile imagen, List<Rol> lstRoles) {
		
		if (selectedFamilia!=null)
			user.setFamilia(familiaService.getFamiliaById(selectedFamilia));
		
		this.gestionRoles(user, lstRoles);
		
		//Gestion de foto
		this.gestionFoto(user, cambiarFoto, imagen);
		
		//Si no tiene id crea nuevo usuario
		if (user.getIdUser()==null){
			
			//Encriptacion de contraseña
			user.setPassword(MyUtils.codificarPasswordBcrypt(user.getPassword()));
			
			//Le insertamos fecha de creacion
			user.setFechaCreacion(new Date());
			
			//Por defecto, creamos al usuario con los atributos a true
			user.setAccountNonExpired(true);
			user.setAccountNonLocked(true);
			user.setEnabled(true);
			user.setCredentialsNonExpired(true);
			
			userDao.create(user);
		}
		else{
			User oldUser=userDao.findById(user.getIdUser());
			this.gestionFamilia(oldUser, selectedFamilia);
			//Comparo la pass actual con la de la bd para ver si hay guardar la pass encriptada
			if (!user.getPassword().equals(oldUser.getPassword()))
				user.setPassword(MyUtils.codificarPasswordBcrypt(user.getPassword()));
			
			userDao.update(user);
		}
		
	}
	
	/**
	 * Gestiona los roles borrando primero todos los existentes anteriormente
	 * y añadiendo despues los seleccionados.
	 */
	private void gestionRoles(User user, List<Rol> lstRoles){
		//Elimino todos los posibles roles que pudiese tener el usuario para dar de alta los que esten ahora checkeados
		user.setLstRoles(new ArrayList<Rol>());
						
		for (Rol rol:lstRoles){
			if (rol.getSelected()!=null && rol.getSelected())
				user.getLstRoles().add(rol);
		}
	}
	
	/**
	 * Gestiona la carga de la fotografia en el User dependiendo de si se ha seleccionado foto o no 
	 * y de si se trata de un usuario nuevo o una modificacion.
	 */
	private void gestionFoto(User user, Boolean cambiarFoto, UploadedFile imagen){
		
		byte[] imagenArrayBytes=null;
		
		
		//Si hemos cargado el cambio de foto
		if (cambiarFoto){
			//Si se carga foto
			if (imagen.getSize()!=0){
				try{
					imagenArrayBytes=IOUtils.toByteArray(imagen.getInputstream());
				}catch(Exception e){
					log.error(e.getMessage());
				}
			}
		}
		//Si no hemos cargado el cambio de foto, 
		else{
			//Si estamos realizando una MODIFICACION del USUARIO 
			if (user.getIdUser()!=null)
				//Debemos de cargar la imagen que ya teneia guardada en BD 
				imagenArrayBytes=this.getUserById(user.getIdUser()).getPhoto();
		}
		
		user.setPhoto(imagenArrayBytes);
	}
	
	/**
	 * Gestion de la familia: 
	 * Si el usuario modifica la familia a la que pertenece:
	 * A.- Saca a todos los users de los productos de los que sea propietario
	 * B.- Elimina todos los productos de los que no se propietario
	 */
	private void gestionFamilia(User user,Integer selectedFamilia){
		
		Integer idFamiliaBd=user.getFamilia()!=null?user.getFamilia().getIdFamilia(): new Integer(0);
		
		//Comparo los ids. Si son diferentes es porque se ha seleccionado otra familia distinta de la que tenia. (Siempre que antes tuviese familia)
		if (idFamiliaBd.compareTo(new Integer(0))!=0 && idFamiliaBd.compareTo(selectedFamilia)!=0){
						
			// Con un for no se puede eliminar un elemento mientras. 
			//Por eso utilizamos un iterador, que tiene el metodo remove para poder eliminar un elemento de forma segura
			
			Iterator<Producto> it= user.getLstProductos().iterator();
			 
			while(it.hasNext()) {
			 	Producto p=it.next();
			 	//Si es propietario saco a los otros users
				if (p.getIdUserOwner().compareTo(user.getIdUser())==0){
					
					for (User u:p.getLstUsuarios()){
						if (u.getIdUser().compareTo(user.getIdUser())!=0)
							u.getLstProductos().remove(p);
						
					}
				}
				//Si no es propietario le quito el producto
				else{
					p.getLstUsuarios().remove(user);
					it.remove();
				}
			}
			
		}//if
		
	}
	
	@Override
	@Transactional
	public void deleteUser(Integer id) {
		User user=this.getUserById(id);
		
		user.getLstRoles().clear();
		
		Iterator<Producto> it= user.getLstProductos().iterator();
		 
		while(it.hasNext()) {
		 	Producto p=it.next();
		 	//Si es propietario saco a los otros users
			if (p.getIdUserOwner().compareTo(user.getIdUser())==0){
				
				for (User u:p.getLstUsuarios()){
					if (u.getIdUser().compareTo(user.getIdUser())!=0)
						u.getLstProductos().remove(p);
					
				}
			}
			//Si no es propietario le quito el producto
			else{
				p.getLstUsuarios().remove(user);
				it.remove();
			}
		}
		
		userDao.deleteUser(user);
		
	}

	@Override
	@Transactional
	public void updateUser(User user) {

		User oldUser=userDao.findById(user.getIdUser());
		//Comparo la pass actual con la de la bd para ver si hay guardar la pass encriptada
		if (!user.getPassword().equals(oldUser.getPassword()))
			user.setPassword(MyUtils.codificarPasswordBcrypt(user.getPassword()));
		
		userDao.update(user);
	}

	
	@Override
	public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException{
		User usuario=userDao.loadUserByUsername(username);
		return usuario;
	}

	@Override
	public List<Rol> findAllRoles() {
		List<Rol> lstRoles=userDao.findAllRoles();
		return lstRoles;
	}


	@Override
	public Boolean isUsernameValido(User user) {
		List<User> lstUsers=userDao.findUsersbyUsername(user.getUsername());		 
		
		//Si es usuario nuevo
		if (user.getIdUser()==null)
			//si no se encuentra ocupado devolvemos true
			return lstUsers.isEmpty();
		else {
			//Si es un usuario existente comparo el username que tiene en base de datos antes
			User userBd=userDao.findById(user.getIdUser());
			if (userBd.getUsername().compareTo(user.getUsername())!=0)
				return lstUsers.isEmpty();
			
			return true;
		}
	}
	
	@Override
	public User getUserById(Integer id) {
		User user=userDao.findById(id);
		return user;
	}

	@Override
	@Transactional
	public List<SelectItem> getFamiliaresForCombo(Integer idUsuario) {
		List<SelectItem>lstItems=new ArrayList<SelectItem>();
		User usuario=userDao.findById(idUsuario);

		//Si el usuario tiene familia
		if(usuario.getFamilia()!=null){
			//Si los usuarios de la familia vienen cargado...
			if (usuario.getFamilia().getLstUsers()!=null){
				//Recorro a todos los familiares para añadirles a la lista (excluyendo a el)
				for(User familiar:usuario.getFamilia().getLstUsers()){
					if (idUsuario.compareTo(familiar.getIdUser())!=0)
						lstItems.add(new SelectItem(familiar.getIdUser(), familiar.getUserNameForSession()));
				}
			}
		}
			
		return lstItems;
	}
}
