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
@Table(name = "tipo_alumno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoAlumno.findAll", query = "SELECT t FROM TipoAlumno t"),
    @NamedQuery(name = "TipoAlumno.findByIdTipo", query = "SELECT t FROM TipoAlumno t WHERE t.idTipo = :idTipo"),
    @NamedQuery(name = "TipoAlumno.findByNombreTipo", query = "SELECT t FROM TipoAlumno t WHERE t.nombreTipo = :nombreTipo")})
public class TipoAlumno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo")
    private Integer idTipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_tipo")
    private String nombreTipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoAlumno")
    private List<InscripcionCurso> inscripcionCursoList;

    public TipoAlumno() {
    }

    public TipoAlumno(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public TipoAlumno(Integer idTipo, String nombreTipo) {
        this.idTipo = idTipo;
        this.nombreTipo = nombreTipo;
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    @XmlTransient
    public List<InscripcionCurso> getInscripcionCursoList() {
        return inscripcionCursoList;
    }

    public void setInscripcionCursoList(List<InscripcionCurso> inscripcionCursoList) {
        this.inscripcionCursoList = inscripcionCursoList;
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
        if (!(object instanceof TipoAlumno)) {
            return false;
        }
        TipoAlumno other = (TipoAlumno) object;
        if ((this.idTipo == null && other.idTipo != null) || (this.idTipo != null && !this.idTipo.equals(other.idTipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.TipoAlumno[ idTipo=" + idTipo + " ]";
    }
    
}
