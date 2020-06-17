package main.server;

public class Benutzer {

    private String email;
    private String vorname;
    private String nachname;
    private String nickname;
    private String passwort;

    public Benutzer(String email, String vorname, String nachname, String nickname, String passwort) {
        this.email = email;
        this.vorname = vorname;
        this.nachname = nachname;
        this.nickname = nickname;
        this.passwort = passwort;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }
}
