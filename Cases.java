import javafx.scene.control.Button;
import javafx.scene.image.ImageView;



public class Cases extends Button { //Classe pour les cases, qui sont en réalité des bouttons

  private int x;
  private int y;
  protected Piece piece;

  public Cases(int x, int y, boolean couleur){ //Constructeur des cases pour le Plateau
    this.x = x;
    this.y = y;
    if(couleur){ //Utilisation du booléen couleur, pour déterminer si la case est noire ou blance
      this.setStyle("-fx-background-radius: 0; -fx-min-height: 62.5px; -fx-min-width: 62.5px; -fx-pref-height: 62.5px; -fx-pref-width: 62.5px; -fx-max-height: 62.5px; -fx-max-width: 62.5px;-fx-background-image: url('assets/WoodB.jpg'); -fx-background-size: 62.5px; -fx-background-repeat: no-repeat; -fx-background-position: 62.5%;"); //Application du style pour la case
    }
    else {
      this.setStyle("-fx-background-radius: 0; -fx-min-height: 62.5px; -fx-min-width: 62.5px; -fx-pref-height: 62.5px; -fx-pref-width: 62.5px; -fx-max-height: 62.5px; -fx-max-width: 62.5px; -fx-background-image: url('assets/Wood.jpg'); -fx-background-size: 62.5px; -fx-background-repeat: no-repeat; -fx-background-position: 62.5%;");
    }

  }

  public Cases(int x, boolean couleur){ //Constructeur de cases pour la fenêtre de promotion
    this.x = x;
  }

  //Setters
  public void setX(int x){
    this.x = x;
  }

  public void setY(int y){
    this.y = y;
  }

  //Getters
  public int getX(){
    return this.x;
  }

  public int getY(){
    return this.y;
  }

  public Piece getPiece(){
    return this.piece;
  }


  public boolean estPris(){ //Test si la case est occupée
    return this.piece != null;
  }

  public void setPiece(Piece piece){ //Place une pièce sur le plateau
    this.piece = piece;
    if (this.piece != null)
      this.setGraphic(new ImageView(piece.getImage()));
    else
      this.setGraphic(new ImageView());
  }

  public String getPieceColor(){ //Pour récupérer la couleur de la pièce
    if (getPiece() != null)
      return getPiece().getColor();
    return "";
  }

  public Piece supprimePiece(){ //Permet de supprimer une pièce
    Piece current = this.piece;
    setPiece(null);
    return current;
  }

};
