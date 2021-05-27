package csvreader.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Subdivision {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "subdivisions", fetch = FetchType.EAGER)
    private Set<BusinessProcess> businessProcesses;

    public Subdivision() {
    }

    public Subdivision(String name) {
        this.name = name;
    }

    public Subdivision(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Subdivision(Subdivision subdivision) {
        this.id = subdivision.id;
        this.name = subdivision.name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<BusinessProcess> getBusinessProcesses() {
        if (businessProcesses == null)
            return new HashSet<>();
        return businessProcesses;
    }

    public void setBusinessProcesses(Set<BusinessProcess> businessProcesses) {
        this.businessProcesses = businessProcesses;
    }

    public void addSubdivision(BusinessProcess businessProcess) {
        if (businessProcesses == null) {
            businessProcesses = new HashSet<>();
        }
        businessProcesses.add(businessProcess);
    }

}
