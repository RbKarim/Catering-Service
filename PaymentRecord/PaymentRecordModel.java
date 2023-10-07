
package PaymentRecord;

public class PaymentRecordModel {
       private int Payment_ID;
       private int Customer_ID;
       private int Order_ID;
       private int advance;
        private int due;
       private String fullpayment; 

    public PaymentRecordModel(int Payment_ID, int Customer_ID, int Order_ID,int advance,int due,String fullpayment) {
        this.Payment_ID = Payment_ID;
        this.Customer_ID = Customer_ID;
        this.Order_ID = Order_ID;

        this.advance = advance;
        this.due=due;
        this.fullpayment = fullpayment;
    }

    public int getPayment_ID() {
        return Payment_ID;
    }

    public void setPayment_ID(int Payment_ID) {
        this.Payment_ID = Payment_ID;
    }

    public int getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(int Customer_ID) {
        this.Customer_ID = Customer_ID;
    }

    public int getOrder_ID() {
        return Order_ID;
    }

    public void setOrder_ID(int Order_ID) {
        this.Order_ID = Order_ID;
    }

 
    public int getAdvance() {
        return advance;
    }

    public void setAdvance(int advance) {
        this.advance = advance;
    }

    public int getDue() {
        return due;
    }

    public void setDue(int due) {
        this.due = due;
    }
    

    public String getFullpayment() {
        return fullpayment;
    }

    public void setFullpayment(String fullpayment) {
        this.fullpayment = fullpayment;
    }
       
       
       
    
}
