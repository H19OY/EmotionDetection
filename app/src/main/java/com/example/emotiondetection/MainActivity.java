package com.example.emotiondetection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    EditText edtName;
    EditText pos;
    Button button;
    Spinner spnr;
    Calendar calendar ;
    SimpleDateFormat date;
    ListView listview;



    DatabaseReference dbr;


    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbr= FirebaseDatabase.getInstance().getReference("Emotions");

        listview=(ListView) findViewById(R.id.listview);



        button=(Button) findViewById(R.id.btn_val);
        edtName=(EditText) findViewById(R.id.txt_val);
        pos=(EditText) findViewById(R.id.pos_val);

        spnr=(Spinner) findViewById(R.id.spinner_val);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddNames();

            }
        });

        ArrayList<String>list= new ArrayList<>();
        ArrayAdapter adapter= new ArrayAdapter<String>(this ,R.layout.list_item,list);
        listview.setAdapter(adapter);

        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot ds : snapshot.getChildren())
                {
                    Names names = ds.getValue(Names.class);

                    String txt= names.getName() + " > " + names.getDate()+" > " +names.getPosition()+" > "+names.getType();

                    list.add(txt);




                }

                adapter.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void AddNames()
    {

        String name=edtName.getText().toString().trim();
        String type= spnr.getSelectedItem().toString();
        String position=pos.getText().toString().trim();

        if(!TextUtils.isEmpty(name) & !TextUtils.isEmpty(position))
        {
            String id= dbr.push().getKey();

            calendar=Calendar.getInstance();
            date=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String timenow=date.format(calendar.getTime());
            Names names=new Names(id,name,type,position,timenow);

            dbr.child(id).setValue(names);

            Toast.makeText(this, "Submission Recorded !", Toast.LENGTH_LONG).show();


        }
        else
        {

            Toast.makeText(this, "Fill the Information ! ", Toast.LENGTH_LONG).show();
        }


    }


}