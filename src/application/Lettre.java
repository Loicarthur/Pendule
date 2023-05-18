package application;
import java.util.ArrayList;
import java.util.Arrays;

//l'objectif c'est de : 
//retrouver le nom caractère par caratère
//la lettre qu'on trouve, il faut auvegarder son emplacement, la stocker dans une liste et ses indices
//dans la classe lettre, va servir à faire ça : stocker les indices de la lettre dans le mot qu'on cherche
//


public class Lettre {
	private char lettre;
	private ArrayList<Integer> positions = new ArrayList<Integer>();
	public ArrayList<Integer> getPositions() {
		return positions;
	}

	//c'est un constructeur un peu particulier, l'attribut String mot, ne fait pas partie des attributs de la classe
	//ici le constructeur est une méthode,  génére un objet mais qui fait un traitement supplémentaire, 
	//l'attribut posistion est calculé via ce constructeur
	
	public Lettre(char _lettre, String mot) {
		lettre = _lettre;
		for(int i=0;i<mot.length();i++) {
			if(mot.charAt(i)==lettre) {
				positions.add(i);
			//ci-dessus, on ajoute la position du caractère dans le mot
			}
		}
	}	
	@Override
	public String toString() {
		return String.valueOf(lettre)+positions;
	}
	@Override
	//utiliser pour indexer l'élément dans les différents types de collection (ArrayList, Set, Map...)
	//cela permettra de distribuer les éléments à rajouter dans une collection selon la valeur retournée de hascode,
	//càd: l'ajout ne se fera pas de façon séquentielle
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + lettre;
		return result;
	}
	@Override
	//cette méthode est générée (en cliquant sur buton droit -> source -> 
	// pour comparer deux objet de même type, ici l'objet lettre, càd comparer deux lettres
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lettre other = (Lettre) obj;
		if (lettre != other.lettre) {
			System.out.println("Equals :"+other.lettre);
			return false;
		}
		return true;
	}
}
