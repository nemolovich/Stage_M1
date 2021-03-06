/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Brian GOHIER
 */
@Entity
@Table(name = "DEVICE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Device.findAll", query = "SELECT w FROM Device w"),
    @NamedQuery(name = "Device.findById", query = "SELECT w FROM Device w WHERE w.id = :id"),
    @NamedQuery(name = "Device.findByWsType", query = "SELECT w FROM Device w WHERE w.wsType = :wsType"),
    @NamedQuery(name = "Device.findByName", query = "SELECT w FROM Device w WHERE w.name = :name"),
    @NamedQuery(name = "Device.findByUserNameDefault", query = "SELECT w FROM Device w WHERE w.userNameDefault = :userNameDefault"),
    @NamedQuery(name = "Device.findByIpAddress", query = "SELECT w FROM Device w WHERE w.ipAddress = :ipAddress"),
    @NamedQuery(name = "Device.findByBrand", query = "SELECT w FROM Device w WHERE w.brand = :brand"),
    @NamedQuery(name = "Device.findByStartDate", query = "SELECT w FROM Device w WHERE w.startDate = :startDate"),
    @NamedQuery(name = "Device.findByProcessor", query = "SELECT w FROM Device w WHERE w.processor = :processor"),
    @NamedQuery(name = "Device.findByMonitor", query = "SELECT w FROM Device w WHERE w.monitor = :monitor"),
    @NamedQuery(name = "Device.findByVideoCard", query = "SELECT w FROM Device w WHERE w.videoCard = :videoCard"),
    @NamedQuery(name = "Device.findByOperatingSystem", query = "SELECT w FROM Device w WHERE w.operatingSystem = :operatingSystem"),
    @NamedQuery(name = "Device.findBySystemVersion", query = "SELECT w FROM Device w WHERE w.systemVersion = :systemVersion"),
    @NamedQuery(name = "Device.findBySystemLicense", query = "SELECT w FROM Device w WHERE w.systemLicense = :systemLicense"),
    @NamedQuery(name = "Device.findByRam", query = "SELECT w FROM Device w WHERE w.ram = :ram"),
    @NamedQuery(name = "Device.findByHardDrive", query = "SELECT w FROM Device w WHERE w.hardDrive = :hardDrive"),
    @NamedQuery(name = "Device.findByObservations", query = "SELECT w FROM Device w WHERE w.observations = :observations"),
    @NamedQuery(name = "Device.findBySleeping", query = "SELECT w FROM Device w WHERE w.sleeping = :sleeping")})
public class Device implements Serializable, Comparable<Device>
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 64)
    @Column(name = "WS_TYPE")
    private String wsType;
    @Basic(optional = false)
    @NotNull
    @Size(max = 64)
    @Column(name = "NAME")
    private String name;
    @Size(max = 30)
    @Column(name = "USER_NAME_DEFAULT")
    private String userNameDefault;
    @Size(max = 39)
    @Column(name = "IP_ADDRESS")
    private String ipAddress;
    @Size(max = 64)
    @Column(name = "BRAND")
    private String brand;
    @Column(name = "START_DATE")
    @Temporal(TemporalType.DATE)
    private Date startDate=null;
    @Size(max = 64)
    @Column(name = "PROCESSOR")
    private String processor;
    @Size(max = 64)
    @Column(name = "MONITOR")
    private String monitor;
    @Size(max = 64)
    @Column(name = "VIDEO_CARD")
    private String videoCard;
    @Size(max = 64)
    @Column(name = "OPERATING_SYSTEM")
    private String operatingSystem;
    @Size(max = 64)
    @Column(name = "SYSTEM_VERSION")
    private String systemVersion;
    @Size(max = 64)
    @Column(name = "SYSTEM_LICENSE")
    private String systemLicense;
    @Size(max = 64)
    @Column(name = "RAM")
    private String ram;
    @Size(max = 64)
    @Column(name = "HARD_DRIVE")
    private String hardDrive;
    @Size(max = 250)
    @Column(name = "OBSERVATIONS")
    private String observations;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SLEEPING")
    private Boolean sleeping=false;
    @JoinColumn(name = "ID_CLIENT", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Client idClient;
    @JoinColumn(name = "ID_FILE_PATH", referencedColumnName = "ID")
    @ManyToOne
    private FilePath idFilePath;
    @OneToMany(mappedBy = "idDevice")
    private List<Task> taskList;

    public Device() {
    }

    public Device(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id==null?-1:this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWsType() {
        return wsType;
    }

    public void setWsType(String wsType) {
        this.wsType = wsType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserNameDefault() {
        return userNameDefault;
    }

    public void setUserNameDefault(String userNameDefault) {
        this.userNameDefault = userNameDefault;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public String getVideoCard() {
        return videoCard;
    }

    public void setVideoCard(String videoCard) {
        this.videoCard = videoCard;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getSystemLicense() {
        return systemLicense;
    }

    public void setSystemLicense(String systemLicense) {
        this.systemLicense = systemLicense;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getHardDrive() {
        return hardDrive;
    }

    public void setHardDrive(String hardDrive) {
        this.hardDrive = hardDrive;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Boolean getSleeping() {
        return sleeping;
    }

    public void setSleeping(Boolean sleeping) {
        this.sleeping = sleeping;
    }

    public Client getIdClient() {
        return idClient;
    }

    public void setIdClient(Client idClient) {
        this.idClient = idClient;
    }

    public FilePath getIdFilePath() {
        return idFilePath;
    }

    public void setIdFilePath(FilePath idFilePath) {
        this.idFilePath = idFilePath;
    }

    @XmlTransient
    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    public int compareTo(Device d)
    {
        return -d.getStartDate().compareTo(this.startDate);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Device other = (Device) obj;
        if ((this.wsType == null) ? (other.wsType != null) : !this.wsType.equals(other.wsType)) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.userNameDefault == null) ? (other.userNameDefault != null) : !this.userNameDefault.equals(other.userNameDefault)) {
            return false;
        }
        if ((this.ipAddress == null) ? (other.ipAddress != null) : !this.ipAddress.equals(other.ipAddress)) {
            return false;
        }
        if ((this.brand == null) ? (other.brand != null) : !this.brand.equals(other.brand)) {
            return false;
        }
        if (this.startDate != other.startDate && (this.startDate == null || !this.startDate.equals(other.startDate))) {
            return false;
        }
        if ((this.processor == null) ? (other.processor != null) : !this.processor.equals(other.processor)) {
            return false;
        }
        if ((this.monitor == null) ? (other.monitor != null) : !this.monitor.equals(other.monitor)) {
            return false;
        }
        if ((this.videoCard == null) ? (other.videoCard != null) : !this.videoCard.equals(other.videoCard)) {
            return false;
        }
        if ((this.operatingSystem == null) ? (other.operatingSystem != null) : !this.operatingSystem.equals(other.operatingSystem)) {
            return false;
        }
        if ((this.systemVersion == null) ? (other.systemVersion != null) : !this.systemVersion.equals(other.systemVersion)) {
            return false;
        }
        if ((this.systemLicense == null) ? (other.systemLicense != null) : !this.systemLicense.equals(other.systemLicense)) {
            return false;
        }
        if ((this.ram == null) ? (other.ram != null) : !this.ram.equals(other.ram)) {
            return false;
        }
        if ((this.hardDrive == null) ? (other.hardDrive != null) : !this.hardDrive.equals(other.hardDrive)) {
            return false;
        }
        if ((this.observations == null) ? (other.observations != null) : !this.observations.equals(other.observations)) {
            return false;
        }
        if (this.sleeping != other.sleeping && (this.sleeping == null || !this.sleeping.equals(other.sleeping))) {
            return false;
        }
        return true;
    }

    public String getFullString()
    {
        return "entity.Device{" + "id=" + id + ", wsType=" + wsType + ", name=" + name + ", userNameDefault=" + userNameDefault + ", ipAddress=" + ipAddress + ", brand=" + brand + ", startDate=" + startDate + ", processor=" + processor + ", monitor=" + monitor + ", videoCard=" + videoCard + ", operatingSystem=" + operatingSystem + ", systemVersion=" + systemVersion + ", systemLicense=" + systemLicense + ", ram=" + ram + ", hardDrive=" + hardDrive + ", observations=" + observations + ", sleeping=" + sleeping + ", idClient=" + idClient + ", idFilePath=" + idFilePath + ", taskList=" + taskList + '}';
    }
    
    @Override
    public String toString() {
        String out="["+this.wsType;
        String br=this.brand!=null&&!this.brand.isEmpty()?(":"+this.brand):"";
        String os=this.operatingSystem!=null&&!this.operatingSystem.isEmpty()?(" on "+this.operatingSystem):"";
        String proc=this.processor!=null&&!this.processor.isEmpty()?(" [P:"+this.processor):"";
        String rm=this.ram!=null&&!this.ram.isEmpty()?((!proc.isEmpty()?"|":" [")+"RAM:"+this.ram):"";
        rm+=(!rm.isEmpty()||!proc.isEmpty())?"]":"";
        out+=br+"] "+this.name+os+proc+rm;
        return out;
    }
}
