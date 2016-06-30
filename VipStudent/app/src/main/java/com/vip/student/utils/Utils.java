package com.vip.student.utils;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.vip.student.MainActivity;
import com.vip.student.R;
import com.vip.student.base.ActionBarActivity;
import com.vip.student.base.App;
import com.vip.student.network.BaseResponseBean;
import com.vip.student.persistobject.UserInfo;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static void toast(Context context, String str) {
        //Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        ToastUtils.showToast(context, str, Toast.LENGTH_SHORT);
    }


    public static int getWindowWidth(Activity cxt) {
        int width;
        DisplayMetrics metrics = cxt.getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        return width;
    }

    public static long getLongByTimeStr(String begin) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SS");
        String origin = "00:00:00.00";
        Date parse = format.parse(begin);
        return parse.getTime() - format.parse(origin).getTime();
    }

    public static String getEquation(int finalNum, int delta) {
        String equation;
        int abs = Math.abs(delta);
        if (delta >= 0) {
            equation = String.format("%d+%d=%d", finalNum - delta, abs, finalNum);
        } else {
            equation = String.format("%d-%d=%d", finalNum - delta, abs, finalNum);
        }
        return equation;
    }

    public static Uri getCacheUri(String path, String url) {
        Uri uri = Uri.parse(url);
        uri = Uri.parse("cache:" + path + ":" + uri.toString());
        return uri;
    }

    public static String getStrByRawId(Context ctx, int id) throws UnsupportedEncodingException {
        InputStream is = ctx.getResources().openRawResource(id);
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "gbk"));
        String line;
        StringBuilder sb = new StringBuilder();
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void notifyMsg(Context cxt, Class<?> toClz, int titleId, int msgId, int notifyId) {
        notifyMsg(cxt, toClz, cxt.getString(titleId), null, cxt.getString(msgId), notifyId);
    }

    public static String getTodayDayStr() {
        String dateStr;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dateStr = sdf.format(new Date());
        return dateStr;
    }

    public static Ringtone getDefaultRingtone(Context ctx, int type) {

        return RingtoneManager.getRingtone(ctx,
                RingtoneManager.getActualDefaultRingtoneUri(ctx, type));

    }

    public static Uri getDefaultRingtoneUri(Context ctx, int type) {
        return RingtoneManager.getActualDefaultRingtoneUri(ctx, type);
    }

    public static boolean isEmpty(Activity activity, String str, String prompt) {
        if (str.isEmpty()) {
            toast(activity, prompt);
            return true;
        }
        return false;
    }

    public static void closeQuietly(Closeable closeable) {
        try {
            closeable.close();
        } catch (Exception e) {
        }
    }

    /*public static String getWifiMac(Context cxt) {
        WifiManager wm = (WifiManager) cxt.getSystemService(Context.WIFI_SERVICE);
        return wm.getConnectionInfo().getMacAddress();
    }*/

    public static String quote(String str) {
        return "'" + str + "'";
    }

    public static String formatString(Context cxt, int id, Object... args) {
        return String.format(cxt.getString(id), args);
    }

    public static void notifyMsg(Context context, Class<?> clz, String title, String ticker, String msg, int notifyId) {
        int icon = context.getApplicationInfo().icon;
        PendingIntent pend = PendingIntent.getActivity(context, 0,
                new Intent(context, clz), 0);
        Notification.Builder builder = new Notification.Builder(context);
        if (ticker == null) {
            ticker = msg;
        }
        builder.setContentIntent(pend)
                .setSmallIcon(icon)
                .setWhen(System.currentTimeMillis())
                .setTicker(ticker)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true);
        NotificationManager man = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        man.notify(notifyId, builder.getNotification());
    }

    public static void sleep(int partMilli) {
        try {
            Thread.sleep(partMilli);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void setLayoutTopMargin(View view, int topMargin) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)
                view.getLayoutParams();
        lp.topMargin = topMargin;
        view.setLayoutParams(lp);
    }

    public static List<?> getCopyList(List<?> ls) {
        List<?> l = new ArrayList(ls);
        return l;
    }

    public static void fixAsyncTaskBug() {
        // android bug
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                return null;
            }
        }.execute();
    }

    public static void openUrl(Context context, String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }

    public static Bitmap getCopyBitmap(Bitmap original) {
        Bitmap copy = Bitmap.createBitmap(original.getWidth(),
                original.getHeight(), original.getConfig());
        Canvas copiedCanvas = new Canvas(copy);
        copiedCanvas.drawBitmap(original, 0f, 0f, null);
        return copy;
    }

    public static Bitmap getEmptyBitmap(int w, int h) {
        return Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    }

    public static void intentShare(Context context, String title, String shareContent) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.utils_share));
        intent.putExtra(Intent.EXTRA_TEXT, shareContent);
        intent.putExtra(Intent.EXTRA_TITLE, title);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.utils_please_choose)));
    }

    public static void toast(int id) {
        toast(App.ctx, id);
    }

    public static void toast(String s) {
        toast(App.ctx, s);
    }

    public static void toast(String s, String exceptionMsg) {
        toast(s);
    }

    public static void toast(int resId, String exceptionMsg) {
        String s = App.ctx.getString(resId);
        toast(s, exceptionMsg);
    }

    public static void toast(Context cxt, int id) {
        ToastUtils.showToast(cxt, id, Toast.LENGTH_SHORT);
        //Toast.makeText(cxt, id, Toast.LENGTH_SHORT).show();
    }

    public static void toastLong(Context cxt, int id) {
        ToastUtils.showToast(cxt, id, Toast.LENGTH_LONG);
        //Toast.makeText(cxt, id, Toast.LENGTH_LONG).show();
    }

    public static void toastLong(String s) {
        ToastUtils.showToast(App.ctx, s, Toast.LENGTH_LONG);
        //Toast.makeText(App.ctx, s, Toast.LENGTH_LONG).show();
    }

    //这个仅判断正整数，如果需要判断正负整数，正则表达式相应修改为 ^-?[0-9]+
    //如果要判断全部数字，正则表达式需要修改为 -?[0-9]+.?[0-9]+
    public static boolean isNumeric(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static ProgressDialog showHorizontalDialog(Activity activity) {
        //activity = modifyDialogContext(activity);
        ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(true);
        if (activity.isFinishing() == false) {
            dialog.show();
        }
        return dialog;
    }

    public static int currentSecs() {
        int l;
        l = (int) (new Date().getTime() / 1000);
        return l;
    }

    public static String md5(String string) {
        byte[] hash = null;
        try {
            hash = string.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh,UTF-8 should be supported?", e);
        }
        return computeMD5(hash);
    }

    public static String computeMD5(byte[] input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input, 0, input.length);
            byte[] md5bytes = md.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < md5bytes.length; i++) {
                String hex = Integer.toHexString(0xff & md5bytes[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean doubleEqual(double a, double b) {
        return Math.abs(a - b) < 1E-8;
    }

    public static String getFullFormateTimeStr(long time) {
        Date date = new Date(time);
        final SimpleDateFormat sFullDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = sFullDateFormat.format(date);
        return timeStr;
    }

    public static long getFullFormateTimeLon(String time) {
        try {
            final SimpleDateFormat sFullDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date timeStr = sFullDateFormat.parse(time);
            return timeStr.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getDateFormateStr(long time) {
        Date date = new Date(time);
        final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String timeStr = sDateFormat.format(date);
        return timeStr;
    }

    /***
     * 解析服务器时间，服务器是GMT+08时区
     * @param serverTimeStr
     * @return 返回 时间戳
     */
    public static long parseServerTime(String serverTimeStr) {
        if (TextUtils.isEmpty(serverTimeStr)) {
            return 0;
        }
        final String FORMAT = "yyyy-MM-dd HH:mm:ss";
        Date date = DateUtil.parseDate(serverTimeStr,FORMAT,TimeZone.getTimeZone("GMT+08"));
        if (date == null) {
            return 0;
        }
        return date.getTime();
    }


    /***
     * 将时间戳装换为服务器时间字符串
     * 服务器是GMT+08时区
     * @param time
     * @return
     */
    public static String formatToServerTimeStr(long time){
        if(time>0){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08"));
            Date date = new Date(time);
            return dateFormat.format(date);
        }else{
            return null;
        }
    }

    public static final SimpleDateFormat sDateFormatYear = new SimpleDateFormat("yyyy");

    public static String getDateFormateStrYear(long time) {
        Date date = new Date(time);
        String timeStr = sDateFormatYear.format(date);
        return timeStr;
    }

    public static final SimpleDateFormat sDateFormatMouth = new SimpleDateFormat("MMM", new Locale("English"));

    public static String getDateFormateStrMouth(long time) {
        Date date = new Date(time);
        String timeStr = sDateFormatMouth.format(date);
        return timeStr;
    }

    public static final SimpleDateFormat sDateFormatDay = new SimpleDateFormat("dd");

    public static String getDateFormateStrDay(long time) {
        Date date = new Date(time);
        String timeStr = sDateFormatDay.format(date);
        return timeStr;
    }

    public static final SimpleDateFormat sDateFormatWeek = new SimpleDateFormat("E", new Locale("English"));

    public static String getDateFormateStrWeek(long time) {
        Date date = new Date(time);
        String timeStr = sDateFormatWeek.format(date);
        return timeStr;
    }

    /**
     * 解压缩一个文件
     *
     * @param zipFile 要解压的压缩文件
     * @param folderPath 解压缩的目标目录
     * @throws IOException 当解压缩过程出错时抛出
     */

    /**
     * 解压一个压缩文档 到指定位置
     *
     * @param zipFileString 压缩包的名字
     * @param outPathString 指定的路径
     * @throws IOException
     * @throws Exception
     */
    public static void UnZipFolder(String zipFileString, String outPathString) throws IOException {
        PathUtils.checkAndMkdirs(outPathString);

        java.util.zip.ZipInputStream inZip = new java.util.zip.ZipInputStream(new java.io.FileInputStream(zipFileString));
        java.util.zip.ZipEntry zipEntry;
        String szName = "";

        while ((zipEntry = inZip.getNextEntry()) != null) {
            szName = zipEntry.getName();
            if (zipEntry.isDirectory()) {
                szName = szName.substring(0, szName.length() - 1);
                java.io.File folder = new java.io.File(outPathString + java.io.File.separator + szName);
                folder.mkdirs();
            } else {

                java.io.File file = new java.io.File(outPathString + java.io.File.separator + szName);
                file.createNewFile();
                // get the output stream of the file
                java.io.FileOutputStream out = new java.io.FileOutputStream(file);
                int len;
                byte[] buffer = new byte[1024];
                // read (len) bytes into buffer
                while ((len = inZip.read(buffer)) != -1) {
                    // write (len) byte from buffer at the position 0
                    out.write(buffer, 0, len);
                    out.flush();
                }
                out.close();
            }
        }

        inZip.close();

    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 把字节数组保存为一个文件
     */
    public static File getFileFromBytes(byte[] b, String outputFile) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = new File(outputFile);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }

    public static void saveBitmap(Bitmap bmp, String savePath) {
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(savePath);
            bmp.compress(CompressFormat.PNG, 40, stream);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideSoftInputFromWindow(View view) {
        InputMethodManager imm = (InputMethodManager) App.ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 下载并安装app
     *
     * @param url
     */
    public static void installApp(String url) {
        final DownloadManager systemService = (DownloadManager) App.ctx.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "upgrade.apk");
        systemService.enqueue(request);
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        final BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

                DownloadManager.Query myDownloadQuery = new DownloadManager.Query();
                myDownloadQuery.setFilterById(reference);

                Cursor myDownload = systemService.query(myDownloadQuery);
                if (myDownload.moveToFirst()) {
                    int fileUriIdx = myDownload.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);

                    String fileUri = myDownload.getString(fileUriIdx);

                    Intent ViewInstallIntent = new Intent(Intent.ACTION_VIEW);
                    ViewInstallIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ViewInstallIntent.setDataAndType(Uri.parse(fileUri), "application/vnd.android.package-archive");
                    context.startActivity(ViewInstallIntent);
                }
                myDownload.close();

                App.ctx.unregisterReceiver(this);
            }
        };
        App.ctx.registerReceiver(receiver, filter);
    }


    /***
     * 装换Json
     *
     * @param json
     * @param beanClass
     * @return 如果转换出错 返回 null
     */
    public static <T extends BaseResponseBean> T parseJson(String json, Class<T> beanClass) {
        Gson gson = new Gson();
        T bean = null;
        try {
            bean = gson.fromJson(json, beanClass);
        } catch (JsonSyntaxException exp) {
            Log.e("Utils", "fromJson error : " + exp.getMessage());
        }
        return bean;
    }

    /**
     * 将Json String 转换为JsonObject 如果Json格式错误或Code!=0 返回null
     *
     * @param json
     * @param beanClass
     * @param <T>
     * @return 转换正确且Code==0返回 beanClass,否则返回null,并Toast 错误信息
     */
    public static <T extends BaseResponseBean> T parseJsonTostError(String json, Class<T> beanClass) {
        T bean = parseJson(json, beanClass);
        if (bean == null) {
            Utils.toast(R.string.json_syntax_error);
        } else {
            if (bean.getCode() == 0) {
                return bean;
            }else if (bean.getCode() == 3) {
                Utils.toast(R.string.utils_token_timeout);
                UserInfo.logout();
                Intent homePageIntent = new Intent(App.ctx, MainActivity.class);
                homePageIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                App.ctx.startActivity(homePageIntent);
            } else {
                Utils.toast(bean.getMessage());
            }
        }
        return null;
    }

    public static boolean checkMobilePhoneNumber(String phoneNumber) {
        String telRegex = "[1]\\d{10}";
        if (TextUtils.isEmpty(phoneNumber)) return false;
        else return phoneNumber.matches(telRegex);
    }

    private static Pattern postCodePattern = Pattern.compile("^\\d{6}$");

    public static boolean checkPostCodeNumber(String postcodeNumber) {
        return postCodePattern.matcher(postcodeNumber).find();
    }


    /**
     * 设置 显示fragment
     *
     * @param activity
     * @param containerViewId
     * @param fragment
     */
    public static void ShowFragment(ActionBarActivity activity, int containerViewId, Fragment fragment) {
        ArrayList<Fragment> fragments = null;

        Intent intent = activity.getIntent();
        Log.e(activity.getClass().getSimpleName(), activity.getLocalClassName());
        Serializable serializableExtra = intent.getSerializableExtra(activity.getLocalClassName());
        if (serializableExtra == null) {
            fragments = new ArrayList<Fragment>();
            intent.putExtra(activity.getLocalClassName(), fragments);
        } else {
            fragments = (ArrayList<Fragment>) serializableExtra;
        }


        android.support.v4.app.FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        if (!fragments.contains(fragment)) {
            fragmentTransaction.add(containerViewId, fragment);
            fragments.add(fragment);
        }
        for (Fragment f : fragments) {
            if (f == fragment) {
                fragmentTransaction.show(f);
            } else {
                fragmentTransaction.hide(f);
            }
        }
        fragmentTransaction.commit();
    }
    /**
     * 得到两日期相差几个月
     *
     * @return
     */
    /*public static long getMonth(Date startDate, Date end) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        long monthday;
        try {
            Date startDate1 = startDate;
            //开始时间与今天相比较
            Date endDate1 = end;

            Calendar starCal = Calendar.getInstance();
            starCal.setTime(startDate1);

            int sYear = starCal.get(Calendar.YEAR);
            int sMonth = starCal.get(Calendar.MONTH);
            int sDay = starCal.get(Calendar.DATE);

            Calendar endCal = Calendar.getInstance();
            endCal.setTime(endDate1);
            int eYear = endCal.get(Calendar.YEAR);
            int eMonth = endCal.get(Calendar.MONTH);
            int eDay = endCal.get(Calendar.DATE);

            monthday = ((eYear - sYear) * 12 + (eMonth - sMonth));

            if (sDay < eDay) {
                monthday = monthday + 1;
            }
            return monthday;
        } catch (Exception e) {
//			logger.debug("获取相差月数失败");
            monthday = 0;
        }
        return monthday == 0 ? 1 : monthday;
    }*/

    public static int getMonth(Date startDate, Date endDate) {
        int monthNum=1;
        Calendar calStart=Calendar.getInstance();
        calStart.setTimeInMillis(startDate.getTime());
        calStart.set(Calendar.DAY_OF_MONTH,1);

        Calendar calEnd=Calendar.getInstance();
        calEnd.setTimeInMillis(endDate.getTime());

        //开始日期加一个月，直到与结束日期年月相同
        while(!(calStart.get(Calendar.YEAR)==calEnd.get(Calendar.YEAR) &&calStart.get(Calendar.MONTH)==calEnd.get(Calendar.MONTH))){
            monthNum++;
            calStart.add(Calendar.MONTH,1);
        }

        return monthNum;
    }

}
