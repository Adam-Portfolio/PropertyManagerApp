package ui;

import domain.Apartment;
import domain.DbContext;
import domain.Invoice;
import domain.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ApartmentController {
    //    public ComboBox aptComboBox;
    @FXML
    public ComboBox<Apartment> aptComboBox;
    //    public ComboBox adminComboBox;
    @FXML
    private ComboBox<Person> adminComboBox; // ex1e
    //    public ComboBox tenantComboBox;
    @FXML
    private ComboBox<Person> tenantComboBox;    // ex 1e
    public Button saveAptButton;
    public Button viewInvoiceDlgButton;
    public TextField aptNumTextField;
    public TextField squareFeetTextField;
    public TextField bathroomsTextField;
    public TextField priceTextField;
    public TextField updatedTextField;
//    private ArrayList<Apartment> apartments = new ArrayList<Apartment>();
//    private ArrayList<Person> people = new ArrayList<Person>();

    public ApartmentController() {
//        this.apartments = DbContext.getApartments();
//        this.people = DbContext.getPeople();
    }

    @FXML
    protected void initialize() {
        ArrayList<Apartment> apartments = DbContext.getApartments();    // ex1e
//        for (Apartment apartment : this.apartments) {   // ex1e
        for (Apartment apartment : apartments) {
            this.aptComboBox.getItems().add(apartment);
        }
        this.aptComboBox.getSelectionModel().selectFirst();
//        Apartment apt = this.apartments.get(0);   // ex1e
//        Apartment apt = this.aptComboBox.getValue();

        ArrayList<Person> people = DbContext.getPeople();   // ex1e

        for (Person person : people) {
//            this.adminComboBox.getItems().add(people.toShortString());
            this.adminComboBox.getItems().add(person);
        }
        for (Person person : people) {
            this.tenantComboBox.getItems().add(person);
        }
//        this.displayApartment(apt); // ex1e
        this.displayApartment(this.aptComboBox.getSelectionModel().getSelectedItem());
    }

    private void displayApartment(Apartment apartment) {
        this.aptNumTextField.setText(apartment.getApartmentNum());
        this.squareFeetTextField.setText(Integer.toString(apartment.getSquareFeet()));
        this.bathroomsTextField.setText(Integer.toString(apartment.getBathrooms()));
        this.priceTextField.setText(String.format("%.2f", apartment.getPrice()));
//        using DateTimeFormatter, display updated
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        this.updatedTextField.setText(apartment.getUpdated().format(formatter));

        int selectedIndex = -1;
        for (int i = 0; i < this.adminComboBox.getItems().size(); i++) {
//            Person person = this.adminComboBox.getItems().get(i);
            Person person = this.adminComboBox.getItems().get(i);
            if (person.equals(apartment.getAdministrator())) {
                selectedIndex = i;
            }
        }
        this.adminComboBox.getSelectionModel().select(selectedIndex);

        selectedIndex = -1;
        for (int i = 0; i < this.tenantComboBox.getItems().size(); i++) {
            Person person = this.tenantComboBox.getItems().get(i);
            if (person.equals(apartment.getTenant())) {
                selectedIndex = i;
            }
        }
        this.tenantComboBox.getSelectionModel().select(selectedIndex);
    }

    public void aptComboBox_ItemSelected(ActionEvent actionEvent) {
//        int selectedIndex = aptComboBox.getSelectionModel().getSelectedIndex();
        Apartment apartment = aptComboBox.getSelectionModel().getSelectedItem();    // ex1e
//        if (selectedIndex >= 0) {
        if (apartment != null) {
            displayApartment(this.aptComboBox.getSelectionModel().getSelectedItem());
        }
    }

    public void saveAptButton_Clicked(ActionEvent actionEvent) {
//        get aptComboBox selected index
//        int selectedAptIndex = this.aptComboBox.getSelectionModel().getSelectedIndex();
//        get selected apartment
        Apartment apartment = this.aptComboBox.getSelectionModel().getSelectedItem();   // ex1e
//        update Apartment fields from TextField controls: AptNum, square feet, bathrooms, price
        apartment.setApartmentNum(aptNumTextField.getText());
        apartment.setSquareFeet(Integer.parseInt(squareFeetTextField.getText()));
        apartment.setBathrooms(Integer.parseInt(bathroomsTextField.getText()));
        apartment.setPrice(Double.parseDouble(priceTextField.getText()));
//        set apartment.updated to current date/time
        apartment.setUpdated(LocalDateTime.now());
//        get adminComboBox selected index
//        int selectedAdminIndex = this.adminComboBox.getSelectionModel().getSelectedIndex();
//        get admin object from people
//        apartment.setAdministrator(people.get(selectedAdminIndex));
        apartment.setAdministrator(this.adminComboBox.getSelectionModel().getSelectedItem());  // ex1e
//        repeat these steps for tenant
//        get tenantComboBox selected index
//        int selectedTenantIndex = this.tenantComboBox.getSelectionModel().getSelectedIndex();
//        get tenant object from people
//        apartment.setTenant(people.get(selectedTenantIndex));
        apartment.setTenant(this.tenantComboBox.getSelectionModel().getSelectedItem());   // ex1e
//        update aptComboBox:
//        remove selected item from aptComboBox
//        ex1e
        int selectedAptIndex = this.aptComboBox.getSelectionModel().getSelectedIndex();
//        this.aptComboBox.getItems().remove(selectedAptIndex);   // ex1e
        this.aptComboBox.getItems().remove(selectedAptIndex);
//        add new apartment string to aptComboBox
//        this.aptComboBox.getItems().add(selectedAptIndex, apartment.toShortString());   // ex1e
        this.aptComboBox.getItems().add(selectedAptIndex, apartment);   // ex1e
//        this.aptComboBox.getItems().add(aptComboBox.getSelectionModel().getSelectedIndex(), apartment);
//        select current index
        this.aptComboBox.getSelectionModel().select(selectedAptIndex);
    }

    public void viewInvoiceDlgButton_Clicked(ActionEvent actionEvent) {
//        int selectedIndex = aptComboBox.getSelectionModel().getSelectedIndex();
//        if (selectedIndex >= 0) {
//            Apartment apartment = apartments.get(selectedIndex);    // ex1e
        Apartment apartment = aptComboBox.getSelectionModel().getSelectedItem();
        if (apartment != null) {
            ArrayList<Invoice> invoices = apartment.getInvoices();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InvoiceView.fxml"));
                InvoiceController invoiceController = new InvoiceController();
                invoiceController.initData(invoices);
                fxmlLoader.setController(invoiceController);
//                Parent root1 = (Parent) fxmlLoader.load();
                Pane pane = (Pane) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("AFravel 2742 Ex1E Invoices");
                stage.setScene(new Scene(pane));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
