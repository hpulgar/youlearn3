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
@Table(name = "curso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Curso.findAll", query = "SELECT c FROM Curso c"),
    @NamedQuery(name = "Curso.subcategoria", query = "SELECT c FROM Curso c WHERE c.idCat.idSubcat LIKE :idSubCat"),
    @NamedQuery(name = "Curso.name", query = "SELECT c.nomCurso FROM Curso c WHERE c.idCurso = :idCurso "),
    @NamedQuery(name = "Curso.buscarIdCurso", query = "SELECT c FROM Curso c WHERE c.idCurso = :idCurso"),
    @NamedQuery(name = "Curso.findByNomCurso", query = "SELECT c FROM Curso c WHERE c.nomCurso = :nomCurso"),
    @NamedQuery(name = "Curso.nombres", query = "SELECT c.nomCurso FROM Curso c WHERE c.nomCurso = :nomCurso"),
    @NamedQuery(name = "Curso.findByPersonasInscritas", query = "SELECT c FROM Curso c WHERE c.personasInscritas = :personasInscritas"),
    @NamedQuery(name = "Curso.findByAutorizado", query = "SELECT c FROM Curso c WHERE c.autorizado = :autorizado"),
    @NamedQuery(name = "Curso.findBySeguidores", query = "SELECT c FROM Curso c WHERE c.seguidores = :seguidores"),
    @NamedQuery(name = "Curso.findByIntroduccionCurso", query = "SELECT c FROM Curso c WHERE c.introduccionCurso = :introduccionCurso"),
    @NamedQuery(name = "Curso.findByImagenPortadaCurso", query = "SELECT c FROM Curso c WHERE c.imagenPortadaCurso = :imagenPortadaCurso"),
    @NamedQuery(name = "Curso.findByFecha", query = "SELECT c FROM Curso c WHERE c.fecha = :fecha")})
public class Curso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_curso")
    private Integer idCurso;
    @Basic(optional = false)
    @NotNull
    
    @Size(min = 1, max = 200)
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
    
    @Basic(optional = false)
    @Size(min = 1, max = 65535)
    @Column(name = "introduccion_curso")
    private String introduccionCurso;
    
    @Size(max = 200)
    @Column(name = "imagen_portada_curso")
    private String imagenPortadaCurso;
   
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCurso")
    private List<Contenidos> contenidosList;
    @JoinColumn(name = "id_cat", referencedColumnName = "id_subcat")
    @ManyToOne(optional = false)
    private CursoSubCat idCat;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idUsuario;

    public Curso() {
    }

    public Curso(Integer idCurso) {
        this.idCurso = idCurso;
    }

   public Curso(Integer idCurso, String nomCurso, int personasInscritas, String descripcionCurso, boolean autorizado, String contenidos, int seguidores, String imagen_portada_curso
    , String introduccion_curso, Date fecha) {
        this.idCurso = idCurso;
        this.nomCurso = nomCurso;
        this.personasInscritas = personasInscritas;
        this.descripcionCurso = descripcionCurso;
        this.autorizado = autorizado;
        this.contenidos = contenidos;
        this.seguidores = seguidores;
        this.imagenPortadaCurso = imagen_portada_curso;
        this.introduccionCurso = introduccion_curso;
        this.fecha = fecha;
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

    public String getIntroduccionCurso() {
        return introduccionCurso;
    }

    public void setIntroduccionCurso(String introduccionCurso) {
        this.introduccionCurso = introduccionCurso;
    }

    public String getImagenPortadaCurso() {
        return imagenPortadaCurso;
    }

    public void setImagenPortadaCurso(String imagenPortadaCurso) {
        this.imagenPortadaCurso = imagenPortadaCurso;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @XmlTransient
    public List<Contenidos> getContenidosList() {
        return contenidosList;
    }

    public void setContenidosList(List<Contenidos> contenidosList) {
        this.contenidosList = contenidosList;
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
