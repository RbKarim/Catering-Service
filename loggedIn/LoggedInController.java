package loggedIn;

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
public class LoggedInController implements Initializable {

    @FXML
    private Button New_OrderBtn;
    @FXML
    private Button Payment_RecordBtn;
    @FXML
    private Button LogOutBtn;
    @FXML
    private ImageView LogoutImg;
    @FXML
    private Button ServiceRecord_btn;
    @FXML
    private Button DeliveryRecord_btn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void NewOrderAction(ActionEvent event) throws IOException {
        Parent SignUp = FXMLLoader.load(getClass().getResource("/Customer/customer_info.fxml"));
        Scene scr = new Scene(SignUp);
        Stage su = (Stage) ((Node) event.getSource()).getScene().getWindow();
        su.setScene(scr);
        su.setTitle("Customer Info");
        su.show();
    }

    @FXML
    private void ServiceRecord_btnAction(ActionEvent event) throws IOException {
        Parent service = FXMLLoader.load(getClass().getResource("/service/FXML_services.fxml"));
        Scene scr = new Scene(service);
        Stage su = (Stage) ((Node) event.getSource()).getScene().getWindow();
        su.setScene(scr);
        su.setTitle("Services info");
        su.show();
    }

    @FXML
    private void PaymentRecordAction(ActionEvent event) throws IOException {
        Parent PaymentRecord = FXMLLoader.load(getClass().getResource("/PaymentRecord/FXML_PaymentRecord.fxml"));
        Scene scr = new Scene(PaymentRecord);
        Stage su = (Stage) ((Node) event.getSource()).getScene().getWindow();
        su.setScene(scr);
        su.setTitle("Payment Record info");
        su.show();
    }

    @FXML
    private void LogoutAction(ActionEvent event) throws IOException {
        Parent SignUpPage = FXMLLoader.load(getClass().getResource("/signin/logIn.fxml"));
        Scene scr = new Scene(SignUpPage);
        Stage su = (Stage) ((Node) event.getSource()).getScene().getWindow();
        su.setScene(scr);
        su.setTitle("Back to Sign In");
        su.show();
    }

     @FXML
    private void DeliveryRecord_btnAction(ActionEvent event) throws IOException {
        Parent DeliveryRecordPage = FXMLLoader.load(getClass().getResource("/delivery/FXML_delivery.fxml"));
        Scene scr = new Scene(DeliveryRecordPage);
        Stage su = (Stage) ((Node) event.getSource()).getScene().getWindow();
        su.setScene(scr);
        su.setTitle("Back to Delivery Records");
        su.show();
    }
}
