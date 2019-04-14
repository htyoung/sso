package com.hty.sso.utils;

import org.junit.Test;

public class SQLSplitterTest {
    @Test
    public void testSplit() {
        String sql = "--sdafdsafs;\nselect 1-2";
        String[] result = SQLSplitter.split(sql);
        for (String str : result) {
            System.out.println(str);
            System.out.println("------------------------------------------");
        }
    }
}
