package csvreader.readerCSV;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class ReaderCSVTest {

    @Autowired
    private ReaderCSV readerCSV;

    @Test
    void read() {
        String csvFilename = "src\\main\\csvfiles\\test_data.CSV";
        String encoding = "windows-1251";
        readerCSV.read(csvFilename, encoding);
    }
}