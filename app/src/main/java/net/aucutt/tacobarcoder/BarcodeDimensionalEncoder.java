package net.aucutt.tacobarcoder;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.EnumMap;
import java.util.Map;

public class BarcodeDimensionalEncoder {

    private static final BarcodeFormat FORMAT = BarcodeFormat.PDF_417;

    public static Bitmap encodeAsBitmap(String data, int pwidth, int pheight) throws WriterException {

        Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
      //  hints.put(EncodeHintType.PDF417_COMPACT, true);  //clips the right hand side, to make it look like ours.
    //    Dimensions demi = new Dimensions(pwidth,pwidth, pheight, pheight);
      //  Dimensions dimensions = (Dimensions) hints.get(EncodeHintType.PDF417_DIMENSIONS);
       // hints.put(EncodeHintType.PDF417_DIMENSIONS,demi);
      hints.put(EncodeHintType.MARGIN,0);
//        encoder.setDimensions(dimensions.getMaxCols(),
//                dimensions.getMinCols(),
//                dimensions.getMaxRows(),
//                dimensions.getMinRows());

        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result = writer.encode(data, FORMAT, 750, 225, hints);
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        // All are 0, or black, by default
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }
        System.out.println(" the Darrell width and height " + width + " vs  " + height);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return Bitmap.createScaledBitmap(bitmap, pwidth, pheight, false);
        //return bitmap;
    }

}
