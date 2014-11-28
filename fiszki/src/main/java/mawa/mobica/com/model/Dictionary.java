package mawa.mobica.com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Dictionary implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
	@Column(name=DB.DICTIONARY__ID)
	private Long id;

	@Column(name=DB.DICTIONARY__UUID)
	private String uuid;
	@Column(name=DB.DICTIONARY__NAME)
	private String name;
	@Column(name=DB.DICTIONARY__DESC)
	private String description;

	
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
	

	@Override
	public String toString() {
		return new StringBuilder().
				append("id: ").append(id).
				append("name: ").append(name).
				toString();
	}
}
