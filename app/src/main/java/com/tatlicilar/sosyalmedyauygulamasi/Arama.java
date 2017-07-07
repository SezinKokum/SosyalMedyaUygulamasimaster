package com.tatlicilar.sosyalmedyauygulamasi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class Arama extends AppCompatActivity {

//    Intent intent;
//    private EditText textwatcher;
//    private ListView lv;
//    private ArrayList<String> array_sort = new ArrayList<String>();
//    int textlength = 0;
//    String listview_array[]={"","","","","",""};
//    Kisi k;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arama);
//        lv = (ListView) findViewById(R.id.listView);
//        intent = getIntent();
//        k = (Kisi) intent.getSerializableExtra("kisi");
//       k.getArkadaslarim();
//        textwatcher = (EditText) findViewById(R.id.editText);
//        lv.setAdapter(new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, listview_array));
//        int x= lv.getHeaderViewsCount ();
//        textwatcher.addTextChangedListener(new TextWatcher()
//        {
//            public void afterTextChanged(Editable s)
//            {
//                // Abstract Method of TextWatcher Interface.
//            }
//            public void beforeTextChanged(CharSequence s,
//                                          int start, int count, int after)
//            {
//                // Abstract Method of TextWatcher Interface.
//            }
//            public void onTextChanged(CharSequence s,
//                                      int start, int before, int count)
//            {
//                textlength = textwatcher.getText().length();
//                array_sort.clear();
//                for (int i = 0; i < listview_array.length; i++)
//                {
//                    if (textlength <= listview_array[i].length())
//                    {
//                        if (textwatcher.getText().toString().equalsIgnoreCase(
//                                (String)
//                                        listview_array[i].subSequence(0,
//                                                textlength)))
//                        {
//                            array_sort.add(listview_array[i]);
//                        }
//                    }
//                }
//                lv.setAdapter(new ArrayAdapter<String>
//                        (Arama.this,
//                                android.R.layout.simple_list_item_1, array_sort));
//            }
//        });
   }
}
