package mawa.mobica.com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import mawa.mobica.com.dao.DB;

/**
 * Class represents single word
 * @author mawa
 *
 */
@Entity
@Table(name=DB.WORD)
@XmlRootElement
public class Word implements Serializable, Model{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
	@Column(name=DB.WORD__ID)
	private Long id;

	@ManyToOne
	@JoinColumn(name=DB.WORD__DICTIONARY, nullable=false)
	private Dictionary dictionary;
	@Column(name=DB.WORD__NAME, nullable=false)
	private String name;
	@Column(name=DB.WORD__REF_NAME, nullable=false)
	private String refName;

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Dictionary getDictionary() {
		return dictionary;
	}
	public void setDictionary(Dictionary dictionary) {
		this.dictionary = dictionary;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRefName() {
		return refName;
	}
	public void setRefName(String refName) {
		this.refName = refName;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getName() == null) ? 0 : getName().hashCode());
		result = prime * result
				+ ((getRefName() == null) ? 0 : getRefName().hashCode());
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result
				+ ((getDictionary() == null) ? 0 : getDictionary().hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Word))
			return false;
		Word other = (Word) obj;
		if (getName() == null) {
			if (other.getName() != null)
				return false;
		} else if (!getName().equals(other.getName()))
			return false;
		if (getRefName() == null) {
			if (other.getRefName() != null)
				return false;
		} else if (!getRefName().equals(other.getRefName()))
			return false;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		if (getDictionary() == null) {
			if (other.getDictionary() != null)
				return false;
		} else if (!getDictionary().equals(other.getDictionary()))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return new StringBuilder().
				append("id: ").append(getId()).append(", ").
				append("name: ").append(getName()).append(", ").
				append("refName: ").append(getRefName()).
				toString();
	}
}
