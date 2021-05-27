package csvreader.service;

import csvreader.adapter.BusinessProcessAdapter;
import csvreader.entity.BusinessProcess;
import csvreader.entity.Subdivision;
import csvreader.repository.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class ProcessService {
    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private SubdivisionService subdivisionService;

    private final LinkedList<BusinessProcess> listParentProcesses = new LinkedList<>();
    private BusinessProcess lastBusinessProcess;
    private int depth = 0;
    private boolean isBeginList = true;

    public void saveProcess(BusinessProcess businessProcess) {
        processRepository.save(businessProcess);
    }

    public void saveProcess(BusinessProcessAdapter adapter) {
        BusinessProcess businessProcess = adapter.getProcess();

        //Если у процесса нет подразделений, то он - родитель
        if (adapter.getSubdivision().isEmpty()) {
            saveParentProcess(businessProcess);
        } else {
            Subdivision subdivision = new Subdivision(adapter.getSubdivision());
            saveProcessWithSubdivision(businessProcess, subdivision);
        }
    }

    private void saveParentProcess(BusinessProcess businessProcess) {
        //Сохраняем родителя в бд и в список уже с id
        saveProcess(businessProcess);
        if (!isBeginList) {
            //Удаляем элемент по индексу и назначаем родителя если он не крайний в списке
            if (listParentProcesses.size() != depth + 1)
                listParentProcesses.get(depth).setParentProcessId(listParentProcesses.get(depth + 1).getId());
            saveProcess(listParentProcesses.get(depth));
            listParentProcesses.remove(depth);
            depth++;
        }
        listParentProcesses.push(businessProcess);
    }

    private void saveProcessWithSubdivision(BusinessProcess businessProcess, Subdivision subdivision) {
        isBeginList = false;

        subdivisionService.saveSubdivision(subdivision);
        if (businessProcess.getName().isEmpty()) {
            businessProcess = lastBusinessProcess;
        }
        businessProcess.addSubdivision(subdivision);
        businessProcess.setParentProcessId(listParentProcesses.peek().getId());
        saveProcess(businessProcess);

        try {
            //Не работает при переприсваивании ссылок, только через клонирование
            lastBusinessProcess = businessProcess.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        depth = 0;
    }

    public void clearList() {
        while (!listParentProcesses.isEmpty()) {
            if (listParentProcesses.size() != 1) {
                listParentProcesses.get(0).setParentProcessId(listParentProcesses.get(1).getId());
            }
            processRepository.save(listParentProcesses.get(0));
            listParentProcesses.removeFirst();
            depth = 0;
            isBeginList = true;
        }
    }
}
