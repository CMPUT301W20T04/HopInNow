package com.example.hopinnow.helperclasses;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

//CodingInfinite post by Ahsen Saeed
//https://codinginfinite.com/qrcode-generator-and-reader-android-example/
/**
 * Edited by Tianyu Bai
 * This class is responsible for creating QR codes that contain total payment of completed rides.
 */
public class QRCodeHelper {
    private static QRCodeHelper qrCodeHelper = null;
    private String mContent;
    private int mWidth, mHeight;

    //TODO CHECKING BITMAP QR?

    //

    /**
     * Constructor class for QRCodeHelper.
     */
    private QRCodeHelper(Context context) {
        mHeight = context.getResources().getDisplayMetrics().heightPixels / 2;
        mWidth = context.getResources().getDisplayMetrics().widthPixels;
    }


    /**
     * Sets a new instance of QRCodeHelper.
     * @return
     *      QR code instance
     */
    public static QRCodeHelper newInstance(Context context) {
        if (qrCodeHelper == null) {
            qrCodeHelper = new QRCodeHelper(context);
        }
        return qrCodeHelper;
    }


    /**
     * Simply setting the encrypted to qrcode.
     * @param content
     *      encrypted content to be stored in the qr code
     * @return
     *      instance of QrCode helper class
     */
    public QRCodeHelper setContent(String content) {
        mContent = content;
        return this;
    }


    /**
     * Simply setting the margin for qrcode.
     * @return the instance of QrCode helper class for to use remaining function in class.
     */
    public QRCodeHelper setMargin() {
        return this;
    }


    /**
     * Generate the QR code.
     * @return
     *      QR code image
     */
    public Bitmap generateQR() {
        try {
            BitMatrix bitMatrix = new QRCodeWriter().encode(mContent, BarcodeFormat.QR_CODE, mWidth, mHeight,null);
            int[] pixels = new int[mWidth * mHeight];
            for (int i = 0; i < mHeight; i++) {
                for (int j = 0; j < mWidth; j++) {
                    if (bitMatrix.get(j, i)) {
                        pixels[i * mWidth + j] = 0xFFFFFFFF;
                    } else {
                        pixels[i * mWidth + j] = 0x282946;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, mWidth, mHeight, Bitmap.Config.ARGB_8888);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
}