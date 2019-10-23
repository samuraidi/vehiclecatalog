package bean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditQuery;

import entity.Vehicle;

@Stateless
public class VehicleBean {
	
	@PersistenceContext(unitName="restPU")
	EntityManager entityManager;
	
	public boolean createVehicleTable(Vehicle vehicle) {
		entityManager.persist(vehicle);
		return true;
	}
	
	public void addVehicle(String name, String type, 
							String yearProduced, String fuelConsumption) {
		Vehicle vehicle = new Vehicle();
		vehicle.setName(name);
		vehicle.setType(type);
		vehicle.setYearProduced(yearProduced);
		vehicle.setFuelConsumption(fuelConsumption);
	}
	
	public void deleteVehicle(String id) {
		Query q = entityManager.createQuery("DELETE FROM Vehicle v WHERE v.id = :id");
		q.setParameter("id", Long.parseLong(id));
		q.executeUpdate();
	}
	
	public void updateVehicle(String id, String name, String type, 
			String yearProduced, String fuelConsumption) {
		Query q = entityManager.createQuery("UPDATE Vehicle v SET v.name = :name, "
				+ "v.type = :type, "
				+ "v.year_produced = :yearProduced, "
				+ "v.fuel_consumption = :fuelConsumption"
				+ "WHERE v.id = : id");
		q.setParameter("id", Long.parseLong(id));
		q.setParameter("name", name);
		q.setParameter("type", type);
		q.setParameter("yearProduced", yearProduced);
		q.setParameter("fuelConsumption", fuelConsumption);		
	}
	
	public Vehicle getVehicleById(String id) {
		Query q = entityManager.createQuery("SELECT v FROM Vehicle v WHERE v.id = :id");
		q.setParameter("id", Long.parseLong(id));
		Vehicle vehicle = (Vehicle) q.getSingleResult();
		return vehicle;
	}
	
	public List<Vehicle> getVehicles(){
		Query q = entityManager.createQuery("SELECT v FROM Vehicle v");
		List<Vehicle> vehicles = q.getResultList();
		return vehicles;
	}

}
