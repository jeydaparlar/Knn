package model;

import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataPointTest {
    DataPoint dataPoint = new DataPoint();

    @Test
    public void testAddGetAttribute() {
        dataPoint.addAttribute("key", "value");
        assertEquals("value", dataPoint.getAttribute("key"));
        assertEquals("key", dataPoint.getAttribute(0));
    }

    @Test
    public void testGetAttributeDouble() {
        dataPoint.addAttribute("key", 1.0);
        assertEquals("1.0", dataPoint.getAttributeDouble("key"));
    }

    @Test
    public void testGetAttributes() {
        dataPoint.addAttribute("key", "value");
        List<String> list = new ArrayList<>();
        list.add("key");
        assertEquals(list, dataPoint.getAttributes());
    }

    @Test
    public void testGetKeysWithStringValue() {
        dataPoint.addAttribute("key", "value");
        dataPoint.addAttribute("key2", 1.0);
        List<String> list = new ArrayList<>();
        list.add("key");
        assertEquals(list, dataPoint.getKeysWithStringValue());
    }

    @Test
    public void testGetKeysWithDoubleValue() {
        dataPoint.addAttribute("key", "value");
        dataPoint.addAttribute("key2", 1.0);
        List<String> list = new ArrayList<>();
        list.add("key2");
        assertEquals(list, dataPoint.getKeysWithDoubleValue());
    }

    @Test
    public void testremoveAttribute() {
        dataPoint.addAttribute("key", "value");
        dataPoint.removeAttribute("key");
        assertEquals(0, dataPoint.getAttributes().size());
    }

    @Test
    public void testContainsKey() {
        dataPoint.addAttribute("key", "value");
        assertTrue(dataPoint.containsAttribute("key"));
    }

    @Test
    public void testToString() {
        dataPoint.addAttribute("key", "value");
        dataPoint.addAttribute("key2", 1.0);
        assertEquals("key : value\nkey2 : 1.0\n", dataPoint.toString());
    }

    @Test
    public void testEquals() {
        DataPoint dataPointnull = null;
        DataPoint dataPoint2 = new DataPoint();
        dataPoint.addAttribute("key", "value");
        dataPoint2.addAttribute("key", "value");
        DataPoint dataPoint3 = new DataPoint();
        dataPoint3.addAttribute("key", "valuess");
        assertTrue(dataPoint.equals(dataPoint2));
        assertFalse(dataPoint.equals(dataPoint3));
        assertFalse(dataPoint2.equals(dataPointnull));
    }

    @Test
    public void testHashCode() {
        DataPoint dataPoint2 = new DataPoint();
        dataPoint.addAttribute("key", "value");
        dataPoint2.addAttribute("key", "value");
        assertEquals(dataPoint.hashCode(), dataPoint2.hashCode());
    }
}
