import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Echec extends Application { //Classe principale pour lancer l'application

  public static void main(String[] args){ //Méthode main
          launch(args);
          System.exit(0);
    }
  private Plateau plat;

  @Override
    public void start(Stage primaryStage) { //Méthode start
      BorderPane root = new BorderPane();
      Scene mainScene = new Scene(root);
      primaryStage.setScene(mainScene);
      plat = new Plateau();
      root.setPrefSize(500,500);
      root.setCenter(plat);

      primaryStage.getIcons().add(new Image("assets/Icon.png"));
      primaryStage.setResizable(false);
      primaryStage.show();
    }
};
