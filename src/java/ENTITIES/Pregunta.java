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
@Table(name = "pregunta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pregunta.findAll", query = "SELECT p FROM Pregunta p"),
    @NamedQuery(name = "Pregunta.findByIdPregunta", query = "SELECT p FROM Pregunta p WHERE p.idPregunta = :idPregunta")})
public class Pregunta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pregunta")
    private Integer idPregunta;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "pregunta")
    private String pregunta;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "informacion")
    private String informacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPregunta")
    private List<QuizImagen> quizImagenList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPregunta")
    private List<PreguntasData> preguntasDataList;

    public Pregunta() {
    }

    public Pregunta(Integer idPregunta) {
        this.idPregunta = idPregunta;
    }

    public Pregunta(Integer idPregunta, String pregunta, String informacion) {
        this.idPregunta = idPregunta;
        this.pregunta = pregunta;
        this.informacion = informacion;
    }

    public Integer getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(Integer idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    @XmlTransient
    public List<QuizImagen> getQuizImagenList() {
        return quizImagenList;
    }

    public void setQuizImagenList(List<QuizImagen> quizImagenList) {
        this.quizImagenList = quizImagenList;
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
        hash += (idPregunta != null ? idPregunta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pregunta)) {
            return false;
        }
        Pregunta other = (Pregunta) object;
        if ((this.idPregunta == null && other.idPregunta != null) || (this.idPregunta != null && !this.idPregunta.equals(other.idPregunta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.Pregunta[ idPregunta=" + idPregunta + " ]";
    }
    
}
