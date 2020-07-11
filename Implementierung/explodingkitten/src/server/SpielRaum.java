package server;

import exceptions.*;
import gui.controller.IRaumObserver;
import server.karten.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SpielRaum extends UnicastRemoteObject implements SpielRaumInterface{

    public SpielChat chat;
    public HashMap<String, IRaumObserver> userRaumServerMap;

    public String name;
    public int anzahlSpieler;
    public ArrayList<Spieler> spieler = new ArrayList<>();
    public Stack<Karte> spielstapel = new Stack<>();
    public Stack<Karte> ablagestapel = new Stack<>();
    public Spieler current;
    public ListIterator<Spieler> reihenfolge;
    boolean running = false;
    int botcounter = 1;

    Executor kartenExecutor = Executors.newSingleThreadExecutor();


    public boolean angriff = false;
    public Spieler ausgewahlter;
    public Karte abgegeben;
    public boolean expolding = false;
    public int position = 0;
    public Karte explKitten;
    public boolean noe = false;


    /**
     * Initialisiert den Spielraum und dessen Chat
     *
     * @throws RemoteException Fehler bei Verbindung
     */
    public SpielRaum() throws RemoteException{
        this.chat = new SpielChat();
        userRaumServerMap = new HashMap<>();
    }

    /**
     * Fügt einen Bot in den Spielraum ein, wenn noch Platz ist
     *
     * @throws SpielraumVollException   Wenn kein Platz mehr ist
     */
    public void botHinzufuegen() throws SpielraumVollException {
        if(anzahlSpieler>4) {
            throw new SpielraumVollException();
        } else {
            Spieler bot = new Spieler(true);
            bot.nickname = "BOT"+botcounter;
            spieler.add(bot);
            botcounter++;
            anzahlSpieler++;
        }
    }

    /**
     * Der angegebene Spieler verlässt den Spielraum
     * @param spielername   Name des Spielers der den Raum verlässt
     */
    public boolean spielraumVerlassen(String spielername) {
        spieler.removeIf(n -> (n.getNickname().equals(spielername)));
        anzahlSpieler--;
        if(spieler.isEmpty()) {
            return true;
        } else {
            for(Spieler s : spieler) {
                if(!s.isBot) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Startet das Spiel indem wie im Regelwerk vorgeschrieben die Karten verteilt werden
     * Bestimmt außerden die Spielreihenfolge und gibt diese als Iterator an
     *
     * @throws NichtGenugSpielerException Falls nur ein Spiler im Raum ist
     */
    public void spielStarten() throws NichtGenugSpielerException {


        /*Prüfen ob genug Spieler im Raum sind um ein Spiel zu starten*/
        if(anzahlSpieler<2) {
            throw new NichtGenugSpielerException();
        }

        running= true;

        /*Vorbereiten des Spielstapels und der Hände der Spieler*/

        for (int i=1; i<5 ; i++) {
            spielstapel.push(new Karte(Integer.toString(i),"Angriff"));
        }
        for (int i = 5;i<9;i++) {
            spielstapel.push(new Karte(Integer.toString(i),"Hops"));
        }
        for (int i = 9;i<13;i++) {
            spielstapel.push(new Karte(Integer.toString(i),"Wunsch"));
        }
        for (int i = 13;i<17;i++) {
            spielstapel.push(new Karte(Integer.toString(i),"Mischen"));
        }
        for (int i = 17;i<22;i++) {
            spielstapel.push(new Karte(Integer.toString(i),"Noe"));
        }
        for (int i = 22;i<27;i++) {
            spielstapel.push(new Karte(Integer.toString(i),"BlickInDieZukunft"));
        }
        for (int i = 27;i<31;i++) {
            spielstapel.push(new Karte(Integer.toString(i),"Katze1"));
        }
        for (int i = 31;i<35;i++) {
            spielstapel.push(new Karte(Integer.toString(i),"Katze2"));
        }
        for (int i = 35;i<39;i++) {
            spielstapel.push(new Karte(Integer.toString(i),"Katze3"));
        }
        for (int i = 39;i<43;i++) {
            spielstapel.push(new Karte(Integer.toString(i),"Katze4"));
        }
        for (int i = 43;i<47;i++) {
            spielstapel.push(new Karte(Integer.toString(i),"Katze5"));
        }
        Collections.shuffle(spielstapel);

        int count = 47;

        for (Spieler s : spieler) {
            for (int i = 1; i<8;i++) {
                Karte k = spielstapel.pop();
                s.handkarte.add(k);
            }
            Karte e = new Karte(Integer.toString(count),"Entschaerfung");
            s.handkarte.add(e);
            count++;
        }
        if(anzahlSpieler==2) {
            spielstapel.push(new Karte(Integer.toString(count),"Entschaerfung"));
            spielstapel.push(new Karte(Integer.toString(count+1),"Entschaerfung"));
            count+=2;
        } else {
            for (int i =6-anzahlSpieler; i>0;i--) {
                spielstapel.push(new Karte(Integer.toString(count),"Entschaerfung"));
                count++;
            }
        }
        for (int i = 1;i<anzahlSpieler;i++) {
            spielstapel.push(new Karte(Integer.toString(count),"ExplodingKitten"));
            count++;
        }
        Collections.shuffle(spielstapel);
        System.out.println("Spiel vorbereitet.");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*Spielreihenfolge festlegen*/
        Collections.shuffle(spieler);
        reihenfolge = spieler.listIterator();
        current = reihenfolge.next();
        if(current.isBot) {
            current.zugDurchfuehren(this);
        } else {
            notify(current.getNickname(), "DuBistDran", null);
        }
    }

    /**
     * Ausführen des Regelwerks, wenn ein Spieler eine bestimmte Karte ablegt
     * Lässt immer nur eine Karte nacheinander ausspielen
     *
     *
     * @param username  Benutzer der die Karte legen will
     * @param k Karte die gelegt werden soll
     * @throws NotYourRundeException    Verhindert das jemad außerhalb seiner Runde ein Karte legt, außer Nö!
     * @throws NoExplodingKittenException   Verhindert das ablegen einer Entschärfung ohne Exploding Kitten.
     */
    public void karteLegen(String username, Karte k) throws NotYourRundeException, NoExplodingKittenException {

        if(k.getEffekt().equals("Noe")) {
            notify(username,"AbgelegtDu",k);
            notifyEverybody("Abgelegt",k);
            current.handkarte.remove(k);
            ablagestapel.push(k);
            changeNoe();
        } else if (!username.equals(amZug())) {
            throw new NotYourRundeException();
        } else if (k.getEffekt().equals("Entschaerfung")) {
            if (!isExpolding()) {
                throw new NoExplodingKittenException();
            }
        } else {
                notify(username, "AbgelegtDu", k);
                notifyEverybody("Abgelegt", k);
                kartenExecutor.execute(new KartenHandler(k, this));
        }
    }

    /**
     * Beendet den Zug eines Spielers und lässt ihn eine Karte ziehen
     *
     * @param username  Name des Spielers, der seinen Zug beenden will
     * @throws NotYourRundeException    Verhindert, dass jemand seinen Zug beendet, ohne am Zug zu sein
     */
    public void zugBeenden(String username) throws NotYourRundeException {
        if (!username.equals(amZug())) {
            throw new NotYourRundeException();
        } else {
            Thread drawer = new Thread(new Drawer(username,this));
            drawer.start();
        }
    }

    /**
     * Lässt den Iterator einen Schritt weiter laufen, außer es wurde vorher eine Angriff-Karte gespielt
     */
    public void naechsterSpieler() {
        if(!angriff) {
            if (!reihenfolge.hasNext()) {
                while (reihenfolge.hasPrevious()) {
                    current = reihenfolge.previous();
                }
            }
            current = reihenfolge.next();
        } else {
            angriff = false;
        }
        if(current.isBot) {
            current.zugDurchfuehren(this);
        } else {
            notify(current.getNickname(), "DuBistDran", null);
        }
    }

    /**
     *
     * @return Gibt den Namen des Spielers zurück, der am Zug ist
     */
    public String amZug() {
        return current.getNickname();
    }

    //Methode für Angriff

    /**
     *
     * @return gibt an ob eine Angriff-Karte aktiv ist
     */
    public boolean isAngriff() {
        return angriff;
    }

    /**
     * Aktiviert den Angriff Zustand, sodass der nächste Spieler zweimal ziehen muss
     * @param angriff Zustand des Angriffs
     */
    public void setAngriff(boolean angriff) {
        this.angriff = angriff;
    }

    //Methoden für Wunsch


    public void selectSpieler(Spieler s) {
        if(current.isBot) {
            current.auswaehlen(this);
        } else {
            notify(current.getNickname(), "Auswahl", null);
        }
    }

    @Override
    public void setAusgewaehler(Spieler s) {
        ausgewahlter = s;
        if(ausgewahlter.isBot) {
            ausgewahlter.abgeben(this);
        } else {
            notify(ausgewahlter.getNickname(), "Abgeben", null);
        }
    }


    public void abgeben (String name,Karte k) {
        abgegeben = k;
        ausgewahlter.handkarte.remove(k);
        current.handkarte.add(k);
        if(!current.isBot) {
            notify(current.getNickname(), "Bekommen", k);
        } if (!ausgewahlter.isBot) {
            notify(name, "Abgegeben", k);
        }
    }

    //Methoden für Entschärfung

    public boolean isExpolding() {
        return expolding;
    }

    public void setExpolding(boolean expolding) {
        this.expolding = expolding;
    }

    //Position für das entschärfte Exploding Kitten im Deck

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

        for(String nom : userRaumServerMap.keySet()){
            IRaumObserver observer = userRaumServerMap.get(nom);
            try {
                observer.updateMessageList();
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
    public void registerObserver(String userName, IRaumObserver io){
        userRaumServerMap.put(userName, io);
    }

    /**
     * Lässt einen neuen Spieler den Raum betreten
     * Erstellt dafür ein neues Spieler Objekt
     *
     * @param name Name des Neuen Spielers
     */
    @Override
    public void betreten(String name) throws SpielLauftBereitsException {
        if(isRunning()) {
            throw new SpielLauftBereitsException();
        } else {
            Spieler sp = new Spieler(false);
            sp.nickname = name;
            spieler.add(sp);
            anzahlSpieler++;
        }
    }


    public ArrayList<Spieler> getSpieler() {
        return spieler;
    }


    @Override
    public boolean isRunning(){
        return running;
    }

    @Override
    public Spieler getCurrent() {
        return current;
    }


    public void notify(String s, String message,Karte k) {
        for(String nom : userRaumServerMap.keySet()){
            IRaumObserver observer = userRaumServerMap.get(nom);
            try {
                observer.notify(s,message,k);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void notifyEverybody(String message,Karte k) {
        for(String nom : userRaumServerMap.keySet()){
            IRaumObserver observer = userRaumServerMap.get(nom);
            try {
                observer.notify(nom,message,k);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public int getStapelSize() {
        return spielstapel.size();
    }

    @Override
    public void explodiert(String spielername) {

        boolean leer = spielraumVerlassen(spielername);

        for(Karte k : current.handkarte) {
            ablagestapel.push(k);
            notify(spielername,"AbgelegtDu",k);
            notifyEverybody("Abgelegt",k);
        }
        if(leer) {
            notifyEverybody("BotWin",null);
            return;
        }
        if(anzahlSpieler == 1) {
            notify(spieler.get(0).getNickname(),"Gewonnen",null);
            //TODO + bestenliste + raum schließen

        } else {
            reihenfolge = spieler.listIterator();
        }
    }
}
