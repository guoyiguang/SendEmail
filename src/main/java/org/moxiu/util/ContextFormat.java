package org.moxiu.util;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ContextFormat {
    private static final Logger logger = LoggerFactory
            .getLogger(ContextFormat.class);
    public static String styleTable = "font-family:Arial;font-size:85%;border-left:1px solid #C7C7C7;border-top:1px solid #C7C7C7;border-spacing:0px;";

    public static String contextFormat(Map<String, String> map, String head) {
        String context = "";
        context = context + "<table style='" + styleTable + "'>\n";

        String title = getTitle(head.split(","));

        context = context + title;
        context = context + "<tbody>\n";

        String body = getBody(map);
        context = context + body;

        context = context + "</tbody>\n";

        context = context + "</table>\n\n<br/>";

        return context;
    }

    // 设置title宽度
    public static String getTitle(String[] head) {
        String tableTitle = "";
        String styleTh = "border-right:1px solid #C7C7C7;border-bottom:1px solid #C7C7C7; padding:0; margin:0;color:#FFFFFF";
        String styleThead = "<thead>\n";
        String styleTr = "<tr bgcolor='#00BFFF'>\n";

        tableTitle = styleThead + styleTr;

        for (String str : head) {
            String title = str.split(";")[0].split("@")[0];
            tableTitle = tableTitle + "<th width='" + getwidth(title)
                    + "' style='" + styleTh + "'>" + title + "</th>\n";
        }
        tableTitle = tableTitle + "</tr>\n";
        tableTitle = tableTitle + "</thead>\n";
        return tableTitle;
    }

    public static String getBody(Map<String, String> map) {
        String body = "";
        String styleBody = "border-right:1px solid #C7C7C7;border-bottom:1px solid #C7C7C7; padding:0; margin:0;padding-right:3px";
        String styleBodyRed = "border-right:1px solid #FF0000;border-bottom:1px solid #FF0000; padding:0; margin:0;padding-right:3px;background:#FF0000";
        String styleTr = "<tr>";

        Object[] str = map.keySet().toArray();
        boolean flag = false;
        for (int i = 0; i < str.length; i++) {
            body = body + styleTr;

            String line = (String) map.get(str[i]);
            //针对行级处理，行标红
            flag = lineColor(line);


            String[] row = line.split("\\[;\\]");

            for (int k = 0; k < row.length; k++) {

                // 字段属性，field@100;p@center;compare@yesterday
                // 字段属性，field@100;p@center;compare@60;text@channel:a,b,c
                String[] row_filed_attribute = row[k].split("\\[:\\]")[0]
                        .split(";");
                String position = "right", compare = "no", value = "";
                for (String attributes : row_filed_attribute) {
                    String[] strs = attributes.split("@");
                    if ("p".equals(strs[0])) {
                        position = strs[1];
                    }
                    if ("c".equals(strs[0])) {
                        compare = strs[1];
                        if (3 == strs.length) {
                            value = strs[2];
                        }
                    }

                }

                // 字段值
                String row_filed = "";
                if (null != row[k] && 1 < row[k].split("\\[:\\]").length) {
                    row_filed = row[k].split("\\[:\\]")[1];
                    if ("before".equals(compare)) {// 与上一条数据进行比较
                        if (i != str.length - 1) {
                            String before_row_filed = "0";
                            try {
                                before_row_filed = ((String) map
                                        .get(str[i + 1])).split("\\[;\\]")[k]
                                        .split("\\[:\\]")[1];
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            row_filed = fieldColor(row_filed, before_row_filed);
                        } else {
                            row_filed = new DecimalFormat(",###").format(
                                    Integer.valueOf(row_filed.trim()))
                                    .toString();
                        }
                    } else if ("percentage".equals(compare)) {// 与百分比数据进行比较
                        row_filed = precentageColor(row_filed, value);
                    } else {
                        try {
                            if(row_filed.trim().startsWith("0")){
                                row_filed = row_filed.trim();
                            }else{
                                row_filed = new DecimalFormat(",###").format(
                                        Integer.valueOf(row_filed.trim()))
                                        .toString();
                            }
                        } catch (Exception e) {
                            // logger.info("字段不能转化为整数类型，无法格式化");
                            row_filed = row_filed.trim();
                        }

                    }
                }
                if(flag){
                    body = body + "<td align='" + position + "' style='"
                            + styleBodyRed + "'>" + row_filed.trim() + "</td>\n";
                }else{
                    body = body + "<td align='" + position + "' style='"
                            + styleBody + "'>" + row_filed.trim() + "</td>\n";
                }

            }

            body = body + "</tr>\n";
        }
        return body;
    }
    //行级标红
    public static boolean lineColor(String line) {
        boolean flag = false;
        if (line.contains("text@")) {
            String text = line.substring(line.indexOf("/(") + 2,
                    line.indexOf("/)"));
            String target = line.substring(line.indexOf("text@") + 5,
                    line.indexOf(":/"));
            String target_String = line.substring(line.indexOf(target+"[:]"));
            String target_s= target_String.substring((target+"[:]").length(), target_String.indexOf("[;]"));
            List<String> hightlight = Arrays.asList(text.split("\\|"));
            if(hightlight.contains(target_s)){
                flag = true;
            }
        }
        return flag;
    }

    // 百分数比较
    public static String precentageColor(String source, String target) {
        try {
            if (source.endsWith("%")) {
                if (Double.valueOf(source.substring(0, source.length() - 1)) < Double
                        .valueOf(target)) {
                    return "<span style='color:rgb(255, 0, 0)'>" + source
                            + "</span>";
                }
            }
        } catch (Exception e) {
            return source;
        }
        return source;
    }

    // 环比颜色
    public static String fieldColor(String source, String target) {
        try {
            if (null != source && "" != source && null != target
                    && "" != target) {
                if (Integer.valueOf(source.trim()) > Integer.valueOf(target
                        .trim())) {
                    // 绿色
                    return "<span style='color:rgb(146, 208, 80)'>"
                            + new DecimalFormat(",###").format(
                            Integer.valueOf(source.trim())).toString()
                            + "</span>";
                } else {
                    // 红色
                    return "<span style='color:rgb(255, 0, 0)'>"
                            + new DecimalFormat(",###").format(
                            Integer.valueOf(source.trim())).toString()
                            + "</span>";
                }
            }
        } catch (Exception e) {
            return source.trim();
        }
        return source.trim();
    }
    /**
     * 设置宽度
     */
    public static int getwidth(String str) {
        int i = 0;
        try {
            String[] size = str.split(";")[0].split("@");
            if (1 < size.length) {
                i = Integer.valueOf(size[1].trim()).intValue();
            } else {
                char[] cc = str.trim().toCharArray();
                for (char c : cc) {
                    if (8 < Integer.toBinaryString(c).length())
                        i += 20;
                    else {
                        i += 10;
                    }
                }
                if (80 > i)
                    i = 80;
            }
        } catch (Exception e) {
            logger.error("设置宽度参数错误:[" + str + "]");
        }
        return i;
    }

}