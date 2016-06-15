package com.solvedbysunrise.identity.data.dao;

import com.solvedbysunrise.identity.data.entity.jpa.ReflectiveEntity;
import org.junit.Test;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings("unchecked")
@Rollback
@Transactional
public abstract class IntegrationTestForBasicDao<DAO extends CrudRepository, ENTITY extends ReflectiveEntity, ID extends Serializable> {

    public abstract DAO getDao();

    public abstract ENTITY entityToSave();

    public abstract ENTITY entityToDelete();

    public abstract ENTITY entityToCompare();

    public abstract ID entityIdToLookup();

    @Test
    public void can_save_entity() throws Exception {
        DAO targetDao = getDao();
        ENTITY savedEntity = (ENTITY)targetDao.save(entityToSave());
        List<ENTITY> allEntities = newArrayList(targetDao.findAll());
        assertThat(allEntities, hasItem(savedEntity));
    }

    @Test
    public void can_lookup_entity() throws Exception {
        DAO targetDao = getDao();
        ID id = entityIdToLookup();
        ENTITY expectedEntity = entityToCompare();
        ENTITY lookedUpEntity = (ENTITY)targetDao.findOne(id);
        assertThat(lookedUpEntity, is(expectedEntity));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void can_delete_entity() throws Exception {
        DAO targetDao = getDao();
        ID id = entityIdToLookup();
        targetDao.delete(id);
        List<ENTITY> allEntities = newArrayList(targetDao.findAll());
        assertThat(allEntities, not(hasItem(entityToDelete())));
    }
}
