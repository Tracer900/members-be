package com.example.membersbe.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "medlemmar")
public class Medlem implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String namn;
    private String gatuadress;
    private String postnummer;
    private String postort;
    private String epost;
    private String telefon;
    private String password;
    private String roll;

    public Medlem() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }

    public String getGatuadress() {
        return gatuadress;
    }

    public void setGatuadress(String gatuadress) {
        this.gatuadress = gatuadress;
    }

    public String getPostnummer() {
        return postnummer;
    }

    public void setPostnummer(String postnummer) {
        this.postnummer = postnummer;
    }

    public String getPostort() {
        return postort;
    }

    public void setPostort(String postort) {
        this.postort = postort;
    }

    public String getEpost() {
        return epost;
    }

    public void setEpost(String epost) {
        this.epost = epost;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Medlem medlem = (Medlem) o;
        return Objects.equals(id, medlem.id)
                && Objects.equals(namn, medlem.namn)
                && Objects.equals(gatuadress, medlem.gatuadress)
                && Objects.equals(postnummer, medlem.postnummer)
                && Objects.equals(postort, medlem.postort)
                && Objects.equals(epost, medlem.epost)
                && Objects.equals(telefon, medlem.telefon)
                && Objects.equals(password, medlem.password)
                && Objects.equals(roll, medlem.roll);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, namn, gatuadress, postnummer, postort, epost, telefon, password, roll);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roll));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return epost;
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
