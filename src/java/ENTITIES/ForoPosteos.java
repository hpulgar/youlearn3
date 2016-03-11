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
@Table(name = "foro_posteos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ForoPosteos.findAll", query = "SELECT f FROM ForoPosteos f"),
    @NamedQuery(name = "ForoPosteos.findByIdPost", query = "SELECT f FROM ForoPosteos f WHERE f.idPost = :idPost"),
    @NamedQuery(name = "ForoPosteos.findByFecha", query = "SELECT f FROM ForoPosteos f WHERE f.fecha = :fecha"),
    @NamedQuery(name = "ForoPosteos.findByAutorizado", query = "SELECT f FROM ForoPosteos f WHERE f.autorizado = :autorizado")})
public class ForoPosteos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_post")
    private Integer idPost;
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
    @Column(name = "contenido")
    private String contenido;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Autorizado")
    private boolean autorizado;
    @JoinColumn(name = "id_subcategoria", referencedColumnName = "id_subcategoria")
    @ManyToOne(optional = false)
    private ForoSubcategoria idSubcategoria;
    @JoinColumn(name = "id_user", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idUser;

    public ForoPosteos() {
    }

    public ForoPosteos(Integer idPost) {
        this.idPost = idPost;
    }

    public ForoPosteos(Integer idPost, String titulo, String contenido, Date fecha, boolean autorizado) {
        this.idPost = idPost;
        this.titulo = titulo;
        this.contenido = contenido;
        this.fecha = fecha;
        this.autorizado = autorizado;
    }

    public Integer getIdPost() {
        return idPost;
    }

    public void setIdPost(Integer idPost) {
        this.idPost = idPost;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean getAutorizado() {
        return autorizado;
    }

    public void setAutorizado(boolean autorizado) {
        this.autorizado = autorizado;
    }

    public ForoSubcategoria getIdSubcategoria() {
        return idSubcategoria;
    }

    public void setIdSubcategoria(ForoSubcategoria idSubcategoria) {
        this.idSubcategoria = idSubcategoria;
    }

    public Usuario getIdUser() {
        return idUser;
    }

    public void setIdUser(Usuario idUser) {
        this.idUser = idUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPost != null ? idPost.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ForoPosteos)) {
            return false;
        }
        ForoPosteos other = (ForoPosteos) object;
        if ((this.idPost == null && other.idPost != null) || (this.idPost != null && !this.idPost.equals(other.idPost))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.ForoPosteos[ idPost=" + idPost + " ]";
    }
    
}
