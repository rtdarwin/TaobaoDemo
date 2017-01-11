package demo.groupnine.taobaodemo.account;

/**
 * Created by ytqqwer on 2017/1/9.
 */

public class OrderPreview {
    public String orderId;
    public String shopName;
    public String orderStatus;
    public String goodsImage;
    public String goodsName;
    public String actualPrice;
    public String goodsNum;
    public String totalPrice;


    public OrderPreview(String id, String name, String status,
                      String goodsImage, String goodsName, Double actualPrice,
                      int goodsNum,Double totalPrice) {  //有图片地址
        this.orderId = id;
        this.shopName = name;
        this.orderStatus = status;
        this.goodsImage = goodsImage;
        this.goodsName = goodsName;
        this.actualPrice = String.valueOf(actualPrice);
        this.goodsNum = String.valueOf(goodsNum);
        this.totalPrice = String.valueOf(totalPrice);
    }

    public OrderPreview(String id, String name, String status,
                        String goodsName, Double actualPrice,
                        int goodsNum,Double totalPrice) {   //无图片地址
        this.orderId = id;
        this.shopName = name;
        this.orderStatus = status;
        this.goodsName = goodsName;
        this.actualPrice = String.valueOf(actualPrice);
        this.goodsNum = String.valueOf(goodsNum);
        this.totalPrice = String.valueOf(totalPrice);
    }

    public OrderPreview(){

    }
}
