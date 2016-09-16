package services.sessions;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 * Created by helen on 1/09/2016.
 */

@Provider
@Produces(MediaType.APPLICATION_XML)
public class SessionResolver implements ContextResolver<JAXBContext> {

    private JAXBContext _context;

    public SessionResolver() {
        try {
            _context = JAXBContext.newInstance(domain.Session.class);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JAXBContext getContext(Class<?> type) {
        if (type.equals(domain.Session.class)) {
            return _context;
        } else {
            return null;
        }
    }
}
