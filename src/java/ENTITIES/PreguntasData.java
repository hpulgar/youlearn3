/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ENTITIES;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Felipe
 */
@Entity
@Table(name = "preguntas_data")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PreguntasData.findAll", query = "SELECT p FROM PreguntasData p"),
    @NamedQuery(name = "PreguntasData.findByIdPreguntasData", query = "SELECT p FROM PreguntasData p WHERE p.idPreguntasData = :idPreguntasData")})
public class PreguntasData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_preguntas_data")
    private Integer idPreguntasData;
    @JoinColumn(name = "id_curso", referencedColumnName = "id_curso")
    @ManyToOne(optional = false)
    private Curso idCurso;
    @JoinColumn(name = "id_pregunta", referencedColumnName = "id_pregunta")
    @ManyToOne(optional = false)
    private Pregunta idPregunta;
    @JoinColumn(name = "id_quiz", referencedColumnName = "id_quiz")
    @ManyToOne(optional = false)
    private Quiz idQuiz;
    @JoinColumn(name = "id_respuesta", referencedColumnName = "id_respuestas")
    @ManyToOne(optional = false)
    private Respuestas idRespuesta;

    public PreguntasData() {
    }

    public PreguntasData(Integer idPreguntasData) {
        this.idPreguntasData = idPreguntasData;
    }

    public Integer getIdPreguntasData() {
        return idPreguntasData;
    }

    public void setIdPreguntasData(Integer idPreguntasData) {
        this.idPreguntasData = idPreguntasData;
    }

    public Curso getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Curso idCurso) {
        this.idCurso = idCurso;
    }

    public Pregunta getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(Pregunta idPregunta) {
        this.idPregunta = idPregunta;
    }

    public Quiz getIdQuiz() {
        return idQuiz;
    }

    public void setIdQuiz(Quiz idQuiz) {
        this.idQuiz = idQuiz;
    }

    public Respuestas getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(Respuestas idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPreguntasData != null ? idPreguntasData.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreguntasData)) {
            return false;
        }
        PreguntasData other = (PreguntasData) object;
        if ((this.idPreguntasData == null && other.idPreguntasData != null) || (this.idPreguntasData != null && !this.idPreguntasData.equals(other.idPreguntasData))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.PreguntasData[ idPreguntasData=" + idPreguntasData + " ]";
    }
    
}
