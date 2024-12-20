package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TableDataPointTest {
    TableDataPoint tdp = new TableDataPoint();

    @Test
    public void testAddData() {
        DataPoint dp = new DataPoint();
        dp.addAttribute("key", "value");
        dp.addAttribute("key2", "value2");
        tdp.addData(dp);
        // Test le Contains
        tdp.addData(dp);
    }

    @Test
    public void testGetData() {
        DataPoint dp = new DataPoint();
        assertNull(tdp.getData(dp));
        dp.addAttribute("key", "value");
        dp.addAttribute("key2", "value2");
        DataPoint dp2 = new DataPoint();
        dp2.addAttribute("key", "value");
        tdp.addData(dp);
        tdp.addData(dp2);
        assertEquals(dp, tdp.getData(dp));
    }

    @Test
    public void testGetDataIndex() {
        DataPoint dp = new DataPoint();
        dp.addAttribute("key", "value");
        dp.addAttribute("key2", "value2");
        DataPoint dp2 = new DataPoint();
        dp2.addAttribute("key", "value");
        tdp.addData(dp);
        tdp.addData(dp2);
        assertEquals(dp, tdp.getData(0));
    }

    @Test
    public void testSetAttribute() {
        DataPoint dp = new DataPoint();
        dp.addAttribute("key", "value");
        dp.addAttribute("key2", "value2");
        tdp.addData(dp);
        tdp.setAttribute(dp, "key", "value3");
        assertEquals("value3", dp.getAttribute("key"));
    }

    @Test
    public void testGetDataList() {
        DataPoint dp = new DataPoint();
        dp.addAttribute("key", "value");
        dp.addAttribute("key2", "value2");
        DataPoint dp2 = new DataPoint();
        dp2.addAttribute("key", "value");
        tdp.addData(dp);
        tdp.addData(dp2);
        assertEquals(2, tdp.getData().size());
    }

    @Test
    public void testToString() {
        DataPoint dp = new DataPoint();
        dp.addAttribute("key", "value");
        dp.addAttribute("key2", "value2");
        tdp.addData(dp);
        assertEquals("key : value\nkey2 : value2\n\n", tdp.toString());
    }

    @Test
    public void testDifferentQualifie() {
        DataPoint dp = new DataPoint();
        dp.addAttribute("key", "value");
        dp.addAttribute("key2", "value2");
        DataPoint dp2 = new DataPoint();
        dp2.addAttribute("key", "value");
        tdp.addData(dp);
        tdp.addData(dp2);
        assertEquals(1, tdp.getDifferentQualifie("key").size());
    }

    @Test
    public void testMaxDonnee() {
        DataPoint dp = new DataPoint();
        dp.addAttribute("key", 1);
        dp.addAttribute("key2", 2);
        DataPoint dp2 = new DataPoint();
        dp2.addAttribute("key", 3);
        tdp.addData(dp);
        tdp.addData(dp2);
        assertEquals(3, tdp.maxDonnee("key"));
    }

    @Test
    public void testremoveAll() {
        DataPoint dp = new DataPoint();
        dp.addAttribute("key", 1);
        dp.addAttribute("key2", 2);
        DataPoint dp2 = new DataPoint();
        dp2.addAttribute("key", 3);
        tdp.addData(dp);
        tdp.addData(dp2);
        tdp.removeAllData();
        assertEquals(0, tdp.getData().size());
    }
}
