package com.pci.afc.repository.impl;

import com.pci.afc.repository.JpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.data.repository.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by xwj on 2018-08-12.
 */
public class JpaRepositoryImpl<T extends Repository<?, ?>> extends ApplicationObjectSupport implements JpaRepository<T> {

    private static final long serialVersionUID = 1L;

    private Class<?> clazz;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JpaRepositoryImpl() {
        Type[] types = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
        if (types[0] instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) types[0];
            clazz = (Class<?>) type.getRawType();
        } else {
            clazz = (Class<?>) types[0];
        }
    }

    @SuppressWarnings("unchecked")
    public T getRepository() {
        return (T) this.getApplicationContext().getBean(clazz);
    }

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

}