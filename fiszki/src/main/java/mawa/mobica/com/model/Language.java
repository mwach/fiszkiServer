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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import mawa.mobica.com.dao.DB;

/**
 * Class represents single language
 * @author mawa
 *
 */
@Entity
@Table(name=DB.LANGUAGE)
public class Language implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
	@Column(name=DB.LANGUAGE__ID)
	private Long id;

	@Column(name=DB.LANGUAGE__NAME, nullable=false, unique=true)
	private String name;
	@Column(name=DB.LANGUAGE__DESC, nullable=false)
	private String description;

	@OneToMany(mappedBy=DB.DICTIONARY__BASE_LANG, cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
	private Set<Dictionary> baseDictionaries = new HashSet<Dictionary>();

	@OneToMany(mappedBy=DB.DICTIONARY__REF_LANG, cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
	private Set<Dictionary> refDictionaries = new HashSet<Dictionary>();

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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

	public Set<Dictionary> getBaseDictionaries() {
		return baseDictionaries;
	}
	public void setBaseDictionaries(Set<Dictionary> baseDictionaries) {
		this.baseDictionaries = baseDictionaries;
	}

	public Set<Dictionary> getRefDictionaries() {
		return refDictionaries;
	}
	public void setRefDictionaries(Set<Dictionary> refDictionaries) {
		this.refDictionaries = refDictionaries;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getDescription() == null) ? 0 : getDescription().hashCode());
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Language))
			return false;
		Language other = (Language) obj;
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

		return true;
	}
	@Override
	public String toString() {
		return new StringBuilder().
				append("id: ").append(getId()).append(", ").
				append("name: ").append(getName()).append(", ").
				append("description: ").append(getDescription()).
				toString();
	}
}
