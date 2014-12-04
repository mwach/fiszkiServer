package mawa.mobica.com.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import mawa.mobica.com.dao.DB;

/**
 * Class represents single dictionary
 * @author mawa
 *
 */
@Entity
@Table(name=DB.DICTIONARY)
@XmlRootElement
public class Dictionary implements Serializable, Model{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
	@Column(name=DB.DICTIONARY__ID)
	private Long id;

	@Column(name=DB.DICTIONARY__UUID, nullable=false, unique=true)
	private String uuid;
	@Column(name=DB.DICTIONARY__NAME, nullable=false)
	private String name;
	@Column(name=DB.DICTIONARY__DESC, nullable=false)
	private String description;
	@ManyToOne
	@JoinColumn(name=DB.DICTIONARY__BASE_LANG, nullable=false)
	private Language baseLanguage;
	@ManyToOne
	@JoinColumn(name=DB.DICTIONARY__REF_LANG, nullable=false)
	private Language refLanguage;

	@OneToMany(mappedBy=DB.WORD__DICTIONARY, cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
	private Set<Word> words = new HashSet<Word>();

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Language getBaseLanguage() {
		return baseLanguage;
	}
	public void setBaseLanguage(Language baseLanguage) {
		this.baseLanguage = baseLanguage;
	}
	public Language getRefLanguage() {
		return refLanguage;
	}
	public void setRefLanguage(Language refLanguage) {
		this.refLanguage = refLanguage;
	}

	public Set<Word> getWords() {
		return words;
	}
	public void setWords(Set<Word> words) {
		this.words = words;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getBaseLanguage() == null) ? 0 : getBaseLanguage().hashCode());
		result = prime * result
				+ ((getDescription() == null) ? 0 : getDescription().hashCode());
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
		result = prime * result
				+ ((getRefLanguage() == null) ? 0 : getRefLanguage().hashCode());
		result = prime * result + ((getUuid() == null) ? 0 : getUuid().hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Dictionary))
			return false;
		Dictionary other = (Dictionary) obj;
		if (getBaseLanguage() == null) {
			if (other.getBaseLanguage() != null)
				return false;
		} else if (!getBaseLanguage().equals(other.getBaseLanguage()))
			return false;
		if (getDescription() == null) {
			if (other.getDescription() != null)
				return false;
		} else if (!getDescription().equals(other.getDescription()))
			return false;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		if (getName() == null) {
			if (other.getName() != null)
				return false;
		} else if (!getName().equals(other.getName()))
			return false;
		if (getRefLanguage() == null) {
			if (other.getRefLanguage() != null)
				return false;
		} else if (!getRefLanguage().equals(other.getRefLanguage()))
			return false;
		if (getUuid() == null) {
			if (other.getUuid() != null)
				return false;
		} else if (!getUuid().equals(other.getUuid()))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return new StringBuilder().
				append("id: ").append(getId()).append(", ").
				append("name: ").append(getName()).
				toString();
	}
}
