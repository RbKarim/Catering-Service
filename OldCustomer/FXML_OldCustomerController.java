package OldCustomer;

import static Customer.Customer_infoController.C_ID;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import static signin.SignUpController.admin;

/**
 * FXML Controller class
 *
 * @author Sean
 */


public class FXML_OldCustomerController implements Initializable {

    @FXML
    private Button ProceedBtn;
    @FXML
    private ImageView backBtn;
    @FXML
    private TextField PhoneNumber;
    
    private int OldC_ID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void ProceedActionBtn(ActionEvent event) throws IOException {

        System.out.println("Old Customer Clicked Proceed ");

        String Phone = PhoneNumber.getText();
        boolean success = false;
        //try {

            if (!Phone.isEmpty()) {

                boolean match = false;
                //database connection
                try {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    String URL = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=Catering;";
                    Connection conn = DriverManager.getConnection(URL);

                    Statement stmt = conn.createStatement();
                    String sql = "select Phone from Customer Where Phone='" + Phone + "'";
                    ResultSet res = stmt.executeQuery(sql);
                    if (res.next()) {

                        match = true;
                        //conn.close();
                        success = true;

                    }
                    if (match) {

                        String sql3 = "Select CustomerID from Customer Where Phone=" + Phone + "";
                        PreparedStatement pstm2 = conn.prepareStatement(sql3);
                        ResultSet r1 = pstm2.executeQuery();
                        if (r1.next()) {
                            OldC_ID = r1.getInt("CustomerID");
                           
                            C_ID=OldC_ID;
                            System.out.println("Old Customer"+C_ID);
                            
                        }

                        conn.close();
                        success = true;

                    } else {

                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setContentText("Phone number does not exist!!");
                        a.show();

                    }

                } catch (Exception e) {
                    System.out.println("Old customer"+e.getMessage());
                }
            

                if (success) {

                    Parent Services = FXMLLoader.load(getClass().getResource("/userAddService/FXML_UserAddService.fxml"));
                    Scene scr = new Scene(Services);
                    Stage service = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    service.setScene(scr);
                    service.setTitle("Services");
                    service.show();

                }
            }

            else{
            //System.out.println(ex);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Please Insert All Information");
            a.show();
        }
    }

    @FXML
    private void BackAction(MouseEvent event) throws IOException {

           if (admin.equals("ADMIN")) {
            Parent loggedIn = FXMLLoader.load(getClass().getResource("/AdminLoggedIn/AdminLoggedIn.fxml"));
            Scene scr = new Scene(loggedIn);
            Stage su = (Stage) ((Node) event.getSource()).getScene().getWindow();
            su.setScene(scr);
            su.setTitle("back into loggedIn");
            su.show();
        } else {
            Parent loggedIn = FXMLLoader.load(getClass().getResource("/loggedIn/LoggedIn.fxml"));
            Scene scr = new Scene(loggedIn);
            Stage su = (Stage) ((Node) event.getSource()).getScene().getWindow();
            su.setScene(scr);
            su.setTitle("back into loggedIn");
            su.show();
        }
    }

}
