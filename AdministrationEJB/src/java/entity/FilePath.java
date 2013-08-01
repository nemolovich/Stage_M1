/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import bean.Utils;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Brian GOHIER
 */
@Entity
@Table(name = "FILE_PATH")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FilePath.findAll", query = "SELECT f FROM FilePath f"),
    @NamedQuery(name = "FilePath.findById", query = "SELECT f FROM FilePath f WHERE f.id = :id"),
    @NamedQuery(name = "FilePath.findByFilePath", query = "SELECT f FROM FilePath f WHERE f.filePath = :filePath")})
public class FilePath implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "FILE_PATH")
    private String filePath;
    public static String TEMP_FOLDER="temp";

    public FilePath() {
    }

    public FilePath(Integer id) {
        this.id = id;
    }

    public FilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getId() {
        return this.id==null?-1:this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public List<File> getFilesInPath()
    {
        List<File> files = new ArrayList<File>();
        String path = Utils.getUploadsPath()+
                this.filePath+File.separator;
        File[] list = new File(path).listFiles();
        if(list!=null)
        {
            for(int i=0;i<list.length;i++)
            {
                if(list[i].isFile())
                {
                    files.add(list[i]);
                }
            }
        }
        return files;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FilePath)) {
            return false;
        }
        FilePath other = (FilePath) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public String getFullString()
    {
        return "entity.FilePath{" + "id=" + id + ", filePath=" + filePath + '}';
    }
    
    @Override
    public String toString() {
        return "['"+this.filePath+"']";
    }
    
}
