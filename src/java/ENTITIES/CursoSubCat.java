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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "curso_sub_cat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CursoSubCat.findAll", query = "SELECT c FROM CursoSubCat c"),
    @NamedQuery(name = "CursoSubCat.findByIdSubcat", query = "SELECT c FROM CursoSubCat c WHERE c.idSubcat = :idSubcat"),
    @NamedQuery(name = "CursoSubCat.findByNomSubcat", query = "SELECT c FROM CursoSubCat c WHERE c.nomSubcat = :nomSubcat")})
public class CursoSubCat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_subcat")
    private Integer idSubcat;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nom_subcat")
    private String nomSubcat;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "id_categoria", referencedColumnName = "id_cat")
    @ManyToOne(optional = false)
    private CursoCategoria idCategoria;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCat")
    private List<Curso> cursoList;

    public CursoSubCat() {
    }

    public CursoSubCat(Integer idSubcat) {
        this.idSubcat = idSubcat;
    }

    public CursoSubCat(Integer idSubcat, String nomSubcat, String descripcion) {
        this.idSubcat = idSubcat;
        this.nomSubcat = nomSubcat;
        this.descripcion = descripcion;
    }

    public Integer getIdSubcat() {
        return idSubcat;
    }

    public void setIdSubcat(Integer idSubcat) {
        this.idSubcat = idSubcat;
    }

    public String getNomSubcat() {
        return nomSubcat;
    }

    public void setNomSubcat(String nomSubcat) {
        this.nomSubcat = nomSubcat;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public CursoCategoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(CursoCategoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    @XmlTransient
    public List<Curso> getCursoList() {
        return cursoList;
    }

    public void setCursoList(List<Curso> cursoList) {
        this.cursoList = cursoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSubcat != null ? idSubcat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CursoSubCat)) {
            return false;
        }
        CursoSubCat other = (CursoSubCat) object;
        if ((this.idSubcat == null && other.idSubcat != null) || (this.idSubcat != null && !this.idSubcat.equals(other.idSubcat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.CursoSubCat[ idSubcat=" + idSubcat + " ]";
    }
    
}
