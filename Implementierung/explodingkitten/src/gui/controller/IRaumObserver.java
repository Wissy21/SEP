package gui.controller;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRaumObserver extends Remote {
    void updateMessageList() throws RemoteException;
}
