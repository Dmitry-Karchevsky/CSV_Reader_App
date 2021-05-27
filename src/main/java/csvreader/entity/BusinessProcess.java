package csvreader.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class BusinessProcess implements Cloneable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    private String code;

    @Column(nullable = false)
    private String name;

    private Long parentProcessId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "subdiv_process",
            joinColumns = { @JoinColumn(name = "process_id") },
            inverseJoinColumns = { @JoinColumn(name = "sub_id") })
    private Set<Subdivision> subdivisions;

    public BusinessProcess() {
    }

    public BusinessProcess(String code, String name, Long parentProcessId, Set<Subdivision> subdivisions) {
        this.code = code;
        this.name = name;
        this.parentProcessId = parentProcessId;
        this.subdivisions = subdivisions.stream().map(Subdivision::new).collect(Collectors.toSet());
    }

    public BusinessProcess(Long id, String code, String name, Long parentProcessId, Set<Subdivision> subdivisions) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.parentProcessId = parentProcessId;
        this.subdivisions = subdivisions.stream().map(Subdivision::new).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getParentProcessId() {
        return parentProcessId;
    }

    public void setParentProcessId(Long parentProcessId) {
        this.parentProcessId = parentProcessId;
    }

    public Set<Subdivision> getSubdivisions() {
        return subdivisions;
    }

    public void setSubdivisions(Set<Subdivision> subdivisions) {
        this.subdivisions = subdivisions;
    }

    public void addSubdivision(Subdivision subdivision) {
        if (subdivisions == null) {
            subdivisions = new HashSet<>();
        }
        subdivisions.add(subdivision);
    }

    @Override
    public BusinessProcess clone() throws CloneNotSupportedException {
        return new BusinessProcess(id, code, name, parentProcessId, subdivisions);
    }

    @Override
    public String toString() {
        return "BuisnessProcess{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", parentProcessId=" + parentProcessId +
                ", subdivisions=" + subdivisions +
                '}';
    }
}
