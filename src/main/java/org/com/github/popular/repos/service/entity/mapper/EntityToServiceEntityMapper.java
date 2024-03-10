package org.com.github.popular.repos.service.entity.mapper;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class EntityToServiceEntityMapper {

    private static MapperFactory factory = new DefaultMapperFactory.Builder().build();

    /**
     * Maps list of objects to the required class
     *
     * @param dest    The class of the dest
     * @param objects The list of objects to be mapped
     * @return A new list of objects of type dest
     */
    public static <T> List<T> mapAsList(Class dest, List<T> objects) {
        if (objects == null) {
            return null;
        }
        MapperFacade mapper = factory.getMapperFacade();
        return (List<T>) mapper.mapAsList(objects, dest);
    }
}
