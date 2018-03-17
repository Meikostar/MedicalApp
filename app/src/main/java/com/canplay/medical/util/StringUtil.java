package com.canplay.medical.util;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Browser;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.view.View;


import com.canplay.medical.R;
import com.canplay.medical.view.SpannableClickable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * 功能描述: 字符串处理工具类
 * 作者:chenwei
 * 时间:2016/8/9
 * 版本:1.0
 ***/
public class StringUtil {
    public  static String errorMsg ="";
    /**
     * Returns true if the string is null or 0-length.
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(CharSequence str){
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }

    /**
     * Returns true if the string is not null and more then 0-length.

     * @return false if str is null or zero length
     */
    public static SpannableStringBuilder formatUrlString(String contentStr){

        SpannableStringBuilder sp;
        if(!TextUtils.isEmpty(contentStr)){

            sp = new SpannableStringBuilder(contentStr);
            try {
                //处理url匹配
                Pattern urlPattern = Pattern.compile("(http|https|ftp|svn)://([a-zA-Z0-9]+[/?.?])" +
                        "+[a-zA-Z0-9]*\\??([a-zA-Z0-9]*=[a-zA-Z0-9]*&?)*");
                Matcher urlMatcher = urlPattern.matcher(contentStr);

                while (urlMatcher.find()) {
                    final String url = urlMatcher.group();
                    if(!TextUtils.isEmpty(url)){
                        sp.setSpan(new SpannableClickable(){
                            @Override
                            public void onClick(View widget) {
                                Uri uri = Uri.parse(url);
                                Context context = widget.getContext();
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.getPackageName());
                                context.startActivity(intent);
                            }
                        }, urlMatcher.start(), urlMatcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }

                //处理电话匹配
                Pattern phonePattern = Pattern.compile("[1][34578][0-9]{9}");
                Matcher phoneMatcher = phonePattern.matcher(contentStr);
                while (phoneMatcher.find()) {
                    final String phone = phoneMatcher.group();
                    if(!TextUtils.isEmpty(phone)){
                        sp.setSpan(new SpannableClickable(){
                            @Override
                            public void onClick(View widget) {
                                Context context = widget.getContext();
                                //用intent启动拨打电话
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        }, phoneMatcher.start(), phoneMatcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            sp = new SpannableStringBuilder();
        }
        return sp;
    }
    public static boolean isNotEmpty(CharSequence str){
        if (str == null || str.length() == 0)
            return false;
        else
            return true;
    }
    //判断手机号
    public static boolean isMobileNum(String mobiles) {
//        Pattern p = Pattern.compile("^0?[18][34578]\\d{9}$");
        Pattern p = Pattern.compile("^1[3-5,7,8]{1}[0-9]{9}$");
        Matcher m = p.matcher(mobiles);
        System.out.println(m.matches() + "---");
        return m.matches();
    }

    //判断邮政编码
    public static boolean isZipNO(String zipString){
        String str = "^[1-9][0-9]{5}$";
        return Pattern.compile(str).matcher(zipString).matches();
    }

    // 判断一个字符串是否含有数字

    public static boolean hasDigit(String content) {

        boolean flag = false;

        Pattern p = Pattern.compile(".*\\d+.*");

        Matcher m = p.matcher(content);

        if (m.matches())

            flag = true;

        return flag;

    }
    /**
     * 设置密码的提示文字
     */
    public static SpannableString getHintText(Context context, String hint) {
        SpannableString span = new SpannableString(hint);
        span.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.txt_hint_color)),
                0, hint.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        span.setSpan(new AbsoluteSizeSpan(18, true),
                0, 5, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        span.setSpan(new AbsoluteSizeSpan(14, true),
                5, hint.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return span;
    }

    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {

        try {

            PackageManager manager = context.getPackageManager();

            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);

            String version = info.versionName;

            return  version;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 可以判断出“       ”
     * @param str
     * @return
     */
    public static boolean isEmptyIncludeSpace(CharSequence str){
        if(isEmpty(str)){
            return true;
        }else {
            str=str.toString().trim();
            if(isEmpty(str)){
                return true;
            }else {
                return false;
            }
        }
    }
    public static  long lastClickTime;
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 2000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * Returns true if a and b are equal, including if they are both null.
     * <p><i>Note: In platform versions 1.1 and earlier, this method only worked well if
     * both the arguments were instances of String.</i></p>
     * @param a first CharSequence to check
     * @param b second CharSequence to check
     * @return true if a and b are equal
     */
    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }
/*    *//**
     * 设置hint提示语
     * @param activity_binder_table
     * @return
     *//*
    public static SpannableStringBuilder getSpannable(String activity_binder_table, Context context, int start, int end){
        SpannableStringBuilder spannable=new SpannableStringBuilder(activity_binder_table);
        CharacterStyle span_1=new ForegroundColorSpan(context.getResources().getColor(R.color.color_red1));
        CharacterStyle span_2=new ForegroundColorSpan(context.getResources().getColor(R.color.color_primary_b3));
        spannable.setSpan(span_1, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(span_2, end, activity_binder_table.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }*/

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
	    /*
	    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
	    联通：130、131、132、152、155、156、185、186
	    电信：133、153、180、189、（1349卫通）
	    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
	    */

        String telRegex = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。

        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }
    public static boolean isLegalPwd(String pwd, String aginpwd){
        if(pwd.equals("") || pwd == null){
            errorMsg = "密码不能为空";
            return false;
        }
        if(pwd.length() <6)
        {
            errorMsg = "密码格式错误";
            return false;
        }
        if (!pwd.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$")) {
            errorMsg = "密码必须是数字和字母组合";
            return false;
        }
        if(!aginpwd.equals(pwd))
        {
            errorMsg = "密码不一致";
            return false;
        }
        return true;
    }
    public static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            return toHexString(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F' };
    private static String toHexString(byte[] b) {
        //String to  byte
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }

  public static String ChangeSumSplit(double sum){
      int i=0;
      String money="";
      String[] list=new String[5];
      long sums=(long)sum;


      while (sums>=1000){
            list[i]=sums%1000==0?"000":sums%1000+"";
            sums=sums/1000;

            i++;
        }
      for(int a=0;a<i;a++){

          money=money+","+list[i-a-1];
      }

          return sums+money;
  }

    public static String getStrMoney(double money){

        String total= String.format("%.2f",money);

        String pre=total.substring(0,total.length()-3);

        String end=total.substring(pre.length(),total.length());

        String total_coat="0.00";

        try {
            total_coat= StringUtil.ChangeSumSplit(Long.parseLong(pre))+end;
        }catch (Exception e){
        }

        return total_coat;
    }
    public static String ChangeNumber(double sum){
        int i=0;
        int s = (int) ((sum-(int)sum)/0.01);
        String substring;
        if(s==0){
            substring=".00";
        }else {
            String s1 = Double.toString(sum);
            substring = s1.substring(s1.indexOf("."));
            if(substring.length()==2){
                substring=substring+0;
            }else if(substring.length()>=3){
                substring = s1.substring(s1.indexOf("."),s1.indexOf(".")+3);
            }
        }


        String money="";
        String[] list=new String[5];
        long sums=(long)sum;


        while (sums>=1000){
            list[i]=sums%1000==0?"000":sums%1000+"";
            sums=sums/1000;

            i++;
        }
        for(int a=0;a<i;a++){

            money=money+","+list[i-a-1];
        }

        return sums+money+substring;
    }
    public static String ChangeSumSplit(long sum){
        int i=0;
        String money="";
        String[] list=new String[5];
        long sums=sum;


        while (sums>=1000){
            list[i]=sums%1000==0?"000":sums%1000+"";
            sums=sums/1000;

            i++;
        }
        for(int a=0;a<i;a++){

            money=money+","+list[i-a-1];
        }

        return sums+money;
    }





    public static String getShowName(String fullName){
        if (isAllChinese(fullName)){
            return getLastTwoChar(fullName);
        }else if (isAllEnglish(fullName)){
            return getWordFirstChar(fullName);
        }else {
            return getFirstChar(fullName);
        }
    }

    private static boolean isAllChinese(String name){
        if (TextUtil.isEmpty(name)){
            return false;
        }else {
            String regEx  =  "[ \u4E00-\u9FA5]+";
            Pattern p   =   Pattern.compile(regEx);
            Matcher m   =   p.matcher(name);
            return m.matches();
        }
    }

    private static boolean isAllEnglish(String name){
        if (TextUtil.isEmpty(name)){
            return false;
        }else {
            String regEx = "[a-zA-Z 0-9]+";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(name);
            return m.matches();
        }
    }

    private static String getLastTwoChar(String name){
        if (!TextUtils.isEmpty(name)){
            if (name.trim().length()>2){
                return name.trim().substring(name.trim().length()-2);
            }
            return name;
        }
        return "";
    }

    private static String getFirstChar(String name){
        if (name == null || name.trim().length() == 0){
            return "";
        }
        return name.trim().substring(0,1);
    }

    private static String getWordFirstChar(String name){
        String nameResult = "";
        if (name!=null){
            String[] allWord = name.split(" ");
            if (allWord!=null && allWord.length>0){
                for (int i=0;i<allWord.length;i++){
                    if (allWord[i]!=null && allWord[i].length()>0){
                        nameResult = nameResult + allWord[i].substring(0,1);
                        if (nameResult.length()>=2){
                            return nameResult;
                        }
                    }
                }
            }
        }
        return nameResult;
    }
    /**
     * 设置hint提示语
     * @param text
     * @return
     */
    public static SpannableStringBuilder getSpannable(String text, Context context, int end){
        SpannableStringBuilder spannable=new SpannableStringBuilder(text);
        CharacterStyle span_1=new ForegroundColorSpan(context.getResources().getColor(R.color.slow_black));
        CharacterStyle span_2=new ForegroundColorSpan(context.getResources().getColor(R.color.hint_red));

        spannable.setSpan(span_1, 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(span_2, end, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }
    public static SpannableStringBuilder getBuleSpannable(String text, Context context, int end){
        SpannableStringBuilder spannable=new SpannableStringBuilder(text);
        CharacterStyle span_1=new ForegroundColorSpan(context.getResources().getColor(R.color.color6));
        CharacterStyle span_2=new ForegroundColorSpan(context.getResources().getColor(R.color.txt_blue));

        spannable.setSpan(span_1, 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(span_2, end, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }
}
