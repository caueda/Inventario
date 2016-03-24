package br.com.cursojsf.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="categoria")
public class Categoria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9170820345325014563L;
	private Long idCategoria;
	private Integer cdCategoria;
	private String nome;
	
	public Categoria(){}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}
	@Column(name="cd_categoria", nullable=false)
	public Integer getCdCategoria() {
		return cdCategoria;
	}

	public void setCdCategoria(Integer cdCategoria) {
		this.cdCategoria = cdCategoria;
	}

	@Column(name="nome", length=50, nullable=false)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
