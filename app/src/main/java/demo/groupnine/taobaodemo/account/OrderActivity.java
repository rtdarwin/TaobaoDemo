package demo.groupnine.taobaodemo.account;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import demo.groupnine.taobaodemo.R;


public class OrderActivity
        extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_order);

        ImageView iv = (ImageView) findViewById(R.id.order_account_back);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        }); //添加返回事件

        FragmentManager fragmentManager = getFragmentManager();
        Fragment orderFragment = fragmentManager.findFragmentById(R.id.activity_order_fragment_container);

        if(orderFragment == null)
        {
            String orderStatus = getIntent().getStringExtra("select");
            orderFragment = OrderFragment.newInstance(orderStatus);
            fragmentManager.beginTransaction()
                    .add(R.id.activity_order_fragment_container, orderFragment)
                    .commit();
        }
    }
}
