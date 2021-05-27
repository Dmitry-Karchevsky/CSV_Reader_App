package csvreader.readerCSV;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import csvreader.adapter.BusinessProcessAdapter;
import csvreader.service.ProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@Component
public class ReaderCSV {

    @Autowired
    private ProcessService processService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void read(String csvFilename, String encoding) {
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilename, Charset.forName(encoding)), ';', '\n', '"')) {
            List<BusinessProcessAdapter> list = new CsvToBean().parse(setColumnMapping(), csvReader);
            while (list.get(0).getName() == null || (!list.get(0).getName().equals("Наименование процесса")))
                list.remove(0);
            list.remove(0);
            //Не понятно, почему считывает элементы другой строки, поэтому еще один remove
            list.remove(0);
            for (BusinessProcessAdapter process : list) {
                processService.saveProcess(process);
            }
            processService.clearList();
        } catch (IOException exception) {
            logger.info("File not found");
            logger.error(exception.getMessage());
        }
    }

    private ColumnPositionMappingStrategy<BusinessProcessAdapter> setColumnMapping()
    {
        ColumnPositionMappingStrategy<BusinessProcessAdapter> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(BusinessProcessAdapter.class);
        String[] columns = new String[] {"code", "name", "subdivision"};
        strategy.setColumnMapping(columns);
        return strategy;
    }
}
