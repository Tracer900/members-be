package com.example.membersbe.service;

import com.example.membersbe.dto.RequestResponse;
import com.example.membersbe.entity.Medlem;
import com.example.membersbe.repository.MedlemRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class HanteraMedlemmarService {

    private MedlemRepository medlemRepository;

    private JwtUtils jwtUtils;

    private AuthenticationManager authenticationManager;

    private PasswordEncoder passwordEncoder;

    public RequestResponse registreraMedlem(RequestResponse registreraMedlemRequest) {

        RequestResponse response = new RequestResponse();

        try {
            Medlem medlem = new Medlem();

            medlem.setEpost(registreraMedlemRequest.getEpost());
            medlem.setPassword(passwordEncoder.encode(registreraMedlemRequest.getPassword()));
            medlem.setNamn(registreraMedlemRequest.getNamn());
            medlem.setGatuadress(registreraMedlemRequest.getGatuadress());
            medlem.setPostnummer(registreraMedlemRequest.getPostnummer());
            medlem.setPostort(registreraMedlemRequest.getPostort());
            medlem.setTelefon(registreraMedlemRequest.getTelefon());
            medlem.setRoll(registreraMedlemRequest.getRoll());

            Medlem nyMedlem = medlemRepository.save(medlem);

            if(nyMedlem.getId() != null) {
                response.setMedlem(nyMedlem);
                response.setMeddelande("Medlem registrerad.");
                response.setHttpStatus(200);
            }

        } catch (Exception e) {
            response.setHttpStatus(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public RequestResponse login(RequestResponse loginRequest) {
        RequestResponse response = new RequestResponse();

        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginRequest.getEpost(), loginRequest.getPassword())
                    );
            var medlem = medlemRepository.findByEpost(loginRequest.getEpost()).orElseThrow();
            var jwt = jwtUtils.skapaJwt(medlem);
            var uppdateradJwt = jwtUtils.uppdateraJwt(new HashMap<>(), medlem);
            response.setHttpStatus(200);
            response.setJwtToken(jwt);
            response.setUppdateraJwtToken(uppdateradJwt);
            response.setUtgangsTid("2Hrs");
            response.setMeddelande("Medlem inloggad.");

        } catch(Exception e) {
            response.setHttpStatus(500);
            response.setMeddelande(e.getMessage());
        }
        return response;
    }

    public RequestResponse uppdateraToken(RequestResponse uppdateraTokenRequest){
        RequestResponse response = new RequestResponse();
        try{
            String ourEmail = jwtUtils.extractUsername(uppdateraTokenRequest.getJwtToken());
            Medlem medlem = medlemRepository.findByEpost(ourEmail).orElseThrow();
            if (jwtUtils.isTokenValid(uppdateraTokenRequest.getJwtToken(), medlem)) {
                var jwt = jwtUtils.skapaJwt(medlem);
                response.setHttpStatus(200);
                response.setJwtToken(jwt);
                response.setUppdateraJwtToken(uppdateraTokenRequest.getJwtToken());
                response.setUtgangsTid("2Hrs");
                response.setMeddelande("Uppdaterade behörighetstoken.");
            }
            response.setHttpStatus(200);
            return response;

        }catch (Exception e){
            response.setHttpStatus(500);
            response.setMeddelande(e.getMessage());
            return response;
        }
    }
}
