package com.example.hopinnow.helperclasses;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.IntRange;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Viola Bai
 */
public class QRCodeHelper {
    private static QRCodeHelper qrCodeHelper = null;
    private int mMargin;
    private String mContent;
    private int mWidth, mHeight;


    /**
     * Constructor class for QRCodeHelper.
     */
    private QRCodeHelper(Context context) {
        mHeight = (int) (context.getResources().getDisplayMetrics().heightPixels / 2);
        mWidth = (int) (context.getResources().getDisplayMetrics().widthPixels);
    }


    /**
     * Sets a new instance of QRCodeHelper.
     *
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
     *
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
     *
     * @param margin for qrcode spaces.
     * @return the instance of QrCode helper class for to use remaining function in class.
     */
    public QRCodeHelper setMargin(@IntRange(from = 0) int margin) {
        mMargin = margin;
        return this;
    }


    /**
     * Generate the QR code.
     *
     * @return
     *      QR code image
     */
    public Bitmap generateQR() {
        Map<EncodeHintType, Object> hintsMap = new HashMap<>();
        hintsMap.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hintsMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
        hintsMap.put(EncodeHintType.MARGIN, mMargin);
        try {
            BitMatrix bitMatrix = new QRCodeWriter().encode(mContent, BarcodeFormat.QR_CODE, mWidth, mHeight, hintsMap);
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