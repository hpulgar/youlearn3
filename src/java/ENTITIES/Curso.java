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
@Table(name = "curso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Curso.findAll", query = "SELECT c FROM Curso c"),
    @NamedQuery(name = "Curso.findByIdCurso", query = "SELECT c FROM Curso c WHERE c.idCurso = :idCurso"),
    @NamedQuery(name = "Curso.findByNomCurso", query = "SELECT c FROM Curso c WHERE c.nomCurso = :nomCurso"),
    @NamedQuery(name = "Curso.findByPersonasInscritas", query = "SELECT c FROM Curso c WHERE c.personasInscritas = :personasInscritas"),
    @NamedQuery(name = "Curso.findByAutorizado", query = "SELECT c FROM Curso c WHERE c.autorizado = :autorizado"),
    @NamedQuery(name = "Curso.findBySeguidores", query = "SELECT c FROM Curso c WHERE c.seguidores = :seguidores")})
public class Curso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_curso")
    private Integer idCurso;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nom_curso")
    private String nomCurso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "personas_inscritas")
    private int personasInscritas;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion_curso")
    private String descripcionCurso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Autorizado")
    private boolean autorizado;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "contenidos")
    private String contenidos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "seguidores")
    private int seguidores;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCurso")
    private List<Archivo> archivoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCurso")
    private List<LogStreaming> logStreamingList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCurso")
    private List<InscripcionCurso> inscripcionCursoList;
    @JoinColumn(name = "id_cat", referencedColumnName = "id_subcat")
    @ManyToOne(optional = false)
    private CursoSubCat idCat;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCurso")
    private List<Tablero> tableroList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCurso")
    private List<PreguntasData> preguntasDataList;

    public Curso() {
    }

    public Curso(Integer idCurso) {
        this.idCurso = idCurso;
    }

    public Curso(Integer idCurso, String nomCurso, int personasInscritas, String descripcionCurso, boolean autorizado, String contenidos, int seguidores) {
        this.idCurso = idCurso;
        this.nomCurso = nomCurso;
        this.personasInscritas = personasInscritas;
        this.descripcionCurso = descripcionCurso;
        this.autorizado = autorizado;
        this.contenidos = contenidos;
        this.seguidores = seguidores;
    }

    public Integer getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Integer idCurso) {
        this.idCurso = idCurso;
    }

    public String getNomCurso() {
        return nomCurso;
    }

    public void setNomCurso(String nomCurso) {
        this.nomCurso = nomCurso;
    }

    public int getPersonasInscritas() {
        return personasInscritas;
    }

    public void setPersonasInscritas(int personasInscritas) {
        this.personasInscritas = personasInscritas;
    }

    public String getDescripcionCurso() {
        return descripcionCurso;
    }

    public void setDescripcionCurso(String descripcionCurso) {
        this.descripcionCurso = descripcionCurso;
    }

    public boolean getAutorizado() {
        return autorizado;
    }

    public void setAutorizado(boolean autorizado) {
        this.autorizado = autorizado;
    }

    public String getContenidos() {
        return contenidos;
    }

    public void setContenidos(String contenidos) {
        this.contenidos = contenidos;
    }

    public int getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(int seguidores) {
        this.seguidores = seguidores;
    }

    @XmlTransient
    public List<Archivo> getArchivoList() {
        return archivoList;
    }

    public void setArchivoList(List<Archivo> archivoList) {
        this.archivoList = archivoList;
    }

    @XmlTransient
    public List<LogStreaming> getLogStreamingList() {
        return logStreamingList;
    }

    public void setLogStreamingList(List<LogStreaming> logStreamingList) {
        this.logStreamingList = logStreamingList;
    }

    @XmlTransient
    public List<InscripcionCurso> getInscripcionCursoList() {
        return inscripcionCursoList;
    }

    public void setInscripcionCursoList(List<InscripcionCurso> inscripcionCursoList) {
        this.inscripcionCursoList = inscripcionCursoList;
    }

    public CursoSubCat getIdCat() {
        return idCat;
    }

    public void setIdCat(CursoSubCat idCat) {
        this.idCat = idCat;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @XmlTransient
    public List<Tablero> getTableroList() {
        return tableroList;
    }

    public void setTableroList(List<Tablero> tableroList) {
        this.tableroList = tableroList;
    }

    @XmlTransient
    public List<PreguntasData> getPreguntasDataList() {
        return preguntasDataList;
    }

    public void setPreguntasDataList(List<PreguntasData> preguntasDataList) {
        this.preguntasDataList = preguntasDataList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCurso != null ? idCurso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Curso)) {
            return false;
        }
        Curso other = (Curso) object;
        if ((this.idCurso == null && other.idCurso != null) || (this.idCurso != null && !this.idCurso.equals(other.idCurso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.Curso[ idCurso=" + idCurso + " ]";
    }
    
}
