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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Felipe
 */
@Entity
@Table(name = "quiz_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "QuizUsuario.findAll", query = "SELECT q FROM QuizUsuario q"),
    @NamedQuery(name = "QuizUsuario.findByIdQuizUsuario", query = "SELECT q FROM QuizUsuario q WHERE q.idQuizUsuario = :idQuizUsuario"),
    @NamedQuery(name = "QuizUsuario.findByIdUsuario", query = "SELECT q FROM QuizUsuario q WHERE q.idUsuario = :idUsuario"),
    @NamedQuery(name = "QuizUsuario.findByFechaFinalizacion", query = "SELECT q FROM QuizUsuario q WHERE q.fechaFinalizacion = :fechaFinalizacion"),
    @NamedQuery(name = "QuizUsuario.findByPuntaje", query = "SELECT q FROM QuizUsuario q WHERE q.puntaje = :puntaje")})
public class QuizUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_quiz_usuario")
    private Integer idQuizUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_usuario")
    private int idUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_finalizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinalizacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "puntaje")
    private int puntaje;
    @JoinColumn(name = "id_quiz", referencedColumnName = "id_quiz")
    @ManyToOne(optional = false)
    private Quiz idQuiz;

    public QuizUsuario() {
    }

    public QuizUsuario(Integer idQuizUsuario) {
        this.idQuizUsuario = idQuizUsuario;
    }

    public QuizUsuario(Integer idQuizUsuario, int idUsuario, Date fechaFinalizacion, int puntaje) {
        this.idQuizUsuario = idQuizUsuario;
        this.idUsuario = idUsuario;
        this.fechaFinalizacion = fechaFinalizacion;
        this.puntaje = puntaje;
    }

    public Integer getIdQuizUsuario() {
        return idQuizUsuario;
    }

    public void setIdQuizUsuario(Integer idQuizUsuario) {
        this.idQuizUsuario = idQuizUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public Quiz getIdQuiz() {
        return idQuiz;
    }

    public void setIdQuiz(Quiz idQuiz) {
        this.idQuiz = idQuiz;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idQuizUsuario != null ? idQuizUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QuizUsuario)) {
            return false;
        }
        QuizUsuario other = (QuizUsuario) object;
        if ((this.idQuizUsuario == null && other.idQuizUsuario != null) || (this.idQuizUsuario != null && !this.idQuizUsuario.equals(other.idQuizUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.QuizUsuario[ idQuizUsuario=" + idQuizUsuario + " ]";
    }
    
}
