package fr.laptoff.authplugin.Managers.member;

import java.util.UUID;

public class Member {

    private String Pseudo;
    private UUID Uuid;
    private String Mail;
    private String Password;

    public Member(String pseudo, UUID uuid, String mail, String password){

        this.Pseudo = pseudo;
        this.Uuid = uuid;
        this.Mail = mail;
        this.Password = password;

    }

}
