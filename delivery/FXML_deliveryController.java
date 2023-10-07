package delivery;

import static Payment.FXML_PaymentController.DeliveryDate;
import static Payment.FXML_PaymentController.fullpayment;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
import service.Services;
import static signin.SignUpController.admin;
import userAddService.UserAddService;

public class FXML_deliveryController implements Initializable {

    @FXML
    private JFXButton UpdateDeliverBtn;
    @FXML
    private JFXButton UpdateDeliverRefreshBtn;
    @FXML
    private TableView<Delivery> deliveryTable;
    private ObservableList<Delivery> data;
    @FXML
    private TableColumn<Delivery, String> delivery_deliveryAddress_col;
    @FXML
    private TableColumn<Delivery, String> delivery_deliveryID_col;
    @FXML
    private TableColumn<Delivery, String> deliverytab_cusId_col;
    @FXML
    private TableColumn<Delivery, String> deliverytab_payId_col;
    @FXML
    private TableColumn<Delivery, String> deliverytab_deliveryDate_col;
    @FXML
    private TableColumn<Delivery, String> deliverytab_record_col;

    static int CustomerNo;
    @FXML
    private JFXButton deliverySearchBtn;
    @FXML
    private JFXTextArea deliverySearchTxtArea;
    @FXML
    private ImageView backBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ShowDeliveryTable();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXML_deliveryController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FXML_deliveryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void UpdateDeliverBtnAction(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
        UpdateDeliverRecord();
    }

    @FXML
    private void UpdateDeliverRefreshBtnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        ShowDeliveryTable();
    }

    void UpdateDeliverRecord() throws ClassNotFoundException, SQLException, IOException {
        Connection con;

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=Catering;";
        con = DriverManager.getConnection(connectionUrl);

        Date CurrentDate = Date.valueOf(LocalDate.now());
        System.out.println(CurrentDate);

        if (deliveryTable.getSelectionModel().getSelectedItem() != null) {

            Delivery data = deliveryTable.getSelectionModel().getSelectedItem();
            CustomerNo = data.getCustomerID();
            Date DeliveryDate = java.sql.Date.valueOf(data.getDeliveryDate());
            System.out.println(DeliveryDate);

            //DeliveryDate.compareTo(CurrentDate)==0;
            Statement stmt = con.createStatement();
            if (DeliveryDate.equals(CurrentDate) && fullpayment.equals("Yes")) {
                String sql = "Update DELIVERY set RECORD='Delivered' where CUSTOMER_ID=" + CustomerNo + "";
                stmt.executeUpdate(sql);

                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("!!Delivery Date matched!!");
                a.show();

            } else {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("!!Delivery Date didn't match!!");
                a.show();
            }

        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("!!No Item is Selected!!");
            a.show();
        }

    }

    void ShowDeliveryTable() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=Catering;";
        Connection con = DriverManager.getConnection(connectionUrl);

        data = FXCollections.observableArrayList();
        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM DELIVERY WHERE RECORD='Active'");

        while (rs.next()) {

            data.add(new Delivery(rs.getInt("PAYMENT_ID"), rs.getInt("CUSTOMER_ID"), rs.getInt("DELIVERY_ID"), rs.getString("DELIVERY_ADDRESS"),
                    rs.getString("DELIVERY_DATE"), rs.getString("RECORD")));
        }
        delivery_deliveryAddress_col.setCellValueFactory(new PropertyValueFactory<>("deliveryAddress"));
        delivery_deliveryID_col.setCellValueFactory(new PropertyValueFactory<>("deliveryID"));
        deliverytab_cusId_col.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        deliverytab_payId_col.setCellValueFactory(new PropertyValueFactory<>("paymentID"));
        deliverytab_deliveryDate_col.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
        deliverytab_record_col.setCellValueFactory(new PropertyValueFactory<>("deliveryRecord"));

        deliveryTable.setItems(null);
        deliveryTable.setItems(data);
    }

    @FXML
    private void deliverySearchBtnAction(ActionEvent event) throws ClassNotFoundException, SQLException {

        Connection conn;

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=Catering;";
        conn = DriverManager.getConnection(connectionUrl);

        deliveryTable.getItems().clear();

        String SearchValue = deliverySearchTxtArea.getText();

        String SearchQuery = "Select *from DELIVERY where CUSTOMER_ID LIKE '%" + SearchValue + "%' OR PAYMENT_ID LIKE '%" + SearchValue + "%' "
                + " OR DELIVERY_ADDRESS LIKE '%" + SearchValue + "%'";

        System.out.println(SearchQuery);
        Statement stm = conn.createStatement();

        ResultSet rs1 = stm.executeQuery(SearchQuery);

        while (rs1.next()) {

            data.add(new Delivery(rs1.getInt("PAYMENT_ID"), rs1.getInt("CUSTOMER_ID"), rs1.getInt("DELIVERY_ID"), rs1.getString("DELIVERY_ADDRESS"),
                    rs1.getString("DELIVERY_DATE"), rs1.getString("RECORD")));

            delivery_deliveryAddress_col.setCellValueFactory(new PropertyValueFactory<>("deliveryAddress"));
            delivery_deliveryID_col.setCellValueFactory(new PropertyValueFactory<>("deliveryID"));
            deliverytab_cusId_col.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            deliverytab_payId_col.setCellValueFactory(new PropertyValueFactory<>("paymentID"));
            deliverytab_deliveryDate_col.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
            deliverytab_record_col.setCellValueFactory(new PropertyValueFactory<>("deliveryRecord"));

            deliveryTable.setItems(data);

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
