package com.example.year2mesima9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Credits extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
    }

    /**
     * This method is called when the user clicks on the menu, it inflates the menu onto the screen
     * @param menu the menu to move between activities
     * @return true or false
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activities, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * This method is called when the main option on the menu is called and it goes back to the main activity
     * @param item item the option from the menu that presents the activity the user wants to move to
     * @return true or false
     */
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();

        if(id == R.id.Main){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}