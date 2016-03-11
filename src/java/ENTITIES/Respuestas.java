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
@Table(name = "respuestas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Respuestas.findAll", query = "SELECT r FROM Respuestas r"),
    @NamedQuery(name = "Respuestas.findByIdRespuestas", query = "SELECT r FROM Respuestas r WHERE r.idRespuestas = :idRespuestas")})
public class Respuestas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_respuestas")
    private Integer idRespuestas;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "respuesta")
    private String respuesta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRespuesta")
    private List<PreguntasData> preguntasDataList;

    public Respuestas() {
    }

    public Respuestas(Integer idRespuestas) {
        this.idRespuestas = idRespuestas;
    }

    public Respuestas(Integer idRespuestas, String respuesta) {
        this.idRespuestas = idRespuestas;
        this.respuesta = respuesta;
    }

    public Integer getIdRespuestas() {
        return idRespuestas;
    }

    public void setIdRespuestas(Integer idRespuestas) {
        this.idRespuestas = idRespuestas;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
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
        hash += (idRespuestas != null ? idRespuestas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Respuestas)) {
            return false;
        }
        Respuestas other = (Respuestas) object;
        if ((this.idRespuestas == null && other.idRespuestas != null) || (this.idRespuestas != null && !this.idRespuestas.equals(other.idRespuestas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.Respuestas[ idRespuestas=" + idRespuestas + " ]";
    }
    
}
