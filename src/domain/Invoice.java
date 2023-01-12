package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Invoice {
    private int invoiceId;
    private int status;
    private LocalDateTime invoiceDate;
    private LocalDateTime dueDate;
    private ArrayList<LineItem> lineItems = new ArrayList<LineItem>();
    private Apartment apartment = null;

    public Invoice(int status, LocalDateTime invoiceDate, LocalDateTime dueDate, Apartment apartment) {
        this.invoiceId = DbContext.getNextInvoiceId();
        this.status = status;
        this.invoiceDate = invoiceDate;
        this.dueDate = dueDate;
        this.apartment = apartment;
    }

    public Invoice(Invoice invoice) {
        this.invoiceId = invoice.invoiceId;
        this.status = invoice.status;
        this.invoiceDate = invoice.invoiceDate;
        this.dueDate = invoice.dueDate;
        this.apartment = invoice.apartment;
        for (LineItem lineItem : invoice.lineItems) {
            this.lineItems.add(lineItem.copy());
        }
    }

    public Invoice copy() {
        return new Invoice(this);
    }

    public void addLineItem(LineItem lineItem) {
        this.lineItems.add(lineItem);
    }

    public void addLineItem(int index, LineItem lineItem) {
        this.lineItems.add(index, new LineItem(lineItem));
    }

    public LineItem removeLineItem(int index) {
        LineItem lineItem = null;
        if (index >= 0 && index < this.lineItems.size()) {
//            lineItem = this.lineItems.get(index);
            lineItem = this.lineItems.get(index).copy();
            this.lineItems.remove(index);
        }
//        return the lineItem we just removed
        return lineItem;
    }

    public LineItem removeLineItem(LineItem lineItem) {
        LineItem removedLineItem = null;
//        use lineItems.indexOf() to search for matching LineItem
        int index = this.lineItems.indexOf(lineItem);
//        if match is found
        if (index >= 0 && index < this.lineItems.size()) {
//            declare removedLineItem and create copy
            removedLineItem = this.lineItems.get(index).copy();
//            remove the LineItem copy
            this.lineItems.remove(index);
        }
//        return removedLineItem
        return removedLineItem;
    }

    public double total() {
        double total = 0.0;
        for (LineItem lineItem : this.lineItems) {
            total += lineItem.getAmount();
        }
        return total;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public int getStatus() {
        return status;
    }

    public LocalDateTime getInvoiceDate() {
        return invoiceDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public LineItem getLineItem(int index) {
        LineItem lineItem = null;
        if (index < this.lineItems.size())
            lineItem = lineItems.get(index).copy();
        return lineItem;
    }

    public ArrayList<LineItem> getLineItems() {
        ArrayList<LineItem> lineItems = new ArrayList<LineItem>();
        for(LineItem lineItem : this.lineItems) {
            lineItems.add(lineItem.copy());
        }
        return lineItems;
    }

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//        return "Invoice{" +   // ex1e
//                "invoiceId=" + invoiceId +   // ex1e
//                ", status=" + status +   // ex1e
//                ", invoiceDate=" + invoiceDate.format(formatter) +   // ex1e
//                ", dueDate=" + dueDate.format(formatter) +   // ex1e
//                '}';   // ex1e
        return Integer.toString(invoiceId) +
                ", status: " + status +
                ", " + invoiceDate.format((formatter));
    }

    public String toShortString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return Integer.toString(invoiceId) +
                ", status: " + status +
                ", " + invoiceDate.format((formatter));
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setInvoiceDate(LocalDateTime invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Apartment getApartment() {
        return apartment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return getInvoiceId() == invoice.getInvoiceId() &&
                getStatus() == invoice.getStatus() &&
                this.invoiceDate.equals(invoice.invoiceDate) &&
                this.dueDate.equals(invoice.dueDate);
    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(getInvoiceId(), getStatus(), getInvoiceDate(), getDueDate());
//    }
}