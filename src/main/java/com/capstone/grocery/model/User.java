package com.capstone.grocery.model;

import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.capstone.grocery.model.product.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails{
        
    @Id
    private String userId;
    private String userFirstName;
    private String userLastName;
    private String userPhone;
    private String userEmail;
    private String userEncryptedPassword;
    private List<Address> userSavedAddresses;
    private UserRole userRole;
    private Cart cart;
    private List<Product> wishlist;
    
    public void setAllAttributes(User newUser){
        this.userFirstName = newUser.userFirstName;
        this.userLastName = newUser.userLastName;
        this.userPhone = newUser.userPhone;
        this.userEmail = newUser.userEmail;
        this.userEncryptedPassword = newUser.userEncryptedPassword;
        this.userSavedAddresses = newUser.userSavedAddresses;
        this.userRole = newUser.userRole;
        this.cart = newUser.cart;
        this.wishlist = newUser.wishlist;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRole != null ? userRole.toString() : "USER"));
    }

    @Override
    public String getPassword() {
        return this.userEncryptedPassword;
    }

    @Override
    public String getUsername() {
        return this.userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
