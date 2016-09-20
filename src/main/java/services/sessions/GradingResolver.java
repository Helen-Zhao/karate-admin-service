package services.sessions;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 * Created by helen on 1/09/2016.
 * <p>
 * Name: Helen Zhao
 * UPI: hzha587
 * AUID: 6913580
 * <p>
 * SOFTENG 325 ASSIGNMENT 1 MAIN
 */

@Provider
@Produces(MediaType.APPLICATION_XML)
public class GradingResolver implements ContextResolver<JAXBContext> {

    private JAXBContext _context;

    public GradingResolver() {
        try {
            _context = JAXBContext.newInstance(domain.Grading.class);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JAXBContext getContext(Class<?> type) {
        if (type.equals(domain.Grading.class)) {
            return _context;
        } else {
            return null;
        }
    }
}
