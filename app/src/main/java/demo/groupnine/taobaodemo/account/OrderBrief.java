package demo.groupnine.taobaodemo.account;

public class OrderBrief {
    public String orderId;
    public String shopName;
    public String orderStatus;

    public OrderBrief(String id, String name, String status) {
        this.orderId = id;
        this.shopName = name;
        this.orderStatus = status;
    }
}