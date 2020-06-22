package com.yuukidach.ucount.draw;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yuukidach.ucount.GlobalVariables;
import com.yuukidach.ucount.R;
import com.yuukidach.ucount.model.IOItem;

import org.litepal.crud.DataSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CanvasActivity extends AppCompatActivity {

//随机生成颜色RGB值
    public static String getRandColor() {
        String R, G, B;
        Random random = new Random();
        R = Integer.toHexString(random.nextInt(256)).toUpperCase();
        G = Integer.toHexString(random.nextInt(256)).toUpperCase();
        B = Integer.toHexString(random.nextInt(256)).toUpperCase();

        R = R.length() == 1 ? "0" + R : R;
        G = G.length() == 1 ? "0" + G : G;
        B = B.length() == 1 ? "0" + B : B;

        return "#" + R + G + B;
    }


    private MyDataView myDataView;
    private HashMap<String, Float> dataDegee = new HashMap<>();
    private HashMap<String, String> dataColor = new HashMap<>();
    private LinearLayout mLny;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_draw_table);
        uodatePieChart();
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void uodatePieChart(){
        mLny = findViewById(R.id.lnyOut);
        //获取总金额
        float total = 0;

        List<IOItem> albumList = DataSupport.where("bookid = ? and type = ?",  String.valueOf(GlobalVariables.getmBookId()), "1").find(IOItem.class);

        for (IOItem ioItem : albumList) {
            System.out.println(ioItem.getMoney());
            total += ioItem.getMoney();
        }

        if(total>0){

            dataColor.clear();
            dataDegee.clear();

            for (IOItem ioItem : albumList) {
                System.out.println(ioItem.getMoney());
                if(dataDegee.get(ioItem.getName()) != null){
                    dataDegee.put(ioItem.getName(), getDegree((float) ioItem.getMoney(), total) + dataDegee.get(ioItem.getName()));
                }else{
                    dataDegee.put(ioItem.getName(), getDegree((float) ioItem.getMoney(), total));
                    dataColor.put(ioItem.getName(), getRandColor());
                }
            }

            System.out.println(dataDegee);
            if(myDataView != null){
                mLny.removeView(myDataView);
            }
            myDataView = new MyDataView(CanvasActivity.this, dataDegee, dataColor);
            mLny.addView(myDataView);
            Log.i("test draw", "remove view ; add view");
            Toast.makeText(this,"哇，你的"+getMaxinumType()+"收入居然这么多", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(this,"账本无数据，请先录入数据！", Toast.LENGTH_SHORT).show();
        }
    }

    //按比例转换为所占圆形的角度
    private float getDegree(float number, float total){
        return  number/total * 360;
    }

//获得花费最多的那个项目
    private String getMaxinumType(){
        String type="";
        float money = Float.MIN_VALUE;
        for(String key : dataDegee.keySet()){
            if(dataDegee.get(key) > money){
                type = key;
                money = dataDegee.get(key);
            }
        }
        return type;
    }
}
