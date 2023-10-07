package InsertUsers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 88016
 */
public class UserManagerController implements Initializable {

    @FXML
    private ImageView backBtn;
    @FXML
    private TextField UserName;
    @FXML
    private TextField Email;
    @FXML
    private TextField Password;
    @FXML
    private Button AddUser;
    
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    @FXML
    private ImageView UserNameError;
    @FXML
    private ImageView EmailError;
    @FXML
    private ImageView PasswordError;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void backAction(MouseEvent event) throws IOException {
         //back to service page
        Parent services =FXMLLoader.load(getClass().getResource("/Users/users.fxml")); 
        Scene scr=new Scene(services);
        Stage su=(Stage)((Node)event.getSource()).getScene().getWindow();
        su.setScene(scr);
        su.setTitle("User Log");
        su.show();
    }

    
    @FXML
    private void adduserAction(ActionEvent event) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream("D:\\3.1 courses\\database lab\\project\\Saber\\Catering\\src\\images\\error-icon-14-256.png");
        Image image = new Image(fis);
        String UName = UserName.getText();
        String email = Email.getText();
        String pass = Password.getText();
        String roles = "USER";
        boolean success = false;
        try {

            if (!UName.isEmpty() && !email.isEmpty() && !pass.isEmpty()) {

                UserNameError.setImage(null);
                EmailError.setImage(null);
                PasswordError.setImage(null);

                boolean match = false;
                //database connection
                try {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String URL = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=Catering;";
                Connection conn = DriverManager.getConnection(URL);

                    Statement stmt = conn.createStatement();
                    String sql = "select Email from users Where Email='" + email + "'";
                    ResultSet res = stmt.executeQuery(sql);
                    if (res.next()) {

                        match = true;

                    }
                    if (match) {
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setContentText("Email already Used");
                        a.show();
                    } else {
                        

                        String  sql2= "Insert into users (Name, Email, Passwords ,Roles)"
                                + " values ('"+UName+"','"+email+"','"+pass+"','"+roles+"')";
                        stmt.executeUpdate(sql2);

                        conn.close();
                        success = true;

                    }

                } catch (Exception e) {
                    System.out.println(e);
                }

                if (success) {
                    
                    //Next Page
                    /*  Parent SignedUp = FXMLLoader.load(getClass().getResource("Home.fxml"));
                    Scene scr = new Scene(SignedUp);
                    Stage signedup = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    signedup.setScene(scr);
                    signedup.setTitle("Hospital Management System");
                    signedup.show();*/
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setContentText("DataBase Updated");
                    a.show();

                }
            } else {

                if (UName.isEmpty()) {

                    UserNameError.setImage(image);
                }
                if (!UName.isEmpty()) {
                    UserNameError.setImage(null);
                }

                if (email.isEmpty()) {
                    EmailError.setImage(image);
                }
                if (!email.isEmpty()) {
                    EmailError.setImage(null);
                }
                if (pass.isEmpty()) {
                    PasswordError.setImage(image);
                }
                if (!pass.isEmpty()) {
                    PasswordError.setImage(null);
                }

            }

        } catch (Exception ex) {

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Please Insert All Information");
            a.show();
        }
    }

    @FXML
    private void CustomerNameError(MouseEvent event) {
    }
    
}
