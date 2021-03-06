/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Brian GOHIER
 */
@Entity
@Table(name = "CLIENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c"),
    @NamedQuery(name = "Client.findById", query = "SELECT c FROM Client c WHERE c.id = :id"),
    @NamedQuery(name = "Client.findByName", query = "SELECT c FROM Client c WHERE c.name = :name"),
    @NamedQuery(name = "Client.findByAddress", query = "SELECT c FROM Client c WHERE c.address = :address"),
    @NamedQuery(name = "Client.findByPostalcode", query = "SELECT c FROM Client c WHERE c.postalcode = :postalcode"),
    @NamedQuery(name = "Client.findByCity", query = "SELECT c FROM Client c WHERE c.city = :city"),
    @NamedQuery(name = "Client.findByPhone", query = "SELECT c FROM Client c WHERE c.phone = :phone"),
    @NamedQuery(name = "Client.findByFax", query = "SELECT c FROM Client c WHERE c.fax = :fax"),
    @NamedQuery(name = "Client.findByTarif", query = "SELECT c FROM Client c WHERE c.tarif = :tarif"),
    @NamedQuery(name = "Client.findByDeplacement", query = "SELECT c FROM Client c WHERE c.deplacement = :deplacement"),
    @NamedQuery(name = "Client.findByMail", query = "SELECT c FROM Client c WHERE c.mail = :mail"),
    @NamedQuery(name = "Client.findByInterventionType", query = "SELECT c FROM Client c WHERE c.interventionType = :interventionType"),
    @NamedQuery(name = "Client.findByInternetOperator", query = "SELECT c FROM Client c WHERE c.internetOperator = :internetOperator"),
    @NamedQuery(name = "Client.findByInternetLogin", query = "SELECT c FROM Client c WHERE c.internetLogin = :internetLogin"),
    @NamedQuery(name = "Client.findByInternetPassword", query = "SELECT c FROM Client c WHERE c.internetPassword = :internetPassword"),
    @NamedQuery(name = "Client.findByInternetDns", query = "SELECT c FROM Client c WHERE c.internetDns = :internetDns"),
    @NamedQuery(name = "Client.findByInternetDnsLogin", query = "SELECT c FROM Client c WHERE c.internetDnsLogin = :internetDnsLogin"),
    @NamedQuery(name = "Client.findByInternetDnsPassword", query = "SELECT c FROM Client c WHERE c.internetDnsPassword = :internetDnsPassword"),
    @NamedQuery(name = "Client.findByObservations", query = "SELECT c FROM Client c WHERE c.observations = :observations"),
    @NamedQuery(name = "Client.findBySleeping", query = "SELECT c FROM Client c WHERE c.sleeping = :sleeping")})
public class Client implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(max = 45)
    @Column(name = "NAME")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(max = 100)
    @Column(name = "ADDRESS")
    private String address;
    @Basic(optional = false)
    @NotNull
    @Column(name = "POSTALCODE")
    private int postalcode;
    @Basic(optional = false)
    @NotNull
    @Size(max = 45)
    @Column(name = "CITY")
    private String city;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "FAX")
    private String fax;
    @Column(name = "TARIF")
    private Double tarif;
    @Column(name = "DEPLACEMENT")
    private Double deplacement;
    @Basic(optional = false)
    @NotNull
    @Size(max = 30)
    @Column(name = "MAIL")
    private String mail;
    @Size(max = 250)
    @Column(name = "INTERVENTION_TYPE")
    private String interventionType;
    @Size(max = 30)
    @Column(name = "INTERNET_OPERATOR")
    private String internetOperator;
    @Size(max = 30)
    @Column(name = "INTERNET_LOGIN")
    private String internetLogin;
    @Size(max = 64)
    @Column(name = "INTERNET_PASSWORD")
    private String internetPassword;
    @Size(max = 64)
    @Column(name = "INTERNET_DNS")
    private String internetDns;
    @Size(max = 30)
    @Column(name = "INTERNET_DNS_LOGIN")
    private String internetDnsLogin;
    @Size(max = 64)
    @Column(name = "INTERNET_DNS_PASSWORD")
    private String internetDnsPassword;
    @Size(max = 250)
    @Column(name = "OBSERVATIONS")
    private String observations;
    @Column(name = "SLEEPING")
    private Boolean sleeping=false;
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID")
    @ManyToOne
    private CUser idUser;
    @JoinColumn(name = "ID_FILE_PATH", referencedColumnName = "ID")
    @OneToOne(optional = true)
    private FilePath idFilePath;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idClient")
    private List<CUser> cUserList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idClient")
    private List<Device> deviceList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idClient")
    private List<Mail> mailList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idClient")
    private List<Software> softwareList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idClient")
    private List<Task> taskList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idClient")
    private List<Services> servicesList;

    public Client() {
    }

    public Client(Integer id) {
        this.id = id;
    }

    public Client(Integer id, String name, String address, int postalcode, String phone, String mail) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalcode = postalcode;
        this.phone = phone;
        this.mail = mail;
    }

    public Integer getId() {
        return this.id==null?-1:this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(int postalcode) {
        String code=String.valueOf(postalcode);
        if(code.length()>5)
        {
            this.postalcode = Integer.parseInt(code.substring(0,5));
        }
        else
        {
            this.postalcode = postalcode;
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Double getTarifValue() {
        return this.tarif==null?0:this.tarif;
    }

    public Double getTarif() {
        return tarif;
    }

    public void setTarif(Double tarif) {
        this.tarif = tarif;
    }

    public Double getDeplacementValue() {
        return this.deplacement==null?0:this.deplacement;
    }

    public Double getDeplacement() {
        return deplacement;
    }

    public void setDeplacement(Double deplacement) {
        this.deplacement = deplacement;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getInterventionType() {
        return interventionType;
    }

    public void setInterventionType(String interventionType) {
        this.interventionType = interventionType;
    }

    public String getInternetOperator() {
        return internetOperator;
    }

    public void setInternetOperator(String internetOperator) {
        this.internetOperator = internetOperator;
    }

    public String getInternetLogin() {
        return internetLogin;
    }

    public void setInternetLogin(String internetLogin) {
        this.internetLogin = internetLogin;
    }

    public String getInternetPassword() {
        return internetPassword;
    }

    public void setInternetPassword(String internetPassword) {
        this.internetPassword = internetPassword;
    }

    public String getInternetDns() {
        return internetDns;
    }

    public void setInternetDns(String internetDns) {
        this.internetDns = internetDns;
    }

    public String getInternetDnsLogin() {
        return internetDnsLogin;
    }

    public void setInternetDnsLogin(String internetDnsLogin) {
        this.internetDnsLogin = internetDnsLogin;
    }

    public String getInternetDnsPassword() {
        return internetDnsPassword;
    }

    public void setInternetDnsPassword(String internetDnsPassword) {
        this.internetDnsPassword = internetDnsPassword;
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

    public CUser getIdUser() {
        return idUser;
    }
    
    public boolean isInterlocuteur(CUser user)
    {
        return (user!=null&&this.idUser!=null&&this.idUser.equals(user));
    }

    public void setIdUser(CUser idUser) {
        this.idUser = idUser;
    }

    public FilePath getIdFilePath() {
        return idFilePath;
    }

    public void setIdFilePath(FilePath idFilepath) {
        this.idFilePath = idFilepath;
    }

    @XmlTransient
    public List<CUser> getCUserList() {
        return cUserList;
    }

    public void setCUserList(List<CUser> cUserList) {
        this.cUserList = cUserList;
    }

    @XmlTransient
    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    @XmlTransient
    public List<Mail> getMailList() {
        return mailList;
    }

    public void setMailList(List<Mail> mailList) {
        this.mailList = mailList;
    }

    @XmlTransient
    public List<Software> getSoftwareList() {
        return softwareList;
    }

    public void setSoftwareList(List<Software> softwareList) {
        this.softwareList = softwareList;
    }

    @XmlTransient
    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    @XmlTransient
    public List<Services> getServicesList() {
        return servicesList;
    }

    public void setServicesList(List<Services> servicesList) {
        this.servicesList = servicesList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 59 * hash + (this.address != null ? this.address.hashCode() : 0);
        hash = 59 * hash + this.postalcode;
        hash = 59 * hash + (this.city != null ? this.city.hashCode() : 0);
        hash = 59 * hash + (this.phone != null ? this.phone.hashCode() : 0);
        hash = 59 * hash + (this.fax != null ? this.fax.hashCode() : 0);
        hash = 59 * hash + (this.tarif != null ? this.tarif.hashCode() : 0);
        hash = 59 * hash + (this.deplacement != null ? this.deplacement.hashCode() : 0);
        hash = 59 * hash + (this.mail != null ? this.mail.hashCode() : 0);
        hash = 59 * hash + (this.interventionType != null ? this.interventionType.hashCode() : 0);
        hash = 59 * hash + (this.internetOperator != null ? this.internetOperator.hashCode() : 0);
        hash = 59 * hash + (this.internetLogin != null ? this.internetLogin.hashCode() : 0);
        hash = 59 * hash + (this.internetPassword != null ? this.internetPassword.hashCode() : 0);
        hash = 59 * hash + (this.internetDns != null ? this.internetDns.hashCode() : 0);
        hash = 59 * hash + (this.internetDnsLogin != null ? this.internetDnsLogin.hashCode() : 0);
        hash = 59 * hash + (this.internetDnsPassword != null ? this.internetDnsPassword.hashCode() : 0);
        hash = 59 * hash + (this.observations != null ? this.observations.hashCode() : 0);
        hash = 59 * hash + (this.sleeping != null ? this.sleeping.hashCode() : 0);
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
        final Client other = (Client) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.address == null) ? (other.address != null) : !this.address.equals(other.address)) {
            return false;
        }
        if (this.postalcode != other.postalcode) {
            return false;
        }
        if ((this.city == null) ? (other.city != null) : !this.city.equals(other.city)) {
            return false;
        }
        if ((this.phone == null) ? (other.phone != null) : !this.phone.equals(other.phone)) {
            return false;
        }
        if ((this.fax == null) ? (other.fax != null) : !this.fax.equals(other.fax)) {
            return false;
        }
        if (this.tarif != other.tarif && (this.tarif == null || !this.tarif.equals(other.tarif))) {
            return false;
        }
        if (this.deplacement != other.deplacement && (this.deplacement == null || !this.deplacement.equals(other.deplacement))) {
            return false;
        }
        if ((this.mail == null) ? (other.mail != null) : !this.mail.equals(other.mail)) {
            return false;
        }
        if ((this.interventionType == null) ? (other.interventionType != null) : !this.interventionType.equals(other.interventionType)) {
            return false;
        }
        if ((this.internetOperator == null) ? (other.internetOperator != null) : !this.internetOperator.equals(other.internetOperator)) {
            return false;
        }
        if ((this.internetLogin == null) ? (other.internetLogin != null) : !this.internetLogin.equals(other.internetLogin)) {
            return false;
        }
        if ((this.internetPassword == null) ? (other.internetPassword != null) : !this.internetPassword.equals(other.internetPassword)) {
            return false;
        }
        if ((this.internetDns == null) ? (other.internetDns != null) : !this.internetDns.equals(other.internetDns)) {
            return false;
        }
        if ((this.internetDnsLogin == null) ? (other.internetDnsLogin != null) : !this.internetDnsLogin.equals(other.internetDnsLogin)) {
            return false;
        }
        if ((this.internetDnsPassword == null) ? (other.internetDnsPassword != null) : !this.internetDnsPassword.equals(other.internetDnsPassword)) {
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
        return "entity.Client{" + "id=" + id + ", name=" + name + ", address=" + address + ", postalcode=" + postalcode + ", city=" + city + ", phone=" + phone + ", fax=" + fax + ", tarif=" + tarif + ", deplacement=" + deplacement + ", mail=" + mail + ", interventionType=" + interventionType + ", internetOperator=" + internetOperator + ", internetLogin=" + internetLogin + ", internetPassword=" + internetPassword + ", internetDns=" + internetDns + ", internetDnsLogin=" + internetDnsLogin + ", internetDnsPassword=" + internetDnsPassword + ", observations=" + observations + ", sleeping=" + sleeping + ", idUser=" + idUser + ", idFilePath=" + idFilePath + ", cUserList=" + cUserList + ", deviceList=" + deviceList + ", mailList=" + mailList + ", softwareList=" + softwareList + ", taskList=" + taskList + ", servicesList=" + servicesList + '}';
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
}