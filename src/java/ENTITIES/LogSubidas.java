/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ENTITIES;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Felipe
 */
@Entity
@Table(name = "log_subidas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LogSubidas.findAll", query = "SELECT l FROM LogSubidas l"),
    @NamedQuery(name = "LogSubidas.findByIdLog", query = "SELECT l FROM LogSubidas l WHERE l.idLog = :idLog"),
    @NamedQuery(name = "LogSubidas.findByFecha", query = "SELECT l FROM LogSubidas l WHERE l.fecha = :fecha")})
public class LogSubidas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_log")
    private Integer idLog;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "informacion")
    private String informacion;
    @JoinColumn(name = "id_archivo", referencedColumnName = "id_archivo")
    @ManyToOne(optional = false)
    private Archivo idArchivo;
    @JoinColumn(name = "id_sesion", referencedColumnName = "id_sesion")
    @ManyToOne(optional = false)
    private Sesion idSesion;

    public LogSubidas() {
    }

    public LogSubidas(Integer idLog) {
        this.idLog = idLog;
    }

    public LogSubidas(Integer idLog, Date fecha, String informacion) {
        this.idLog = idLog;
        this.fecha = fecha;
        this.informacion = informacion;
    }

    public Integer getIdLog() {
        return idLog;
    }

    public void setIdLog(Integer idLog) {
        this.idLog = idLog;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    public Archivo getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(Archivo idArchivo) {
        this.idArchivo = idArchivo;
    }

    public Sesion getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(Sesion idSesion) {
        this.idSesion = idSesion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLog != null ? idLog.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogSubidas)) {
            return false;
        }
        LogSubidas other = (LogSubidas) object;
        if ((this.idLog == null && other.idLog != null) || (this.idLog != null && !this.idLog.equals(other.idLog))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.LogSubidas[ idLog=" + idLog + " ]";
    }
    
}
