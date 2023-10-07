package userAddService;

import static Customer.Customer_infoController.C_ID;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javafx.stage.Stage;
import static service.FXML_servicesController.O_ID;
import service.Services;

public class FXML_UserAddServiceController implements Initializable {

    @FXML
    private TableView<UserAddService> servicesTable;
    @FXML
    private TableColumn<UserAddService, String> foodItemCol;
    @FXML
    private TableColumn<UserAddService, String> packageNameCol;
    @FXML
    private TableColumn<UserAddService, String> costCol;
    @FXML
    private JFXTextField search_field;
    @FXML
    private ImageView search_img;
    @FXML
    private JFXButton search_btn;
    @FXML
    private JFXTextField quantityTXTfield;
    @FXML
    private JFXButton proceedBtn;
    @FXML
    private ImageView backBtn;

    private ObservableList<UserAddService> data;

    //DATABASE CONNECTION VARIABLE
    Connection con;

    static int idNo;

    static String foodName;
    static int foodPrice;
    static String quantity;
    public static int O_ID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            // TODO
            DatabaseValue();
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
            Logger.getLogger(FXML_UserAddServiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void DatabaseValue() throws SQLException, ClassNotFoundException {

        //sean//Connection con;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=Catering;";
        con = DriverManager.getConnection(connectionUrl);

        data = FXCollections.observableArrayList();
        ResultSet rs = con.createStatement().executeQuery("select *from CUS_SERVICES");

        while (rs.next()) {

            data.add(new UserAddService(rs.getInt("ITEM_ID"), rs.getString("PACKAGE_NAME"), rs.getInt("PRICE")));
        }
        foodItemCol.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
        packageNameCol.setCellValueFactory(new PropertyValueFactory<>("PackageName"));

        servicesTable.setItems(null);
        servicesTable.setItems(data);
    }

    @FXML
    private void searchBtnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        Connection conn;

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=Catering;";
        conn = DriverManager.getConnection(connectionUrl);

        servicesTable.getItems().clear();

        String itemName = search_field.getText();

        String SearchQuery = "Select *from CUS_SERVICES where ITEM_ID LIKE '%" + itemName + "%' OR PACKAGE_NAME LIKE '%" + itemName + "%'";

        PreparedStatement pst = conn.prepareStatement(SearchQuery);

        ResultSet rs1 = pst.executeQuery();

        while (rs1.next()) {

            data.add(new UserAddService(rs1.getInt("ITEM_ID"), rs1.getString("PACKAGE_NAME"), rs1.getInt("PRICE")));

            foodItemCol.setCellValueFactory(new PropertyValueFactory<>("itemID"));
            costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
            packageNameCol.setCellValueFactory(new PropertyValueFactory<>("PackageName"));

            servicesTable.setItems(data);

        }
    }

    @FXML
    private void proceedBtnAction(ActionEvent event) throws SQLException, IOException {

        if (servicesTable.getSelectionModel().getSelectedItem() != null) {

            UserAddService data = servicesTable.getSelectionModel().getSelectedItem();
            idNo = data.getItemID();
            quantity = quantityTXTfield.getText();
        
            //sean
            foodPrice = data.getCost();
            foodName = data.getPackageName();
            insert();

            Parent Order = FXMLLoader.load(getClass().getResource("/Order/FXML_Order.fxml"));
            Scene scn = new Scene(Order);
            Stage order = (Stage) ((Node) event.getSource()).getScene().getWindow();

            order.setScene(scn);
            order.setTitle("Order");
            order.show();

        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("!!No Item is Selected!!");
            a.show();
        }
    }

    //sean
    public void insert() throws SQLException {
        int total_costPerItem = foodPrice * Integer.parseInt(quantity);
        String sql = "INSERT INTO ORDERS (Item_ID, Quantity, Total_costPerItem,Customer_ID,Order_Status)"
                + "VALUES (" + idNo + "," + quantity + "," + total_costPerItem + "," + C_ID + ",'No')";

        System.out.println("Insert " + sql);

        try {

            Connection conn;

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=Catering;";
            conn = DriverManager.getConnection(connectionUrl);

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("A new order was inserted successfully!");
            conn.close();

            //Order Id
            String sql3 = "Select Order_ID from ORDERS Where Customer_ID=" + C_ID + " AND Order_Status='No'";
            PreparedStatement pstm2 = con.prepareStatement(sql3);
            ResultSet r1 = pstm2.executeQuery();
            if (r1.next()) {
                O_ID = r1.getInt("Order_ID");
                System.out.println("Order ID=" + O_ID + " OF Customer ID =" + C_ID + "");
            }

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("A new order insertion failed!" + e.getMessage());
        }
    }

    @FXML
    private void backBtnAction(MouseEvent event) throws IOException {
        Parent ReturnToCustomerInfo = FXMLLoader.load(getClass().getResource("/Customer/customer_info.fxml"));
        Scene scr = new Scene(ReturnToCustomerInfo);
        Stage su = (Stage) ((Node) event.getSource()).getScene().getWindow();
        su.setScene(scr);
        su.setTitle("back into customerInfo");
        su.show();
    }
}
