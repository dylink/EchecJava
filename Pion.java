public class Pion extends Piece{ //Classe du pion
  public Pion(boolean couleur){
    super(couleur);
  }

  protected String getName(){ //Méthode get pour le nom de la pièce
    return "Pion";
  }
}
