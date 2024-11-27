package com.example.membersbe.service;

import com.example.membersbe.dto.RequestResponse;
import com.example.membersbe.entity.Medlem;
import com.example.membersbe.repository.MedlemRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HanteraMedlemmarService {

    private final MedlemRepository medlemRepository;

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    public HanteraMedlemmarService(MedlemRepository medlemRepository, JwtUtils jwtUtils, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.medlemRepository = medlemRepository;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public RequestResponse registreraMedlem(RequestResponse registreraMedlemRequest) {

        RequestResponse response = new RequestResponse();

        try {
            Medlem medlem = new Medlem();

            medlem.setNamn(registreraMedlemRequest.getNamn());
            medlem.setGatuadress(registreraMedlemRequest.getGatuadress());
            medlem.setPostnummer(registreraMedlemRequest.getPostnummer());
            medlem.setPostort(registreraMedlemRequest.getPostort());
            medlem.setEpost(registreraMedlemRequest.getEpost());
            medlem.setPassword(passwordEncoder.encode(registreraMedlemRequest.getPassword()));
            medlem.setTelefon(registreraMedlemRequest.getTelefon());
            medlem.setRoll(registreraMedlemRequest.getRoll());

            Medlem nyMedlem = medlemRepository.save(medlem);

            if(nyMedlem.getId() != null) {
                response.setMedlem(nyMedlem);
                response.setMeddelande("Medlem registrerad");
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
            response.setMeddelande("Medlem inloggad");

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
                response.setMeddelande("Behörighetstoken är uppdaterad");
            }
            response.setHttpStatus(200);
            return response;

        }catch (Exception e){
            response.setHttpStatus(500);
            response.setMeddelande(e.getMessage());
            return response;
        }
    }

    public RequestResponse hamtaAllaMedlemmar() {
        RequestResponse response = new RequestResponse();

        try {
            List<Medlem> result = medlemRepository.findAll();
            if (!result.isEmpty()) {
                response.setMedlemmar(result);
                response.setHttpStatus(200);
                response.setMeddelande("Hämtade alla medlemmar");
            } else {
                response.setHttpStatus(404);
                response.setMeddelande("Kunde inte hitta några medlemmar");
            }
            return response;
        } catch (Exception e) {
            response.setHttpStatus(500);
            response.setMeddelande("Error vid hämtning av medlemmarna: " + e.getMessage());
            return response;
        }
    }

    public RequestResponse hamtaMedlemMedId(Integer id) {
        RequestResponse response = new RequestResponse();
        try {
            Medlem medlem = medlemRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not found"));
            response.setMedlem(medlem);
            response.setHttpStatus(200);
            response.setMeddelande("Medlem med id " + id + " hämtad");
        } catch (Exception e) {
            response.setHttpStatus(500);
            response.setMeddelande("Error vid hämtning av medlem med id " + id + ": " + e.getMessage());
        }
        return response;
    }


    public RequestResponse raderaMedlem(Integer id) {
        RequestResponse response = new RequestResponse();
        try {
            Optional<Medlem> optionalMedlem = medlemRepository.findById(id);
            if (optionalMedlem.isPresent()) {
                medlemRepository.deleteById(id);
                response.setHttpStatus(200);
                response.setMeddelande("Medlem raderad");
            } else {
                response.setHttpStatus(404);
                response.setMeddelande("Medlemmen kunde inte hittas");
            }
        } catch (Exception e) {
            response.setHttpStatus(500);
            response.setMeddelande("Error, medlemmen kunde inte raderas: " + e.getMessage());
        }
        return response;
    }

    public RequestResponse uppdateraMedlem(Integer id, Medlem medlem) {
        RequestResponse response = new RequestResponse();
        try {
            Optional<Medlem> optionalMedlem = medlemRepository.findById(id);
            if (optionalMedlem.isPresent()) {
                Medlem existerandeMedlem = optionalMedlem.get();
                existerandeMedlem.setEpost(medlem.getEpost());
                existerandeMedlem.setNamn(medlem.getNamn());
                existerandeMedlem.setRoll(medlem.getRoll());

                // Check if password is present in the request
                if (medlem.getPassword() != null && !medlem.getPassword().isEmpty()) {
                    // Encode the password and update it
                    existerandeMedlem.setPassword(passwordEncoder.encode(medlem.getPassword()));
                }

                Medlem uppdateradMedlem = medlemRepository.save(existerandeMedlem);
                response.setMedlem(uppdateradMedlem);
                response.setHttpStatus(200);
                response.setMeddelande("Medlem uppdaterad");
            } else {
                response.setHttpStatus(404);
                response.setMeddelande("Medlemmen kunde inte hittas");
            }
        } catch (Exception e) {
            response.setHttpStatus(500);
            response.setMeddelande("Error, medlemmen kunde inte uppdateras: " + e.getMessage());
        }
        return response;
    }


    public RequestResponse hamtaMinInfo(String email){
        RequestResponse response = new RequestResponse();
        try {
            Optional<Medlem> userOptional = medlemRepository.findByEpost(email);
            if (userOptional.isPresent()) {
                response.setMedlem(userOptional.get());
                response.setHttpStatus(200);
                response.setMeddelande("Hämtade profilinformation");
            } else {
                response.setHttpStatus(404);
                response.setMeddelande("Medlemmen kunde inte hittas");
            }

        }catch (Exception e){
            response.setHttpStatus(500);
            response.setMeddelande("Error vid hämtning av profilinformation: " + e.getMessage());
        }
        return response;
    }
}
