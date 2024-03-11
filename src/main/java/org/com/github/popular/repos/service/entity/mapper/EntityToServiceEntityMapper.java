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
     * Maps a list of objects to the required class.
     *
     * @param <T> The type of objects in the returned list
     * @param dest The class of the destination type. This class type should match or be a superclass/interface of the type <T>
     * @param objects The list of objects to be mapped
     * @return A new list of objects of the specified destination type
     */
    public static <T> List<T> mapAsList(Class<T> dest, List<?> objects) {
        if (objects == null) {
            return null;
        }
        MapperFacade mapper = factory.getMapperFacade();
        // Assumes `mapper.mapAsList` is correctly typed to return List<T>
        return mapper.mapAsList(objects, dest);
    }
}
