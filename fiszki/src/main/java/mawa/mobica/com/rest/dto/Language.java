package mawa.mobica.com.rest.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Language implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private Long id;
		private String name;
		private String description;

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
