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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Felipe
 */
@Entity
@Table(name = "publicacion_perfil")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PublicacionPerfil.findAll", query = "SELECT p FROM PublicacionPerfil p"),
    @NamedQuery(name = "PublicacionPerfil.findByIdPublicacion", query = "SELECT p FROM PublicacionPerfil p WHERE p.idPublicacion = :idPublicacion"),
    @NamedQuery(name = "PublicacionPerfil.findByPublicacion", query = "SELECT p FROM PublicacionPerfil p WHERE p.publicacion = :publicacion"),
    @NamedQuery(name = "PublicacionPerfil.findByFechaPublicacion", query = "SELECT p FROM PublicacionPerfil p WHERE p.fechaPublicacion = :fechaPublicacion")})
public class PublicacionPerfil implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_publicacion")
    private Integer idPublicacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "publicacion")
    private String publicacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_publicacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPublicacion;
    @JoinColumn(name = "id_publicador", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idPublicador;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idUsuario;

    public PublicacionPerfil() {
    }

    public PublicacionPerfil(Integer idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public PublicacionPerfil(Integer idPublicacion, String publicacion, Date fechaPublicacion) {
        this.idPublicacion = idPublicacion;
        this.publicacion = publicacion;
        this.fechaPublicacion = fechaPublicacion;
    }

    public Integer getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(Integer idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public String getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(String publicacion) {
        this.publicacion = publicacion;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Usuario getIdPublicador() {
        return idPublicador;
    }

    public void setIdPublicador(Usuario idPublicador) {
        this.idPublicador = idPublicador;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPublicacion != null ? idPublicacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PublicacionPerfil)) {
            return false;
        }
        PublicacionPerfil other = (PublicacionPerfil) object;
        if ((this.idPublicacion == null && other.idPublicacion != null) || (this.idPublicacion != null && !this.idPublicacion.equals(other.idPublicacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.PublicacionPerfil[ idPublicacion=" + idPublicacion + " ]";
    }
    
}
