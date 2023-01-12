package ui;

import domain.Invoice;
import domain.LineItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javax.sound.sampled.Line;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class InvoiceController {
    public TextField invoiceIdTextField;
    public TextField statusTextField;
    public TextField invoiceDateTextField;
    public TextField dueDateTextField;
    //    public ComboBox invoiceComboBox;    // ex1e
    @FXML
    private ComboBox<Invoice> invoiceComboBox;
    //    public ListView lineItemListView;   // ex1e
    @FXML
    private ListView<LineItem> lineItemListView;
    public TextField descriptionTextField;
    public TextField amountTextField;
    public Button saveInvoiceButton;
    public Button saveLineItemButton;
    public TextField totalTextField;
    public Button addLineItemButton;
    public Button deleteLineItemButton;

    private ArrayList<Invoice> invoices = new ArrayList<>(); // do not delete ex1e

//    public InvoiceController() {
//        this.invoices = DbContext.getInvoices();
//    }

    public InvoiceController() {
    }

    public void initData(ArrayList<Invoice> invoices) {
        this.invoices = invoices;
    }

    private void displayInvoice(Invoice invoice) {
        this.invoiceIdTextField.setText(Integer.toString(invoice.getInvoiceId()));
        this.statusTextField.setText(Integer.toString(invoice.getStatus()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        this.invoiceDateTextField.setText(invoice.getInvoiceDate().format(formatter));
        this.dueDateTextField.setText(invoice.getDueDate().format(formatter));
    }

    @FXML
    protected void initialize() {
        if (this.invoices.size() > 0) {
            for (Invoice invoice : this.invoices) {
//                invoiceComboBox.getItems().add(invoice.toShortString());    // ex1e
                invoiceComboBox.getItems().add(invoice);
            }
            this.invoices = null;   // ex1e
            invoiceComboBox.getSelectionModel().selectFirst();
//            Invoice invoice = this.invoices.get(0); // ex1e
            Invoice invoice = this.invoiceComboBox.getSelectionModel().getSelectedItem();
            this.displayInvoice(invoice);
            this.displayLineItems(invoice);
        }
    }

    public void invoiceComboBoxItemSelected(ActionEvent actionEvent) {
//        int index = invoiceComboBox.getSelectionModel().getSelectedIndex();   // ex1e
//        if (index >= 0) { // ex1e
        Invoice index = invoiceComboBox.getSelectionModel().getSelectedItem();
//        if (index >= 0) {   // ex1e
        if (index != null) {
//            Invoice invoice = this.invoices.get(index); // ex1e
//            Invoice invoice = this.invoiceComboBox.getSelectionModel().getSelectedItem();
//            this.displayInvoice(invoice); // ex1e
//            this.displayLineItems(invoice);   // ex1e
//            this.displayInvoice(invoice); // ex1e
//            this.displayLineItems(invoice);   // ex1e
            this.displayInvoice(index);
            this.displayLineItems(index);
        }
    }

    public void lineItemListViewClicked(MouseEvent mouseEvent) {
//        1) get index of selected invoice
//        int index = this.invoiceComboBox.getSelectionModel().getSelectedIndex();  // ex1e
////        2) get selected invoice from this.invoices  // ex1e
//        Invoice invoice = this.invoices.get(index);  // ex1e
////        3) get lineItems from selected invoice  // ex1e
//        ArrayList<LineItem> lineItems = invoice.getLineItems();  // ex1e
//        Invoice index = this.invoiceComboBox.getSelectionModel().getSelectedItem();
//        ArrayList<LineItem> lineItems = index.getLineItems();
        ArrayList<LineItem> lineItems =
                invoiceComboBox.getSelectionModel().getSelectedItem().getLineItems();   // ex1e
        LineItem lineItemIndex = this.lineItemListView.getSelectionModel().getSelectedItem();
//        4) get index of selected lineItem
//        int index2 = this.lineItemListView.getSelectionModel().getSelectedIndex();
//        5) get selected lineItem
//        LineItem lineItem = lineItems.get(index2);
//        6) displayLineItem()
        displayLineItem(lineItemIndex);
    }

    private void displayLineItem(LineItem lineItem) {
        this.descriptionTextField.setText(lineItem.getDescription());
        this.amountTextField.setText(Double.toString(lineItem.getAmount()));
    }

    private void displayLineItems(Invoice invoice) {
//      1) clear lineItemsListview
        this.lineItemListView.getItems().clear();
//      2) lineItems = invoice.getLineItems()
        ArrayList<LineItem> lineItems = invoice.getLineItems();
//      3) copy each LineItem to lineItemsListView
        for (LineItem x : lineItems) {
//            lineItemListView.getItems().add(x.toShortString());   // ex1e
            lineItemListView.getItems().add(x);
        }
//      4) clear description and amount textFields
        this.descriptionTextField.clear();
        this.amountTextField.clear();
//      5) select first item in lineItemsListView
        lineItemListView.getSelectionModel().selectFirst();
//      6) if lineItems has an item, displayLineItem()
//        if (lineItems.size() > 0) {   // ex1e
//            displayLineItem(lineItems.get(0));   // ex1e
//        }   // ex1e
        LineItem lineItem = lineItemListView.getSelectionModel().getSelectedItem();
        if (lineItem != null)
            displayLineItem(lineItem);
        totalTextField.setText(String.format(Double.toString(invoice.total())));
    }

    public void saveInvoiceButtonClicked(ActionEvent actionEvent) {
////        get index of selected invoice
//        int index = this.invoiceComboBox.getSelectionModel().getSelectedIndex();  // ex1e
        Invoice exOneE = this.invoiceComboBox.getSelectionModel().getSelectedItem();
////        get selected invoice
//        Invoice invoice = this.invoices.get(index);  // ex1e
//        copy from form controls to invoice
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate date;
        date = LocalDate.parse(this.invoiceDateTextField.getText(), formatter);
//        invoice.setInvoiceDate(date.atStartOfDay());    // ex1e
        exOneE.setInvoiceDate(date.atStartOfDay());
        date = LocalDate.parse(this.dueDateTextField.getText(), formatter);
//        invoice.setDueDate(date.atStartOfDay());    // ex1e
        exOneE.setDueDate(date.atStartOfDay());
//        remove selected item from invoiceComboBox
//        this.invoiceComboBox.getItems().remove(index);  // ex1e
        int index = this.invoiceComboBox.getSelectionModel().getSelectedIndex();    // ex1e
        this.invoiceComboBox.getItems().remove(index);
//        add invoice.toShortString to invoiceComboBox
//        this.invoiceComboBox.getItems().add(index, invoice.toShortString());    // ex1e
        this.invoiceComboBox.getItems().add(index, exOneE);
//        select item in invoiceComboBox
        this.invoiceComboBox.getSelectionModel().select(index);
    }

    public void saveLineItemButtonClicked(ActionEvent actionEvent) {
        Invoice invoice = this.invoiceComboBox.getSelectionModel().getSelectedItem();
        int lineItemIndex = this.lineItemListView.getSelectionModel().getSelectedIndex();
        invoice.removeLineItem(lineItemIndex);
        LineItem lineItem = new LineItem(Double.parseDouble(
                this.amountTextField.getText()),
                this.descriptionTextField.getText().trim());
        invoice.addLineItem(lineItemIndex,lineItem);
        this.lineItemListView.getItems().remove(lineItemIndex);
        this.lineItemListView.getItems().add(lineItemIndex,lineItem);
        this.lineItemListView.getSelectionModel().select(lineItemIndex);
        this.totalTextField.setText(String.format("%.2f", invoice.total()));
    }

    public void addLineItemButtonClicked(ActionEvent actionEvent) {
//        get index of selected invoice
        Invoice invoice = this.invoiceComboBox.getSelectionModel().getSelectedItem();
        LineItem lineItem = new LineItem(0.0,"");
        invoice.addLineItem(lineItem);
//        add LineItem shortString to ListView
        this.lineItemListView.getItems().add(lineItem);
//        select last item in ListView
        this.lineItemListView.getSelectionModel().selectLast();
//        scroll to last item in ListView
        this.lineItemListView.scrollTo(lineItem);
//        displayLineItem()
        displayLineItem(lineItem);
//        set focus to description
        descriptionTextField.requestFocus();
    }

    public void deleteLineItemButtonClicked(ActionEvent actionEvent) {
//        clear text from amountTextField and descriptionTextField
        this.amountTextField.clear();
        this.descriptionTextField.clear();
        Invoice invoice = this.invoiceComboBox.getSelectionModel().getSelectedItem();
        if (invoice != null) {
            int lineItemIndex = this.lineItemListView.getSelectionModel().getSelectedIndex();
            if (lineItemIndex >= 0) {
                invoice.removeLineItem(lineItemIndex);
                this.lineItemListView.getItems().remove(lineItemIndex);
                this.lineItemListView.getSelectionModel().selectLast();
//                lineItemIndex = this.lineItemListView.getSelectionModel().getSelectedIndex();
//                if (lineItemIndex >= 0) {
//                    this.lineItemListView.scrollTo(lineItemIndex);
//                    LineItem lineItem = invoice.getLineItem(lineItemIndex);
//                    displayLineItem(lineItem);
//                }
                LineItem lineItem = this.lineItemListView.getSelectionModel().getSelectedItem();
                if (lineItem != null) {
                    this.lineItemListView.scrollTo(lineItem);
                    displayLineItem(lineItem);
                }
//                display total
                this.totalTextField.setText(String.format("%.2f", invoice.total()));
            }
        }
    }
}
