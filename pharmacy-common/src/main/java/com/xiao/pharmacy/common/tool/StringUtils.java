package com.xiao.pharmacy.common.tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 字符串工具类
 *
 * @author guer
 */
public final class StringUtils {

    /**
     * 空字符串
     */
    public static final String EMPTY = "";

    private StringUtils() {

    }

    /**
     * 判断是否为空白字符串
     *
     * @param cs
     *         字符
     * @return 是否为空白字符串
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * * 判断一个对象是否为空
     *
     * @param object Object
     * @return true：为空 false：非空
     */
    public static boolean isNull(Object object)
    {
        return object == null;
    }

    /**
     * 判断是否不为空白字符串
     *
     * @param cs
     *         字符
     * @return 是否不为空白字符串
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * 判断字符串数组中是否有空白字符串
     *
     * @param css
     *         字符串数组
     * @return 是否有空白字符串
     */
    public static boolean isAnyBlank(final CharSequence... css) {
        return css != null && css.length > 0 && Arrays.stream(css).anyMatch(StringUtils::isBlank);
    }

    /**
     * 去除字符串两端空白，如果为空字符串则返回null
     *
     * @param str
     *         字符串
     * @return 去除两端空白后的字符串
     */
    public static String trimToNull(final String str) {
        if (str == null) {
            return null;
        }
        String ts = str.trim();
        return ts.length() == 0 ? null : ts;
    }

    /**
     * 将对象数组字符串化后通过连字符进行拼接
     *
     * @param collection
     *         待拼接的对象数组
     * @param separator
     *         连字符
     * @return 拼接后的字符串
     */
    public static String join(Collection<?> collection, String separator) {
        if (collection == null || collection.isEmpty()) {
            return EMPTY;
        }

        return join(collection.toArray(), separator);
    }

    /**
     * 将对象数组字符串化后通过连字符进行拼接
     *
     * @param array
     *         待拼接的对象数组
     * @param separator
     *         连字符
     * @return 拼接后的字符串
     */
    public static String join(Object[] array, String separator) {
        if (array == null) {
            return null;
        }

        return join(array, separator, 0, array.length);
    }

    /**
     * 将对象数组字符串化后通过连字符进行拼接
     *
     * @param array
     *         待拼接的对象数组
     * @param separator
     *         连字符
     * @param startIndex
     *         开始位置
     * @param endIndex
     *         结束位置
     * @return 拼接后的字符串
     */
    public static String join(Object[] array, String separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }

        int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return EMPTY;
        }
        if (separator == null) {
            separator = EMPTY;
        }

        StringBuilder buf = new StringBuilder(noOfItems * 16);

        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    /**
     *将字符串根据split分割转换为list
     * split 不传默认为","
     * @param split 分割的字符串
     * @return
     */
    public static List<String>  spiltStrToList(String str,String split) {
        if(isNotBlank(str)){
            if(isNotBlank(split)){
                return new ArrayList(Arrays.asList(str.split(split)));
            }
            return spiltStrToList(str);
        }
        return null;
    }
    /**
     *将字符串分割','转换为list
     * @param str 待转化的字符串
     * @return
     */
    public static List<String> spiltStrToList(String str) {
        if(isNotBlank(str)){
            String unit=",";
            return new ArrayList(Arrays.asList(str.split(unit)));
        }
        return null;
    }

    /**
     *
     * @param list 要转成string的集合
     * @param connector 连接符，传空默认为","
     * @return
     */
    public static  String listToStr(List list,String connector){
        if(isBlank(connector)){
            connector=",";
        }
        String str ="";
        for (Object object:list){
            str=str+connector+object;
        }
        return str.substring(1,str.length());
    }

    public static  String listToStr(List list){
        if(null==list||list.size()==0){
            return "";
        }
        return listToStr(list,null);
    }

    /**
     * 字符串去重
     * @param str
     * @return ,分隔的字符串
     */
    public static String  deduplicate(String str){
        StringBuffer sb=new StringBuffer();
        String[] split = str.split(",");
        if(isNotBlank(str)){
            for (int i=0;i<split.length;i++){
                 if(sb.indexOf(split[i])==-1){
                     sb.append(split[i]+",");
                 }
            }
            return sb.substring(0,sb.length()-1);
        }
        return null;
    }

    /**
     * * 判断一个对象是否非空
     *
     * @param object Object
     * @return true：非空 false：空
     */
    public static boolean isNotNull(Object object)
    {
        return !isNull(object);
    }
}
