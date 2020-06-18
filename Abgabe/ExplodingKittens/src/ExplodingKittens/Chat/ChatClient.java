package ExplodingKittens.Chat;

import ExplodingKittens.User.User;
import javafx.scene.control.Label;

import java.rmi.RemoteException;

public interface ChatClient {
    Label displayMessage(User user, String message) throws RemoteException;
}
