package gui.controller.Spiel;

import gui.controller.SpielraumController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import server.karten.Karte;

public class KarteController {

    double ogX;
    double ogY;
    Karte k;

    @FXML
    ImageView karte;


    public void setKarte(Karte k) {
        this.k = k;
    }
    public void bewegen(MouseEvent mouseEvent) {

        ImageView p = (ImageView) mouseEvent.getTarget();

        p.setX(mouseEvent.getX()-53);
        p.setY(mouseEvent.getY()-75);

    }

    public void check(MouseEvent mouseEvent) {

        ImageView p = (ImageView) mouseEvent.getTarget();
        if (350 <= p.getX() + 53 && p.getX() + 53 <= 457 && 200 <= p.getY() + 75 && p.getY() + 75 <= 350) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../controller/SpielraumController"));
            SpielraumController sc = loader.getController();
            sc.legen(k);
        } else {
            p.setX(ogX);
            p.setY(ogY);
        }
    }

    public void record(MouseEvent mouseEvent) {
        ImageView r = (ImageView) mouseEvent.getTarget();

        ogX = r.getX();
        ogY = r.getY();

    }
}
