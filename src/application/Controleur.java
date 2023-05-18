package application;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.text.Text;

public class Controleur {

	//les composants graphiques de l'interface 
	@FXML
	private Label lResteAJouer;
	@FXML
	private TextField tfSaisieLettre;
	@FXML
	private TextField tfMotDevine;
	@FXML
	private Button btValiderLettre;
	@FXML
	private Button btChoixMot;
	@FXML
	private Text tLettresFausses;
	@FXML
	private ImageView imagePendu;

	//images est un tableau qui va contenir les images du jeu
	Image[] images = new Image[8];


	//Hashset une liste : une collection de type ensemble, qui ne peut pas contenir de doublon
	//Hashset est un type de collection de Lettre 
	private HashSet<Lettre> mot = new HashSet<Lettre>();

	//lettreErreurs sera utilis�e pour pourvoir stocker les lettres non correcte, donn�es par le joueur 
	private HashSet<Lettre> lettresErreurs = new HashSet<Lettre>();

	//StringBuffer une structure de donn�es de chaine de caract�re. 
	//L'utilit� de StringBuffer est que les traitement se font sur la m�me chaine et sera affich�e dans le composant tfMotDevine  
	private StringBuffer chaineTrouvee; 
	
	private int err = 0;
	private int nbLettresTrouvees = 0;
	public static String chaine = "pollution";
	private int nbCoups = 6;
	//listeMots est une liste de chaine de caract�res qui va contenur les mots � deviner 
	public List<String> listeMots = new ArrayList<String>(); 	


	//la fonction initialize() permet d'initaliser l'interface du jeu et tous les objets n�cessaires
	@FXML
	public void initialize() {
		listeMots.add("java");
		listeMots.add("mondialisation");
		listeMots.add("lancement");
		
		tLettresFausses.setText("Lettres d�j� tir�es :");
		nbCoups = 6;
		err = 0;
		// dessinerPendu(err); plus tard
		String txt = lResteAJouer.getText();
		//la fonction split permet de d�composer txt contenu dans le label "lResteAJouer" par rapport au : 
		String[] tab = txt.split(":");
	
		lResteAJouer.setText(tab[0] + ":" + nbCoups);
		//appel de fonction pour charger un fichier d'images. Cf. plus loin la fonction chargerImages()
		chargerImages();
		//chargerMots(); //on n'utilisera pas cette fonction

		System.out.println("liste mots : " + listeMots);
		
		//la fonction de Random ici permet de tirer au hasard, un mot � deviner de la liste "listeMots"
		//la variable chaine va contenir le mot � deviner 
		Random rnd = new Random();
		chaine = listeMots.get(rnd.nextInt(listeMots.size()));
		System.out.println(chaine);
		//mettre le contenu de la chaine en majescule 
		chaine = chaine.toUpperCase();
		
		//traitement initiale qui permet d'afficher autant de "_" que le nombre de lettre dans le mot � deviner
		chaineTrouvee = new StringBuffer();
		for (int i = 0; i < chaine.length(); i++) {
			chaineTrouvee.append('_');
			chaineTrouvee.append(' ');
		}
		tfMotDevine.setText(chaineTrouvee.toString());
		
		System.out.println(chaineTrouvee.length());
		System.out.println(chaine);

		//la boucle ci-apr�s est tr�s utile car elle permet d'obtenir le hashSet de Lettre contenant tous les caract�res du mot � deviner avec leur position respective 
		for (int i = 0; i < chaine.length(); i++) {
			Lettre let = new Lettre((char) chaine.charAt(i), chaine);
			mot.add(let);
		}

	}
	
	//la fonction suivante permet de d�finir le comportement du bouton "btValiderLettre"
	@FXML
	public void actionValiderLettre() {

		try {
			//r�cup�rer le contenu � partir du "tfSaisieLettre" dans la variable let,
			String let = tfSaisieLettre.getText();
			let = let.trim(); //fct java qui permet d'enlever les espaces
			
			//ici on traite que le premier caract�re saisi dans "tfSaisieLettre"
			char car = let.toUpperCase().charAt(0);
			
			//l'instruction suivante permet de cr�er l'objet Lettre du caract�re saisi, 
			//c�d; en revenant � la classe Lettre, cela signifie que <ArrayList> positions donne les positions de cette lettre dans le mot � deviner
			Lettre lettre = new Lettre(car, chaine);
			
			
			if (mot.contains(lettre)) { 
				//le cas o� le caract�re est pr�sent dans le mot � deviner
				System.out.println(car + " trouv�");
				tfSaisieLettre.setText("");

				System.out.println("taille=" + lettre.getPositions());
				nbLettresTrouvees += (lettre.getPositions().size());

				//la boucle ci-apr�s permet de parcourir les positions du caract�re et de mettre � jour le StringBuffer chaineTrouvee  
				for (int i = 0; i < lettre.getPositions().size(); i++) {
					int pos = lettre.getPositions().get(i);
					chaineTrouvee.setCharAt(pos * 2, car);
				}
				tfMotDevine.setText(chaineTrouvee.toString());

				System.out.println("Lettres trouv�ees :" + nbLettresTrouvees);
				if (nbLettresTrouvees == chaine.length()) {
					
				//traitement accessoir 
					//La classe alert est utiliser pour cr�er un objet pour afficher une fenetre modal ou une boite de dialogue
					Alert dialog = new Alert(AlertType.INFORMATION);
					
					dialog.setTitle("FIN DE PARTIE");
					dialog.setHeaderText(null);
					dialog.setContentText("Vous avez GAGNE !!");
					dialog.showAndWait();
					Platform.exit();
				}
			} else //traitemenu du cas lettre est non trouv�e dans le mot � deviner
				{
				System.out.println("lettresErreurs = "+lettresErreurs);
				System.out.println("Lettre = "+lettre);
				if (!lettresErreurs.contains(lettre)) {
					nbCoups--;
					String txt = lResteAJouer.getText();
					String[] tab = txt.split(":");
					lResteAJouer.setText(tab[0] + ":" + nbCoups);
					tfSaisieLettre.setText("");
					System.out.println(car + " non trouv�");
					err++;
					//ci-apr�s appel de la fonction dessinerPendul en fonction de la valeur de err 
					dessinerPendu(err);
					tLettresFausses.setText(tLettresFausses.getText() + car + "-");
				}
				else {
					lettresErreurs.add(lettre);
					
					System.out.println(car + " d�j� tir�.");
				}
				if (err == 6) {

				}
			}
		} catch (StringIndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();

			tfSaisieLettre.getStyleClass().add("my-button");
		}
	}

	@FXML
	public void actionRejouer() {
		System.out.println("Rejouer");
		initialize();
	}
	// Méthodes auxiliaires

	private void chargerImages() {// Chargement des images
		for (int i = 0; i < 8; i++) {
			//l'instruction qui permet de charger un fichier d'images 
			Path P = Paths.get("application", "images", "pendu_" + i + ".jpg"); // import java.nio.file.Path
			//demander d'afficher P avec toString
			images[i] = new Image(P.toString());
			//le chemin o� se trouve les images
		}
		
	}

	private void chargerMots() {// Chargement des images
		Path P1 = Paths.get(System.getProperty("user.dir"));
		Path P2 = Paths.get(P1.toString(), "src\\application\\docs", "dictionnaire.txt");
		try {
			System.out.println(P2.toString());
			listeMots = Files.readAllLines(P2.toAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void dessinerPendu(int err) {
		imagePendu.setImage(images[err]);
	}

	private void afficheAlert() {
		Alert dialog = new Alert(AlertType.CONFIRMATION);
		dialog.setTitle("FIN DE PARTIE");
		dialog.setHeaderText(null);
		dialog.setContentText("Vous avez perdu !!");
		dialog.showAndWait();
		Platform.exit();
	}
}
