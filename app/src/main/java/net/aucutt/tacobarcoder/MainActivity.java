package net.aucutt.tacobarcoder;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;


public class MainActivity extends AppCompatActivity {

    private static final int  smallerDimension = 1024;
    private Bitmap mBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generateQRCode(BarcodeFormat.CODE_128);
    }

    private void generateQRCode(BarcodeFormat format){

        EditText text = (EditText) findViewById(R.id.textField);
        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(text.getText().toString(),
                format,
                smallerDimension);
        Bitmap bmp;
        try {
            bmp =  qrCodeEncoder.encodeAsBitmap();
            ImageView view = (ImageView)findViewById(R.id.imageView);
            view.setImageBitmap(bmp);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }

    public void doGenerate(View v){
        generateQRCode(generateFormat());
    }

    public void doGenerateDumbDown(View v) {
        EditText text = (EditText) findViewById(R.id.textField);

        Bitmap bmp;
        try {
            bmp = BarcodeEncoder.encodeAsBitmap(text.getText().toString());
            ImageView view = (ImageView)findViewById(R.id.imageView2);
            view.setImageBitmap(bmp);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }

    public void doGenerateFromHeightAndWidth(View v ){
        EditText text = (EditText) findViewById(R.id.textField);

        Bitmap bmp;
        try {
            if(mBitmap != null ){
                ImageView oldView = (ImageView)findViewById(R.id.imageView);
                oldView.setImageBitmap(mBitmap);
            }
            EditText width = (EditText) findViewById(R.id.editText);
            EditText height = (EditText) findViewById(R.id.editText2);
            mBitmap = BarcodeDimensionalEncoder.encodeAsBitmap(text.getText().toString(),
                    Integer.valueOf(width.getText().toString()),
                    Integer.valueOf(height.getText().toString()));
            ImageView view = (ImageView)findViewById(R.id.imageView2);

            view.setImageBitmap(mBitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        doGenerate(null);

    }

    private BarcodeFormat generateFormat() {
        RadioButton other = (RadioButton) findViewById(R.id.format39Button);
        if (other.isChecked()) {
            return BarcodeFormat.CODE_39;
        } else if (((RadioButton) findViewById(R.id.format128Button)).isChecked()) {
            return BarcodeFormat.CODE_128;
        } else {
            return BarcodeFormat.PDF_417;
        }

    }
}
