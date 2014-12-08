package mawa.mobica.com.rest;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import mawa.mobica.com.dao.IDao;
import mawa.mobica.com.model.Model;
import mawa.mobica.com.util.LogHelper;
import mawa.mobica.com.util.ResourceHelper;

public abstract class RestService<T extends Model, DTO> {

	protected abstract IDao<T> getDao();
	protected abstract ResourceHelper<T, DTO> getResourceHelper();

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public DTO getResource(
		@PathParam("id") long id){

		try {
			return getResourceHelper().toDto(getDao().get(id));
		} catch (SQLException e) {
			LogHelper.error(getClass(), "getResource", e.getLocalizedMessage());
			throw new WebApplicationException(Status.NOT_FOUND.getStatusCode());
		}
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response create(DTO resource){

		Long resourceId = null;
		try {
			resourceId = getDao().create(getResourceHelper().fromDto(resource)).getId();
		} catch (SQLException e) {
			LogHelper.error(getClass(), "addResource", e.getLocalizedMessage());
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR.getStatusCode());
		}
		return Response.status(Status.CREATED).entity(String.valueOf(resourceId)).build();
	}

	@PUT
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	public void updateResource(DTO resource,
			@PathParam("id") long id){
		try {
			T dbResource = getResourceHelper().fromDto(resource);
			dbResource.setId(id);
			getDao().update(getResourceHelper().fromDto(resource));
		} catch (SQLException e) {
			LogHelper.error(getClass(), "updateResource", e.getLocalizedMessage());
			throw new WebApplicationException(Status.NOT_FOUND.getStatusCode());
		}
	}

	@DELETE
	@Path("/{id}")
	public void deleteResource(
			@PathParam("id") long id){

		try {
			getDao().delete(id);
		} catch (SQLException e) {
			LogHelper.error(getClass(), "deleteResource", e.getLocalizedMessage());
			throw new WebApplicationException(Status.NOT_FOUND.getStatusCode());
		}
	}

}
