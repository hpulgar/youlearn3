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
import javax.persistence.Lob;
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
@Table(name = "quiz")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Quiz.findAll", query = "SELECT q FROM Quiz q"),
    @NamedQuery(name = "Quiz.findByIdQuiz", query = "SELECT q FROM Quiz q WHERE q.idQuiz = :idQuiz")})
public class Quiz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_quiz")
    private Integer idQuiz;
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
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idQuiz")
    private List<QuizUsuario> quizUsuarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idQuiz")
    private List<PreguntasData> preguntasDataList;

    public Quiz() {
    }

    public Quiz(Integer idQuiz) {
        this.idQuiz = idQuiz;
    }

    public Quiz(Integer idQuiz, String titulo, String descripcion) {
        this.idQuiz = idQuiz;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public Integer getIdQuiz() {
        return idQuiz;
    }

    public void setIdQuiz(Integer idQuiz) {
        this.idQuiz = idQuiz;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<QuizUsuario> getQuizUsuarioList() {
        return quizUsuarioList;
    }

    public void setQuizUsuarioList(List<QuizUsuario> quizUsuarioList) {
        this.quizUsuarioList = quizUsuarioList;
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
        hash += (idQuiz != null ? idQuiz.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Quiz)) {
            return false;
        }
        Quiz other = (Quiz) object;
        if ((this.idQuiz == null && other.idQuiz != null) || (this.idQuiz != null && !this.idQuiz.equals(other.idQuiz))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.Quiz[ idQuiz=" + idQuiz + " ]";
    }
    
}
