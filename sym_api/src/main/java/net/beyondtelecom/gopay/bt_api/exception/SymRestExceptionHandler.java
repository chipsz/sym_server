package net.beyondtelecom.gopay.bt_api.exception;

/***************************************************************************
 * *
 * Created:     07 / 01 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

import net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode;
import net.beyondtelecom.gopay.bt_core.contract.BTResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class SymRestExceptionHandler implements ExceptionMapper<SymRestException>
{
	private static final Logger logger = Logger.getLogger(SymRestExceptionHandler.class.getSimpleName());
	@Override
	public Response toResponse(SymRestException exception) {
		logger.severe("General error occurred on web service: " + exception.getMessage());
		exception.printStackTrace();
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(
			new BTResponse(BTResponseCode.GENERAL_ERROR).setResponse_message(exception.getMessage())
		).build();
	}
}
