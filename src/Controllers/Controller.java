package Controllers;

import Film.Film;
import Film.ListeFilms;
import Salles.ListeSalles;
import Seances.ListeSeances;

import Seances.Seance;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import sample.ConfermBox;
import sample.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;

/**
 * Classe Controller
 * Controller de l'interface FilmsM.
 */
public class Controller implements Initializable {
    public StackPane film1;
    public Label title1;
    public Text synopsis1;
    public StackPane film2;
    public Label title2;
    public Text synopsis2;
    public StackPane film3;
    public Label title3;
    public Text synopsis3;
    public StackPane film4;
    public Label title4;
    public Text synopsis4;

    public ComboBox<String> days;
    public ComboBox<String> cat;
    public VBox listeS;
    public TextField serchBar;
    public TabPane tabs;
    public HBox topBox;

    public static ListeFilms lf = new ListeFilms();
    Queue<Film> qf = new LinkedList<>(lf.getFilms());

    public static ListeSeances ls = new ListeSeances();
    public static ListeSalles lsl = new ListeSalles();

    /**
     * M�thode initialize
     * Initialisation de l'interface FilmsM en chargeant les affiches des films, leur titre et leur synopsis,
     * ainsi que les diff�rents boutons permettant � l'admin d'acc�der ses fonctionnalit�s.
     * @param url
     * @param rb
     */
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        ListeSeances.updateSeance(ls.getSeances());
        if(LoginScreenController.u.getType().equals("Admin")) {
            HBox actions = new HBox();
            Button users = new Button("Utilisateurs");
            users.setOnAction(this::showUsers);

            Button planning = new Button("Planning");
            planning.setOnAction(this::addToPlannig);

            Button stat = new Button("Stat");
            stat.setOnAction(this::showStat);

            actions.getChildren().addAll(users, planning, stat);
            actions.setSpacing(5);
            actions.setAlignment(Pos.CENTER);

            topBox.getChildren().add(1, actions);
        }

        Film f = qf.poll();
        qf.add(f);
        ImageView bg = null;
        try {
            Image img = new Image("res/"+f.getTitre());
            bg = new ImageView(img);
        }catch (Exception e){
            Image img = new Image("res/icon.png");
            bg = new ImageView(img);
        }
        bg.setFitHeight(287);
        bg.setFitWidth(189);

        film4.getChildren().add(bg);
        title4.setText(f.getTitre());
        film4.setOnMouseClicked(e ->{
            createSeances(ls.getSeanceByFilm(title4.getText()));
        });

        f = qf.poll();
        qf.add(f);
        try {
            Image img = new Image("res/"+f.getTitre());
            bg = new ImageView(img);
        }catch (Exception e){
            Image img = new Image("res/icon.png");
            bg = new ImageView(img);
        }
        bg.setFitHeight(287);
        bg.setFitWidth(189);

        film3.getChildren().add(bg);
        title3.setText(f.getTitre());
        film3.setOnMouseClicked(e ->{
            createSeances(ls.getSeanceByFilm(title3.getText()));
        });

        f = qf.poll();
        qf.add(f);
        try {
            Image img = new Image("res/"+f.getTitre());
            bg = new ImageView(img);
        }catch (Exception e){
            Image img = new Image("res/icon.png");
            bg = new ImageView(img);
        }
        bg.setFitHeight(287);
        bg.setFitWidth(189);

        film2.getChildren().add(bg);
        title2.setText(f.getTitre());
        film2.setOnMouseClicked(e ->{
            createSeances(ls.getSeanceByFilm(title2.getText()));
        });

        f = qf.poll();
        qf.add(f);
        try {
            Image img = new Image("res/"+f.getTitre());
            bg = new ImageView(img);
        }catch (Exception e){
            Image img = new Image("res/icon.png");
            bg = new ImageView(img);
        }
        bg.setFitHeight(287);
        bg.setFitWidth(189);

        film1.getChildren().add(bg);
        title1.setText(f.getTitre());
        film1.setOnMouseClicked(e ->{
            createSeances(ls.getSeanceByFilm(title1.getText()));
        });
    }

    /**
     * M�thode showStat
     * Affichage des statistiques.
     * @param e
     */
    public void showStat(ActionEvent e){
        Main.changeWindow(e, "../xml/stats.fxml");
    }

    /**
     * M�thode addToPlannig
     * Affichage de l'interface d'ajout de s�ance.
     * @param e
     */
    public void addToPlannig(ActionEvent e){
        Main.changeWindow(e, "../xml/addToPlanning.fxml");
    }

    /**
     * M�thode showUsers
     * Affiche les comptes enregistr�s dans logiciel.
     * @param e
     */
    public void showUsers(ActionEvent e){
        Main.changeWindow(e, "../xml/UsresBoard.fxml");
    }

    /**
     * M�thode logOut
     * D�connexion du logiciel.
     * @param e
     */
    public void logOut(ActionEvent e){
        Main.changeWindow(e, "../xml/LoginScreen.fxml");
    }

    /**
     * M�thode nextFilm
     * D�filement � droite et mise � jour des conteneurs d'image des films,
     * de leur titre et de leur description.
     */
    public void nextFilm(){
        Film f = qf.poll();
        qf.add(f);
        ImageView bg = null;
        try {
            Image img = new Image("res/"+f.getTitre());
            bg = new ImageView(img);
        }catch (Exception e){
            Image img = new Image("res/icon.png");
            bg = new ImageView(img);
        }

        film4.getChildren().removeAll(film4.getChildren());
        if(!film3.getChildren().isEmpty())
            film4.getChildren().add(film3.getChildren().get(0));
        title4.setText(title3.getText());

        film3.getChildren().removeAll(film3.getChildren());
        if(!film2.getChildren().isEmpty())
            film3.getChildren().add(film2.getChildren().get(0));
        title3.setText(title2.getText());

        film2.getChildren().removeAll(film2.getChildren());
        if(!film1.getChildren().isEmpty())
            film2.getChildren().add(film1.getChildren().get(0));
        title2.setText(title1.getText());

        film1.getChildren().removeAll(film1.getChildren());
        film1.getChildren().add(bg);
        title1.setText(f.getTitre());
    }

    /**
     * M�thode prevFilm
     * Defilement � gauche et mise � jour des conteneurs d'image des films,
     * de leur titre et de leur description.
     */
    public void prevFilm(){
        Film f = qf.poll();
        qf.add(f);

        ImageView bg = null;
        try {
            Image img = new Image("res/"+f.getTitre());
            bg = new ImageView(img);
        }catch (Exception e){
            Image img = new Image("res/icon.png");
            bg = new ImageView(img);
        }
        bg.setFitHeight(287);
        bg.setFitWidth(189);

        film1.getChildren().removeAll(film1.getChildren());
        if(!film3.getChildren().isEmpty())
            film1.getChildren().add(film2.getChildren().get(0));
        title1.setText(title2.getText());

        film2.getChildren().removeAll(film2.getChildren());
        if(!film3.getChildren().isEmpty())
            film2.getChildren().add(film3.getChildren().get(0));
        title2.setText(title3.getText());

        film3.getChildren().removeAll(film3.getChildren());
        if(!film4.getChildren().isEmpty())
            film3.getChildren().add(film4.getChildren().get(0));
        title3.setText(title4.getText());

        film4.getChildren().removeAll(film4.getChildren());
        film4.getChildren().add(bg);
        title4.setText(f.getTitre());
    }

    /**
     * M�thode showDesc1
     * Simulation de l'effet hover.
     */
    public void showDesc1(){
        Film f = lf.getfilm(title1.getText());
        synopsis1.setText(f.getDescriptif());
    }

    /**
     * M�thode hideDesc1
     * Simulation de l'effet hover.
     */
    public void hideDesc1(){
        synopsis1.setText("");
    }

    /**
     * M�thode showDesc2
     * Simulation de l'effet hover.
     */
    public void showDesc2(){
        Film f = lf.getfilm(title2.getText());
        synopsis2.setText(f.getDescriptif());
    }

    /**
     * M�thode hideDesc2
     * Simulation de l'effet hover.
     */
    public void hideDesc2(){
        synopsis2.setText("");
    }

    /**
     * M�thode showDesc3
     * Simulation de l'effet hover.
     */
    public void showDesc3(){
        Film f = lf.getfilm(title3.getText());
        synopsis3.setText(f.getDescriptif());
    }

    /**
     * M�thode hideDesc3
     * Simulation de l'effet hover.
     */
    public void hideDesc3(){
        synopsis3.setText("");
    }

    /**
     * M�thode showDesc4
     * Simulation de l'effet hover.
     */
    public void showDesc4(){
        Film f = lf.getfilm(title4.getText());
        synopsis4.setText(f.getDescriptif());
    }
    
    /**
     * M�thode hideDesc4
     * Simulation de l'effet hover.
     */
    public void hideDesc4(){
        synopsis4.setText("");
    }

    /**
     * M�thode getSeances
     * Tri de l'affichage des s�ances par date.
     */
    public void getSeances(){
        days.getItems().removeAll(days.getItems());
        for(String day : ls.getProjectionDays())
            days.getItems().add(day);

        createSeances(ls.getSeances());
    }

    /**
     * M�thode sort
     * Gestion du tri de l'affichage des s�ances.
     */
    public void sort(){
        String selectedDay = days.getSelectionModel().getSelectedItem();
        String selectedCat = cat.getSelectionModel().getSelectedItem();
        String title = serchBar.getText();
        ArrayList<Seance> seancesD = ls.getSeancesByDay(selectedDay);
        ArrayList<Seance> seancesC = ls.getSeancesByCategorie(selectedCat);
        ArrayList<Seance> seancesT = ls.getSeanceByFilm(title);

        if(selectedDay == null && title.equals("")) {
            createSeances(seancesC);
        }else if(selectedCat == null && title.equals("")) {
            createSeances(seancesD);
        }else if(selectedDay == null && selectedCat == null){
            createSeances(seancesT);
        }else if(selectedDay == null){
            createSeances(seancesT);
        }else if(selectedCat == null){
            seancesD.retainAll(seancesT);
            createSeances(seancesD);
        }else if(title.equals("")){
            seancesD.retainAll(seancesC);
            createSeances(seancesD);
        }
    }

    /**
     * M�thode createSeances
     * Cr�ation de la liste affichable des s�ances en ayant un liste des s�ances.
     * @param seances La liste des s�ances � afficher.
     */
    private void createSeances(ArrayList<Seance> seances){
        tabs.getSelectionModel().select(1);
        listeS.getChildren().removeAll(listeS.getChildren());
        for(Seance s : seances){
            HBox b = new HBox();
            b.getStyleClass().add("item");
            b.setSpacing(200);

            ImageView imgv = null;
            try {
                Image img = new Image("res/"+s.getF().getTitre());
                imgv = new ImageView(img);
            }catch (Exception e){
                Image img = new Image("res/icon.png");
                imgv = new ImageView(img);
            }

            imgv.setFitHeight(205);
            imgv.setFitWidth(130);

            Label start = new Label("De: " +s.getHeureDebut());
            start.getStyleClass().add("gray-text");

            Label end = new Label("à: "+s.getHeureFin());
            end.getStyleClass().add("gray-text");

            HBox h = new HBox(start, end);
            h.getStyleClass().add("start-end");

            Label date = new Label("Le: " + s.getDate());
            date.getStyleClass().add("gray-text");

            Label dispo = new Label(s.getNbRes() + "/" + s.getS().getCapacite());

            Label titre = new Label(s.getF().getTitre());
            titre.setStyle("-fx-font-size: 35px; -fx-font-weight: 700; -fx-text-fill: #ba6c4f");

            Label salle = new Label("Salle: " + String.format("%d", s.getS().getNumeroSalle()));
            salle.setStyle("-fx-font-size: 20; -fx-text-fill: #d87d5c");

            VBox t = new VBox(titre, h, date, dispo, salle);
            t.setSpacing(10);

            if(!LoginScreenController.u.getType().equals("Comptable")) {
                Button btn = new Button("Reserver");

                btn.setOnAction(e -> {
                    ReservationController.loadInfo(s);
                    Main.changeWindow(e, "../xml/Reservation.fxml");
                });

                t.getChildren().add(btn);
            }

            if(LoginScreenController.u.getType().equals("Admin")){
                Button btn = new Button("Supprimer");

                btn.setOnAction(e -> {
                    if(ConfermBox.display("Supprimer", "Voulez vous supprimer cette seance")){
                        ls.Supprimer(s.getId_seance());
                        listeS.getChildren().remove(b);
                    }
                });

                t.getChildren().add(btn);
            }

            b.getChildren().addAll(imgv, t);
            listeS.getChildren().add(b);
        }
    }
}