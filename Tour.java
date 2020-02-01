
public class Tour extends Piece{ //Classe de la Tour
  public Tour(boolean couleur){
    super(couleur);
  }

  protected String getName(){ //Méthode get pour le nom de la pièce
    return "Tour";
  }
}
