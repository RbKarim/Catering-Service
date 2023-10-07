/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Users;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 88016
 */
public class UsersController implements Initializable {

    @FXML
    private Button DeleteUserBtn;
    @FXML
    private Button UpdateUserBtn;
    @FXML
    private Button Insert_UserBtn;
    @FXML
    private ImageView backBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    

    @FXML
    private void DeleteUserAction(ActionEvent event) throws IOException {
        Parent SignUp =FXMLLoader.load(getClass().getResource("/DeleteUser/DeleteUser.fxml")); 
        Scene scr=new Scene(SignUp);
        Stage su=(Stage)((Node)event.getSource()).getScene().getWindow();
        su.setScene(scr);                                                    
        su.setTitle("Delete User");
        su.show();
    }

    @FXML
    private void UpdateUserAction(ActionEvent event) throws IOException {
        Parent SignUp =FXMLLoader.load(getClass().getResource("/UpdateUserTable/ShowTable.fxml")); 
        Scene scr=new Scene(SignUp);
        Stage su=(Stage)((Node)event.getSource()).getScene().getWindow();
        su.setScene(scr);                                                    
        su.setTitle("Update User Info");
        su.show();
    }

    @FXML
    private void InsertUserAction(ActionEvent event) throws IOException {
        try{
         Parent SignUp =FXMLLoader.load(getClass().getResource("/InsertUsers/UserManager.fxml")); 
        Scene scr=new Scene(SignUp);
        Stage su=(Stage)((Node)event.getSource()).getScene().getWindow();
        su.setScene(scr);                                                    
        su.setTitle("Insert User");
        su.show();}
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    @FXML
    private void backAction(MouseEvent event) {
        try{
            Parent Menu = FXMLLoader.load(getClass().getResource("/AdminLoggedIn/AdminLoggedIn.fxml"));
                Scene scr = new Scene(Menu);
                Stage menuPage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                menuPage.setScene(scr);
                menuPage.setTitle("Menu");
                menuPage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
}
