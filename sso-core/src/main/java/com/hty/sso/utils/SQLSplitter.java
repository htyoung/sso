package com.hty.sso.utils;

import java.util.*;

/**
 * @author tongyang.hty
 * 正确的分隔多段SQL
 */
public class SQLSplitter {
    public static String[] split(String sqls) {
        LinkedList<String> sqlList = new LinkedList<>();
        Stack<Integer> posStack = new Stack<>();
        Stack<Character> charStack = new Stack<>();
        char[] chars = sqls.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int dis = -1;
            switch (chars[i]) {
                //单引号
                case '\'':
                    dis = charStack.search('\'');
                    if (dis > -1) {
                        batchPop(charStack, dis);
                        batchPop(posStack, dis);
                    } else {
                        charStack.push(chars[i]);
                        posStack.push(i);
                    }
                    break;
                //双引号
                case '"':
                    dis = charStack.search('"');
                    if (dis > -1) {
                        batchPop(charStack, dis);
                        batchPop(posStack, dis);
                    } else {
                        charStack.push(chars[i]);
                        posStack.push(i);
                    }
                    break;
                //遇到注释(--)
                case '-':
                    if (i + 1 < chars.length && chars[i + 1] == '-') {
                        charStack.push(chars[i]);
                        posStack.push(i);
                        i++;
                    }
                    break;
                //遇到注释（--）结束符\n
                case '\n':
                    dis = charStack.search('-');
                    if (dis > -1) {
                        batchPop(charStack, dis);
                        batchPop(posStack, dis);
                    }
                    break;
                //遇到转义字符\跳过转义字符后面的一个字符
                case '\\':
                    i++;
                    break;
                //遇到分号;直接入栈
                case ';':
                    charStack.push(chars[i]);
                    posStack.push(i);
                    break;
                default:
            }
        }
        //如果最后一个sql不带分号;
        if (posStack.empty() || posStack.peek() != sqls.length() - 1) {
            posStack.push(sqls.length());
        }
        //遍历栈，按pos分隔sql
        int startPos = 0;
        int endPos = 0;
        for (int i = 0; i < posStack.size(); i++) {
            endPos = posStack.get(i);
            sqlList.add(sqls.substring(startPos, endPos));
            startPos = endPos + 1;
        }
        String[] result = new String[sqlList.size()];
        return sqlList.toArray(result);
    }

    private static void batchPop(Stack stack, int n) {
        for (int i = n; i > 0; i--) {
            stack.pop();
        }
    }

    /*private static boolean isSqlLegal(Stack<Character> statck) {
        for (int i = 0; i < statck.size(); i++) {
            if (statck.get(i) != ';') {
                return false;
            }
        }
        return true;
    }*/
}
