package service;

import java.io.File;
import java.util.List;
import java.util.concurrent.Executor;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.jaxrs.FormParam;

import bean.CsvVehicleBean;
import bean.VehicleBean;
import config.Config;
import entity.Vehicle;

@Path("vehicle")
public class VehicleService extends Config{
	
	@EJB
	VehicleBean vehicleBean;
	
	@EJB
	CsvVehicleBean csvVehicleBean;
	
	@Inject
    private Executor executor;
	
	@POST
	@Path("addVehicle/{name}/{type}/{yearProduced}/{fuelConsumption}")
	@Produces(MediaType.APPLICATION_JSON)
	public String addVehicle(@PathParam("name") String name, 
							 @PathParam("type") String type,
							 @PathParam("yearProduced") String yearProduced,
							 @PathParam("fuelConsumption") String fuelConsumption) {
		vehicleBean.addVehicle(name, type, yearProduced, fuelConsumption);
		return "{\"status\":\"ok\"}";
	}
	
	@PUT
	@Path("updateVehicle/{id}/{name}/{type}/{yearProduced}/{fuelConsumption}")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateVehicle(@PathParam("id") String id,
								@PathParam("name") String name, 
								@PathParam("type") String type,
								@PathParam("yearProduced") String yearProduced,
								@PathParam("fuelConsumption") String fuelConsumption) {
		vehicleBean.updateVehicle(id, name, type, yearProduced, fuelConsumption);
		return "{\"status\":\"ok\"}";
	}
	
	@GET
	@Path("getVehicleByIdJSON/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Vehicle getVehicleByIdJSON(@PathParam("id") String id) {
		return vehicleBean.getVehicleById(id);
	}
	
	@GET
	@Path("getVehiclesJSON")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Vehicle> getVehiclesJSON(){
		return vehicleBean.getVehicles();
	}
	
	@DELETE
	@Path("deleteVehicleById/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteVehicleById(@PathParam("id") String id) {
		vehicleBean.deleteVehicle(id);
		return "{\"status\":\"ok\"}";
	}
	
	@POST
	@Path("/upload")
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public String uploadCsvFile(@FormParam ("path") String path) {
		File file = new File(path);
		csvVehicleBean.loadAllVehicles(file);
		return "{\"status\":\"ok\"}";
	}
	
	/*
	 * @GET public void asyncGet(@Suspended final AsyncResponse asyncResponse) {
	 * executor.execute(() -> { File file = csvVehicleBean.veryExpensiveOperation();
	 * asyncResponse.resume(Response.ok((Object) file).build()); }); }
	 */

}
