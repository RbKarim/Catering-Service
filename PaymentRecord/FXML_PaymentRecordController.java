package PaymentRecord;

import static Customer.Customer_infoController.C_ID;
import Order.FXML_OrderController;
import Order.Order_totalPrice;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import static signin.SignUpController.admin;

/**
 * FXML Controller class
 *
 * @author Sean
 */
public class FXML_PaymentRecordController implements Initializable {

    @FXML
    private JFXButton refreshBtn;
    @FXML
    private JFXButton updateBtn;
    @FXML

    private TableView<PaymentRecordModel> payment_record_table;
    @FXML
    private TableColumn<PaymentRecordModel, String> payment_id;
    @FXML
    private TableColumn<PaymentRecordModel, String> customer_id;
    @FXML
    private TableColumn<PaymentRecordModel, String> order_id;
    @FXML
    private TableColumn<PaymentRecordModel, String> advance;
    @FXML
    private TableColumn<PaymentRecordModel, String> due;
    @FXML
    private TableColumn<PaymentRecordModel, String> fullpayment;

    private ObservableList<PaymentRecordModel> data;

    public static int P_ID;

    Connection con;
    PreparedStatement pst;
    ResultSet rs1;
    @FXML
    private ImageView backBtn;
    @FXML
    private JFXTextArea PaymentSearchTxtArea;
    @FXML
    private JFXButton PaymentSearchBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            DatabaseValue();
        } catch (SQLException ex) {
            Logger.getLogger(FXML_PaymentRecordController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXML_PaymentRecordController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void DatabaseValue() throws SQLException, ClassNotFoundException {

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=Catering;";
        con = DriverManager.getConnection(connectionUrl);

        data = FXCollections.observableArrayList();

        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Payment");

            while (rs.next()) {

                data.add(new PaymentRecordModel(rs.getInt("Payment_ID"), rs.getInt("Customer_ID"), rs.getInt("Order_ID"),
                        rs.getInt("advance"), rs.getInt("due"), rs.getString("fullpayment")));
            }
            payment_id.setCellValueFactory(new PropertyValueFactory<>("Payment_ID"));
            customer_id.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
            order_id.setCellValueFactory(new PropertyValueFactory<>("Order_ID"));
            advance.setCellValueFactory(new PropertyValueFactory<>("advance"));
            due.setCellValueFactory(new PropertyValueFactory<>("due"));
            fullpayment.setCellValueFactory(new PropertyValueFactory<>("fullpayment"));

            payment_record_table.setItems(null);
            payment_record_table.setItems(data);

            //Payment Id
            /*String sql3 = "Select * from Payment";
                         PreparedStatement pstm2 = con.prepareStatement(sql3);
                         ResultSet r1=pstm2.executeQuery();
                         if(r1.next()) {
                             P_ID = r1.getInt("Payment_Id");
                             System.out.println("Payment ID="+P_ID+" OF Customer ID ="+C_ID+"");
                         }*/
            con.close();

        } catch (Exception ex) {

            System.out.println("Error in payment record" + ex.getMessage());
        }

    }

    @FXML
    private void refreshBtnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        DatabaseValue();
    }

    @FXML
    private void updateBtnAction(ActionEvent event) throws IOException {

        if (payment_record_table.getSelectionModel().getSelectedItem() != null) {

            PaymentRecordModel data = payment_record_table.getSelectionModel().getSelectedItem();
            P_ID = data.getPayment_ID();

            Pane view = new FXMLLoader().load(getClass().getResource("FXML_UpdateDuePayment.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(view));
            stage.show();

        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("!!No Item is Selected!!");
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

    @FXML
    private void paymentSearchBtnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        Connection conn;

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=Catering;";
        conn = DriverManager.getConnection(connectionUrl);

        payment_record_table.getItems().clear();

        String SearchedValue = PaymentSearchTxtArea.getText();

        PreparedStatement pst = conn.prepareStatement("Select *from Payment where Payment_Id LIKE '%" + SearchedValue + "%'");

        ResultSet rs1 = pst.executeQuery();

        while (rs1.next()) {
            data.add(new PaymentRecordModel(rs1.getInt("Payment_ID"), rs1.getInt("Customer_ID"), rs1.getInt("Order_ID"),
                    rs1.getInt("advance"), rs1.getInt("due"), rs1.getString("fullpayment")));

        }
        payment_id.setCellValueFactory(new PropertyValueFactory<>("Payment_ID"));
        customer_id.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        order_id.setCellValueFactory(new PropertyValueFactory<>("Order_ID"));
        advance.setCellValueFactory(new PropertyValueFactory<>("advance"));
        due.setCellValueFactory(new PropertyValueFactory<>("due"));
        fullpayment.setCellValueFactory(new PropertyValueFactory<>("fullpayment"));

        payment_record_table.setItems(null);
        payment_record_table.setItems(data);
    }
}

