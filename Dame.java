public class Dame extends Piece{ //Classe de la Dame
  public Dame(boolean couleur){
    super(couleur);
  }
  protected String getName(){ //Méthode get pour le nom de la pièce
    return "Dame";
  }
}
