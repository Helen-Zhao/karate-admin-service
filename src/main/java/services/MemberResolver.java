package services;

import domain.Member;

import javax.ws.rs.ext.ContextResolver;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 * Created by helen on 1/09/2016.
 */
public class MemberResolver implements ContextResolver<JAXBContext> {

    private JAXBContext _context;

    public MemberResolver() {
        try {
            _context = JAXBContext.newInstance(Member.class);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JAXBContext getContext(Class<?> type) {
        if (type.equals(Member.class)) {
            return _context;
        } else {
            return null;
        }
    }
}
