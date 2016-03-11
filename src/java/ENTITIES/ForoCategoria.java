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
import javax.persistence.Lob;
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
@Table(name = "foro_categoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ForoCategoria.findAll", query = "SELECT f FROM ForoCategoria f"),
    @NamedQuery(name = "ForoCategoria.findByIdCategoria", query = "SELECT f FROM ForoCategoria f WHERE f.idCategoria = :idCategoria"),
    @NamedQuery(name = "ForoCategoria.findByCateFecha", query = "SELECT f FROM ForoCategoria f WHERE f.cateFecha = :cateFecha")})
public class ForoCategoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_categoria")
    private Integer idCategoria;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cate_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cateFecha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCategoria")
    private List<ForoSubcategoria> foroSubcategoriaList;

    public ForoCategoria() {
    }

    public ForoCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public ForoCategoria(Integer idCategoria, String titulo, String descripcion, Date cateFecha) {
        this.idCategoria = idCategoria;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.cateFecha = cateFecha;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getCateFecha() {
        return cateFecha;
    }

    public void setCateFecha(Date cateFecha) {
        this.cateFecha = cateFecha;
    }

    @XmlTransient
    public List<ForoSubcategoria> getForoSubcategoriaList() {
        return foroSubcategoriaList;
    }

    public void setForoSubcategoriaList(List<ForoSubcategoria> foroSubcategoriaList) {
        this.foroSubcategoriaList = foroSubcategoriaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCategoria != null ? idCategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ForoCategoria)) {
            return false;
        }
        ForoCategoria other = (ForoCategoria) object;
        if ((this.idCategoria == null && other.idCategoria != null) || (this.idCategoria != null && !this.idCategoria.equals(other.idCategoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.ForoCategoria[ idCategoria=" + idCategoria + " ]";
    }
    
}
