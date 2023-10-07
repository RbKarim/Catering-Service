package Order;

import static Customer.Customer_infoController.C_ID;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.Services;

/**
 * FXML Controller class
 *
 * @author Sean
 */
public class FXML_OrderController implements Initializable {

    @FXML
    private TableColumn<Order_totalPrice, String> item_id;
    @FXML
    private TableColumn<Order_totalPrice, String> item_quantity;
    @FXML
    private TableColumn<Order_totalPrice, String> item_totalperprice;
    private Label total_amount;
    @FXML
    private TableView<Order_totalPrice> OrderTable;
    private ObservableList<Order_totalPrice> data;

    Connection con;
    PreparedStatement pst;
    ResultSet rs1;
    @FXML
    private Label totalAmount;
    public static String Amount;
    @FXML
    private JFXButton calculate_totalAmount;
    @FXML
    private JFXButton order_proceed;
    @FXML
    private ImageView backBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            DatabaseValue();
        } catch (SQLException ex) {
            Logger.getLogger(FXML_OrderController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXML_OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void DatabaseValue() throws SQLException, ClassNotFoundException {

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=Catering;";
        con = DriverManager.getConnection(connectionUrl);

        data = FXCollections.observableArrayList();

        try {

            Statement stm = con.createStatement();
            String sql = "Select * from ORDERS Where Customer_ID=" + C_ID + " AND Order_Status='No'";
            ResultSet rs = stm.executeQuery(sql);

            System.out.println("ORDERS " + sql);

            while (rs.next()) {

                data.add(new Order_totalPrice(rs.getInt("ITEM_ID"), rs.getInt("Quantity"), rs.getInt("Total_costPerItem")));

            }
            item_id.setCellValueFactory(new PropertyValueFactory<>("ItemName_ID"));
            item_quantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
            item_totalperprice.setCellValueFactory(new PropertyValueFactory<>("PricePerItem"));

            OrderTable.setItems(null);
            OrderTable.setItems(data);

            con.close();

        } catch (Exception ex) {

            System.out.println("Error" + ex.getMessage());
        }

    }

    @FXML
    private void order_proceed_onclick(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
       if (!(totalAmount.getText().isEmpty())) {
  
            Connection conn;

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=Catering;";
            conn = DriverManager.getConnection(connectionUrl);
            
            String sql="Update ORDERS set Order_Status='Yes' where Customer_Id="+ C_ID+" And Order_Status='No'";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.close();

           
           
        Parent home = FXMLLoader.load(getClass().getResource("/Payment/FXML_Payment.fxml"));
        Scene scn = new Scene(home);
        Stage dashboard = (Stage) ((Node) event.getSource()).getScene().getWindow();

        dashboard.setScene(scn);
        dashboard.setTitle("Payment");
        dashboard.show();
        } else {

        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setContentText("!!Total Field is empty. Please press calculate total to know the sum!!");
        a.show();
        }
    }

    @FXML
    private void calculate_totalAmount_onclick(ActionEvent event) throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=Catering;";
        con = DriverManager.getConnection(connectionUrl);
        String sqlsum = "SELECT SUM(Total_costPerItem) FROM ORDERS Where Customer_ID=" + C_ID + " AND Order_Status='No'";
        System.out.println("before try");
        try {

            pst = con.prepareStatement(sqlsum);
            rs1 = pst.executeQuery();

            String Countrun = "";
            while (rs1.next()) {
                Countrun = rs1.getString(1);
                System.out.println("Total sum :" + Countrun);
                totalAmount.setText(Countrun);
                Amount = Countrun;
                System.out.println(Amount);
            }

            /*rs1.next();
            sum = rs1.getString(1);
            System.out.println(sum);
            totalAmount.setText(sum);*/
            con.close();
        } catch (Exception ex) {

            System.out.println("failed to sum");
        }

    }

    @FXML

    private void BackAction(MouseEvent event) throws IOException {

        Parent ReturnToCustomerInfo = FXMLLoader.load(getClass().getResource("/userAddService/FXML_UserAddService.fxml"));
        Scene scr = new Scene(ReturnToCustomerInfo);
        Stage su = (Stage) ((Node) event.getSource()).getScene().getWindow();
        su.setScene(scr);
        su.setTitle("back into customerInfo");
        su.show();
    }
}
