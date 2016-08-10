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
 * @author Zotindows
 */
@Entity
@Table(name = "mensaje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mensaje.chat", query = "SELECT m FROM Mensaje m WHERE m.idReceptor.idUsuario = :idUsuario and m.idEmisor.idUsuario = :idAmigo or m.idReceptor.idUsuario = :idAmigo and m.idEmisor.idUsuario = :idUsuario order by m.fechaEnvio desc"),
    @NamedQuery(name = "Mensaje.findAll", query = "SELECT m FROM Mensaje m"),
    @NamedQuery(name = "Mensaje.findByIdMensaje", query = "SELECT m FROM Mensaje m WHERE m.idMensaje = :idMensaje"),
    @NamedQuery(name = "Mensaje.findByFechaEnvio", query = "SELECT m FROM Mensaje m WHERE m.fechaEnvio = :fechaEnvio")})
public class Mensaje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_mensaje")
    private Integer idMensaje;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "contenido")
    private String contenido;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_envio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEnvio;
    @JoinColumn(name = "id_emisor", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idEmisor;
    @JoinColumn(name = "id_receptor", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idReceptor;

    public Mensaje() {
    }

    public Mensaje(Integer idMensaje) {
        this.idMensaje = idMensaje;
    }

    public Mensaje(Integer idMensaje, String contenido, Date fechaEnvio) {
        this.idMensaje = idMensaje;
        this.contenido = contenido;
        this.fechaEnvio = fechaEnvio;
    }

    public Integer getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(Integer idMensaje) {
        this.idMensaje = idMensaje;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Usuario getIdEmisor() {
        return idEmisor;
    }

    public void setIdEmisor(Usuario idEmisor) {
        this.idEmisor = idEmisor;
    }

    public Usuario getIdReceptor() {
        return idReceptor;
    }

    public void setIdReceptor(Usuario idReceptor) {
        this.idReceptor = idReceptor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMensaje != null ? idMensaje.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mensaje)) {
            return false;
        }
        Mensaje other = (Mensaje) object;
        if ((this.idMensaje == null && other.idMensaje != null) || (this.idMensaje != null && !this.idMensaje.equals(other.idMensaje))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.Mensaje[ idMensaje=" + idMensaje + " ]";
    }
    
}
