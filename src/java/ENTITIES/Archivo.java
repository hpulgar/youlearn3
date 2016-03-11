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
@Table(name = "archivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Archivo.findAll", query = "SELECT a FROM Archivo a"),
    @NamedQuery(name = "Archivo.findByIdArchivo", query = "SELECT a FROM Archivo a WHERE a.idArchivo = :idArchivo"),
    @NamedQuery(name = "Archivo.findByNomArchivo", query = "SELECT a FROM Archivo a WHERE a.nomArchivo = :nomArchivo"),
    @NamedQuery(name = "Archivo.findByUbicacion", query = "SELECT a FROM Archivo a WHERE a.ubicacion = :ubicacion"),
    @NamedQuery(name = "Archivo.findByAutorizado", query = "SELECT a FROM Archivo a WHERE a.autorizado = :autorizado")})
public class Archivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_archivo")
    private Integer idArchivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nom_archivo")
    private String nomArchivo;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "ubicacion")
    private String ubicacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Autorizado")
    private boolean autorizado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idArchivo")
    private List<LogSubidas> logSubidasList;
    @JoinColumn(name = "id_curso", referencedColumnName = "id_curso")
    @ManyToOne(optional = false)
    private Curso idCurso;
    @JoinColumn(name = "id_tipo_archivo", referencedColumnName = "id_tipo")
    @ManyToOne(optional = false)
    private TipoArchivo idTipoArchivo;

    public Archivo() {
    }

    public Archivo(Integer idArchivo) {
        this.idArchivo = idArchivo;
    }

    public Archivo(Integer idArchivo, String nomArchivo, String descripcion, String ubicacion, boolean autorizado) {
        this.idArchivo = idArchivo;
        this.nomArchivo = nomArchivo;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.autorizado = autorizado;
    }

    public Integer getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(Integer idArchivo) {
        this.idArchivo = idArchivo;
    }

    public String getNomArchivo() {
        return nomArchivo;
    }

    public void setNomArchivo(String nomArchivo) {
        this.nomArchivo = nomArchivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public boolean getAutorizado() {
        return autorizado;
    }

    public void setAutorizado(boolean autorizado) {
        this.autorizado = autorizado;
    }

    @XmlTransient
    public List<LogSubidas> getLogSubidasList() {
        return logSubidasList;
    }

    public void setLogSubidasList(List<LogSubidas> logSubidasList) {
        this.logSubidasList = logSubidasList;
    }

    public Curso getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Curso idCurso) {
        this.idCurso = idCurso;
    }

    public TipoArchivo getIdTipoArchivo() {
        return idTipoArchivo;
    }

    public void setIdTipoArchivo(TipoArchivo idTipoArchivo) {
        this.idTipoArchivo = idTipoArchivo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArchivo != null ? idArchivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Archivo)) {
            return false;
        }
        Archivo other = (Archivo) object;
        if ((this.idArchivo == null && other.idArchivo != null) || (this.idArchivo != null && !this.idArchivo.equals(other.idArchivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.Archivo[ idArchivo=" + idArchivo + " ]";
    }
    
}
