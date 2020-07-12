package gui.controller.Spiel;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;


public class OpponentController {

    @FXML
    Text name;
    @FXML
    Text anzahlk;
    @FXML
    ImageView bild;


    /**
     * Erstellt die Ansicht für die Gegener im Spielraum
     *
     * @param nummer Nummer des Gegeners in der Lobby zwischne 1 und 4
     * @param n Name des Gegners
     * @param karten Anzahl an Handkarten
     */
    public void set(int nummer, String n, int karten) {
        name.setText(n);
        anzahlk.setText("Anzahl Karten:"+karten);
        switch (nummer) {
            case 1:
                Image image = new Image(getClass().getResource("../../images/user_images/playericon2.png").toString(),100,0,true,false);
                bild.setImage(image);
                break;
            case 2:
                Image image2 = new Image(getClass().getResource("../../images/user_images/playericon3.png").toString(),100,0,true,false);
                bild.setImage(image2);
                break;
            case 3:
                Image image3 = new Image(getClass().getResource("../../images/user_images/playericon4.png").toString(),100,0,true,false);
                bild.setImage(image3);
                break;
            case 4:
                Image image4 = new Image(getClass().getResource("../../images/user_images/playericon7.png").toString(),100,0,true,false);
                bild.setImage(image4);
                break;
        }
    }
}
