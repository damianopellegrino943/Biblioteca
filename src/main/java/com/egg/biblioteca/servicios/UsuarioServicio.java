package com.egg.biblioteca.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.egg.biblioteca.entidades.Usuario;
import com.egg.biblioteca.enumeraciones.Rol;
import com.egg.biblioteca.repositorios.UsuarioRepositorio;
import com.egg.excepciones.MiException;

import jakarta.servlet.http.HttpSession;

@Service
public class UsuarioServicio implements UserDetailsService {
@Autowired
private UsuarioRepositorio usuarioRepositorio;
//Los métodos del servicio


@Override
public UserDetails loadUserByUsername(String email) throws
UsernameNotFoundException {
Usuario usuario = usuarioRepositorio.buscarPorEmail(email);

if (usuario != null) {
List<GrantedAuthority> permisos = new ArrayList<>();
GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" +usuario.getRol().toString());
permisos.add(p);
ServletRequestAttributes attr = (ServletRequestAttributes)

RequestContextHolder.currentRequestAttributes();

HttpSession session = attr.getRequest().getSession(true);
session.setAttribute("usuariosession", usuario);
return new User(usuario.getEmail(), usuario.getPassword(),permisos);
} else {
return null;
}
}
 public static void registrar (MultipartFile archivo, String nombre, String email, String password, String password2) throws MiException{

        validar(nombre, email, password, password2);
                Usuario usuario = new Usuario();
                usuario.setNombre(nombre);
                usuario.setEmail(email);
                usuario.setPassword(new BCryptPasswordEncoder().encode(password));
                usuario.setRol(Rol.USER);
        
                /*Imagen imagen=imagenService.guardar(archivo);
                usuario.setImagen(imagen);
                usuarioRepositorio.save(usuario);*/
            }
            private static void validar(String nombre, String email, String password, String password2) throws MiException {

        //no es obligatorio cargar una imagen al momento de crear un usuario.

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacío");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("el email no puede ser nulo o estar vacío");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }
    }


}