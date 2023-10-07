package Order;


public class Order_totalPrice {
    
    private int ItemName_ID;
    //private String ItemName;
    private int Quantity;
    private int PricePerItem;

    public Order_totalPrice(int ItemName_ID,int Quantity,int PricePerItem) {
        this.ItemName_ID = ItemName_ID;
        //this.ItemName = ItemName;
        this.Quantity = Quantity;
        this.PricePerItem = PricePerItem;
    }

    public int getItemName_ID() {
        return ItemName_ID;
    }

    public void setItemName_ID(int ItemName_ID) {
        this.ItemName_ID = ItemName_ID;
    }

    
    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public int getPricePerItem() {
        return PricePerItem;
    }

    public void setPricePerItem(int PricePerItem) {
        this.PricePerItem = PricePerItem;
    }

 
    
    
    
    
}
