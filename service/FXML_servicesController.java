package service;

import Customer.Customer_infoController;
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
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import static signin.SignUpController.admin;

/**
 * FXML Controller class
 *
 * @author Rbkar
 */
public class FXML_servicesController implements Initializable {

    @FXML
    private TableColumn<Services, String> foodItemCol;
    @FXML
    private TableColumn<Services, String> costCol;
    @FXML
    private JFXTextField search_field;
    @FXML
    private ImageView search_img;
    @FXML
    private JFXButton search_btn;
    @FXML
    private JFXButton updateBtn;
    @FXML
    private JFXButton removeBtn;
    @FXML
    private TableView<Services> servicesTable;
    @FXML
    private TableColumn<Services, String> packageNameCol;
    private JFXTextField quantityTXTfield;

    private ObservableList<Services> data;
    @FXML
    private JFXButton addBtn;

    static int idNo;

    static String foodName;
    static int foodPrice;
    static String quantity;
    public static int O_ID;

    //sean
    Connection con;

    @FXML
    private JFXButton refreshBtn;
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXML_servicesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FXML_servicesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Hello");
    }

    public void DatabaseValue() throws SQLException, ClassNotFoundException {

        //sean//Connection con;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=Catering;";
        con = DriverManager.getConnection(connectionUrl);

        data = FXCollections.observableArrayList();
        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM CUS_SERVICES");

        while (rs.next()) {

            data.add(new Services(rs.getInt("ITEM_ID"), rs.getString("PACKAGE_NAME"), rs.getInt("PRICE")));
            System.out.println(rs.getInt("ITEM_ID"));
            System.out.println("Hello");
            System.out.println(rs.getString("PACKAGE_NAME"));
            System.out.println(rs.getInt("PRICE"));
        }
        foodItemCol.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
        packageNameCol.setCellValueFactory(new PropertyValueFactory<>("PackageName"));

        servicesTable.setItems(null);
        servicesTable.setItems(data);
    }

    void RemoveService() throws ClassNotFoundException {
        Services data = servicesTable.getSelectionModel().getSelectedItem();
        int id = data.getItemID();

        try {
            Connection con;

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=Catering;";
            con = DriverManager.getConnection(connectionUrl);

            Statement stm = con.createStatement();
            String sql = "delete from CUS_SERVICES where ITEM_ID=" + id + "";
            System.out.println(sql);
            stm.executeUpdate(sql);
            con.close();

            servicesTable.getItems().remove(data);
            System.out.println("SuccessfulI");
        } catch (SQLException ex) {
            System.out.println("Error:" + ex);

        }
    }

    @FXML
    private void updateBtnAction(ActionEvent event) throws IOException {

        if (servicesTable.getSelectionModel().getSelectedItem() != null) {

            Services data = servicesTable.getSelectionModel().getSelectedItem();
            idNo = data.getItemID();

            Pane view = new FXMLLoader().load(getClass().getResource("FXML_updatePrice.fxml"));
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
    private void removeBtnAction(ActionEvent event) throws ClassNotFoundException {
        if (servicesTable.getSelectionModel().getSelectedItem() != null) {
            RemoveService();
        } else {

            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Choose an option");
            a.show();
        }

    }

    @FXML
    private void addBtnAction(ActionEvent event) throws IOException {
        Parent home = FXMLLoader.load(getClass().getResource("FXML_addService.fxml"));
        Scene scn = new Scene(home);
        Stage dashboard = (Stage) ((Node) event.getSource()).getScene().getWindow();

        dashboard.setScene(scn);
        dashboard.setTitle("Add Service");
        dashboard.show();
    }

    @FXML
    private void refreshBtnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        DatabaseValue();
    }

    @FXML
    private void searchBtnAction(ActionEvent event) throws ClassNotFoundException, SQLException {

        SearchService();
    }

    void SearchService() throws ClassNotFoundException, SQLException {

        Connection conn;

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=Catering;";
        conn = DriverManager.getConnection(connectionUrl);

        servicesTable.getItems().clear();

        String itemName = search_field.getText();

        PreparedStatement pst = conn.prepareStatement("Select *from CUS_SERVICES where ITEM_ID LIKE '%" + itemName + "%' OR PACKAGE_NAME LIKE '%" + itemName + "%'");

        ResultSet rs1 = pst.executeQuery();

        while (rs1.next()) {

            data.add(new Services(rs1.getInt("ITEM_ID"), rs1.getString("PACKAGE_NAME"), rs1.getInt("PRICE")));

            foodItemCol.setCellValueFactory(new PropertyValueFactory<>("itemID"));
            costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
            packageNameCol.setCellValueFactory(new PropertyValueFactory<>("PackageName"));

            servicesTable.setItems(data);

        }
    }

    @FXML
    private void backBtnAction(MouseEvent event) throws IOException {

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
