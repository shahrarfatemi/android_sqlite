package com.example.database;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseHelper databaseHelper;
    private EditText nameEditText,ageEditText,deptEditText,idEditText;
    private Button insertButton,displayButton,updateButton,deleteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        nameEditText = (EditText) findViewById(R.id.nameText);
        ageEditText = (EditText) findViewById(R.id.ageText);
        deptEditText = (EditText) findViewById(R.id.deptText);
        idEditText = (EditText) findViewById(R.id.idText);
        insertButton = (Button) findViewById(R.id.insertButtonId);
        insertButton.setOnClickListener(this);
        displayButton = (Button) findViewById(R.id.displayButtonId);
        displayButton.setOnClickListener(this);
        updateButton = (Button) findViewById(R.id.updateButtonId);
        updateButton.setOnClickListener(this);
        deleteButton = (Button) findViewById(R.id.deleteButtonId);
        deleteButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String name = nameEditText.getText().toString();
        String dept = deptEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String id = idEditText.getText().toString();
        if(v.getId() == R.id.insertButtonId) {
            if (!(name.equals("") || dept.equals("") || age.equals(""))) {
                int _age = Integer.parseInt(age);
                if (databaseHelper.insertToDatabase(name, dept, _age) == -1) {
                    Toast.makeText(MainActivity.this, "not inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "INSERTED", Toast.LENGTH_SHORT).show();

                }
            }
        }
        else if(v.getId() == R.id.displayButtonId){
            Cursor cursor = databaseHelper.displayAll();
            if (cursor.getCount() == 0) {
//                    Toast.makeText(MainActivity.this,"Nothing to show",Toast.LENGTH_SHORT).show();
                showData("Results", "Nothing to show");
            } else {
                String results = "";
                while (cursor.moveToNext()) {
                    results += "Id : " + cursor.getString(0) + "\n";
                    results += "Name : " + cursor.getString(1) + "\n";
                    results += "Dept : " + cursor.getString(2) + "\n";
                    results += "Age : " + cursor.getString(3) + "\n";
                    results += "\n\n";
                }
                showData("Results", results);
            }
        }
        else if(v.getId() == R.id.updateButtonId){
            if (!(name.equals("") || dept.equals("") || age.equals("") || id.equals(""))) {
                int _id,_age;
                _id = Integer.parseInt(id);
                _age = Integer.parseInt(age);
                boolean isUpdated = databaseHelper.updateDatabase(_id,name,dept,_age);
                if(isUpdated){
                    Toast.makeText(MainActivity.this, "updated", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "not updated", Toast.LENGTH_SHORT).show();
                }
            }
        }

        else if(v.getId() == R.id.deleteButtonId) {
            if (!(id.equals(""))){
                int _id;
                _id = Integer.parseInt(id);
                int isDeleted = databaseHelper.deleteDatabase(_id);
                if (isDeleted == 0) {
                    Toast.makeText(MainActivity.this, "not deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "deleted", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void showData(String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(true);
        builder.show();
    }
}
