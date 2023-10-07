/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UpdateUser;

import static UpdateUserTable.ShowTableController.email;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author 88016
 */
public class UpdateUserController implements Initializable {

    @FXML
    private TextField NewUserName;
    @FXML
    private TextField NewPassword;
    @FXML
    private Button UpdateUser;
    @FXML
    private AnchorPane UpdateUserPSAnchor;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

     
    @FXML
  
    private void UpdateUserAction(ActionEvent event) throws IOException {
          
       try{
            Connection con;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=Catering;";
            con = DriverManager.getConnection(connectionUrl);
            Statement stmt= con.createStatement();
            String sql = "update users set Name='"+NewUserName.getText()+"' , Passwords= '"+NewPassword.getText()+"' where Email='"+email+"'";
            stmt.executeUpdate(sql);
            
            UpdateUserPSAnchor.getScene().getWindow().hide();
             
     }catch(Exception ex) {
         System.out.println("Error");
     }  
    }
}
