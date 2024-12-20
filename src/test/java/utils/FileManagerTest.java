package utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
class FileManagerTest {

    FileManager fm;

    @BeforeEach
    void initialisation(){
        this.fm = new FileManager();
    }

    @Test
    void testCountLine() throws IOException {
        assertEquals(150, FileManager.countLines("src/test/resources/test.csv"));
    }

    @Test
    void testIfFileExist() {
        assertTrue(FileManager.ifExist("src/test/resources/test.csv"));
    }



}