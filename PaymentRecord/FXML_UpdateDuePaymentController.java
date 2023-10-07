package PaymentRecord;

import static PaymentRecord.FXML_PaymentRecordController.P_ID;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;


/**
 * FXML Controller class
 *
 * @author Sean
 */
public class FXML_UpdateDuePaymentController implements Initializable {

    @FXML
    private JFXButton doneUpdateBtn;
    @FXML
    private JFXTextField updateDueTxtField;
    @FXML
    private AnchorPane updateDuePane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO 

    }

    @FXML
    private void doneUpdateBtnAction(ActionEvent event) {
        UpdatePrice();
    }

    private void UpdatePrice() {
        //String Updatevalue = updateDueTxtField.getText();

        /* String sql = "update Payment set Due=" + updateDueTxtField.getText() + ""
                    + " where Payment_Id=" + P_ID +"";       
         */
        try {
            Connection con;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=Catering;";
            con = DriverManager.getConnection(connectionUrl);

            Statement stm = con.createStatement();
      

            if (!updateDueTxtField.getText().isEmpty()) {

                String sql = "update Payment set Due=" + updateDueTxtField.getText() + ""
                        + " where Payment_Id=" + P_ID + "";

                System.out.println("Payment" + sql);

                stm.executeUpdate(sql);

                
                String sql2 = "Update Payment set FullPayment = CASE when Due = 0 THEN 'YES' ELSE 'NO' END where Payment_Id=" + P_ID + "";

                stm.executeUpdate(sql2);
                updateDuePane.getScene().getWindow().hide();
            } 
            else {
                showAlertWithHeaderText();
            }
        } catch (Exception ex) {

            System.out.println("Error in case" + ex.getMessage());
        }
    }

    private void showAlertWithHeaderText() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning alert");
        alert.setHeaderText("Empty Text Field");
        alert.setContentText("Please Fill up all the Text Fields");

        alert.showAndWait();

    }
}
