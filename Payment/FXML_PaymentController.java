package Payment;

import static Customer.Customer_infoController.C_ID;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.InputMethodEvent;
import javax.swing.JOptionPane;
import static Order.FXML_OrderController.Amount;
import java.io.IOException;
import java.sql.Statement;
import java.util.Date;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import static userAddService.FXML_UserAddServiceController.O_ID;

/**
 * FXML Controller class
 *
 * @author Sean
 */
public class FXML_PaymentController implements Initializable {

    @FXML
    private JFXButton payment_submit_btn;
    @FXML
    private JFXTextField advance;
    @FXML
    private Label due;
    @FXML
    private JFXDatePicker delivery_date;
    @FXML
    private JFXTextField delivery_address;

    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public static String fullpayment;
    int calculate;

    String total;
    @FXML
    private JFXTimePicker delivery_time;
    @FXML
    private Label totalAmountPay;
    @FXML
    private ImageView backBtn;

    public static Date DeliveryDate;
    public static String DeliveryAddress;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        totalAmountPay.setText(Amount);

    }

    @FXML
    private void payment_submit_btn_onclick(ActionEvent event) throws ClassNotFoundException, SQLException {

        if (advance.getText() != null && due.getText() != null && delivery_date.getValue() != null && delivery_time.getValue() != null && !delivery_address.getText().isEmpty()) {
            String Advance = advance.getText();
            DeliveryDate = java.sql.Date.valueOf(delivery_date.getValue());
            System.out.println(DeliveryDate);
            DeliveryAddress = delivery_address.getText();

            //String total=total_amount.getText();
            total = Amount;

            String Adv = advance.getText();
            calculate = Integer.parseInt(total) - Integer.parseInt(Adv);
            due.setText(Integer.toString(calculate));

            if (calculate == 0) {
                fullpayment = "YES";
            } else {
                fullpayment = "NO";
            }

            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=Catering;";
                con = DriverManager.getConnection(connectionUrl);

                //if(advance.getText()!=null && due.getText()!=null && 
                //delivery_address.getText()!=null )
                //{
                Statement stm = con.createStatement();
                String sql1 = "INSERT INTO Payment(Customer_Id,Order_Id,Advance,Due,FullPayment) "
                        + "VALUES(" + C_ID + "," + O_ID + "," + Advance + "," + calculate + ",'" + fullpayment + "')";

                stm.executeUpdate(sql1);
                System.out.println(sql1);
           

                //Insert data for delivery status table-->rifat
                Connection connection;
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String connectionUrl2 = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=Catering;";
                connection = DriverManager.getConnection(connectionUrl2);

                String PayIdSql = "Select Payment_Id from Payment where Customer_Id=" + C_ID + "";
                System.out.println(PayIdSql);
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(PayIdSql);

                int PayId=0;
                if(rs.next()){
                 PayId = rs.getInt("Payment_Id");    
                }
                

                String sql2 = "INSERT INTO DELIVERY(DELIVERY_ADDRESS,CUSTOMER_ID,PAYMENT_ID,DELIVERY_DATE,RECORD) "
                        + "VALUES('" + DeliveryAddress + "'," + C_ID + "," + PayId + ",'" + DeliveryDate +"','Active')";

                System.out.println(sql2);
                stm.executeUpdate(sql2);

                System.out.println("Inserted");

                Parent deliveryPage = FXMLLoader.load(getClass().getResource("/delivery/FXML_delivery.fxml"));
                Scene scr = new Scene(deliveryPage);
                Stage su = (Stage) ((Node) event.getSource()).getScene().getWindow();
                su.setScene(scr);
                su.setTitle("Delivery Status");
                su.show();
    
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);
                ex.printStackTrace();
                System.out.println("Not Inserted Payment" + ex.getMessage());

            }
        } else {
            showAlertWithHeaderText();
        }

    }

    @FXML
    private void Show_advance_text(ActionEvent event) {

    }

    @FXML
    private void show_delivery_date(ActionEvent event) {
    }

    @FXML
    private void show_deliver_address(ActionEvent event) {
    }

    @FXML
    private void due_calculate(InputMethodEvent event) {

    }

    private void showAlertWithHeaderText() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning alert");
        alert.setHeaderText("Empty Text Field");
        alert.setContentText("Please Fill up all the Text Fields");

        alert.showAndWait();
    }

    @FXML
    private void BackAction(MouseEvent event) throws IOException {

        Parent ReturnToCustomerInfo = FXMLLoader.load(getClass().getResource("/Order/FXML_Order.fxml"));
        Scene scr = new Scene(ReturnToCustomerInfo);
        Stage su = (Stage) ((Node) event.getSource()).getScene().getWindow();
        su.setScene(scr);
        su.setTitle("back into Total Order");
        su.show();
    }
}
