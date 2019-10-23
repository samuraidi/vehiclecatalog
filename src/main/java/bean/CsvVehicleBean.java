package bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections4.ListUtils;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;

import entity.Vehicle;

@Stateless
public class CsvVehicleBean {
	
	@PersistenceContext(unitName="restPU")
	EntityManager entityManager;
	
	public boolean createVehicleTable(Vehicle vehicle) {
		entityManager.persist(vehicle);
		return true;
	}
	
	public List<Vehicle> loadAllVehicles(File csvFile){
		
		String line = "";
		String csvSplitBy = ",";
		
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			if (csvFile.length() == 0) {
				throw new RuntimeException("CSV file is empty!");
			}
			
			List<Vehicle> allVehicles = new ArrayList<Vehicle>();
			
			while ((line = br.readLine()) != null) {
				String[] csvLine = line.split(csvSplitBy);
				Vehicle vehicle = createVehicleEntity(csvLine);
				
				allVehicles.add(vehicle);
			}
			
			return allVehicles;
			
		} catch(FileNotFoundException e) {
			
			throw new RuntimeException("CSV file was not found", e);
			
		} catch(IOException e) {
			
			throw new RuntimeException(e);
		}
	}
	
	private static Vehicle createVehicleEntity(String[] csvLine) {
		
		try {
			String name = csvLine[0].trim();
			String type = csvLine[1].trim();
			String yearProduced = csvLine[2].trim();
			String fuelConsumption = csvLine[3].trim();
			
			Vehicle entity = new Vehicle();
			
			entity.setName(name);
			entity.setType(type);
			entity.setYearProduced(yearProduced);
			entity.setFuelConsumption(fuelConsumption);
			
			return entity;
			
		} catch (Exception e) {
			throw new RuntimeException("incorrect data in CSV file", e);
		}
	}
	
	public List<Vehicle> loadTestRev(Long id) {
		AuditReader auditReader = AuditReaderFactory.get(entityManager);
		List<Number> revisions = ListUtils.emptyIfNull(auditReader.getRevisions(Vehicle.class, id));
		
		return revisions.stream()
				.map(rev -> auditReader.find(Vehicle.class, id, rev))
				.collect(Collectors.toList());
		
	}

}
