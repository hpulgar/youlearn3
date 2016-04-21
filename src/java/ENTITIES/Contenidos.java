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
@Table(name = "contenidos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contenidos.findAll", query = "SELECT c FROM Contenidos c"),
    @NamedQuery(name = "Contenidos.findByIdContenido", query = "SELECT c FROM Contenidos c WHERE c.idContenido = :idContenido"),
    @NamedQuery(name = "Contenidos.findByUnidad", query = "SELECT c FROM Contenidos c WHERE c.unidad = :unidad"),
    @NamedQuery(name = "Contenidos.findByNombreUnidad", query = "SELECT c FROM Contenidos c WHERE c.nombreUnidad = :nombreUnidad"),
    @NamedQuery(name = "Contenidos.findByFecha", query = "SELECT c FROM Contenidos c WHERE c.fecha = :fecha")})
public class Contenidos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_contenido")
    private Integer idContenido;
    @Basic(optional = false)
    @NotNull
    @Column(name = "unidad")
    private int unidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre_unidad")
    private String nombreUnidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "contenido")
    private String contenido;
    @JoinColumn(name = "id_curso", referencedColumnName = "id_curso")
    @ManyToOne(optional = false)
    private Curso idCurso;

    public Contenidos() {
    }

    public Contenidos(Integer idContenido) {
        this.idContenido = idContenido;
    }

    public Contenidos(Integer idContenido, int unidad, String nombreUnidad, Date fecha, String contenido) {
        this.idContenido = idContenido;
        this.unidad = unidad;
        this.nombreUnidad = nombreUnidad;
        this.fecha = fecha;
        this.contenido = contenido;
    }

    public Integer getIdContenido() {
        return idContenido;
    }

    public void setIdContenido(Integer idContenido) {
        this.idContenido = idContenido;
    }

    public int getUnidad() {
        return unidad;
    }

    public void setUnidad(int unidad) {
        this.unidad = unidad;
    }

    public String getNombreUnidad() {
        return nombreUnidad;
    }

    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Curso getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Curso idCurso) {
        this.idCurso = idCurso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idContenido != null ? idContenido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contenidos)) {
            return false;
        }
        Contenidos other = (Contenidos) object;
        if ((this.idContenido == null && other.idContenido != null) || (this.idContenido != null && !this.idContenido.equals(other.idContenido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.Contenidos[ idContenido=" + idContenido + " ]";
    }
    
}
