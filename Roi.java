
public class Roi extends Piece{ //Classe du Roi
  public Roi(boolean couleur){
    super(couleur);
  }

  protected String getName(){ //Méthode get pour le nom de la pièce
    return "Roi";
  }
}
