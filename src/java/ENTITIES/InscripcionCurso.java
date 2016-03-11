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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Felipe
 */
@Entity
@Table(name = "inscripcion_curso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InscripcionCurso.findAll", query = "SELECT i FROM InscripcionCurso i"),
    @NamedQuery(name = "InscripcionCurso.findByIdInsc", query = "SELECT i FROM InscripcionCurso i WHERE i.idInsc = :idInsc"),
    @NamedQuery(name = "InscripcionCurso.findByFechaInsc", query = "SELECT i FROM InscripcionCurso i WHERE i.fechaInsc = :fechaInsc")})
public class InscripcionCurso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_insc")
    private Integer idInsc;
    @Column(name = "fecha_insc")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsc;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "id_curso", referencedColumnName = "id_curso")
    @ManyToOne(optional = false)
    private Curso idCurso;
    @JoinColumn(name = "tipo_alumno", referencedColumnName = "id_tipo")
    @ManyToOne(optional = false)
    private TipoAlumno tipoAlumno;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idUsuario;

    public InscripcionCurso() {
    }

    public InscripcionCurso(Integer idInsc) {
        this.idInsc = idInsc;
    }

    public Integer getIdInsc() {
        return idInsc;
    }

    public void setIdInsc(Integer idInsc) {
        this.idInsc = idInsc;
    }

    public Date getFechaInsc() {
        return fechaInsc;
    }

    public void setFechaInsc(Date fechaInsc) {
        this.fechaInsc = fechaInsc;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Curso getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Curso idCurso) {
        this.idCurso = idCurso;
    }

    public TipoAlumno getTipoAlumno() {
        return tipoAlumno;
    }

    public void setTipoAlumno(TipoAlumno tipoAlumno) {
        this.tipoAlumno = tipoAlumno;
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
        hash += (idInsc != null ? idInsc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InscripcionCurso)) {
            return false;
        }
        InscripcionCurso other = (InscripcionCurso) object;
        if ((this.idInsc == null && other.idInsc != null) || (this.idInsc != null && !this.idInsc.equals(other.idInsc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.InscripcionCurso[ idInsc=" + idInsc + " ]";
    }
    
}
