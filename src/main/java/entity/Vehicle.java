package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "Vehicle")
@XmlRootElement(name = "vehicle")
public class Vehicle implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@Column( name = "type")
	private String type;
	
	@Column(name = "year_produced")
	private String yearProduced;
	
	@Column(name = "fuel_consumption")
	private String fuelConsumption;
	
	@XmlElement
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElement
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@XmlElement
	public String getYearProduced() {
		return yearProduced;
	}

	public void setYearProduced(String yearProduced) {
		this.yearProduced = yearProduced;
	}
	
	@XmlElement
	public String getFuelConsumption() {
		return fuelConsumption;
	}

	public void setFuelConsumption(String fuelConsumption) {
		this.fuelConsumption = fuelConsumption;
	}
	
	

}
