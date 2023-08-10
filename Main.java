import java.sql.*;

/**
 * Cette classe sert à se connecter dans une base de données et effectuer des requêtes SQL.
 *
 * @author : Noureddine Stambouli CODE PERMANENT : STAN65090008
 * @author : Issam Khalladi CODE PERMANENT : KHAI27029800
 */
public class Main {
  public static final String BD_URL_SERVEUR = "zeta2.labunix.uqam.ca";
  public static final int BD_PORT = 1521;
  public static final String BD_SID = "BACLAB";
  public static final String BD_UTILISATEUR = "fc891922";
  public static final String BD_MOT_PASSE = "TOwlCrVk";

  public static void ajouterEntretien(
      int noEntretien,
      String date,
      double nbHeures,
      int noImmeuble,
      int noLogement,
      String employe) {
    try {
      Class.forName("oracle.jdbc.driver.OracleDriver");

      Connection laConnectionJDBC =
          DriverManager.getConnection(
              "jdbc:oracle:thin:@" + BD_URL_SERVEUR + ":" + BD_PORT + ":" + BD_SID,
              BD_UTILISATEUR,
              BD_MOT_PASSE);

      Statement requete = laConnectionJDBC.createStatement();

      String insertSQL =
          "INSERT INTO ENTRETIENS (NO_ENTRETIEN, ENT_DATE, NB_HEURES, IMM_NO_IMMEUBLE,"
              + " LOG_NO_LOGEMENT, EMP_EMP_NOM) VALUES ("
              + noEntretien
              + ", TO_DATE('"
              + date
              + "', 'YYYY-MM-DD'), "
              + nbHeures
              + ", "
              + noImmeuble
              + ", "
              + noLogement
              + ", '"
              + employe
              + "')";

      requete.executeUpdate(insertSQL);

      System.out.println("Entretien ajouté avec succès.");

      requete.close();
      laConnectionJDBC.close();

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    int buildingNumber = Integer.parseInt(args[0]);
    try {
      Class.forName("oracle.jdbc.driver.OracleDriver");

      Connection laConnectionJDBC =
          DriverManager.getConnection(
              "jdbc:oracle:thin:@" + BD_URL_SERVEUR + ":" + BD_PORT + ":" + BD_SID,
              BD_UTILISATEUR,
              BD_MOT_PASSE);

      Statement requete = laConnectionJDBC.createStatement();

      ResultSet resultats =
          requete.executeQuery(
              "SELECT * FROM LOGEMENTS NATURAL JOIN IMMEUBLES  WHERE A_LOUER = 'O' AND NO_IMMEUBLE"
                  + " = "
                  + buildingNumber);

      while (resultats.next()) {
        String immeuble = resultats.getString("NO_IMMEUBLE");
        String nombre_logement = resultats.getString("NO_LOGEMENT");
        String loyer = resultats.getString("LOYER");
        String nb_chambres = resultats.getString("NB_CHAMBRES");
        String bains = resultats.getString("NB_SALLE_BAINS");
        String alouer = resultats.getString("A_LOUER");
        String chauffage = resultats.getString("CHAUFFAGE");
        String foyer = resultats.getString("FOYER");
        String meuble = resultats.getString("MEUBLE");
        String diner = resultats.getString("SALLE_A_DINER");

        System.out.println(
            "IMMEUBLE: "
                + immeuble
                + "\t\tNO_LOGEMENT: "
                + nombre_logement
                + "\t\tLOYER: "
                + loyer
                + "\t\tNOMBRE DE CHAMBRES: "
                + nb_chambres
                + "\t\tNB_SALLE_BAINS: "
                + bains
                + "\t\tA_LOUER`:"
                + alouer
                + "\t\tCHAUFFAGE: "
                + chauffage
                + "\t\tFOYER: "
                + foyer
                + "\t\tMEUBLE: "
                + meuble
                + "\t\tSALLE_A_DINER: "
                + diner);
      }

      resultats.close();
      requete.close();
      laConnectionJDBC.close();

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }

    ajouterEntretien(10, "2023-06-10", 4.5, 1010, 1, "Lisa Thomas");
  }
}
