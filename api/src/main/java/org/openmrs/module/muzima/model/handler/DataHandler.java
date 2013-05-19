package org.openmrs.module.muzima.model.handler;

import org.openmrs.module.muzima.model.Data;

/**
 */
public interface DataHandler {

    /**
     * Handler that will be executed when a data is saved.
     *
     * @param data the data.
     */
    void saveHandler(final Data data);

    /**
     * Handler that will be executed when a data is deleted.
     *
     * @param data the data.
     */
    void deleteHandler(final Data data);

}
