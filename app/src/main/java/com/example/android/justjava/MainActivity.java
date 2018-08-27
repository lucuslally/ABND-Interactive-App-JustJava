/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Context;
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

import static com.example.android.justjava.R.layout.activity_main;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;
    boolean hasWhip = false;
    boolean hasChocolate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
    }

    /**
     * Practice calling methods
     */
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        TextView textView = new TextView(this);
//        textView.setText("Hello Beeb");
//        textView.setTextSize(24);
//        textView.setTextColor(Color.DKGRAY);
//
//        setContentView(textView);
//    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        // Find Name input and get value
        EditText nameField = findViewById(R.id.editText);
        String name = nameField.getText().toString();

        // calculate price
        int price = calculatePrice(quantity, 5,1,2);


        // display it and send email "mkhcreative@gmail.com"
        createOrderSummary(price, hasWhip, hasChocolate, name);
    }

    /**
     * Create summary of the order
     *
     * @param price value used previously for calculatePrice method
     * @param hasWhip
     * @return string summary
     */

    private String createOrderSummary(int price, boolean hasWhip, boolean hasChocolate, String name) {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, hasWhip);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, hasChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price, price);
        priceMessage += "\n" + getString(R.string.thank_you);
        composeEmail("lucuslally1@gmail.com","Just Java coffee order for " + name , priceMessage);
        return priceMessage;
    }

    /**
     * This method calculates the price based on the current quantity
     *
     * @param quantity is number of coffees ordered
     */
    private int calculatePrice(int quantity, int pricePerCup, int whipPrice, int chocoPrice) {
        int total = 0;
        if (hasWhip & hasChocolate){
            total = whipPrice + chocoPrice + (quantity * pricePerCup);
        } else if (hasWhip) {
            total = whipPrice + (quantity * pricePerCup);
        } else if (hasChocolate){
            total = chocoPrice + (quantity * pricePerCup);
        } else {
            total = quantity * pricePerCup;
        }
        return total;
    }

    /**
     * This method is called when the plus button is clicked
     **/
    public void incrementOrder(View view) {
        if (quantity < 100){
            quantity++;
            displayQuantity(quantity);
            Context context = getApplicationContext();
            return;
        } else {
            Context context = getApplicationContext();
            CharSequence text = "That's too many coffees of us!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    /**
     * This method is called when the minus button is clicked
     **/
    public void decrementOrder(View view) {
        if (quantity > 0){
            quantity--;
            displayQuantity(quantity);
            return;
        } else {
            Context context = getApplicationContext();
            CharSequence text = "Please enter valid number of coffees";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    public boolean checkForWhip (View view) {
        CheckBox whipCreamCheckBox = findViewById(R.id.whipcream_checkbox);
        hasWhip = whipCreamCheckBox.isChecked();
        //Log.v("Main Activity","The value of hasWHipCream = " + hasWhip);
        return hasWhip;
    }
    public boolean checkForChocolate (View view) {
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        hasChocolate = chocolateCheckBox.isChecked();
        //Log.v("Main Activity","The value of hasChocolate = " + hasChocolate);
        return hasChocolate;
    }
    public void composeEmail(String address, String subject, String emailBody) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, address);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(android.content.Intent.EXTRA_TEXT,emailBody);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}