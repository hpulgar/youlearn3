/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ENTITIES;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Felipe
 */
@Entity
@Table(name = "sesion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sesion.findAll", query = "SELECT s FROM Sesion s"),
    @NamedQuery(name = "Sesion.findByIdSesion", query = "SELECT s FROM Sesion s WHERE s.idSesion = :idSesion"),
    @NamedQuery(name = "Sesion.findByIp", query = "SELECT s FROM Sesion s WHERE s.ip = :ip"),
    @NamedQuery(name = "Sesion.findByInicio", query = "SELECT s FROM Sesion s WHERE s.inicio = :inicio"),
    @NamedQuery(name = "Sesion.findByFin", query = "SELECT s FROM Sesion s WHERE s.fin = :fin")})
public class Sesion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_sesion")
    private Integer idSesion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ip")
    private String ip;
    @Basic(optional = false)
    @NotNull
    @Column(name = "inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fin;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSesion")
    private List<LogSubidas> logSubidasList;

    public Sesion() {
    }

    public Sesion(Integer idSesion) {
        this.idSesion = idSesion;
    }

    public Sesion(Integer idSesion, String ip, Date inicio, Date fin) {
        this.idSesion = idSesion;
        this.ip = ip;
        this.inicio = inicio;
        this.fin = fin;
    }

    public Integer getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(Integer idSesion) {
        this.idSesion = idSesion;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @XmlTransient
    public List<LogSubidas> getLogSubidasList() {
        return logSubidasList;
    }

    public void setLogSubidasList(List<LogSubidas> logSubidasList) {
        this.logSubidasList = logSubidasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSesion != null ? idSesion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sesion)) {
            return false;
        }
        Sesion other = (Sesion) object;
        if ((this.idSesion == null && other.idSesion != null) || (this.idSesion != null && !this.idSesion.equals(other.idSesion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.Sesion[ idSesion=" + idSesion + " ]";
    }
    
}
