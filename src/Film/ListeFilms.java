package Film;

import Observateur.ObservateursListeFilms;
import Observateur.Sujet;
import sample.Strategy.ConnectorInterface;
import sample.Strategy.MySQL_Connector;

import java.sql.Connection;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.sql.Statement;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Classe ListFilms
 * Classe permettant d'interagir avec la table Film de la base de donn�es.
 */
public class ListeFilms implements Sujet {
    List<ObservateursListeFilms> observateurs;
    Queue<Film> films;
    Film filmModifie;
    ConnectorInterface connector;

    /**
     * Constructeur ListeFilms
     * Constructeur de la classe permettant de charger tous les films de la table Film
     * dans une structure de donn�es de type FIFO (File).
     */
    public ListeFilms() {
        observateurs=new ArrayList<>();
        try {
           // Connection connection =  MySQL_Connector.connect();
            this.connector = MySQL_Connector.getInstance();
            Connection connection = ((MySQL_Connector)connector).connect();
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM films");

            films = new LinkedList<>();
            while(res.next()){
                Film f = new Film(res.getString("titre"),
                        res.getString("realisateur"),
                        res.getString("date_sortie"),
                        res.getString("categorie"),
                        res.getString("Date_publi"),
                        res.getString("descriptif"));
                f.setId_film(res.getInt("id_Film"));
                films.add(f);
            }
            res.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode Ajout
     * Ajoute le film f dans la base de données et dans la file.
     * @param f : Film à ajouter.
     */
    public boolean Ajout(Film f) {
        try {
           // Connection connection =  MySQL_Connector.connect();
            Connection connection = ((MySQL_Connector)connector).connect();
            Statement statement = connection.createStatement();
            String query = "INSERT INTO films (titre, realisateur,date_Sortie,categorie,  Date_Publi ,descriptif) VALUES"
                    + " ('"+ f.getTitre() +
                    "','"+f.getRealisateur()+
                    "','"+f.getDate_sortie()+
                    "','"+f.getCategorie()+
                    "','"+f.getDate_publi()+
                    "','"+f.getDescriptif()+"')";

            if(statement.executeUpdate(query) == 1) {
                films.add(f);

                query = "Select id_film From films WHERE"
	                    + " titre='"+ f.getTitre()+"'";
	
	            ResultSet res = statement.executeQuery(query);
	            
	            res.next();
	            
	            if(res != null)
	            	f.setId_film(res.getInt("id_film"));
            	res.close();  
            	return true;
            }
        }catch( Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Méthode Modifier
     * Modifie les informations d'un film identifié par son titre.
     * @param f La nouvelle version des informations du film.
     * @param id_film Identifiant du film � modifier.
     */
    public boolean Modifier(Film f, int id_film) {

        try {
          //  Connection connection =  MySQL_Connector.connect();
            Connection connection = ((MySQL_Connector)connector).connect();
            Statement statement = connection.createStatement();
            String query= "UPDATE films SET titre='"+f.getTitre()
                    +"',realisateur='"+f.getRealisateur()
                    +"',date_Sortie='"+f.getDate_sortie()
                    +"',categorie='"+f.getCategorie()
                    +"',Date_publi='"+f.getDate_publi()
                    +"',descriptif='"+f.getDescriptif()
                    +"' WHERE id_film="+id_film;

            if(statement.executeUpdate(query) == 1) {
                films.remove(getfilm(f.getTitre()));
                films.add(f);
                return true;
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Méthode Suppression
     * Supprime un film de la base de données et de la file
     * @param titre Film à supprimer.
     */
    public boolean Suppression(String titre) {
        try {
            filmModifie=getfilm(titre);
           // Connection connection =  MySQL_Connector.connect();
            Connection connection = ((MySQL_Connector)connector).connect();
            Statement statement = connection.createStatement();
            String query="DELETE FROM films WHERE titre='"+titre+"'";

            if(statement.executeUpdate(query) == 1) {
                films.remove(getfilm(titre));
                notifier();
                return true;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Méthode getfilm
     * Cherche un film dans la base de données en ayant son titre.
     * @param titre Titre du film.
     * @return Le film trouvé.
     */
    public Film getfilm(String titre) {
        for(Film f : films){
            if(f.getTitre().equals(titre))
                return f;
        }
        return null;
    }

    /**
     * Méthode getfilmById
     * Cherche un film dans la base de données en ayant son identifiant
     * @param id Identifiant du film.
     * @return Le film trouvé.
     */
    public Film getfilmById(int id) {
        for(Film f : films){
            if(f.getId_film() == id)
                return f;
        }
        return null;
    }

    /**
     * M�thode getFilmByCategorie
     * Renvoie une file de films ayant la m�me cat�gorie.
     * @param categorie
     */
    public boolean getFilmByCategorie(String categorie) {
        try {
           // Connection connection =  MySQL_Connector.connect();
            Connection connection = ((MySQL_Connector)connector).connect();
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM films WHERE categorie='"+categorie+"'");
            if(res != null) {
                films.removeAll(films);
                while (res.next()){
                    films.add( new Film(res.getString("titre"),
                            res.getString("realisateur"),
                            res.getString("date_sortie"),
                            res.getString("categorie"),
                            res.getString("Date_publi"),
                            res.getString("descriptif")));
                }
                res.close();
                statement.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Méthode getFilms
     * Retourne les films de la liste.
     * @return
     */
    public Queue<Film> getFilms() {
        return films;
    }

    @Override
    public void addObservateur(ObservateursListeFilms ob) {
        if(!observateurs.contains(ob)) {
            observateurs.add(ob);
        }

    }

    @Override
    public void removeObservateur(ObservateursListeFilms ob) {
        int id;
        if(observateurs.contains(ob)) {
         id=observateurs.indexOf(ob);
         observateurs.remove(id);

    }

    }

    @Override
    public void notifier() {
        for(ObservateursListeFilms ob:observateurs){
            ob.update(filmModifie);
        }

    }
}

