package server;

import exceptions.NichtGenugSpielerException;
import exceptions.NoExplodingKittenException;
import exceptions.NotYourRundeException;
import exceptions.SpielraumVollException;
import gui.controller.ILobbyObserver;
import server.karten.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SpielRaum extends UnicastRemoteObject implements SpielRaumInterface{

    public SpielChat chat;
    public HashMap<String, ILobbyObserver> userLobserverMap;

    public String name;
    public int anzahlSpieler;
    public ArrayList<Spieler> spieler;
    public Stack<Karte> spielstapel;
    public Stack<Karte> ablagestapel;
    public Spieler current;
    public ListIterator<Spieler> reihenfolge;

    Executor kartenExecutor = Executors.newSingleThreadExecutor();


    public boolean angriff = false;
    public Spieler ausgewahlter;
    public Karte abgegeben;
    public boolean expolding = false;
    public int position;
    public Karte explKitten;
    public boolean noe = false;


    public SpielRaum() throws RemoteException{
        this.chat = new SpielChat();
        userLobserverMap = new HashMap<String, ILobbyObserver>();
    }

    public void botHinzufuegen() throws SpielraumVollException {
        if(anzahlSpieler>4) {
            throw new SpielraumVollException();
        } else {

            //spieler.add(new Bot());
            anzahlSpieler++;
        }
    }

    public void spielraumVerlassen(String spielername) {
        spieler.removeIf(n -> (n.getNickname().equals(spielername)));
        anzahlSpieler--;
        if(anzahlSpieler<1) {
            spielraumSchliessen();
        }
    }

    public void spielraumSchliessen() {

    }


    public void spielStarten() throws NichtGenugSpielerException {

        /*Prüfen ob genug Spieler im Raum sind um ein Spiel zu starten*/
        if(anzahlSpieler<2) {
            throw new NichtGenugSpielerException();
        }
        /*Vorbereiten des Spielstapels und der Hände der Spieler*/

        for (int i=1; i<5 ; i++) {
            spielstapel.add(new Angriff(Integer.toString(i),"Angriff"));
        }
        for (int i = 5;i<9;i++) {
            spielstapel.add(new Hops(Integer.toString(i),"Hops"));
        }
        for (int i = 9;i<13;i++) {
            spielstapel.add(new Wunsch(Integer.toString(i),"Wunsch"));
        }
        for (int i = 13;i<17;i++) {
            spielstapel.add(new Mischen(Integer.toString(i),"Mischen"));
        }
        for (int i = 17;i<22;i++) {
            spielstapel.add(new Noe(Integer.toString(i),"Noe"));
        }
        for (int i = 22;i<27;i++) {
            spielstapel.add(new BlickInDieZukunkt(Integer.toString(i),"BlickInDieZukunft"));
        }
        for (int i = 27;i<31;i++) {
            spielstapel.add(new Katze1(Integer.toString(i),"Katze1"));
        }
        for (int i = 31;i<35;i++) {
            spielstapel.add(new Katze2(Integer.toString(i),"Katze2"));
        }
        for (int i = 35;i<39;i++) {
            spielstapel.add(new Katze3(Integer.toString(i),"Katze3"));
        }
        for (int i = 39;i<43;i++) {
            spielstapel.add(new Katze4(Integer.toString(i),"Katze4"));
        }
        for (int i = 43;i<47;i++) {
            spielstapel.add(new Katze5(Integer.toString(i),"Katze5"));
        }
        Collections.shuffle(spielstapel);

        int count = 47;

        for (Spieler s : spieler) {
            for (int i = 1; i<8;i++) {
                s.handkarte.add(spielstapel.pop());
            }
            s.handkarte.add(new Entschaerfung(Integer.toString(count),"Entschaerfung"));
            count++;
        }
        if(anzahlSpieler==2) {
            spielstapel.add(new Entschaerfung(Integer.toString(count),"Entschaerfung"));
            spielstapel.add(new Entschaerfung(Integer.toString(count+1),"Entschaerfung"));
            count+=2;
        } else {
            for (int i =6-anzahlSpieler; i>0;i--) {
                spielstapel.add(new Entschaerfung(Integer.toString(count),"Entschaerfung"));
                count++;
            }
        }
        for (int i = 1;i<anzahlSpieler;i++) {
            spielstapel.add(new ExplodingKitten(Integer.toString(count),"ExplodingKitten"));
            count++;
        }
        Collections.shuffle(spielstapel);

        /*Spielreihenfolge festlegen*/
        Collections.shuffle(spieler);
        reihenfolge = (ListIterator<Spieler>) spieler.iterator();
        current = reihenfolge.next();
    }


    public void karteLegen(String username, Karte k) throws NotYourRundeException, NoExplodingKittenException {
        if(!username.equals(amZug())) {
            if (!k.getEffekt().equals("Noe")){
                throw new NotYourRundeException();
            } else {
                kartenExecutor.execute(new KartenHandler(k,this));
            }
        } else {
            if(k.getEffekt().equals("Entschaerfung")){
                if(!isExpolding()) {
                    throw new NoExplodingKittenException();
                }
            } else{
                kartenExecutor.execute(new KartenHandler(k,this));
            }
        }
    }

    public void zugBeenden(String username) throws NotYourRundeException {
        if (!username.equals(amZug())) {
            throw new NotYourRundeException();
        } else {
            Thread draw = new Thread(new Drawer(username,this));
            draw.start();
        }
    }


    public void naechsterSpieler() {
        if(!angriff) {
            if (reihenfolge.hasNext()) {
                current = reihenfolge.next();
            } else {
                while (reihenfolge.hasPrevious()) {
                    current = reihenfolge.previous();
                }
            }
            angriff = false;
        }
    }


    public String amZug() {
        return current.getNickname();
    }

    //Methode für Angriff
    public boolean isAngriff() {
        return angriff;
    }

    public void setAngriff(boolean angriff) {
        this.angriff = angriff;
    }

    //Methoden für Wunsch
    public void selectSpieler(Spieler s) {
        this.ausgewahlter = s;
    }
    public void abgeben (String name,Karte k) throws NotYourRundeException {
        if (name.equals(ausgewahlter.getNickname())) {
            abgegeben = k;
            ausgewahlter.handkarte.remove(k);
            current.handkarte.add(k);
        } else {
            throw new NotYourRundeException();
        }
    }

    //Methoden für Entschärfung

    public boolean isExpolding() {
        return expolding;
    }

    public void setExpolding(boolean expolding) {
        this.expolding = expolding;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    //Methoden für Nö

    public boolean isNoe() {
        return noe;
    }
    public void changeNoe() {
        noe = !noe;
    }
    public void setNoe(boolean noe) {
        this.noe = noe;
    }

    //Chat-Methoden

    @Override
    public void sendMessage(String msg , String time ,String benutzername){
        chat.nachrichSchreiben(msg , time ,benutzername);

        for(String nom : userLobserverMap.keySet()){
            ILobbyObserver current = userLobserverMap.get(nom);
            try {
                current.updateMessageList();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public ArrayList<Nachricht> getMessage(){
        return chat.nachrichten;
    }


    @Override
    public void registerObserver(String userName, ILobbyObserver io){
        userLobserverMap.put(userName, io);
    }

}
