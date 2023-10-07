package delivery;
public class Delivery {

int paymentID;
int customerID;
int deliveryID;
String deliveryAddress;
String deliveryDate;
String deliveryRecord;

    public Delivery(int paymentID, int customerID, int deliveryID, String deliveryAddress, String deliveryDate, String deliveryRecord) {
        this.paymentID = paymentID;
        this.customerID = customerID;
        this.deliveryID = deliveryID;
        this.deliveryAddress = deliveryAddress;
        this.deliveryDate = deliveryDate;
        this.deliveryRecord = deliveryRecord;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(int deliveryID) {
        this.deliveryID = deliveryID;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryRecord() {
        return deliveryRecord;
    }

    public void setDeliveryRecord(String deliveryRecord) {
        this.deliveryRecord = deliveryRecord;
    }



}
