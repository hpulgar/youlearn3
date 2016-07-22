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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

/**
 *
 * @author Zotindows
 */
@Entity
@Table(name = "amigos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Amigos.findAll", query = "SELECT a FROM Amigos a"),
    @NamedQuery(name = "Amigos.findByIdAmistad", query = "SELECT a FROM Amigos a WHERE a.idAmistad = :idAmistad"),
    @NamedQuery(name = "Amigos.findByAprobado", query = "SELECT a FROM Amigos a WHERE a.aprobado = :aprobado"),
    @NamedQuery(name = "Amigos.findByFechaAmistad", query = "SELECT a FROM Amigos a WHERE a.fechaAmistad = :fechaAmistad")})
public class Amigos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_amistad")
    private Integer idAmistad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aprobado")
    private boolean aprobado;
    @Column(name = "fecha_amistad")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAmistad;
    @JoinColumn(name = "id_usuario1", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idUsuario1;
    @JoinColumn(name = "id_usuario2", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idUsuario2;

    public Amigos() {
    }

    public Amigos(Integer idAmistad) {
        this.idAmistad = idAmistad;
    }

    public Amigos(Integer idAmistad, boolean aprobado) {
        this.idAmistad = idAmistad;
        this.aprobado = aprobado;
    }

    public Integer getIdAmistad() {
        return idAmistad;
    }

    public void setIdAmistad(Integer idAmistad) {
        this.idAmistad = idAmistad;
    }

    public boolean getAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }

    public Date getFechaAmistad() {
        return fechaAmistad;
    }

    public void setFechaAmistad(Date fechaAmistad) {
        this.fechaAmistad = fechaAmistad;
    }

    public Usuario getIdUsuario1() {
        return idUsuario1;
    }

    public void setIdUsuario1(Usuario idUsuario1) {
        this.idUsuario1 = idUsuario1;
    }

    public Usuario getIdUsuario2() {
        return idUsuario2;
    }

    public void setIdUsuario2(Usuario idUsuario2) {
        this.idUsuario2 = idUsuario2;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAmistad != null ? idAmistad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Amigos)) {
            return false;
        }
        Amigos other = (Amigos) object;
        if ((this.idAmistad == null && other.idAmistad != null) || (this.idAmistad != null && !this.idAmistad.equals(other.idAmistad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.Amigos[ idAmistad=" + idAmistad + " ]";
    }
    
}
