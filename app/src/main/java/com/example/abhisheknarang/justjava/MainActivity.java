package com.example.abhisheknarang.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;
    String priceMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.editText);
        String name = nameField.getText().toString();
        CheckBox whippedCream = (CheckBox) findViewById(R.id.hascheckBox);
        boolean hasWhippedCream = whippedCream.isChecked();
        CheckBox chocolate = (CheckBox) findViewById(R.id.hascheckBox1);
        boolean hasChocolate = chocolate.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = orderSummary(name, price, hasWhippedCream, hasChocolate);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order For "+name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;
        if (addWhippedCream) {
            basePrice += 1;
        }
        if (addChocolate) {
            basePrice += 2;
        }
        return quantity * basePrice;

    }

    private String orderSummary(String name, int price, boolean hasWhippedCream, boolean hasChocolate) {
        if (quantity >= 1 && quantity <= 99) {
            priceMessage = "Name:" + name;
            priceMessage += "\nAdd whipped cream?" + hasWhippedCream;
            priceMessage += "\nAdd chocolate?" + hasChocolate;
            priceMessage += "\nQuantity = " + quantity;
            priceMessage += "\nValue: $ " + price;
        }
        return (priceMessage);
    }

    public void increment(View view) {
        if (quantity < 99) {
            quantity += 1;
            displayQuantity(quantity);
        }
        else {
            Toast.makeText(MainActivity.this,"Sorry, you can order max 99 coffees at a time", Toast.LENGTH_LONG).show();
        }
    }
    public void decrement(View view) {
        if (quantity>1) {
            quantity -= 1;
            displayQuantity(quantity);
        }
        else {
            Toast.makeText(MainActivity.this,"Sorry, you cannot order less than 1 coffee", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
            quantityTextView.setText("" + number);

    }

    }
