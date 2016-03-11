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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Felipe
 */
@Entity
@Table(name = "tipo_archivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoArchivo.findAll", query = "SELECT t FROM TipoArchivo t"),
    @NamedQuery(name = "TipoArchivo.findByIdTipo", query = "SELECT t FROM TipoArchivo t WHERE t.idTipo = :idTipo"),
    @NamedQuery(name = "TipoArchivo.findByNomTipo", query = "SELECT t FROM TipoArchivo t WHERE t.nomTipo = :nomTipo")})
public class TipoArchivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo")
    private Integer idTipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nom_tipo")
    private String nomTipo;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoArchivo")
    private List<Archivo> archivoList;

    public TipoArchivo() {
    }

    public TipoArchivo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public TipoArchivo(Integer idTipo, String nomTipo, String descripcion) {
        this.idTipo = idTipo;
        this.nomTipo = nomTipo;
        this.descripcion = descripcion;
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public String getNomTipo() {
        return nomTipo;
    }

    public void setNomTipo(String nomTipo) {
        this.nomTipo = nomTipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Archivo> getArchivoList() {
        return archivoList;
    }

    public void setArchivoList(List<Archivo> archivoList) {
        this.archivoList = archivoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipo != null ? idTipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoArchivo)) {
            return false;
        }
        TipoArchivo other = (TipoArchivo) object;
        if ((this.idTipo == null && other.idTipo != null) || (this.idTipo != null && !this.idTipo.equals(other.idTipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.TipoArchivo[ idTipo=" + idTipo + " ]";
    }
    
}
