package com.example.membersbe.dto;

import com.example.membersbe.entity.Medlem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestResponse {

    private int httpStatus;
    private String error;
    private String meddelande;
    private String jwtToken;
    private String uppdateraJwtToken;
    private String utgangsTid;
    private String namn;
    private String gatuadress;
    private String postnummer;
    private String postort;
    private String epost;
    private String password;
    private String telefon;
    private String roll;
    private Medlem medlem;
    private List<Medlem> medlemmar;

    public RequestResponse() {
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMeddelande() {
        return meddelande;
    }

    public void setMeddelande(String meddelande) {
        this.meddelande = meddelande;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getUppdateraJwtToken() {
        return uppdateraJwtToken;
    }

    public void setUppdateraJwtToken(String uppdateraJwtToken) {
        this.uppdateraJwtToken = uppdateraJwtToken;
    }

    public String getUtgangsTid() {
        return utgangsTid;
    }

    public void setUtgangsTid(String utgangsTid) {
        this.utgangsTid = utgangsTid;
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

    public void setPostort(String postnummer) {
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

    public String getPassword() {
        return password;
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

    public Medlem getMedlem() {
        return medlem;
    }

    public void setMedlem(Medlem medlem) {
        this.medlem = medlem;
    }

    public List<Medlem> getMedlemmar() {
        return medlemmar;
    }

    public void setMedlemmar(List<Medlem> medlemmar) {
        this.medlemmar = medlemmar;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RequestResponse response = (RequestResponse) o;
        return httpStatus == response.httpStatus
                && Objects.equals(error, response.error)
                && Objects.equals(meddelande, response.meddelande)
                && Objects.equals(jwtToken, response.jwtToken)
                && Objects.equals(uppdateraJwtToken, response.uppdateraJwtToken)
                && Objects.equals(utgangsTid, response.utgangsTid)
                && Objects.equals(namn, response.namn)
                && Objects.equals(gatuadress, response.gatuadress)
                && Objects.equals(postnummer, response.postnummer)
                && Objects.equals(postort, response.postort)
                && Objects.equals(epost, response.epost)
                && Objects.equals(password, response.password)
                && Objects.equals(telefon, response.telefon)
                && Objects.equals(roll, response.roll)
                && Objects.equals(medlem, response.medlem)
                && Objects.equals(medlemmar, response.medlemmar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpStatus, error, meddelande, jwtToken, uppdateraJwtToken,
                utgangsTid, namn, gatuadress, postnummer, postort, epost, password,
                telefon, roll, medlem, medlemmar);
    }

    @Override
    public String toString() {
        return "RequestResponse{" +
                "httpStatus=" + httpStatus +
                ", error='" + error + '\'' +
                ", meddelande='" + meddelande + '\'' +
                ", jwtToken='" + jwtToken + '\'' +
                ", uppdateraJwtToken='" + uppdateraJwtToken + '\'' +
                ", utgangsTid='" + utgangsTid + '\'' +
                ", namn='" + namn + '\'' +
                ", gatuadress='" + gatuadress + '\'' +
                ", postnummer='" + postnummer + '\'' +
                ", postort='" + postort + '\'' +
                ", epost='" + epost + '\'' +
                ", password='" + password + '\'' +
                ", telefon='" + telefon + '\'' +
                ", roll='" + roll + '\'' +
                ", medlem=" + medlem +
                ", medlemmar=" + medlemmar +
                '}';
    }
}
