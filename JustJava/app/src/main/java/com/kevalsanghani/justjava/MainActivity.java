/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.kevalsanghani.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 **/
public class MainActivity extends AppCompatActivity {
    int quantity=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //NAME FIELD
        EditText nameField = (EditText)findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        //WHIPPED CREAM
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        /*
        Log.v("MainActivity", "Has Whipped Cream? -> " + hasWhippedCream);
        */
        //CHOCO FLAKES!
        CheckBox chocoFlakesCheckBox = (CheckBox) findViewById(R.id.choco_flakes_checkbox);
        boolean hasChocoFlakes = chocoFlakesCheckBox.isChecked();
        //CALCULATING PRICE
        int price = calculatePrice(hasChocoFlakes, hasWhippedCream);
        //
        String priceMessage = orderSummary(name, price, hasWhippedCream, hasChocoFlakes);

        Intent mail_intent = new Intent(Intent.ACTION_SENDTO);
        mail_intent.setData(Uri.parse("mailto:"));
        mail_intent.putExtra(Intent.EXTRA_EMAIL, "order@justjava.com");
        mail_intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava Order Summary for " + name);
        mail_intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if(mail_intent.resolveActivity(getPackageManager()) != null){
            startActivity(mail_intent);
        }

        displayMessage(priceMessage);

    }
    public void inc(View view){
        if(quantity==20){
            Toast.makeText(this, "You can't order more than 20 coffees at a time!", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity+1;
        displayQuantity(quantity);
    }
    public void dec(View view){
        if(quantity==1){
            Toast.makeText(this, "You can't order less than 1 coffee at a time!", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }
    private int calculatePrice(boolean hasChocoFlakes, boolean hasWhippedCream){
        int price = 0;
        if(quantity > 0) {
            if (hasWhippedCream) {
                price += quantity * 10;
            }
            if (hasChocoFlakes) {
                price += quantity * 25;
            }
            price = price + (quantity * 50);
        }
        /*
        *
        * */

        return price;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberCoffee) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberCoffee);
    }

    private String orderSummary(String name, int price, boolean hasWhippedCream, boolean hasChocoFlakes){
        String priceMessage = "Name: "+ name;
        priceMessage = priceMessage + "\nQuantity: " + quantity;
        priceMessage +=  "\nWhipped Cream added: " + hasWhippedCream;
        priceMessage +=  "\nChoco Flakes added: " + hasChocoFlakes;
        priceMessage = priceMessage + "\nTotal: Rs." + price;
        /* To display price after each press on Order button(For Debbuging)
        Log.v("MainActivity", "The price is " + price);
        */
        priceMessage = priceMessage + "\nThank You!";
        return priceMessage;
    }

    private void displayMessage(String priceMessage){
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(priceMessage);
    }
}