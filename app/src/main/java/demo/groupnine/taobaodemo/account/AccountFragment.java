package demo.groupnine.taobaodemo.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import demo.groupnine.taobaodemo.R;
import demo.groupnine.taobaodemo.net.HttpCallbackListener;
import demo.groupnine.taobaodemo.net.HttpRequest;

public class AccountFragment extends Fragment {

    private boolean hasFetchedResult;
    private UserInfo userInfo;

    @Override
    public void onCreate(
            @Nullable
                    Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);    //有系统标题栏
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable
                                     ViewGroup container,
                             @Nullable
                                     Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        TextView user_name = (TextView) view.findViewById(R.id.account_user_name);

        hasFetchedResult = false;
        HttpRequest.getUserInfo("",
                new HttpCallbackListener() {
                    @Override
                    public void onFinish(Object responese)
                    {
                        userInfo = (UserInfo) responese;
                        hasFetchedResult = true;
                    }

                    @Override
                    public void onError(Exception e)
                    {
                        Log.d("account", "request failed");
                    }
                });
        while (!hasFetchedResult) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // do nothing
            }
        }
        user_name.setText(userInfo.username);

        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageView iv1 = (ImageView) getActivity().findViewById(R.id.account_user_avatar);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AccountDetailsActivity.class);
                startActivity(intent);
            }
        });//点击头像进入个人资料详情

        TextView tv1 = (TextView) getActivity().findViewById(R.id.account_all_orders);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(),OrderActivity.class);
                intent.putExtra("select","*");
                startActivity(intent);
            }
        });//查看全部订单

        ImageView iv2 = (ImageView) getActivity().findViewById(R.id.account_wait_pay);
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),OrderActivity.class);
                intent.putExtra("select","待付款");
                startActivity(intent);
            }
        });//查看待付款订单

        ImageView iv3 = (ImageView) getActivity().findViewById(R.id.account_wait_send);
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),OrderActivity.class);
                intent.putExtra("select","待发货");
                startActivity(intent);
            }
        });//查看待发货订单

        ImageView iv4 = (ImageView) getActivity().findViewById(R.id.account_wait_receive);
        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),OrderActivity.class);
                intent.putExtra("select","待收货");
                startActivity(intent);
            }
        });//查看待收货订单

        ImageView iv5 = (ImageView) getActivity().findViewById(R.id.account_wait_evaluate);
        iv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),OrderActivity.class);
                intent.putExtra("select","待评价");
                startActivity(intent);
            }
        });//查看待评价订单

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater)
    {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.fragment_account, menu);  //设置系统标题栏的布局
    }

}
