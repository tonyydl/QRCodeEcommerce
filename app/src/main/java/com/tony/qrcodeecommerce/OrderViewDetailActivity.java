package com.tony.qrcodeecommerce;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tony.qrcodeecommerce.utils.MyOrder;
import com.tony.qrcodeecommerce.utils.Tool;

import org.json.JSONArray;
import org.json.JSONException;

public class OrderViewDetailActivity extends ActionBarActivity{
    private static final String TAG = "OrderViewDetailActivity";

    private TextView oid,rname,rphone,remail,tplace,ttime,oprice,tupdate;
    private MyOrder myOrder;
    private ListView listView;
    private JSONArray jsonArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderview_detail);
        oid = (TextView) findViewById(R.id.oid);
        rname = (TextView) findViewById(R.id.rname);
        rphone = (TextView) findViewById(R.id.rphone);
        remail = (TextView) findViewById(R.id.remail);
        tplace = (TextView) findViewById(R.id.tplace);
        ttime = (TextView) findViewById(R.id.ttime);
        oprice = (TextView) findViewById(R.id.oprice);
        tupdate = (TextView) findViewById(R.id.tupdate);
        listView = (ListView) findViewById(R.id.listview);

        myOrder = MainApplication.getMyOrder();

        jsonArray = myOrder.getOrderItemArr();

        oid.setText(String.format(getResources().getString(R.string.orderview_oid),myOrder.getOid()));
        rname.setText(String.format(getResources().getString(R.string.orderview_rname),myOrder.getRname()));
        rphone.setText(String.format(getResources().getString(R.string.orderview_rphone),myOrder.getRphone()));
        remail.setText(String.format(getResources().getString(R.string.orderview_remail),myOrder.getRemail()));
        tplace.setText(String.format(getResources().getString(R.string.orderview_tplace),myOrder.getTplace()));
        ttime.setText(String.format(getResources().getString(R.string.orderview_ttime),myOrder.getTtime()));
        oprice.setText(String.format(getResources().getString(R.string.orderview_oprice),myOrder.getOprice()));
        tupdate.setText(String.format(getResources().getString(R.string.orderview_tupdate),myOrder.getTupdate()));

        MyAdapter myAdapter = new MyAdapter(this);
        listView.setAdapter(myAdapter);
    }

    private final class MyView {
        public TextView pid,spec,price,number;
        public ImageView img;
    }

    /**
     * 實作一個 Adapter 繼承 BaseAdapter
     */
    public class MyAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        public MyAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            //回傳這個 List 有幾個 item
            return jsonArray.length();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            // MyAdapter配置使用listview_cart的layout介面
            convertView = inflater.inflate(R.layout.listview_orderdetail, null);
            // new一個自訂View的class
            final MyView myviews = new MyView();
            // 指定元件
            myviews.pid = (TextView) convertView.findViewById(R.id.pid);
            myviews.spec = (TextView) convertView.findViewById(R.id.spec);
            myviews.price = (TextView) convertView.findViewById(R.id.price);
            myviews.number = (TextView) convertView.findViewById(R.id.number);
            myviews.img = (ImageView) convertView.findViewById(R.id.img);

            int price = 0;
            String pid = null, spec = null, num = null, pic = null, pic_link = null;

            try {
                pid = jsonArray.getJSONObject(position).getString("pid");
                spec = jsonArray.getJSONObject(position).getString("spec");
                num = jsonArray.getJSONObject(position).getString("num");
                price = jsonArray.getJSONObject(position).getInt("price");
                pic = jsonArray.getJSONObject(position).getString("pic");
                pic_link = jsonArray.getJSONObject(position).getString("pic_link");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //print
            Log.i(TAG, "pid:" + pid + " spec:" + spec + " num:" + num + " price:" + price + " pic:" + pic + " pic_link:" + pic_link);

            //如果圖片檔名不是空的，就取得bitmap給img
            if(pic != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(Tool.QRCodeEcommercePath+"/images/"+pic);
                myviews.img.setImageBitmap(bitmap);
            }

            //
            myviews.pid.setText(String.format(getString(R.string.orderview_listview_pid),pid));
            myviews.spec.setText(String.format(getString(R.string.orderview_listview_spec),spec));
            myviews.price.setText(String.format(getString(R.string.orderview_listview_price),price));
            myviews.number.setText(String.format(getString(R.string.orderview_listview_number),num));
            return convertView;
        }
    }
}
