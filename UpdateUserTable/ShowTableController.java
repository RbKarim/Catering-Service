/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UpdateUserTable;

import DeleteUser.DeleteUserModel;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 88016
 */
public class ShowTableController implements Initializable {

    @FXML
    private ImageView backBtn;
    @FXML
    private TableView<ShowTableModel> ShowTable;
    @FXML
    private TableColumn<ShowTableModel, String> Col_Name;
    @FXML
    private TableColumn<ShowTableModel, String> Col_Email;
    @FXML
    private TableColumn<ShowTableModel, String> Col_Password;
    @FXML
    private Button updateUser;
    private ObservableList<ShowTableModel> data;
    
    Connection con;
    PreparedStatement pst;
    ResultSet rs1;
    @FXML
    private Button RefreshBtn;
    public static String email;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            databaseRecord();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ShowTableController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ShowTableController.class.getName()).log(Level.SEVERE, null, ex);
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

            data.add(new ShowTableModel(rs.getString("Name"),rs.getString("Email"),rs.getString("Passwords")));
            email= rs.getString("Email");
        }
        Col_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Col_Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        Col_Password.setCellValueFactory(new PropertyValueFactory<>("password"));

        ShowTable.setItems(null);
        ShowTable.setItems(data);
        
        con.close();
            
        }  
        catch(Exception ex){
     
         System.out.println("Error IS Shit ="+ex.getMessage());
     }
         
        
     }
     
    @FXML
    private void UpdateAction(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
      /* Parent Menu = FXMLLoader.load(getClass().getResource("/UpdateUser/UpdateUser.fxml"));
                Scene scr = new Scene(Menu);
                Stage menuPage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                menuPage.setScene(scr);
                menuPage.setTitle("User Log");
                menuPage.show();*/
      if (ShowTable.getSelectionModel().getSelectedItem() != null) {

                ShowTableModel data = ShowTable.getSelectionModel().getSelectedItem();
               // idNo = data.getItemID();

                Pane view = new FXMLLoader().load(getClass().getResource("/UpdateUser/UpdateUser.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(view));
                stage.show();

            } 
            else {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("!!No Item is Selected!!");
                a.show();
            }
         
    }

    @FXML
    private void RefreshAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=Catering;";
        con = DriverManager.getConnection(connectionUrl);

        data = FXCollections.observableArrayList();
        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM users Where Roles='USER'");

        while (rs.next()) {

          data.add(new ShowTableModel(rs.getString("Name"),rs.getString("Email"),rs.getString("Passwords")));
            
        }
        Col_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Col_Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        Col_Password.setCellValueFactory(new PropertyValueFactory<>("password"));

        ShowTable.setItems(null);
        ShowTable.setItems(data);
    }
    
}
