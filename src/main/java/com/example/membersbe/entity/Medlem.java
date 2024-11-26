package com.example.membersbe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "Medlemmar")
public class Medlem implements UserDetails {
    @Id
    private UUID id;
    private String namn;
    private String gatuadress;
    private int postnummer;
    private String postort;
    private String epost;
    private String telefon;
    private String password;
    private String roll;

    public Medlem(UUID id, String namn, String gatuadress, int postnummer, String postort, String epost, String telefon, String password, String roll) {
        this.id = id;
        this.namn = namn;
        this.gatuadress = gatuadress;
        this.postnummer = postnummer;
        this.postort = postort;
        this.epost = epost;
        this.telefon = telefon;
        this.password = password;
        this.roll = roll;
    }

    public Medlem() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public int getPostnummer() {
        return postnummer;
    }

    public void setPostnummer(int postnummer) {
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
        return postnummer == medlem.postnummer && Objects.equals(id, medlem.id) && Objects.equals(namn, medlem.namn) && Objects.equals(gatuadress, medlem.gatuadress) && Objects.equals(postort, medlem.postort) && Objects.equals(epost, medlem.epost) && Objects.equals(telefon, medlem.telefon) && Objects.equals(password, medlem.password) && Objects.equals(roll, medlem.roll);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, namn, gatuadress, postnummer, postort, epost, telefon, password, roll);
    }

    @Override
    public String toString() {
        return "Medlem{" +
                "id=" + id +
                ", namn='" + namn + '\'' +
                ", gatuadress='" + gatuadress + '\'' +
                ", postnummer=" + postnummer +
                ", postort='" + postort + '\'' +
                ", epost='" + epost + '\'' +
                ", telefon='" + telefon + '\'' +
                ", password='" + password + '\'' +
                ", roll='" + roll + '\'' +
                '}';
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
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
