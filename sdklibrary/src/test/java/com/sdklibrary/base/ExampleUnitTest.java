package com.sdklibrary.base;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void asdf() throws Exception {
        String jsonp="callback( {\"client_id\":\"1106716984\",\"openid\":\"33D82CB501B55ADFFA65DC6BF738A35E\",\"unionid\":\"UID_9F078E3D6F34183C094F4B0707912BC3\"} );";

        System.out.println(jsonp.length());
        System.out.println(jsonp.indexOf("(") + 1);
        System.out.println(jsonp.lastIndexOf(")"));
        String json = jsonp.substring(jsonp.indexOf("(") + 1, jsonp.lastIndexOf(")"));
        System.out.println(json);
    }
    @Test
    public void asdsfdf() throws Exception {
        String jsonp="callbacd";
        String json = jsonp.substring(2, 4);
        System.out.println(json);
    }
}