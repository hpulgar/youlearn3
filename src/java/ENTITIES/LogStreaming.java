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
@Table(name = "log_streaming")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LogStreaming.findAll", query = "SELECT l FROM LogStreaming l"),
    @NamedQuery(name = "LogStreaming.findByIdStreaming", query = "SELECT l FROM LogStreaming l WHERE l.idStreaming = :idStreaming"),
    @NamedQuery(name = "LogStreaming.findByTitulo", query = "SELECT l FROM LogStreaming l WHERE l.titulo = :titulo"),
    @NamedQuery(name = "LogStreaming.findByViewers", query = "SELECT l FROM LogStreaming l WHERE l.viewers = :viewers"),
    @NamedQuery(name = "LogStreaming.findByFechaInicio", query = "SELECT l FROM LogStreaming l WHERE l.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "LogStreaming.findByFechaFin", query = "SELECT l FROM LogStreaming l WHERE l.fechaFin = :fechaFin")})
public class LogStreaming implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_streaming")
    private Integer idStreaming;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "viewers")
    private int viewers;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @JoinColumn(name = "id_curso", referencedColumnName = "id_curso")
    @ManyToOne(optional = false)
    private Curso idCurso;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idUsuario;

    public LogStreaming() {
    }

    public LogStreaming(Integer idStreaming) {
        this.idStreaming = idStreaming;
    }

    public LogStreaming(Integer idStreaming, String titulo, int viewers, Date fechaInicio, Date fechaFin) {
        this.idStreaming = idStreaming;
        this.titulo = titulo;
        this.viewers = viewers;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Integer getIdStreaming() {
        return idStreaming;
    }

    public void setIdStreaming(Integer idStreaming) {
        this.idStreaming = idStreaming;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getViewers() {
        return viewers;
    }

    public void setViewers(int viewers) {
        this.viewers = viewers;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Curso getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Curso idCurso) {
        this.idCurso = idCurso;
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
        hash += (idStreaming != null ? idStreaming.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogStreaming)) {
            return false;
        }
        LogStreaming other = (LogStreaming) object;
        if ((this.idStreaming == null && other.idStreaming != null) || (this.idStreaming != null && !this.idStreaming.equals(other.idStreaming))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.LogStreaming[ idStreaming=" + idStreaming + " ]";
    }
    
}
