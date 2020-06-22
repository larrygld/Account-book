package com.yuukidach.ucount;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yuukidach.ucount.model.BookItem;
import com.yuukidach.ucount.model.IOItem;

import org.litepal.crud.DataSupport;

import java.util.List;

public class ShowInTable extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_in_table);

        System.out.println("------");

        BookItem bookItem = DataSupport.find(BookItem.class, GlobalVariables.getmBookId());
        System.out.println(bookItem.getName());

        System.out.println(bookItem.getIoItemList().isEmpty());
        System.out.println(bookItem.getSumAll());


        List<IOItem> albumList = DataSupport.where("bookid = ?",  String.valueOf(GlobalVariables.getmBookId())).find(IOItem.class);

        for (IOItem ioItem : albumList) {
            System.out.println(ioItem.getMoney());
        }

    }
}
