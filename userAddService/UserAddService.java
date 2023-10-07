package userAddService;
public class UserAddService {
int itemID;
String PackageName;
int cost;

    public UserAddService(int itemID, String PackageName, int cost) {
        this.itemID = itemID;
        this.PackageName = PackageName;
        this.cost = cost;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getPackageName() {
        return PackageName;
    }

    public void setPackageName(String PackageName) {
        this.PackageName = PackageName;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }


}
