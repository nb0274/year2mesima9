package com.example.year2mesima9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSION = 1;
    TextView display;
    EditText input;
    Intent in;
    private final String FILENAME = "internalTextFile.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = (TextView) findViewById(R.id.displayTV);
        input = (EditText) findViewById(R.id.inputET);

        if(checkAllPremissions()) {
            String internalFileText = readFile();
            display.setText(internalFileText);
        }
    }

    /**
     * checks if the storage is available
     * @return boolean(true or false)
     */
    public boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }


    /**
     * checks if premission is enabled
     * @return boolean(true or false)
     */
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * this function is called to request a premission to aces file
     */
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
    }

    /**
     * this function is called to handle the descision of the user to the premission acessing the file
     * @param permissions The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *     which is either {@link android.content.pm.PackageManager#PERMISSION_GRANTED}
     *     or {@link android.content.pm.PackageManager#PERMISSION_DENIED}. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission to access external storage granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Permission to access external storage NOT granted", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * This func writes to the file the text we got as a parameter
     * @param strwr the given text to the file
     */
    public void writeToFile(String strwr) {
        try {
            File externalDir = Environment.getExternalStorageDirectory();
            File file = new File(externalDir, FILENAME);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            writer.write(strwr);
            writer.close();
        }
        catch (IOException e) {
            Toast.makeText(this, "Error, couldn't write file", Toast.LENGTH_LONG).show();
            Log.e("MainActivity", "Error, couldn't write file");
        }
    }

    /**
     * This func reads the content from the file
     * @return a string that represents the content of the file
     */
    public String readFile() {
        String text = "";
        try {
            File externalDir = Environment.getExternalStorageDirectory();
            File file = new File(externalDir, FILENAME);
            file.getParentFile().mkdirs();
            FileReader reader = new FileReader(file);
            BufferedReader bR = new BufferedReader(reader);
            StringBuilder sB = new StringBuilder();
            String line = bR.readLine();
            while (line != null) {
                sB.append(line+'\n');
                line = bR.readLine();
            }
            bR.close();
            reader.close();
            text = sB.toString();
        }
        catch (IOException e) {
            Toast.makeText(this, "Error, couldn't read file", Toast.LENGTH_LONG).show();
            Log.e("MainActivity", "Error, couldn't read file");
        }
        return text;
    }

    /**
     * This func saves to the file the input the user has entered
     * @param view the "save" button
     */
    public void save(View view) {
        if(checkAllPremissions()) {
            String text = readFile();
            text += input.getText().toString();
            writeToFile(text);
            display.setText(text);
        }
    }

    /**
     * This function reset the file and the display
     * @param view the "reset" button
     */
    public void reset(View view) {
        if(checkAllPremissions()) {
            writeToFile("");
            display.setText("");
        }
    }

    /**
     * This method saves the data and exits the app
     * @param view the "exit" button
     */
    public void exit(View view) {
        if(checkAllPremissions()) {
            save(view);
            finish();
        }
    }

    /**
     * This method checks if there is an overall premission to access the storage
     * @return true if there is an overall premission, false otherwise
     */
    public boolean checkAllPremissions(){
        if ((checkPermission()) == false) {
            requestPermission();
        }
        if(((checkPermission()) == false) || (isExternalStorageAvailable()) == false)
        {
            Toast.makeText(this, "Couldn't access storage", Toast.LENGTH_SHORT).show();
        }
        return (isExternalStorageAvailable()) && (checkPermission());
    }

    /**
     * This method is called when the user clicks on the menu, it inflates the menu onto the screen
     * @param menu the menu to move between activities
     * @return true or false
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activities,menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * This method is called when the credits option on the menu is called and it goes to the credits activity
     * @param item the option from the menu that presents the activity the user wants to move to
     * @return true or false
     */
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.Credits){
            in = new Intent(this, Credits.class);
            startActivity(in);
        }

        return super.onOptionsItemSelected(item);
    }
}