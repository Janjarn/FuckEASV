import easvbar.gui.controller.LoginViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args)
    {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EventMaker.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TicketMaker.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/LoginView.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("EASV Bar");
        primaryStage.setMaximized(false);
        LoginViewController controller = loader.getController();
        controller.setUp();
        primaryStage.show();
    }
}