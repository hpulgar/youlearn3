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
@Table(name = "foro_subcategoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ForoSubcategoria.findAll", query = "SELECT f FROM ForoSubcategoria f"),
    @NamedQuery(name = "ForoSubcategoria.findByIdSubcategoria", query = "SELECT f FROM ForoSubcategoria f WHERE f.idSubcategoria = :idSubcategoria"),
    @NamedQuery(name = "ForoSubcategoria.findBySubcateFecha", query = "SELECT f FROM ForoSubcategoria f WHERE f.subcateFecha = :subcateFecha")})
public class ForoSubcategoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_subcategoria")
    private Integer idSubcategoria;
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
    @Column(name = "subcate_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date subcateFecha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSubcategoria")
    private List<ForoPosteos> foroPosteosList;
    @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria")
    @ManyToOne(optional = false)
    private ForoCategoria idCategoria;

    public ForoSubcategoria() {
    }

    public ForoSubcategoria(Integer idSubcategoria) {
        this.idSubcategoria = idSubcategoria;
    }

    public ForoSubcategoria(Integer idSubcategoria, String titulo, String descripcion, Date subcateFecha) {
        this.idSubcategoria = idSubcategoria;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.subcateFecha = subcateFecha;
    }

    public Integer getIdSubcategoria() {
        return idSubcategoria;
    }

    public void setIdSubcategoria(Integer idSubcategoria) {
        this.idSubcategoria = idSubcategoria;
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

    public Date getSubcateFecha() {
        return subcateFecha;
    }

    public void setSubcateFecha(Date subcateFecha) {
        this.subcateFecha = subcateFecha;
    }

    @XmlTransient
    public List<ForoPosteos> getForoPosteosList() {
        return foroPosteosList;
    }

    public void setForoPosteosList(List<ForoPosteos> foroPosteosList) {
        this.foroPosteosList = foroPosteosList;
    }

    public ForoCategoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(ForoCategoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSubcategoria != null ? idSubcategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ForoSubcategoria)) {
            return false;
        }
        ForoSubcategoria other = (ForoSubcategoria) object;
        if ((this.idSubcategoria == null && other.idSubcategoria != null) || (this.idSubcategoria != null && !this.idSubcategoria.equals(other.idSubcategoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.ForoSubcategoria[ idSubcategoria=" + idSubcategoria + " ]";
    }
    
}
