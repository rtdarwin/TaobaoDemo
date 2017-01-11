package demo.groupnine.taobaodemo.account;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import demo.groupnine.taobaodemo.R;
import demo.groupnine.taobaodemo.net.HttpCallbackListener;
import demo.groupnine.taobaodemo.net.HttpRequest;

public class AccountDetailsActivity
        extends Activity {

    private boolean hasFetchedResult;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);        //去掉标题栏
        setContentView(R.layout.activity_account_details);

        ImageView iv1 = (ImageView) findViewById(R.id.account_detail_back);
        iv1.setOnClickListener(new View.OnClickListener() {        //给返回按钮注册事件
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        TextView tv1 = (TextView) findViewById(R.id.account_detail_name);
        TextView tv2 = (TextView) findViewById(R.id.account_detail_nickname);
        TextView tv3 = (TextView) findViewById(R.id.account_detail_sex);

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

        tv1.setText(userInfo.username);
        tv2.setText(userInfo.nickname);
        tv3.setText(userInfo.sex);
    }

}
