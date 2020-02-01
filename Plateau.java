import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Plateau extends GridPane { //Classe du plateau
  public Cases[][] carre = new Cases[8][8]; //Déclaration des cases du plateau
  public Cases current = null; //Déclaration de la case courrante
  public boolean joueur = true; //Variable qui détermine à qui est le tour
  public Stage newF = new Stage(); //Déclaration du stage qui servira à la fenêtre

  /******************************************************* Variable qui stockent les styles à appliquer aux cases ************************************************************************************/
  final String possible = "-fx-background-radius: 0; -fx-min-height: 62.5px; -fx-min-width: 62.5px; -fx-pref-height: 62.5px; -fx-pref-width: 62.5px; -fx-max-height: 62.5px; -fx-max-width: 62.5px;-fx-background-image: url('assets/WoodBl2.jpg'); -fx-background-size: 62.5px; -fx-background-repeat: no-repeat; -fx-background-position: 62.5%;";
  final String caseBlanche = "-fx-background-radius: 0; -fx-min-height: 62.5px; -fx-min-width: 62.5px; -fx-pref-height: 62.5px; -fx-pref-width: 62.5px; -fx-max-height: 62.5px; -fx-max-width: 62.5px;-fx-background-image: url('assets/WoodB.jpg'); -fx-background-size: 62.5px; -fx-background-repeat: no-repeat; -fx-background-position: 62.5%;";
  final String caseNoire = "-fx-background-radius: 0; -fx-min-height: 62.5px; -fx-min-width: 62.5px; -fx-pref-height: 62.5px; -fx-pref-width: 62.5px; -fx-max-height: 62.5px; -fx-max-width: 62.5px;-fx-background-image: url('assets/Wood.jpg'); -fx-background-size: 62.5px; -fx-background-repeat: no-repeat; -fx-background-position: 62.5%;";
  final String caseCourrante = "-fx-background-radius: 0; -fx-min-height: 62.5px; -fx-min-width: 62.5px; -fx-pref-height: 62.5px; -fx-pref-width: 62.5px; -fx-max-height: 62.5px; -fx-max-width: 62.5px;-fx-background-image: url('assets/WoodR.jpg'); -fx-background-size: 62.5px; -fx-background-repeat: no-repeat; -fx-background-position: 62.5%;";


  public Plateau(){ //Constructeur du plateau
    super();
    for (int x = 0; x < carre[0].length; x++){
      for(int y = 0; y < carre[1].length; y++){
        boolean couleur = ( (x + y) % 2 != 0 ); // True ou false selon les coordonnées de la case actuelle
        carre[x][y] = new Cases(x, y, couleur); //Instanciation des cases
        this.add(carre[x][y], x, y);

        final int xVal = x; //Déclaration obligatoire car expression lambda
        final int yVal = y;
        carre[x][y].setOnAction( e -> casesClic(xVal, yVal)); //Action qui s'effectuera au moment du clic sur une case
      }
    }
    /******************************************************* Placement des pièces sur le plateau ************************************************************************************/
    this.carre[0][0].setPiece( new Tour(false));
    this.carre[1][0].setPiece( new Cavalier(false));
    this.carre[2][0].setPiece( new Fou(false));
    this.carre[3][0].setPiece( new Dame(false));
    this.carre[4][0].setPiece( new Roi(false));
    this.carre[5][0].setPiece( new Fou(false));
    this.carre[6][0].setPiece( new Cavalier(false));
    this.carre[7][0].setPiece( new Tour(false));

    for (int i = 0; i < this.carre[0].length; i++)
      this.carre[i][1].setPiece( new Pion(false));

    this.carre[0][7].setPiece(new Tour(true));
    this.carre[1][7].setPiece(new Cavalier(true));
    this.carre[2][7].setPiece(new Fou(true));
    this.carre[3][7].setPiece(new Dame(true));
    this.carre[4][7].setPiece(new Roi(true));
    this.carre[5][7].setPiece(new Fou(true));
    this.carre[6][7].setPiece(new Cavalier(true));
    this.carre[7][7].setPiece(new Tour(true));

    for (int i = 0; i < this.carre[0].length; i++)
      this.carre[i][6].setPiece( new Pion(true));
    /******************************************************* Placement des pièces sur le plateau ************************************************************************************/
  }

  public void setPlateau(){ //Acutalisation du plateau, même code que dans le constructeur
    for (int x = 0; x < carre[0].length; x++){
      for(int y = 0; y < carre[1].length; y++){
        boolean couleur = ( (x + y) % 2 != 0 );
        if (couleur)
          carre[x][y].setStyle(caseBlanche);
        else
          carre[x][y].setStyle(caseNoire);
      }
    }
  }

  public void close(){ //Méthode utilisée dans la classe Promotion, pour fermer la fenêtre de Promotion au moment du clic
    newF.close();
  }

  public void choisit(boolean couleur, Stage newF, int x1, int y1){ //Fonction qui permet de séléctionner une pièce lors de la promotion
    BorderPane sec = new BorderPane();
    Scene secS = new Scene(sec, 270, 62);
    Promotion prom = new Promotion(couleur, newF, carre[x1][y1]);
    sec.setCenter(prom);
    newF.setScene(secS);
    newF.setResizable(false);
    newF.show();
  }

  /*public void echecRoi(){
    int i, j, x1 =0, y1=0;
    for(i=0 ; i<8; i++){
      for(j=0 ; j<8; j++){
        if(carre[j][i].estPris()){
          if(carre[j][i].getPiece().getName() == "Roi" && carre[j][i].getPieceColor() == "Noir" ){
            x1=j;
            y1=i;
          }
        }
      }
    }
    if(FouMove(x1,y1,x,y) && joueur == true){
      System.out.println("Non autorisé");
    }
  }*/

  public void echec(int x, int y){ //Teste si un roi est en échec (Non abouti)
    echecNoir(x, y);
    echecBlanc(x, y);
  }

  public void echecNoir(int x, int y){ //Teste si le roi noir est en échec (Non abouti)
    int i,j,y1= 0, x1 = 0;
    //coor roi
    for(i=0 ; i<8; i++){
      for(j=0 ; j<8; j++){
        if(carre[j][i].estPris()){
          if(carre[j][i].getPiece().getName() == "Roi" && carre[j][i].getPieceColor() == "Noir" ){ //Nous cherchons où le roi noir se situe sur le plateau et prenons ses coordonnées
            x1=j;
            y1=i;
          }
        }
      }
    }
    if(this.current.getPiece().getName() == "Fou" && FouMove(x,y,x1,y1) && joueur == true){ //Si la pièce que nous déplaçons est un fou et s'il s'agit d'une pièce de couleur opposée au roi
      System.out.println("le roi noir est en echec");
    }
    if(this.current.getPiece().getName() == "Cavalier" && CavalierMove(x,y,x1,y1) && joueur == true){
      System.out.println("le roi noir est en echec");
    }
    if(this.current.getPiece().getName() == "Dame" && DameMove(x,y,x1,y1) && joueur == true){
      System.out.println("le roi noir est en echec");
    }
    if(this.current.getPiece().getName() == "Tour" && TourMove(x,y,x1,y1) && joueur == true){
      System.out.println("le roi noir est en echec");
    }
  }

  public void echecBlanc(int x, int y){ //Teste si le roi blanc est en échec (Non abouti)
    int i,j,y1= 0, x1 = 0;
    for(i=0 ; i<8; i++){
      for(j=0 ; j<8; j++){
        if(carre[j][i].estPris()){
          if(carre[j][i].getPiece().getName() == "Roi" && carre[j][i].getPieceColor() == "Blanc" ){
            x1=j;
            y1=i;
          }
        }
      }
    }
    if(FouMove(x,y,x1,y1) && joueur == false){
      System.out.println("le roi blanc est en echec");
    }
    if(this.current.getPiece().getName() == "Cavalier" && CavalierMove(x,y,x1,y1) && joueur ==false){
      System.out.println("le roi blanc est en echec");
    }
    if(this.current.getPiece().getName() == "Dame" && DameMove(x,y,x1,y1) && joueur ==false){
      System.out.println("le roi blanc est en echec");
    }
    if(this.current.getPiece().getName() == "Tour" && TourMove(x,y,x1,y1) && joueur ==false){
      System.out.println("le roi blanc est en echec");
    }
  }

  public void coupPossible(Piece p){ //Affiche sur le plateau les coups disponibles pour la pièce sélectionnée
    int x = this.current.getX(); //On récupére les coordonnées de la case courrante
    int y = this.current.getY();

    int i, j;
    if (p.getName() == "Pion"){ //Test de quelle pièce il s'agit
      if(p.getColor() == "Blanc" && joueur == true){ //Test de la couleur, dans le cas particulier du pion (pour la direction de déplacement)
        if(y == 6){
          if(!carre[x][y - 1].estPris())
            carre[x][y - 1].setStyle(possible); //Application du style "possible", référencé plus haut, à la case testée
          if(!carre[x][y - 2].estPris() && !carre[x][y - 1].estPris())
            carre[x][y - 2].setStyle(possible);
          if(x > 0 && x < 7){
            if(carre[x-1][y - 1].estPris() && carre[x-1][y - 1].getPieceColor() != this.current.getPieceColor()) //Test si la pièce mangeable n'est pas une pièce alliée
              carre[x-1][y - 1].setStyle(possible);
            if(carre[x+1][y - 1].estPris() && carre[x+1][y - 1].getPieceColor() != this.current.getPieceColor())
              carre[x+1][y - 1].setStyle(possible);
          }
        }
        else{
          if(!carre[x][y - 1].estPris())
            carre[x][y - 1].setStyle(possible);
          if(x > 0 && x < 7){
            if(carre[x-1][y - 1].estPris() && carre[x-1][y - 1].getPieceColor() != this.current.getPieceColor())
              carre[x-1][y - 1].setStyle(possible);
            if(carre[x+1][y - 1].estPris() && carre[x+1][y - 1].getPieceColor() != this.current.getPieceColor())
              carre[x+1][y - 1].setStyle(possible);
          }

        }
      }
      if(p.getColor() == "Noir" && joueur == false){
        if(y == 1){
          if(!carre[x][y + 1].estPris())
            carre[x][y + 1].setStyle(possible);
          if(!carre[x][y + 2].estPris() && !carre[x][y + 1].estPris())
            carre[x][y + 2].setStyle(possible);
          if(x > 0 && x < 7){
            if(carre[x-1][y + 1].estPris() && carre[x-1][y + 1].getPieceColor() != this.current.getPieceColor())
              carre[x-1][y + 1].setStyle(possible);
            if(carre[x+1][y + 1].estPris() && carre[x+1][y + 1].getPieceColor() != this.current.getPieceColor())
              carre[x+1][y + 1].setStyle(possible);
          }
        }
        else{
          if(!carre[x][y + 1].estPris())
            carre[x][y + 1].setStyle(possible);
          if(x > 0 && x < 7){
            if(carre[x-1][y + 1].estPris() && carre[x-1][y + 1].getPieceColor() != this.current.getPieceColor())
              carre[x-1][y + 1].setStyle(possible);
            if(carre[x+1][y + 1].estPris() && carre[x+1][y + 1].getPieceColor() != this.current.getPieceColor())
              carre[x+1][y + 1].setStyle(possible);
          }
        }
      }
    }
    if (p.getName() == "Cavalier"){
      if(x > 1 && y > 0) {
        if(carre[x-2][y - 1].getPieceColor() != this.current.getPieceColor())
          carre[x-2][y - 1].setStyle(possible);
      }
      if(x > 1 && y < 7) {
        if(carre[x-2][y + 1].getPieceColor() != this.current.getPieceColor())
          carre[x-2][y + 1].setStyle(possible);
      }
      if(y < 6 && x > 0) {
        if(carre[x-1][y + 2].getPieceColor() != this.current.getPieceColor())
          carre[x-1][y + 2].setStyle(possible);
      }
      if(y < 6 && x < 7) {
        if(carre[x+1][y + 2].getPieceColor() != this.current.getPieceColor())
          carre[x+1][y + 2].setStyle(possible);
      }
      if(y > 1 && x > 0){
        if(carre[x-1][y - 2].getPieceColor() != this.current.getPieceColor())
          carre[x-1][y - 2].setStyle(possible);
      }
      if(y > 1 && x < 7){
        if(carre[x+1][y - 2].getPieceColor() != this.current.getPieceColor())
          carre[x+1][y - 2].setStyle(possible);
      }
      if(y > 0 && x < 6) {
        if(carre[x+2][y - 1].getPieceColor() != this.current.getPieceColor())
          carre[x+2][y - 1].setStyle(possible);
      }
      if(y < 7 && x < 6) {
        if(carre[x+2][y + 1].getPieceColor() != this.current.getPieceColor())
          carre[x+2][y + 1].setStyle(possible);
      }
    }
    if (p.getName() == "Tour"){
      for(i = x-1; i>=0; --i){  //Boucle for nécessaire pour les pièces à mouvements multiples, Fou, Tour et Dame, jusqu'à ce qu'une pièce allié ou ennemie soit sur le chemin
        if(carre[i][y].getPieceColor() == this.current.getPieceColor())
          break;
        carre[i][y].setStyle(possible);
        if(carre[i][y].estPris())
          break;
      }
      for(i = x+1; i<=7; ++i){
        if(carre[i][y].getPieceColor() == this.current.getPieceColor())
          break;
        carre[i][y].setStyle(possible);
        if(carre[i][y].estPris())
          break;
      }
      for(i = y+1; i<=7; ++i){
        if(carre[x][i].getPieceColor() == this.current.getPieceColor())
          break;
        carre[x][i].setStyle(possible);
        if(carre[x][i].estPris())
          break;
      }
      for(i = y-1; i>=0; --i){
        if(carre[x][i].getPieceColor() == this.current.getPieceColor())
          break;
        carre[x][i].setStyle(possible);
        if(carre[x][i].estPris())
          break;
      }
    }
    if (p.getName() == "Fou"){
      for(i = x-1, j = y-1; i>=0 && j>=0; --i, --j){
        if(carre[i][j].getPieceColor() == this.current.getPieceColor())
          break;
        carre[i][j].setStyle(possible);
        if(carre[i][j].estPris())
          break;
      }
      for(i = x+1, j = y-1; i<=7 && j>=0; ++i, --j){
        if(carre[i][j].getPieceColor() == this.current.getPieceColor())
          break;
        carre[i][j].setStyle(possible);
        if(carre[i][j].estPris())
          break;
      }
      for(i = x-1, j = y+1; i>=0 && j<=7; --i, ++j){
        if(carre[i][j].getPieceColor() == this.current.getPieceColor())
          break;
        carre[i][j].setStyle(possible);
        if(carre[i][j].estPris())
          break;
      }
      for(i = x+1, j = y+1; i<=7 && j<=7; ++i, ++j){
        if(carre[i][j].getPieceColor() == this.current.getPieceColor())
          break;
        carre[i][j].setStyle(possible);
        if(carre[i][j].estPris())
          break;
      }
    }
    if (p.getName() == "Dame"){
      Piece e = new Fou(true);
      Piece f = new Tour(true);
      this.coupPossible(e); //Application des coups possibles du pion et de la tour, la Dame étant une combinaison des deux
      this.coupPossible(f);
    }
    if (p.getName() == "Roi"){
      if(y > 0 && x > 0 && carre[x-1][y-1].getPieceColor() != this.current.getPieceColor())
        carre[x-1][y - 1].setStyle(possible);
      if(y <7 && x > 0 && carre[x-1][y+1].getPieceColor() != this.current.getPieceColor())
        carre[x-1][y + 1].setStyle(possible);
      if(y > 0 && x < 7 && carre[x+1][y-1].getPieceColor() != this.current.getPieceColor())
        carre[x+1][y - 1].setStyle(possible);
      if(y < 7 && x < 7 && carre[x+1][y+1].getPieceColor() != this.current.getPieceColor())
        carre[x+1][y + 1].setStyle(possible);
      if(y > 0 && carre[x][y-1].getPieceColor() != this.current.getPieceColor())
        carre[x][y - 1].setStyle(possible);
      if(y < 7 && carre[x][y+1].getPieceColor() != this.current.getPieceColor())
        carre[x][y + 1].setStyle(possible);
      if(x < 7 && carre[x-1][y].getPieceColor() != this.current.getPieceColor())
        carre[x-1][y].setStyle(possible);
      if(x < 7 && carre[x+1][y].getPieceColor() != this.current.getPieceColor())
        carre[x+1][y].setStyle(possible);
    }
  }

  public void setCurrent(Cases s){ //Fonction qui applique la case courrante
    if (this.current != null){ //Si la case cliquée n'est pas Override
      this.setPlateau(); //Permet de réinitialiser le plateau après que le coup ait été joué
    }
    this.current = s;
    if (this.current != null)
      this.current.setStyle(caseCourrante); //Applique le style de case courrante si la case n'est pas vide (ne contient pas de pièce)

  }

  public Cases getCurrent(){
    return this.current;
  } //Méthode get pour la case courrante

  public void casesClic(int x, int y){ //Fonction lorsqu'un clic est effectué sur une case
    Cases clic = carre[x][y];

        if (current != null &&
        current.getPiece() != null &&
        clic.getPieceColor() != current.getPieceColor()) { //Si le joueur n'a pas cliqué sur une pièce alliée après en avoir sélectionné une
          if(this.jouerCoup(x, y)){ //Teste si un coup a été joué
            this.setPlateau(); //Réactualise le plateau
          }
        }
    if (carre[x][y].getPiece() != null){ //Si la case sur laquelle on clique n'est pas vide
      if (carre[x][y].getPieceColor() == "Blanc" && joueur == true){ //Autorise la sélection de pièce qu'au joueur actuel (blanc ou noir donc)
        this.setCurrent(carre[x][y]); //Attribue la case courante à la case cliquée
        this.coupPossible(carre[x][y].getPiece()); //Affichage des coups possible pour la case en question
      }
      if (carre[x][y].getPieceColor() == "Noir" && joueur == false){
        this.setCurrent(carre[x][y]);
        this.coupPossible(carre[x][y].getPiece());
      }
    }
  }

  public boolean jouerCoup(int x1, int y1){ //Fonction qui permet de jouer un coup
    int x = current.getX(); // On récupère les coordonnées de la case courrante
    int y = current.getY();
    Cases g = current; //Déclaration d'une variable qui prendra les attributs de la case courrante
    Cases f = carre[x1][y1]; //Déclaraction d'une variable qui prendra les attributs de la case cliquée
    if(coupLegaux(x, y, x1, y1)){ //Test si le coup joué est un coup légal
      f.setPiece(g.supprimePiece()); //Appel à la méthode "supprimePiece" de la classe Piece
      joueur = !joueur; //Alternance entre les deux joueurs
      return true;
    }
    return false;
  }

  public boolean coupLegaux(int x, int y, int x1, int y1){ //Fonction qui détermine les coups légaux pour chaque pièce
    if(PionMove(x, y, x1, y1)){
      return true;
    }
    else if(this.current.getPiece().getName() == "Fou"){
      if(FouMove(x, y, x1, y1)){
        echec(x1, y1); //Appel de la fonction échec qui affiche si un roi est en échec après le coup joué
        return true;
      }
    }
    else if(this.current.getPiece().getName() == "Tour"){
      if(TourMove(x, y, x1, y1)){
        echec(x1, y1);
        return true;
      }
    }
    else if(this.current.getPiece().getName() == "Cavalier"){
      if(CavalierMove(x, y, x1, y1)){
        echec(x1, y1);
        return true;
      }
    }
    else if(DameMove(x, y, x1, y1)){
      echec(x1, y1);
      return true;
    }
    else if(this.current.getPiece().getName() == "Roi"){
      if(RoiMove(x, y, x1, y1)){
        echec(x1, y1);
        return true;
      }
    }
    return false;
  }

  public boolean FouMove(int x, int y, int x1, int y1){ //Fonction qui détermine les mouvements du Fou
    int i, j;
      if(((x1 - x) == (y - y1))) { //Si l'intervalle entre les coordonnées (x1, x) et (y1, y) (de gauche à droite) est le même
        if (y-y1 > 0 && x1 - x > 0){ //Si le déplacement est ascendant
          for(i=x+1, j =y-1; i<x1 && j>y1; i++, j--){ //Boucle for qui détermine tous les coups possibles jusqu'à y1 et x1 (case cliquée)
            if(carre[i][j].estPris()){ //Si la case est prise, alors impossibilité de dépasser cette case
              return false;
            }
          }
          return true;
        }
        else if(y1-y > 0 && x - x1 > 0){ //Si le déplacement est descendant
          for(i=x-1, j =y+1; i>x1 && j<y1; i--, j++){
            if(carre[i][j].estPris()){
              return false;
            }
          }
          return true;
        }
      }
      else if (((x - x1) == (y - y1))){ //Si l'intervalle entre les coordonnées (x, x1) et (y, y1) (de droite à gauche) est le même
        if (y-y1 > 0 && x - x1 > 0){
          for(i=x-1, j =y-1; i>x1 && j>y1; i--, j--){
            if(carre[i][j].estPris()){
              return false;
            }
          }
          return true;
        }
        else if(y1-y > 0 && x1 - x > 0){
          for(i=x+1, j =y+1; i<x1 && j<y1; i++, j++){
            if(carre[i][j].estPris()){
              return false;
            }
          }
          return true;
        }
      }
    return false;
  }

  public boolean CavalierMove(int x, int y, int x1, int y1){ //Fonction qui détermine les mouvements du Cavalier
    if(this.current.getPiece().getName() == "Cavalier"){
      if((x1==x-1 && y1==y-2) ^ (x1==x+1 && y1==y-2) ^ (x1==x-1 && y1==y+2) ^ (x1==x+1 && y1==y+2) ^
      (x1==x-2 && y1==y-1) ^ (x1==x+2 && y1==y-1) ^ (x1==x-2 && y1==y+1) ^ (x1==x+2 && y1==y+1) ){ //Si le coup a la forme d'un "L", conformément aux déplacements du Cavalier en échec
        return true;
      }
      return false;
    }
    return false;
  }

  public boolean TourMove(int x, int y, int x1, int y1){ //Fonction qui détermine les mouvements du Tour
    int i;
    if(x==x1){ //Mouvement vertical
      if(y-y1 > 0){//La tour monte
        for(i=y-1; i>y1 ; i--){
          if(carre[x][i].estPris()){
            return false;
          }
        }
        return true;
      }
      else if(y-y1 < 0){//La tour descend
        for(i=y+1; i<y1 ; i++){
          if(carre[x][i].estPris()){
            return false;
          }
        }
        return true;
      }
    }
    if(y==y1){ //mouvement Horizontal
      if(x-x1 > 0){//La tour va vers la gauche
        for(i=x-1; i>x1 ; i--){
            if(carre[i][y].estPris()){
              return false;
            }
          }
          return true;
      }
      else if(x-x1 < 0){//La tour va vers la droite
        for(i=x+1; i<x1 ; i++){
          if(carre[i][y].estPris()){
            return false;
          }
        }
        return true;
      }
    }
    return false;
  }

  public boolean DameMove(int x, int y, int x1, int y1){ //Fonction qui détermine les mouvements de la Dame
    if(this.current.getPiece().getName() == "Dame"){
      if(TourMove(x, y, x1, y1) ^ FouMove(x, y, x1, y1)){ //Addition des 2 mouvements Fou et Tour pour la Dame
        return true;
      }
    }
    return false;
  }

  public boolean RoiMove(int x, int y, int x1, int y1){ //Fonction qui détermine les mouvements de la Roi
    if (this.current.getPiece().getName() == "Roi"){
      if( ((y - y1 == 1) && (x == x1)) ^ ((y1 - y == 1) && (x==x1)) ^ ((x - x1 == 1) && (y==y1)) ^ ((x1 - x == 1) && (y==y1)) ^ ((x1 - x) == (y - y1) && (x1 - x) == 1) ^ ((x - x1) == (y - y1) && (x - x1)  == 1) ^ ((x - x1) == (y1 - y) && (x - x1) == 1) ^ ((x1 - x) == (y1 - y) && (x1 - x)  == 1)){ //Application des 8 directions disponibles du Roi
        return true;
      }
    }
    return false;
  }

  public boolean PionMove(int x, int y, int x1, int y1){ //Fonction qui détermine les mouvements de la Pion
    if (this.current.getPiece().getName() == "Pion"){
      if (this.current.getPieceColor() == "Blanc" && joueur == true){ //Test si la pièce est un pion, pour la direction de déplacement unique du Pion
        if(y1 == 0){
          this.choisit(true, newF, x1, y1); //Si le pion est de l'autre côté du plateau, alors lancement de la procédure de Promotion
        }
        if(y - y1 > 0 && y - y1 < 3 && x == x1 && !carre[x1][y1].estPris() && !carre[x][y-1].estPris()  && y == 6){ //Test si le pion s'est déjà déplacé en regardant sa case actuelle
          return true;
        }
        else if(y - y1 > 0 && y - y1 < 2 && x == x1 && !carre[x1][y1].estPris() && y != 6){
          return true;
        }
        else if(y - y1 > 0 && y-y1 > 0 && y-y1 < 2 && x - x1 != 0 && carre[x1][y1].estPris()) { //Si une case est occupée par un pion ennemi, alors possibilité de le manger en diagonale
          return true;
        }
        else {
          return false;
        }
      }
      else if (this.current.getPieceColor() == "Noir" && joueur == false){
        if(y1 == 7){
          this.choisit(false, newF, x1, y1);
        }
        if(y1 - y > 0 && y1 - y < 3 && x == x1 && !carre[x1][y1].estPris() && !carre[x][y+1].estPris() && y == 1){
          return true;
        }
        else if(y1 - y > 0 && y1 - y < 2 && x == x1 && !carre[x1][y1].estPris() && y != 1){
          return true;
        }
        else if(y1 - y > 0 && y1-y > 0 && y1-y < 2 && x - x1 != 0 && carre[x1][y1].estPris()) {
          return true;
        }
        else {
          return false;
        }
      }
    }
    return false;
  }

};
