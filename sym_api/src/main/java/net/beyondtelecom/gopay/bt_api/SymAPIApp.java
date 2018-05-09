package net.beyondtelecom.gopay.bt_api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.beyondtelecom.gopay.bt_api.exception.SymRestExceptionHandler;
import net.beyondtelecom.gopay.bt_api.webservice.SymJSONRestService;
import net.beyondtelecom.gopay.bt_api.webservice.SymXMLRestService;
import net.beyondtelecom.gopay.bt_api.webservice.GoPayJSONRestService;
import net.beyondtelecom.gopay.bt_api.webservice.GoPayXMLRestService;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ext.ContextResolver;

/***************************************************************************
 * *
 * Created:     16 / 01 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

@Configuration
public class SymAPIApp extends ResourceConfig {

    public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

        private final ObjectMapper mapper;

        ObjectMapperContextResolver() {
            mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }

        @Override
        public ObjectMapper getContext(Class<?> type) {
            return mapper;
        }

    }

    public SymAPIApp() {
        register(new ObjectMapperContextResolver());
        register(MultiPartFeature.class);
        register(SymRestExceptionHandler.class);
        register(GoPayXMLRestService.class);
        register(GoPayJSONRestService.class);
        register(SymXMLRestService.class);
        register(SymJSONRestService.class);
    }
}
