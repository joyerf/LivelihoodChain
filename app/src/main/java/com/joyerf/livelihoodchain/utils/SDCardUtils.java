package com.joyerf.livelihoodchain.utils;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.text.DecimalFormat;


public class SDCardUtils {
    public final static String SDCARD_PATH = "/gehua/";
    public static String sExternalPath;
    public static String sExternalDataPath;
    public static String mSdcardRootPath;
    public static String getSDcardRootPath() {
        if (TextUtils.isEmpty(mSdcardRootPath)) {
//            String carRomPath = null;
//            try{
//                carRomPath = WeCarInfoManager.getInstance().getSdCardPath();
//            }catch(Throwable e) {}
//            if (!TextUtils.isEmpty(carRomPath)) {
//                File file = new File(carRomPath);
//                if (file.canWrite() && file.canRead()) {
//                    mSdcardRootPath =  carRomPath+"/";
//                }
//            }
            if (TextUtils.isEmpty(mSdcardRootPath)) {
                mSdcardRootPath =  Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
            }
        }
        return mSdcardRootPath;
    }

    public static long getAvailableBytes(){
        StatFs statFs;
        try {
            statFs = new StatFs(getSDcardRootPath());
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
                return statFs.getAvailableBytes();
            } else {
                return (long) statFs.getAvailableBlocks() * statFs.getBlockSize();
            }
        }catch(Exception e) {
        }
        return 0L;
    }

    public synchronized  static String getSDcardPath() {
        File file = new File(getSDcardRootPath(),SDCARD_PATH);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Logger.d("getSDcardPath mkdirs failed");
            }
        }
        return file.getAbsolutePath()+"/";
    }

    private static final double MIN_FREE_SPACE = 10;
    public static final int SDCARD_NORMAL = 0;
    public static final int SDCARD_FULL = 1;
    public static final int SDCARD_ERROR = 2;
    public static final int SDCARD_NOTFOUND = 3;

    public static final int MIN_FREE_SIZE = 1024 * 1024 * 15;

    // SD卡可用空间小于此值时,提示用户
    public static final int MIN_CACHE_FREE_SIZE = 1024 * 1024 * 20;


    /**
     * 检查SD状态
     *
     * @param sizeInByte 需要检查的所需空间大小
     * @param bBuffer 是否预留buffer( 15M )
     * @return SD卡正常时，检查是否有sizeInByte的可用空间，否则返回SD卡状态
     */
    public static int handleSdcardError(long sizeInByte, boolean bBuffer) {
        int state = SDCARD_ERROR;
        try {
            StatFs sfs = getSdcardSize();
            long freeSize = getAvailableBytes(sfs);
            if (freeSize < ((bBuffer ? MIN_FREE_SIZE : 0) + sizeInByte)) {
                return SDCARD_FULL;
            } else {
                state = SDCARD_NORMAL;
            }
        } catch (Throwable e) {
        }
        return state;
    }

    @SuppressWarnings("deprecation")
    private static long getAvailableBytes(StatFs statFs){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            return statFs.getAvailableBytes();
        }else{
            return (long) statFs.getAvailableBlocks() * statFs.getBlockSize();
        }
    }

    public static int getSdcardState() {
        // Log.e(TAG, "getSdcardState");
        String status = Environment.getExternalStorageState();

        if (status == null || Environment.MEDIA_BAD_REMOVAL.equals(status)) {
            // Log.e(TAG, "MEDIA_BAD_REMOVAL");
            return SDCARD_ERROR;
        } else if (Environment.MEDIA_CHECKING.equals(status)) {
            // Log.e(TAG, "MEDIA_CHECKING");
        } else if (Environment.MEDIA_MOUNTED.equals(status)) {
            // OK
            // Windows Media Sync
            // Motorola Phone Portal
            // Log.e(TAG, "MEDIA_MOUNTED");
            return SDCARD_NORMAL;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(status)) {
            // Log.e(TAG, "MEDIA_MOUNTED_READ_ONLY");
            return SDCARD_ERROR;
        } else if (Environment.MEDIA_NOFS.equals(status)) {
            // Log.e(TAG, "MEDIA_NOFS");
            return SDCARD_ERROR;
        } else if (Environment.MEDIA_REMOVED.equals(status)) {
            // Remove Sdcard
            // Log.e(TAG, "MEDIA_REMOVED");
            return SDCARD_NOTFOUND;
        } else if (Environment.MEDIA_SHARED.equals(status)) {
            // USB Mass Storage
            // Log.e(TAG, "MEDIA_SHARED");
            return SDCARD_NOTFOUND;
        } else if (Environment.MEDIA_UNMOUNTABLE.equals(status)) {
            // Log.e(TAG, "MEDIA_UNMOUNTABLE");
            return SDCARD_ERROR;
        } else if (Environment.MEDIA_UNMOUNTED.equals(status)) {
            // Log.e(TAG, "MEDIA_UNMOUNTED");
            return SDCARD_NOTFOUND;
        }
        return SDCARD_NORMAL;
    }

    private static StatFs getSdcardSize() {
        StatFs stat;
        try {
            stat = new StatFs(getSDcardRootPath());
        }catch(Exception e) {
            stat = null;
        }
        return stat;
    }

//    public static long getSdcardSpace() {
//        Long diskSpace = (long) 0;
//        int state = SDCARD_NORMAL;
//        try {
//            state = getSdcardState();
//            if (state == SDCARD_NORMAL) {
//                StatFs sfs = getSdcardSize();
//                long freeSize = sfs.getBlockSize();
//                long freeBlock = sfs.getFreeBlocks();
//                diskSpace = freeSize * freeBlock;
//            }
//        } catch (Exception e) {
//        }
//        return diskSpace;
//    }

    /**
     * 单位换算
     *
     * @param size 单位为B
     * @param isInteger 是否返回取整的单位
     * @return 转换后的单位
     */
    private static DecimalFormat fileIntegerFormat = new DecimalFormat("#0");
    private static DecimalFormat fileDecimalFormat = new DecimalFormat("#.##");
    public static String formatFileSize(long size, boolean isInteger) {
        DecimalFormat df = isInteger ? fileIntegerFormat : fileDecimalFormat;
        String fileSizeString = "0B";
        if (size < 1024 && size > 0) {
            fileSizeString = df.format((double) size) + "B";
        } else if (size < 1024 * 1024) {
            fileSizeString = df.format((double) size / 1024) + "K";
        } else if (size < 1024 * 1024 * 1024) {
            fileSizeString = df.format((double) size / (1024 * 1024)) + "M";
        } else {
            fileSizeString = df.format((double) size / (1024 * 1024 * 1024)) + "G";
        }
        return fileSizeString;
    }
}
