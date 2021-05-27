package csvreader.adapter;

import csvreader.entity.BusinessProcess;

public class BusinessProcessAdapter {
    private BusinessProcess process;

    private String code;
    private String name;
    private String subdivision;

    public BusinessProcessAdapter() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(String subdivision) {
        this.subdivision = subdivision;
    }

    public BusinessProcess getProcess() {
        if (process == null){
            process = new BusinessProcess();
            process.setCode(code);
            process.setName(name);
        }
        return process;
    }

    @Override
    public String toString() {
        return "BusinessProcessAdapter{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", subdivision='" + subdivision + '\'' +
                '}';
    }
}
