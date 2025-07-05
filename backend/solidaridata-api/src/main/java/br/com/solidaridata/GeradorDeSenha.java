package br.com.solidaridata;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradorDeSenha {

    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String senhaPura = "admin";
        String hashCriptografado = passwordEncoder.encode(senhaPura);

        System.out.println("************************************************************");
        System.out.println("Para a senha '" + senhaPura + "', o hash gerado é:");
        System.out.println(hashCriptografado);
        System.out.println("Use este hash no seu script de UPDATE.");
        System.out.println("************************************************************");
    }
}