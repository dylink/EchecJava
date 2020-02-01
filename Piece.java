import javafx.scene.image.Image;

public abstract class Piece { //Classe abstraite pièce, qui regroupe les attributs communs aux pièces
  protected boolean couleur;
  protected Image image;

  public Piece(boolean couleur){ //Constructeur de Piece
    this.couleur = couleur;

    String chemin = "assets/pieces/" + this.getName() + "_" + this.getColor() + ".png"; //Localisation des images correspondantes aux pièces, selon leur nom et leur couleur
    this.image = new Image(chemin); //Variable contenant l'image correspondant aux pièces
  }
  public String getColor(){ //Récupère la couleur de la pièce
    if (this.couleur == true)
      return "Blanc";
    else
      return "Noir";
  }

  public Image getImage(){ //Récupère l'image de la pièce
    return this.image;
  }

  protected abstract String getName(); //Méthode pour récupérer le nom des pièces
}
