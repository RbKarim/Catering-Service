package Customer;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import static signin.SignUpController.admin;

/**
 * FXML Controller class
 *
 * @author 88016
 */
public class Customer_infoController implements Initializable {

    @FXML
    private TextField CustomerName;
    @FXML
    private TextField PhoneNumber;
    @FXML
    private ImageView backBtn;
    @FXML
    private ImageView CustomerNameError;
    @FXML
    private ImageView PhoneNumberError;
    @FXML
    private ImageView AddressError;
    @FXML
    private TextField Address;
    @FXML
    private Button OldCustomerBtn;
    @FXML
    private Button ProceedBtn;

    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    public static int C_ID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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

    @FXML
    private void CustomerNameError(MouseEvent event) {
    }

    @FXML
    private void PhoneNoError(MouseEvent event) {
    }

    @FXML
    private void AddressError(MouseEvent event) {
    }

    @FXML
    private void OldCusBtn(ActionEvent event) throws IOException {

        Parent OldCustomer = FXMLLoader.load(getClass().getResource("/OldCustomer/FXML_OldCustomer.fxml"));
        Scene scr = new Scene(OldCustomer);
        Stage service = (Stage) ((Node) event.getSource()).getScene().getWindow();
        service.setScene(scr);
        service.setTitle("Old customer");
        service.show();
    }

    @FXML
    private void ProceedActionBtn(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {

        FileInputStream fis = new FileInputStream("D:\\3.1 courses\\database lab\\project\\Saber\\Catering\\src\\images\\error-icon-14-256.png");
        Image image = new Image(fis);
        String CusName = CustomerName.getText();
        String Phone = PhoneNumber.getText();
        String adrs = Address.getText();
        boolean success = false;
        try {

            if (!CusName.isEmpty() && !Phone.isEmpty() && !adrs.isEmpty()) {

                CustomerNameError.setImage(null);
                PhoneNumberError.setImage(null);
                AddressError.setImage(null);

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

                    }
                    if (match) {
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setContentText("Phone already Used");
                        a.show();
                    } else {

                        String sql2 = "Insert into Customer (Customer_name, Phone, Addresses) values (?,?,?)";
                        PreparedStatement pstm = conn.prepareStatement(sql2);

                        pstm.setString(1, CusName);
                        pstm.setString(2, Phone);
                        pstm.setString(3, adrs);

                        pstm.executeUpdate();

                        String sql3 = "Select CustomerID from Customer Where Phone=" + Phone + "";
                        PreparedStatement pstm2 = conn.prepareStatement(sql3);
                        ResultSet r1 = pstm2.executeQuery();
                        if (r1.next()) {
                            C_ID = r1.getInt("CustomerID");
                            System.out.println(C_ID);
                        }

                        conn.close();
                        success = true;

                    }

                } catch (Exception e) {
                    System.out.println(e);
                }

                if (success) {

                    Parent UserServices = FXMLLoader.load(getClass().getResource("/userAddService/FXML_UserAddService.fxml"));
                    Scene scr = new Scene(UserServices);
                    Stage service = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    service.setScene(scr);
                    service.setTitle("User Services");
                    service.show();

                }
            } else {

                if (CusName.isEmpty()) {

                    CustomerNameError.setImage(image);
                }
                if (!CusName.isEmpty()) {
                    CustomerNameError.setImage(null);
                }

                if (Phone.isEmpty()) {
                    PhoneNumberError.setImage(image);
                }
                if (!Phone.isEmpty()) {
                    PhoneNumberError.setImage(null);
                }
                if (adrs.isEmpty()) {
                    AddressError.setImage(image);
                }
                if (!adrs.isEmpty()) {
                    AddressError.setImage(null);
                }

            }

        } catch (Exception ex) {
            System.out.println(ex);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Please Insert All Information");
            a.show();
        }
    }

}
