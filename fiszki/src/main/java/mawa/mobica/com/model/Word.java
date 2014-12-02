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
public class Word implements Serializable{

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
	@Column(name=DB.WORD__BASE_WORD, nullable=false)
	private String baseWord;
	@Column(name=DB.WORD__REF_WORD, nullable=false)
	private String refWord;

	
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
	public String getBaseWord() {
		return baseWord;
	}
	public void setBaseWord(String baseWord) {
		this.baseWord = baseWord;
	}
	public String getRefWord() {
		return refWord;
	}
	public void setRefWord(String refWord) {
		this.refWord = refWord;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getBaseWord() == null) ? 0 : getBaseWord().hashCode());
		result = prime * result
				+ ((getRefWord() == null) ? 0 : getRefWord().hashCode());
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
		if (getBaseWord() == null) {
			if (other.getBaseWord() != null)
				return false;
		} else if (!getBaseWord().equals(other.getBaseWord()))
			return false;
		if (getRefWord() == null) {
			if (other.getRefWord() != null)
				return false;
		} else if (!getRefWord().equals(other.getRefWord()))
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
				append("baseWord: ").append(getBaseWord()).append(", ").
				append("refWord: ").append(getRefWord()).
				toString();
	}
}
