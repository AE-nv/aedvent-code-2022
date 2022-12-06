package com.selwin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BrokenCommSystemAddonTest {
    @Test
    void parseElvenGibberishForStartPoint_giveString1() {
        int result = BrokenCommSystemAddon.parseElvenGibberishForStartPoint("mjqjpqmgbljsphdztnvjfqwrcgsmlb",4);
        assertEquals(result, 7);
    }
    @Test
    void parseElvenGibberishForStartPoint_giveString2() {
        int result = BrokenCommSystemAddon.parseElvenGibberishForStartPoint("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw",4);
        assertEquals(result, 11);
    }

    @Test
    void parseElvenGibberishForStartPoint_giveString2AndLongDisitnct() {
        int result = BrokenCommSystemAddon.parseElvenGibberishForStartPoint("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw",14);
        assertEquals(result, 26);
    }
}
