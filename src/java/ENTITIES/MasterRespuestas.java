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
@Table(name = "master_respuestas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MasterRespuestas.findAll", query = "SELECT m FROM MasterRespuestas m"),
    @NamedQuery(name = "MasterRespuestas.findByIdRespuestas", query = "SELECT m FROM MasterRespuestas m WHERE m.idRespuestas = :idRespuestas"),
    @NamedQuery(name = "MasterRespuestas.buscarIdComentario", query = "SELECT m FROM MasterRespuestas m WHERE m.idComentario.idComentario = :idComentario"),
    @NamedQuery(name = "MasterRespuestas.findByFechaRespuesta", query = "SELECT m FROM MasterRespuestas m WHERE m.fechaRespuesta = :fechaRespuesta")})
public class MasterRespuestas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_respuestas")
    private Integer idRespuestas;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "respuesta")
    private String respuesta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_respuesta")
    @Temporal(TemporalType.DATE)
    private Date fechaRespuesta;
    @JoinColumn(name = "id_comentario", referencedColumnName = "id_comentario")
    @ManyToOne(optional = false)
    private MasterComentario idComentario;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idUsuario;

    public MasterRespuestas() {
    }

    public MasterRespuestas(Integer idRespuestas) {
        this.idRespuestas = idRespuestas;
    }

    public MasterRespuestas(Integer idRespuestas, String respuesta, Date fechaRespuesta) {
        this.idRespuestas = idRespuestas;
        this.respuesta = respuesta;
        this.fechaRespuesta = fechaRespuesta;
    }

    public Integer getIdRespuestas() {
        return idRespuestas;
    }

    public void setIdRespuestas(Integer idRespuestas) {
        this.idRespuestas = idRespuestas;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public Date getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setFechaRespuesta(Date fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }

    public MasterComentario getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(MasterComentario idComentario) {
        this.idComentario = idComentario;
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
        hash += (idRespuestas != null ? idRespuestas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterRespuestas)) {
            return false;
        }
        MasterRespuestas other = (MasterRespuestas) object;
        if ((this.idRespuestas == null && other.idRespuestas != null) || (this.idRespuestas != null && !this.idRespuestas.equals(other.idRespuestas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.MasterRespuestas[ idRespuestas=" + idRespuestas + " ]";
    }
    
}
