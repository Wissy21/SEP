package exceptions;

public class BenutzerDatenNichtKorrekt extends RuntimeException {
    public BenutzerDatenNichtKorrekt(String message) {
        super(message);
    }
}
