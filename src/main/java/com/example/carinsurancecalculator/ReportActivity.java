package com.example.carinsurancecalculator;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

public class ReportActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 1;

    private double sumInsured;
    private int engineSize;
    private String coverageType;
    private String selectedNCD;
    private boolean hasWindscreenCover;
    private double formattedPremium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // Retrieve data from Intent extras
        boolean isWestMalaysia = getIntent().getBooleanExtra("IS_WEST_MALAYSIA", false);
        sumInsured = getIntent().getDoubleExtra("SUM_INSURED", 0.0);
        engineSize = getIntent().getIntExtra("ENGINE_SIZE", 0);
        coverageType = getIntent().getStringExtra("COVERAGE_TYPE");
        selectedNCD = getIntent().getStringExtra("NCD");
        hasWindscreenCover = getIntent().getBooleanExtra("HAS_WINDSCREEN_COVER", false);
        double premium = getIntent().getDoubleExtra("PAYMENT_AMOUNT", 0.0);

        // Format the premium to display with two decimal points
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        formattedPremium = Double.parseDouble(decimalFormat.format(premium));

        // Display insurance details and price
        TextView textViewSumInsuredReport = findViewById(R.id.textViewSumInsuredReport);
        TextView textViewEngineSizeReport = findViewById(R.id.textViewEngineSizeReport);
        TextView textViewCoverageTypeReport = findViewById(R.id.textViewCoverageTypeReport);
        TextView textViewNCDReport = findViewById(R.id.textViewNCDReport);
        TextView textViewWindScreenReport = findViewById(R.id.textViewWindScreenReport);
        TextView textViewTotalPayment = findViewById(R.id.textViewTotalPayment);

        textViewSumInsuredReport.setText("Sum Insured: RM" + sumInsured);
        textViewEngineSizeReport.setText("Engine Size: " + engineSize + "cc");
        textViewCoverageTypeReport.setText("Coverage Type: " + coverageType);
        textViewNCDReport.setText("NCD: " + selectedNCD );
        textViewWindScreenReport.setText("Windscreen Cover: " + (hasWindscreenCover ? "Yes" : "No"));
        textViewTotalPayment.setText("Total Payment: RM " + formattedPremium);

        // Confirm Payment button in the report page
        Button confirmPaymentButtonInReport = findViewById(R.id.buttonConfirmPaymentInReport);
        confirmPaymentButtonInReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmationDialog();
            }
        });

        // Add a new button for PDF generation
        Button generatePDFButton = findViewById(R.id.buttonGeneratePDF);
        generatePDFButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check for WRITE_EXTERNAL_STORAGE permission
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    // Permission already granted, generate PDF
                    generateAndSavePDF();
                } else {
                    // Request WRITE_EXTERNAL_STORAGE permission
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
                }
            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_WRITE_EXTERNAL_STORAGE) {
            // Check if the permission was granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, generate PDF
                generateAndSavePDF();
            } else {
                // Permission denied, show a message or take appropriate action
                Toast.makeText(this, "Permission denied. Cannot generate PDF.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Payment");
        builder.setMessage("Are you sure you want to proceed with the payment?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // User clicked Confirm, show loading message
                showLoadingMessage();

                // Simulate payment success after a delay
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showPaymentSuccessPage();
                    }
                }, 3000); // 3 seconds delay (adjust as needed)
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // User clicked Cancel, show payment fail message
                showPaymentFailMessage();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showLoadingMessage() {
        // Display a loading message, you can use a ProgressDialog or any other UI element
        // For simplicity, updating the existing successMessage TextView
        TextView successMessage = findViewById(R.id.successMessage);
        successMessage.setText("Processing payment...");
    }

    private void showPaymentSuccessPage() {
        // Display payment success page
        TextView successMessage = findViewById(R.id.successMessage);
        successMessage.setText("PAYMENT SUCCESSFUL!");
    }

    private void showPaymentFailMessage() {
        // Display payment fail message
        TextView successMessage = findViewById(R.id.successMessage);
        successMessage.setText("PAYMENT FAILED");
    }

    private void generateAndSavePDF() {
        // Create a new PdfDocument
        PdfDocument pdfDocument = new PdfDocument();

        // Create a page
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        // Get the Canvas for rendering into the page
        Canvas canvas = page.getCanvas();

        // Add content to the page (customize as needed)
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(12f);
        canvas.drawText("Insurance Report", 10, 25, paint);
        canvas.drawText("Sum Insured: RM" + sumInsured, 10, 45, paint);
        canvas.drawText("Engine Size: " + engineSize + "cc", 10, 65, paint);
        canvas.drawText("Coverage Type: " + coverageType, 10, 85, paint);
        canvas.drawText("NCD: " + selectedNCD, 10, 105, paint);
        canvas.drawText("Windscreen Cover: " + (hasWindscreenCover ? "Yes" : "No"), 10, 125, paint);
        canvas.drawText("Total Payment: RM " + formattedPremium, 10, 145, paint);

        // Finish the page
        pdfDocument.finishPage(page);

        // Save the document
        String pdfFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/insurance_report.pdf";
        try {
            File file = new File(pdfFilePath);
            FileOutputStream fos = new FileOutputStream(file);
            pdfDocument.writeTo(fos);
            fos.close();

            // Display a success message
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("PDF Report");
            builder.setMessage("PDF Report generated and saved to Downloads folder");
            builder.setPositiveButton("OK", null); // You can handle the OK button if needed

            AlertDialog dialog = builder.create();
            dialog.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception (e.g., display an error message)
        } finally {
            // Close the document
            pdfDocument.close();
        }
    }
}
