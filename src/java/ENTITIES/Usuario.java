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
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
     @NamedQuery(name = "Usuario.logIn", query = "SELECT u FROM Usuario u WHERE  u.username = :username AND u.password =:password"),
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByIdUsuario", query = "SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario"),
    @NamedQuery(name = "Usuario.findByUsername", query = "SELECT u FROM Usuario u WHERE u.username = :username"),
    @NamedQuery(name = "Usuario.findByPassword", query = "SELECT u FROM Usuario u WHERE u.password = :password"),
    @NamedQuery(name = "Usuario.findByFechaCreacion", query = "SELECT u FROM Usuario u WHERE u.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Usuario.findByCorreo", query = "SELECT u FROM Usuario u WHERE u.correo = :correo"),
    @NamedQuery(name = "Usuario.findByCreditos", query = "SELECT u FROM Usuario u WHERE u.creditos = :creditos")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @Size(max = 50)
    @Column(name = "correo")
    private String correo;
    @Column(name = "creditos")
    private Integer creditos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "imagen_portada_perfil")
    private String imagen_portada_perfil;
    @Basic(optional = false)
    
    @Size(min = 1, max = 200)
    @Column(name = "imagen_dashboard")
    private String imagen_dashboard;
    @Basic(optional = false)
    
    @Size(min = 1, max = 200)
    @Column(name = "imagen_foto_perfil")
    private String imagen_foto_perfil;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<Sesion> sesionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPublicador")
    private List<PublicacionPerfil> publicacionPerfilList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<PublicacionPerfil> publicacionPerfilList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario1")
    private List<Amigos> amigosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario2")
    private List<Amigos> amigosList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser")
    private List<ForoPosteos> foroPosteosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<Persona> personaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<LogStreaming> logStreamingList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<MasterRespuestas> masterRespuestasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<InscripcionCurso> inscripcionCursoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<Notificaciones> notificacionesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<Curso> cursoList;
    @JoinColumn(name = "id_perfil", referencedColumnName = "id_perfil")
    @ManyToOne(optional = false)
    private Perfil idPerfil;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmisor")
    private List<Mensaje> mensajeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idReceptor")
    private List<Mensaje> mensajeList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<Tablero> tableroList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<MasterComentario> masterComentarioList;

    public Usuario() {
    }

    public Usuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario(Integer idUsuario, String username, String password, Date fechaCreacion, String imagen_portada_perfil, String imagen_dashboard, String imagen_foto_perfil) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.password = password;
        this.fechaCreacion = fechaCreacion;
        this.imagen_portada_perfil = imagen_portada_perfil;
        this.imagen_dashboard = imagen_dashboard;
        this.imagen_foto_perfil = imagen_foto_perfil;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }
    
    public String getImagen_portada_perfil() {
        return imagen_portada_perfil;
    }

    public void setImagen_portada_perfil(String imagen_portada_perfil) {
        this.imagen_portada_perfil = imagen_portada_perfil;
    }

    public String getImagen_dashboard() {
        return imagen_dashboard;
    }

    public void setImagen_dashboard(String imagen_dashboard) {
        this.imagen_dashboard = imagen_dashboard;
    }

    public String getImagen_foto_perfil() {
        return imagen_foto_perfil;
    }

    public void setImagen_foto_perfil(String imagen_foto_perfil) {
        this.imagen_foto_perfil = imagen_foto_perfil;
    }

    @XmlTransient
    public List<Sesion> getSesionList() {
        return sesionList;
    }

    public void setSesionList(List<Sesion> sesionList) {
        this.sesionList = sesionList;
    }

    @XmlTransient
    public List<PublicacionPerfil> getPublicacionPerfilList() {
        return publicacionPerfilList;
    }

    public void setPublicacionPerfilList(List<PublicacionPerfil> publicacionPerfilList) {
        this.publicacionPerfilList = publicacionPerfilList;
    }

    @XmlTransient
    public List<PublicacionPerfil> getPublicacionPerfilList1() {
        return publicacionPerfilList1;
    }

    public void setPublicacionPerfilList1(List<PublicacionPerfil> publicacionPerfilList1) {
        this.publicacionPerfilList1 = publicacionPerfilList1;
    }

    @XmlTransient
    public List<Amigos> getAmigosList() {
        return amigosList;
    }

    public void setAmigosList(List<Amigos> amigosList) {
        this.amigosList = amigosList;
    }

    @XmlTransient
    public List<Amigos> getAmigosList1() {
        return amigosList1;
    }

    public void setAmigosList1(List<Amigos> amigosList1) {
        this.amigosList1 = amigosList1;
    }

    @XmlTransient
    public List<ForoPosteos> getForoPosteosList() {
        return foroPosteosList;
    }

    public void setForoPosteosList(List<ForoPosteos> foroPosteosList) {
        this.foroPosteosList = foroPosteosList;
    }

    @XmlTransient
    public List<Persona> getPersonaList() {
        return personaList;
    }

    public void setPersonaList(List<Persona> personaList) {
        this.personaList = personaList;
    }

    @XmlTransient
    public List<LogStreaming> getLogStreamingList() {
        return logStreamingList;
    }

    public void setLogStreamingList(List<LogStreaming> logStreamingList) {
        this.logStreamingList = logStreamingList;
    }

    @XmlTransient
    public List<MasterRespuestas> getMasterRespuestasList() {
        return masterRespuestasList;
    }

    public void setMasterRespuestasList(List<MasterRespuestas> masterRespuestasList) {
        this.masterRespuestasList = masterRespuestasList;
    }

    @XmlTransient
    public List<InscripcionCurso> getInscripcionCursoList() {
        return inscripcionCursoList;
    }

    public void setInscripcionCursoList(List<InscripcionCurso> inscripcionCursoList) {
        this.inscripcionCursoList = inscripcionCursoList;
    }

    @XmlTransient
    public List<Notificaciones> getNotificacionesList() {
        return notificacionesList;
    }

    public void setNotificacionesList(List<Notificaciones> notificacionesList) {
        this.notificacionesList = notificacionesList;
    }

    @XmlTransient
    public List<Curso> getCursoList() {
        return cursoList;
    }

    public void setCursoList(List<Curso> cursoList) {
        this.cursoList = cursoList;
    }

    public Perfil getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Perfil idPerfil) {
        this.idPerfil = idPerfil;
    }

    @XmlTransient
    public List<Mensaje> getMensajeList() {
        return mensajeList;
    }

    public void setMensajeList(List<Mensaje> mensajeList) {
        this.mensajeList = mensajeList;
    }

    @XmlTransient
    public List<Mensaje> getMensajeList1() {
        return mensajeList1;
    }

    public void setMensajeList1(List<Mensaje> mensajeList1) {
        this.mensajeList1 = mensajeList1;
    }

    @XmlTransient
    public List<Tablero> getTableroList() {
        return tableroList;
    }

    public void setTableroList(List<Tablero> tableroList) {
        this.tableroList = tableroList;
    }

    @XmlTransient
    public List<MasterComentario> getMasterComentarioList() {
        return masterComentarioList;
    }

    public void setMasterComentarioList(List<MasterComentario> masterComentarioList) {
        this.masterComentarioList = masterComentarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.Usuario[ idUsuario=" + idUsuario + " ]";
    }
    
}
