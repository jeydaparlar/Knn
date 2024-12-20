package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableIrisTest {

    TableIris ti;
    Iris iris;

    @BeforeEach
    void initialisation(){
    this.ti = new TableIris();
    iris = new Iris(2.0,2.02,2.0,2.0);
    this.ti.addIris(iris);
    }

    @Test
    void testAddIris() {
        assertEquals(1,this.ti.getIris().size());
        this.ti.addIris(new Iris(2.0,2.02,7.0,5.0));
        assertEquals(2,this.ti.getIris().size());
    }

    @Test
    void testAddIrisFullParam() {
        assertEquals(1,this.ti.getIris().size());
        this.ti.addIris(2.0,2.02,7.0,5.0);
        assertEquals(2,this.ti.getIris().size());
    }

    @Test
    void testRemoveIris() {
        assertEquals(1,this.ti.getIris().size());
        Iris iris = new Iris(2.0,2.02,2.0,2.0);
        this.ti.removeIris(iris);
        assertEquals(0,this.ti.getIris().size());

    }

    @Test
    void testRemoveAllIris() {
        assertEquals(1,this.ti.getIris().size());
        this.ti.removeAllIris();
        assertEquals(0,this.ti.getIris().size());
    }

    @Test
    void testGetIris() {
        assertEquals(1,this.ti.getIris().size());
        assertEquals(iris,this.ti.getIris(iris));
        assertEquals(null,this.ti.getIris(new Iris(15.0,0.0,2.0,3.0)));
    }

    @Test
    void getTableIris() {
        assertNotNull(this.ti.getIris());
    }

    @Test
    void testSetVariety() {
        assertEquals(1,this.ti.getIris().size());
        this.ti.setVariety(VARIETY.SETOSA, iris);
        assertEquals(VARIETY.SETOSA,this.ti.getIris(iris).getVariety());
    }

    @Test
    void testRemoveIrisAll () {
        assertEquals(1,this.ti.getIris().size());
        this.ti.removeIris(this.ti.getIris());
        assertEquals(0,this.ti.getIris().size());
    }

    /*@Test
    void testAjouterCsv() {
        assertEquals(1,this.ti.getIris().size());
        this.ti.ajouterCsv("src/test/resources/test.csv");
        assertEquals(150,this.ti.getIris().size());
    }*/

    @Test
    void testMaxDonnee() {
        assertEquals(2.02, this.ti.maxDonnee("sepal_width"));
        assertEquals(2.0, this.ti.maxDonnee("sepal_length"));
        assertEquals(2.0, this.ti.maxDonnee("petal_length"));
        assertEquals(2.0, this.ti.maxDonnee("petal_width"));
    }

    @Test
    void testToString() {
        assertEquals("Iris{sepal_length=2.0, sepal_width=2.02, petal_length=2.0, petal_width=2.0, variety=null}\n", this.ti.toString());
    }
}