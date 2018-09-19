package com.hpp.utils;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/1/5 0005.
 */
public class SqlInjection {
    /// <summary>
    /// 验证是否存在注入代码(条件语句）
    /// </summary>
    /// <param name="inputData"></param>
    public static Matcher HasInjectionData(String inputData)
    {
        //里面定义恶意字符集合
        //验证inputData是否包含恶意集合
        Pattern pat = Pattern.compile(GetRegexString());
        return pat.matcher(inputData.toLowerCase());
    }

    /// <summary>
    /// 获取正则表达式
    /// </summary>
    /// <returns></returns>
    private static String GetRegexString()
    {
        //构造SQL的注入关键字符
        String[] strBadChar =
                {
                        //"select\\s",
                        //"from\\s",
                        "insert\\s",
                        "delete\\s",
                        "update\\s",
                        "drop\\s",
                        "truncate\\s",
                        "exec\\s",
                        "count\\(",
                        "declare\\s",
                        "asc\\(",
                        "mid\\(",
                        "char\\(",
                        "net user",
                        "xp_cmdshell",
                        "/add\\s",
                        "exec master.dbo.xp_cmdshell",
                        "net localgroup administrators"
                };

        //构造正则表达式
        String str_Regex = ".*(";
        for (int i = 0; i < strBadChar.length - 1; i++)
        {
            str_Regex += strBadChar[i] + "|";
        }
        str_Regex += strBadChar[strBadChar.length - 1] + ").*";

        return str_Regex;
    }
}
