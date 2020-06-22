package main.server;

public class Benutzer {

    private String benutzername;
    private String passwort;
    private int   punkt;

    public Benutzer( String nickname, String passwort) {

        this.benutzername = nickname;
        this.passwort = passwort;
    }


    public String getNickname() {
        return benutzername;
    }

    public void setNickname(String nickname) {
        this.benutzername = nickname;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public int getPunkt() {
        return punkt;
    }

    public void setPunkt(int punkt) {
        this.punkt = punkt;
    }
}
