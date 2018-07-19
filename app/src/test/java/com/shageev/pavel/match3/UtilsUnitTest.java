package com.shageev.pavel.match3;

import org.junit.Test;
import static org.junit.Assert.*;

public class UtilsUnitTest {
    @Test
    public void formatCounter(){
        // if counter < 10 000 - show as is
        // 10000 > 10.0K
        // 10250 > 10.2K
        // 100 000 > 100K
        // 103 500 > 103K
        // 1 000 000 > 1.00M
        // 1 015 000 > 1.01M
        // 10 000 000 > 10.0M
        // 100 000 000 > 100M
        // 1 000 000 000 > 1.00B
        // 1 000 000 000 000 > 1.00T
        // 1 000 000 000 000 000 > 1.00q
        // 1 000 000 000 000 000 000 > 1.00Q
        // 9 223 372 036 854 775 808 > 9.22Q

        // counter < 0 returns "Error"
        assertEquals("Error", Utils.formatCounter(-1));
        // if counter < 10 000 - show as is
        assertEquals("0", Utils.formatCounter(0));
        assertEquals("123", Utils.formatCounter(123));
        // 10000 > 10.0K
        assertEquals("10.0K", Utils.formatCounter(10000));
        // 10250 > 10.2K
        assertEquals("10.2K", Utils.formatCounter(10250));
        // 100 000 > 100K
        assertEquals("100K", Utils.formatCounter(100000));
        // 103 500 > 103K
        assertEquals("103K", Utils.formatCounter(103500));
        // 1 000 000 > 1.00M
        assertEquals("1.00M", Utils.formatCounter(1000000));
        // 1 015 000 > 1.01M
        assertEquals("1.01M", Utils.formatCounter(1015000));
        // 10 000 000 > 10.0M
        assertEquals("10.0M", Utils.formatCounter(10000000));
        // 100 000 000 > 100M
        assertEquals("100M", Utils.formatCounter(100000000));
        // 1 000 000 000 > 1.00B
        assertEquals("1.00B", Utils.formatCounter(1000000000L));
        assertEquals("1.01B", Utils.formatCounter(1015000000L));
        assertEquals("10.0B", Utils.formatCounter(10000000000L));
        assertEquals("100B", Utils.formatCounter(100000000000L));
        // 1 000 000 000 000 > 1.00T
        assertEquals("1.00T", Utils.formatCounter(1000000000000L));
        assertEquals("1.01T", Utils.formatCounter(1015000000000L));
        assertEquals("10.0T", Utils.formatCounter(10000000000000L));
        assertEquals("100T", Utils.formatCounter(100000000000000L));
        // 1 000 000 000 000 000 > 1.00q
        assertEquals("1.00q", Utils.formatCounter(1000000000000000L));
        assertEquals("1.01q", Utils.formatCounter(1015000000000000L));
        assertEquals("10.0q", Utils.formatCounter(10000000000000000L));
        assertEquals("100q", Utils.formatCounter(100000000000000000L));
        // 1 000 000 000 000 000 000 > 1.00Q
        assertEquals("1.00Q", Utils.formatCounter(1000000000000000000L));
        assertEquals("1.01Q", Utils.formatCounter(1015000000000000000L));
        // 9 223 372 036 854 775 808 > 9.22Q
        assertEquals("9.22Q", Utils.formatCounter(9223372036854775807L));
        assertEquals("Error", Utils.formatCounter(9223372036854775807L+1));
    }
}
