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
import javax.persistence.Lob;
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
@Table(name = "master_comentario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MasterComentario.findAll", query = "SELECT m FROM MasterComentario m"),
    @NamedQuery(name = "MasterComentario.findByIdComentario", query = "SELECT m FROM MasterComentario m WHERE m.idComentario = :idComentario"),
    @NamedQuery(name = "MasterComentario.findByFechaComentario", query = "SELECT m FROM MasterComentario m WHERE m.fechaComentario = :fechaComentario"),
    @NamedQuery(name = "MasterComentario.buscaComentario", query = "SELECT m FROM MasterComentario m WHERE m.idPublicacion = :idPublicacion AND m.idPft.idPft = :idPtf"),
    @NamedQuery(name = "MasterComentario.buscaComentarioPorID", query = "SELECT m FROM MasterComentario m WHERE m.idPft.idPft = :idPtf"),
    @NamedQuery(name = "MasterComentario.findByIdPublicacion", query = "SELECT m FROM MasterComentario m WHERE m.idPublicacion = :idPublicacion")})

public class MasterComentario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_comentario")
    private Integer idComentario;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "comentario")
    private String comentario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_comentario")
    @Temporal(TemporalType.DATE)
    private Date fechaComentario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_publicacion")
    private int idPublicacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idComentario")
    private List<MasterRespuestas> masterRespuestasList;
    @JoinColumn(name = "id_pft", referencedColumnName = "id_pft")
    @ManyToOne(optional = false)
    private MasterPft idPft;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idUsuario;

    public MasterComentario() {
    }

    public MasterComentario(Integer idComentario) {
        this.idComentario = idComentario;
    }

    public MasterComentario(Integer idComentario, String comentario, Date fechaComentario, int idPublicacion) {
        this.idComentario = idComentario;
        this.comentario = comentario;
        this.fechaComentario = fechaComentario;
        this.idPublicacion = idPublicacion;
    }

    public Integer getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(Integer idComentario) {
        this.idComentario = idComentario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFechaComentario() {
        return fechaComentario;
    }

    public void setFechaComentario(Date fechaComentario) {
        this.fechaComentario = fechaComentario;
    }

    public int getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(int idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    @XmlTransient
    public List<MasterRespuestas> getMasterRespuestasList() {
        return masterRespuestasList;
    }

    public void setMasterRespuestasList(List<MasterRespuestas> masterRespuestasList) {
        this.masterRespuestasList = masterRespuestasList;
    }

    public MasterPft getIdPft() {
        return idPft;
    }

    public void setIdPft(MasterPft idPft) {
        this.idPft = idPft;
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
        hash += (idComentario != null ? idComentario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterComentario)) {
            return false;
        }
        MasterComentario other = (MasterComentario) object;
        if ((this.idComentario == null && other.idComentario != null) || (this.idComentario != null && !this.idComentario.equals(other.idComentario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.MasterComentario[ idComentario=" + idComentario + " ]";
    }
    
}
