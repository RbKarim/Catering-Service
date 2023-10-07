package signin;

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
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author 88016
 */
public class SignUpController extends loggedIn.LoggedInController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginBtn;
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public static String admin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void loginAction(ActionEvent event) {

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=Catering;";
            con = DriverManager.getConnection(connectionUrl);

            String sql = "select * from users where Name='" + username.getText() + "' AND Passwords='" + password.getText() + "'";

            Statement stm = con.createStatement();

            ResultSet rs = stm.executeQuery(sql);

            if (rs.next()) {
                admin = rs.getString("Roles");
                if (admin.equalsIgnoreCase("ADMIN")) {
                    Parent AdminPage = FXMLLoader.load(getClass().getResource("/AdminLoggedIn/AdminLoggedIn.fxml"));
                    Scene scr = new Scene(AdminPage);
                    Stage mp = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    mp.setScene(scr);
                    mp.setTitle("Admin Menu");
                    mp.show();

                } else {

                    Parent MenuPage = FXMLLoader.load(getClass().getResource("/loggedIn/LoggedIn.fxml"));
                    Scene scr = new Scene(MenuPage);
                    Stage mp = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    mp.setScene(scr);
                    mp.setTitle("User Menu");
                    mp.show();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please Insert Correct Data");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            ex.printStackTrace();
        }
    }

}
