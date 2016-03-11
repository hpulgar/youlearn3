/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ENTITIES;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Felipe
 */
@Entity
@Table(name = "master_pft")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MasterPft.findAll", query = "SELECT m FROM MasterPft m"),
    @NamedQuery(name = "MasterPft.findByIdPft", query = "SELECT m FROM MasterPft m WHERE m.idPft = :idPft"),
    @NamedQuery(name = "MasterPft.findByDescripcion", query = "SELECT m FROM MasterPft m WHERE m.descripcion = :descripcion")})
public class MasterPft implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pft")
    private Integer idPft;
    @Size(max = 45)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPft")
    private List<MasterComentario> masterComentarioList;

    public MasterPft() {
    }

    public MasterPft(Integer idPft) {
        this.idPft = idPft;
    }

    public Integer getIdPft() {
        return idPft;
    }

    public void setIdPft(Integer idPft) {
        this.idPft = idPft;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<MasterComentario> getMasterComentarioList() {
        return masterComentarioList;
    }

    public void setMasterComentarioList(List<MasterComentario> masterComentarioList) {
        this.masterComentarioList = masterComentarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPft != null ? idPft.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterPft)) {
            return false;
        }
        MasterPft other = (MasterPft) object;
        if ((this.idPft == null && other.idPft != null) || (this.idPft != null && !this.idPft.equals(other.idPft))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.MasterPft[ idPft=" + idPft + " ]";
    }
    
}
