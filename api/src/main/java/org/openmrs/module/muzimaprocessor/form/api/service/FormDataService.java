package org.openmrs.module.muzimaprocessor.form.api.service;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.muzimaprocessor.form.definition.FormData;

import java.util.List;

/**
 */
public interface FormDataService extends OpenmrsService {

    /**
     * Return the data with the given id.
     *
     * @param id the form data id.
     * @return the form data with the matching id.
     * @should return form data with matching id.
     * @should return null when no form data with matching id.
     */
    FormData getFormData(final Integer id);

    /**
     * Return the data with the given uuid.
     *
     * @param uuid the form data uuid.
     * @return the form data with the matching uuid.
     * @should return form data with matching uuid.
     * @should return null when no form data with matching uuid.
     */
    FormData getFormDataByUuid(final String uuid);

    /**
     * Return all saved form data.
     *
     * @param includeVoided flag whether voided form data should be returned or not.
     * @return all saved form data including voided form data.
     * @should return empty list when no form data are saved in the database.
     * @should return all saved form data.
     */
    List<FormData> getAllFormData(final boolean includeVoided);

    /**
     * Return all saved form data with matching name.
     *
     * @param name           the name of the form data.
     * @param exactMatchOnly flag whether matching should be exact.
     * @return all saved form data including voided form data with matching name.
     * @should return empty list when no form data are saved in the database with matching name.
     * @should return all saved form data with matching name.
     */
    List<FormData> getAllFormData(final String name, final boolean exactMatchOnly);

    /**
     * Save form data into the database.
     *
     * @param formData the form data.
     * @return saved form data.
     * @should save form data into the database.
     */
    FormData saveFormData(final FormData formData);

    /**
     * Delete form data from the database.
     *
     * @param formData the form data
     * @should remove form data from the database
     */
    void purgeFormData(final FormData formData);
}
