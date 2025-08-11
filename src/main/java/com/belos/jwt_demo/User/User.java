package com.belos.jwt_demo.User;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
/**
 * UserDetails clase de springframework.security.core
 */
public class User implements UserDetails {
    @Id
    @GeneratedValue
    Integer id;

    String username;
    String lastname;
    String firstname;
    String country;
    String password;

    @Enumerated(EnumType.STRING)
    Role role;

    /**
     * *MÉTODO IMPLEMENTADO
     * esta cosa retorna una lista con un objeto que va a representar la autoridad otorgada al usuario autenticado
     * por eso se le especifica el rol en el parametro
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name())); // SimpleGrantedAuthority es una clase que implementa GrantedAuthority
    }
}
