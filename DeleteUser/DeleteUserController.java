/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DeleteUser;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 88016
 */
public class DeleteUserController implements Initializable {

    @FXML
    private ImageView backBtn;
    @FXML
    private TableView<DeleteUserModel> DeleteTable;
    @FXML
    private TableColumn<DeleteUserModel, String> Col_Name;
    @FXML
    private TableColumn<DeleteUserModel, String> Col_Email;
    @FXML
    private TableColumn<DeleteUserModel, String> Col_Password;
    
    private ObservableList<DeleteUserModel> data;
    
    Connection con;
    PreparedStatement pst;
    ResultSet rs1;
    @FXML
    private Button deleteUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            databaseRecord();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DeleteUserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DeleteUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void backAction(MouseEvent event) throws IOException {
        
            Parent Menu = FXMLLoader.load(getClass().getResource("/Users/users.fxml"));
                Scene scr = new Scene(Menu);
                Stage menuPage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                menuPage.setScene(scr);
                menuPage.setTitle("User Log");
                menuPage.show();
        
    }

    private void databaseRecord() throws ClassNotFoundException, SQLException {
          
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=Catering;";
        con = DriverManager.getConnection(connectionUrl);

        data = FXCollections.observableArrayList();
        
        try{
        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM users Where Roles='USER'");

        while (rs.next()) {

            data.add(new DeleteUserModel(rs.getString("Name"),rs.getString("Email"),rs.getString("Passwords")));

        }
        Col_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Col_Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        Col_Password.setCellValueFactory(new PropertyValueFactory<>("password"));

        DeleteTable.setItems(null);
        DeleteTable.setItems(data);
        
        con.close();
            
        }  
        catch(Exception ex){
     
         System.out.println("Error"+ex.getMessage());
     }
         
        
     }

    @FXML
    private void DeleteAction(ActionEvent event) throws ClassNotFoundException {
         DeleteUserModel data = DeleteTable.getSelectionModel().getSelectedItem();
        String name = data.getName();

        try {
            Connection con;

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=Catering;";
            con = DriverManager.getConnection(connectionUrl);

            String sql = "delete from users where Name='"+name+"'";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            con.close();

            DeleteTable.getItems().remove(data);
            System.out.println("SuccessfulI");
        } catch (SQLException ex) {
            System.out.println("Error:" + ex);

        }
    }
    
    
}
