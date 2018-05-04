package de.unipotsdam.elis.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

import de.unipotsdam.elis.api.OSMOpeningHours;
import de.unipotsdam.elis.core.TimeRetriever;

@Path("/update")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf8")
public class UpdateResource {

	private TimeRetriever times;

	public UpdateResource(TimeRetriever times) {
		this.times = times;
	}

	@GET
	@Timed
	public OSMOpeningHours update(@QueryParam("location") String location) throws Exception {
		return times.readPalaisCanteen();
	}
}
