package com.egg.biblioteca.entidades;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import com.egg.biblioteca.enumeraciones.Rol;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Usuario {
@Id
@GeneratedValue (generator = "uuid")
@GenericGenerator (name ="uuid", strategy = "uuid2")
private UUID id;
private String nombre;
private String email;
private String password;
@Enumerated(EnumType.STRING)
private Rol rol;
// Constructor Vacío
public Usuario() {
}

public String getNombre() {
    return nombre;
}
public void setNombre(String nombre) {
    this.nombre = nombre;
}
public String getEmail() {
    return email;
}
public void setEmail(String email) {
    this.email = email;
}
public String getPassword() {
    return password;
}
public void setPassword(String password) {
    this.password = password;
}
public Rol getRol() {
    return rol;
}
public void setRol(Rol rol) {
    this.rol = rol;
}

public UUID getId() {
    return id;
}

public void setId(UUID id) {
    this.id = id;
}


}