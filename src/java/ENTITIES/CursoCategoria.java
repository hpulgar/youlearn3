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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Felipe
 */
@Entity
@Table(name = "curso_categoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CursoCategoria.findAll", query = "SELECT c FROM CursoCategoria c"),
    @NamedQuery(name = "CursoCategoria.findByIdCat", query = "SELECT c FROM CursoCategoria c WHERE c.idCat = :idCat"),
    @NamedQuery(name = "CursoCategoria.findByNomCat", query = "SELECT c FROM CursoCategoria c WHERE c.nomCat = :nomCat")})
public class CursoCategoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cat")
    private Integer idCat;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nom_cat")
    private String nomCat;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCategoria")
    private List<CursoSubCat> cursoSubCatList;

    public CursoCategoria() {
    }

    public CursoCategoria(Integer idCat) {
        this.idCat = idCat;
    }

    public CursoCategoria(Integer idCat, String nomCat) {
        this.idCat = idCat;
        this.nomCat = nomCat;
    }

    public Integer getIdCat() {
        return idCat;
    }

    public void setIdCat(Integer idCat) {
        this.idCat = idCat;
    }

    public String getNomCat() {
        return nomCat;
    }

    public void setNomCat(String nomCat) {
        this.nomCat = nomCat;
    }

    @XmlTransient
    public List<CursoSubCat> getCursoSubCatList() {
        return cursoSubCatList;
    }

    public void setCursoSubCatList(List<CursoSubCat> cursoSubCatList) {
        this.cursoSubCatList = cursoSubCatList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCat != null ? idCat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CursoCategoria)) {
            return false;
        }
        CursoCategoria other = (CursoCategoria) object;
        if ((this.idCat == null && other.idCat != null) || (this.idCat != null && !this.idCat.equals(other.idCat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.CursoCategoria[ idCat=" + idCat + " ]";
    }
    
}
