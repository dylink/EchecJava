import javafx.stage.Stage;


public class Promotion extends Plateau { //Classe de la promotion

  Cases[] carre = new Cases[4];

  public Promotion(boolean couleur, Stage newF, Cases g){ //Constructeur de la fenêtre de Promotion, avec un aspect similaire au constructeur du Plateau
    super();
    for (int x = 0; x < carre.length; x++){
        carre[x] = new Cases(x, couleur);
        this.add(carre[x], x, 0);

        final int xVal = x;
        carre[x].setOnAction( e -> casesClic(xVal, newF, g)); //Application de la fonction "casesClic" lorsqu'une case est cliquée
    }
    if(couleur){
      this.carre[0].setPiece( new Tour(true));
      this.carre[1].setPiece( new Cavalier(true));
      this.carre[2].setPiece( new Fou(true));
      this.carre[3].setPiece( new Dame(true));
    }
    else {
      this.carre[0].setPiece( new Tour(false));
      this.carre[1].setPiece( new Cavalier(false));
      this.carre[2].setPiece( new Fou(false));
      this.carre[3].setPiece( new Dame(false));
    }
  }

  public void casesClic(int i, Stage newF, Cases g){ //Fonction appelée lors de la sélection d'une pièce pour la promotion
    g.supprimePiece(); //Suppression de la pièce en cours
    g.setPiece(carre[i].piece); //Ajout de la pièce sélectionnée pour la promotion
    newF.close(); //Fermeture de la fenêtre de promotion
  }
};
