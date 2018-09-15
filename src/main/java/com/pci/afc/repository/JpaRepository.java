package com.pci.afc.repository;

import org.springframework.data.repository.Repository;

import java.io.*;

/**
 * Created by xwj on 2018-08-12.
 */
public interface JpaRepository<I extends Repository<?, ?>> extends Serializable {

    I getRepository();

}