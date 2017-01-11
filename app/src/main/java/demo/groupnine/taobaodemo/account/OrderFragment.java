package demo.groupnine.taobaodemo.account;

import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import demo.groupnine.taobaodemo.R;
import demo.groupnine.taobaodemo.net.HttpCallbackListener;
import demo.groupnine.taobaodemo.net.HttpRequest;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by ytqqwer on 2017/1/9.
 */

public class OrderFragment
        extends Fragment {
    private static String TAG = "OrderFragment";

    public String keyword;

    public LinkedList<OrderBrief> briefData;
    public OrderPreviewAdapter adapter;
    public boolean hasFetchedResult;
    public RecyclerView recyclerView;
    public ImageView blankPreview;


    private TextView all_order;
    private TextView wait_pay_order;
    private TextView wait_send_order;
    private TextView wait_receive_order;
    private TextView wait_evaluate_order;

    public static OrderFragment newInstance(String keyword)
    {
        OrderFragment fragment = new OrderFragment();
        fragment.keyword = keyword;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.orderPreview_Recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        blankPreview = (ImageView) view.findViewById(R.id.orderPreview_no_order);

        briefData = new LinkedList<OrderBrief>();

        this.all_order = (TextView) view.findViewById(R.id.order_all);
        this.wait_pay_order = (TextView) view.findViewById(R.id.order_wait_pay);
        this.wait_send_order = (TextView) view.findViewById(R.id.order_wait_send);
        this.wait_receive_order = (TextView) view.findViewById(R.id.order_wait_receive);
        this.wait_evaluate_order = (TextView) view.findViewById(R.id.order_wait_evaluate);

        all_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                resetColor();
                all_order.setTextColor(getResources().getColor(R.color.tb_red));
                fetchOrderBrief("*");
                updateResultUI();
            }
        });
        wait_pay_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                resetColor();
                wait_pay_order.setTextColor(getResources().getColor(R.color.tb_red));
                fetchOrderBrief("待付款");
                updateResultUI();
            }
        });
        wait_send_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                resetColor();
                wait_send_order.setTextColor(getResources().getColor(R.color.tb_red));
                fetchOrderBrief("待发货");
                updateResultUI();
            }
        });
        wait_receive_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                resetColor();
                wait_receive_order.setTextColor(getResources().getColor(R.color.tb_red));
                fetchOrderBrief("待收货");
                updateResultUI();
            }
        });
        wait_evaluate_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                resetColor();
                wait_evaluate_order.setTextColor(getResources().getColor(R.color.tb_red));
                fetchOrderBrief("已完成");
                updateResultUI();
            }
        });

        fetchOrderBrief(keyword);
        updateResultUI();

        return view;
    }

    private void resetColor()
    {
        all_order.setTextColor(getResources().getColor(R.color.black));
        wait_pay_order.setTextColor(getResources().getColor(R.color.black));
        wait_send_order.setTextColor(getResources().getColor(R.color.black));
        wait_receive_order.setTextColor(getResources().getColor(R.color.black));
        wait_evaluate_order.setTextColor(getResources().getColor(R.color.black));
    }

    private void updateResultUI()
    {
        if (briefData.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            blankPreview.setVisibility(View.VISIBLE);
        } else {
            blankPreview.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        adapter = new OrderPreviewAdapter(briefData);
        recyclerView.setAdapter(adapter);
    }

    private void fetchOrderBrief(String orderStatus)
    {
        hasFetchedResult = false;
        HttpRequest.getOrderByStatus("?orderStatus=" + orderStatus
                        + "&maxNumInOnePage=200"
                        + "&pageNum=1",
                new HttpCallbackListener() {
                    @Override
                    public void onFinish(Object responese)
                    {
                        briefData = new LinkedList<OrderBrief>((ArrayList<OrderBrief>) responese);
                        hasFetchedResult = true;
                    }

                    @Override
                    public void onError(Exception e)
                    {
                        Log.d(TAG, "request failed");
                    }
                });
        while (!hasFetchedResult) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // do nothing
            }
        }
    }


////////////////////////////////////////////////////////////
// inner class

    private class OrderHolder
            extends RecyclerView.ViewHolder {
        private TextView mShopName;
        private TextView mOrderStatus;
        private TextView mGoodsName;
        private TextView mActualPrice;
        private TextView mTotalPrice;
        private TextView mGoodsPrice;
        private TextView mGoodsNumber;
        private ImageView mOrderImage;

        public OrderHolder(View view)
        {
            super(view);
            mShopName = (TextView) view.findViewById(R.id.orderPreview_shop_name);
            mOrderStatus = (TextView) view.findViewById(R.id.orderPreview_status);
            mGoodsName = (TextView) view.findViewById(R.id.orderPreview_goods_name);
            mActualPrice = (TextView) view.findViewById(R.id.orderPreview_goods_actual_price);
            mGoodsPrice = (TextView) view.findViewById(R.id.orderPreview_goods_price);
            mGoodsNumber = (TextView) view.findViewById(R.id.orderPreview_goods_number);
            mTotalPrice = (TextView) view.findViewById(R.id.orderPreview_total_price);
            mOrderImage = (ImageView) view.findViewById(R.id.orderPreview_goods_picture);
        }

        public void bindOrder(final OrderBrief orderBriefItem)
        {
            mShopName.setText(orderBriefItem.shopName);
            mOrderStatus.setText(orderBriefItem.orderStatus);
            mGoodsPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//设置中划线

            final OrderPreview previewData = new OrderPreview();
            HttpRequest.getOrderById("?orderId=" + orderBriefItem.orderId,
                    new HttpCallbackListener() {
                        @Override
                        public void onFinish(final Object responese)
                        {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run()
                                {

                                    OrderDetail orderDetail = (OrderDetail) responese;
                                    mActualPrice.setText(orderDetail.total);
                                    mGoodsPrice.setText(orderDetail.total);
                                    mTotalPrice.setText(orderDetail.total);

                                    ArrayList<GoodsInOrder> gio = orderDetail.goodsInOrder;
                                    int num = 0;
                                    StringBuilder name = new StringBuilder();
                                    for (int j = 0; j < gio.size(); j++) {
                                        num = num + Integer.parseInt(gio.get(j).goodsNum);   //获取商品总数
                                        name.append("+");
                                        name.append(gio.get(j).goods.goodsName);           //获取所有商品名
                                    }
                                    previewData.goodsImage = gio.get(0).goods.imageAddr; //只取一张图片地址
                                    HttpRequest.getImage(previewData.goodsImage,
                                            new HttpCallbackListener() {
                                                public void onFinish(Object o)
                                                {
                                                    final Drawable img = (Drawable) o;
                                                    getActivity().runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run()
                                                        {
                                                            mOrderImage.setImageDrawable(img);
                                                        }
                                                    });
                                                }

                                                public void onError(Exception e)
                                                {
                                                }
                                            });
                                    previewData.goodsNum = String.valueOf(num);
                                    mGoodsNumber.setText(previewData.goodsNum);
                                    previewData.goodsName = name.toString();
                                    mGoodsName.setText(previewData.goodsName);
                                }
                            });
                        }

                        @Override
                        public void onError(Exception e)
                        {
                            Log.d(TAG, "request failed");
                        }
                    });
        }
    }

////////////////////////////////////////////////////////////
// inner class

    private class OrderPreviewAdapter
            extends RecyclerView.Adapter<OrderHolder> {
        private LinkedList<OrderBrief> myOrderBrief;

        public OrderPreviewAdapter(LinkedList<OrderBrief> briefData)
        {
            myOrderBrief = briefData;
        }

        @Override
        public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            // get inflater directly from LayoutInflater
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View iv = inflater.inflate(R.layout.fragment_order_item, parent, false);
            return new OrderHolder(iv);
        }

        @Override
        public void onBindViewHolder(final OrderHolder holder, int position)
        {
            OrderBrief orderBriefItem = myOrderBrief.get(position);
            holder.bindOrder(orderBriefItem);
        }

        @Override
        public int getItemCount()
        {
            return myOrderBrief.size();
        }
    }

}












