package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IrisTest {

    Iris iris;
    Iris iris2;
    Iris iris3;

    @BeforeEach
    void initialisation(){
        this.iris = new Iris(2.0,2.0,2.0,2.0);
        this.iris2 = new Iris(2.0,2.0,2.0,2.0);
        this.iris3 = new Iris();
    }

   @Test
   void testConstructeur(){
        assertEquals(2.0, iris.getSepal_length());
        assertEquals(2.0, iris.getSepal_width());
        assertEquals(2.0, iris.getPetal_length());
        assertEquals(2.0, iris.getPetal_width());
    }

    @Test
    void getSepal_length() {
        assertEquals(2.0,this.iris.getSepal_length());
        this.iris.setSepal_length(null);
        assertEquals(null,this.iris.getSepal_length());
    }

    @Test
    void setSepal_length() {
        this.iris.setSepal_length(3.5);
        assertEquals(3.5,this.iris.getSepal_length());
    }

    @Test
    void getSepal_width() {
        assertEquals(2.0,this.iris.getSepal_width());
        this.iris.setSepal_width(null);
        assertEquals(null,this.iris.getSepal_width());
    }

    @Test
    void setSepal_width() {
        this.iris.setSepal_width(3.5);
        assertEquals(3.5,this.iris.getSepal_width());
    }

    @Test
    void getPetal_length() {
        assertEquals(2.0,this.iris.getPetal_length());
        this.iris.setPetal_length(null);
        assertEquals(null,this.iris.getPetal_length());
    }

    @Test
    void setPetal_length() {
        this.iris.setPetal_length(3.5);
        assertEquals(3.5,this.iris.getPetal_length());
    }

    @Test
    void getPetal_width() {
        assertEquals(2.0,this.iris.getPetal_width());
        this.iris.setPetal_width(null);
        assertEquals(null,this.iris.getPetal_width());
    }

    @Test
    void setPetal_width() {
        this.iris.setPetal_width(3.5);
        assertEquals(3.5,this.iris.getPetal_width());
    }

    @Test
    void getVariety() {
        assertEquals(null, this.iris.getVariety());
        this.iris.setVariety(VARIETY.SETOSA);
        assertEquals(VARIETY.SETOSA, this.iris.getVariety());
    }

    @Test
    void setVariety() {
        this.iris.setVariety(VARIETY.SETOSA);
        assertEquals(VARIETY.SETOSA, this.iris.getVariety());
    }

    @Test
    void isOriginUnkown() {
        assertFalse(this.iris.isOriginUnkown());
    }

    @Test
    void testEquals() {
        assertTrue(this.iris.equals(iris2));
        assertFalse(this.iris.equals(null));
        assertFalse(this.iris.equals(iris3));
    }

    @Test
    void testHashcode() {
        assertEquals(iris.hashCode(), iris.hashCode());
    }

    @Test
    void testToString() {
        assertEquals("Iris{sepal_length=2.0, sepal_width=2.0, petal_length=2.0, petal_width=2.0, variety=null}", this.iris.toString());
    }
}