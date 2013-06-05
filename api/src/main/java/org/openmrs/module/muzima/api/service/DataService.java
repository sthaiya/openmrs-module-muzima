package org.openmrs.module.muzima.api.service;

import org.openmrs.Person;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.muzima.model.ArchiveData;
import org.openmrs.module.muzima.model.DataSource;
import org.openmrs.module.muzima.model.ErrorData;
import org.openmrs.module.muzima.model.NotificationData;
import org.openmrs.module.muzima.model.QueueData;

import java.util.List;

/**
 */
public interface DataService extends OpenmrsService {

    /**
     * Return the queue data with the given id.
     *
     * @param id the queue data id.
     * @return the queue data with the matching id.
     * @should return queue data with matching id.
     * @should return null when no queue data with matching id.
     */
    QueueData getQueueData(final Integer id);

    /**
     * Return the queue data with the given uuid.
     *
     * @param uuid the queue data uuid.
     * @return the queue data with the matching uuid.
     * @should return queue data with matching uuid.
     * @should return null when no queue data with matching uuid.
     */
    QueueData getQueueDataByUuid(final String uuid);

    /**
     * Return all saved queue data.
     *
     * @return all saved queue data including voided queue data.
     * @should return empty list when no queue data are saved in the database.
     * @should return all saved queue data.
     */
    List<QueueData> getAllQueueData();

    /**
     * Save queue data into the database.
     *
     * @param queueData the queue data.
     * @return saved queue data.
     * @should save queue data into the database.
     */
    QueueData saveQueueData(final QueueData queueData);

    /**
     * Delete queue data from the database.
     *
     * @param queueData the queue data
     * @should remove queue data from the database
     */
    void purgeQueueData(final QueueData queueData);

    /**
     * Return the error data with the given id.
     *
     * @param id the error data id.
     * @return the error data with the matching id.
     * @should return error data with matching id.
     * @should return null when no error data with matching id.
     */
    ErrorData getErrorData(final Integer id);

    /**
     * Return the error data with the given uuid.
     *
     * @param uuid the error data uuid.
     * @return the error data with the matching uuid.
     * @should return error data with matching uuid.
     * @should return null when no error data with matching uuid.
     */
    ErrorData getErrorDataByUuid(final String uuid);

    /**
     * Return all saved error data.
     *
     * @return all saved error data including voided error data.
     * @should return empty list when no error data are saved in the database.
     * @should return all saved error data.
     */
    List<ErrorData> getAllErrorData();

    /**
     * Save error data into the database.
     *
     * @param ErrorData the error data.
     * @return saved error data.
     * @should save error data into the database.
     */
    ErrorData saveErrorData(final ErrorData ErrorData);

    /**
     * Delete error data from the database.
     *
     * @param ErrorData the error data
     * @should remove error data from the database
     */
    void purgeErrorData(final ErrorData ErrorData);

    /**
     * Return the archive data with the given id.
     *
     * @param id the archive data id.
     * @return the archive data with the matching id.
     * @should return archive data with matching id.
     * @should return null when no archive data with matching id.
     */
    ArchiveData getArchiveData(final Integer id);

    /**
     * Return the archive data with the given uuid.
     *
     * @param uuid the archive data uuid.
     * @return the archive data with the matching uuid.
     * @should return archive data with matching uuid.
     * @should return null when no archive data with matching uuid.
     */
    ArchiveData getArchiveDataByUuid(final String uuid);

    /**
     * Return all saved archive data.
     *
     * @return all saved archive data including voided archive data.
     * @should return empty list when no archive data are saved in the database.
     * @should return all saved archive data.
     */
    List<ArchiveData> getAllArchiveData();

    /**
     * Save archive data into the database.
     *
     * @param ArchiveData the archive data.
     * @return saved archive data.
     * @should save archive data into the database.
     */
    ArchiveData saveArchiveData(final ArchiveData ArchiveData);

    /**
     * Delete archive data from the database.
     *
     * @param ArchiveData the archive data
     * @should remove archive data from the database
     */
    void purgeArchiveData(final ArchiveData ArchiveData);

    /**
     * Return the data source with the given id.
     *
     * @param id the data source id.
     * @return the data source with the matching id.
     * @should return data source with matching id.
     * @should return null when no data source with matching id.
     */
    DataSource getDataSource(final Integer id);

    /**
     * Return the data source with the given uuid.
     *
     * @param uuid the data source uuid.
     * @return the data source with the matching uuid.
     * @should return data source with matching uuid.
     * @should return null when no data source with matching uuid.
     */
    DataSource getDataSourceByUuid(final String uuid);

    /**
     * Return all saved data source.
     *
     * @param name           the name of the data.
     * @param exactMatchOnly flag whether matching should be exact.
     * @param includeVoided  flag whether voided data should be returned or not.
     * @return all saved data source including voided data source.
     * @should return empty list when no data source are saved in the database.
     * @should return all saved data source.
     */
    List<DataSource> getAllDataSource(final String name, final boolean exactMatchOnly, final boolean includeVoided);

    /**
     * Save data source into the database.
     *
     * @param DataSource the data source.
     * @return saved data source.
     * @should save data source into the database.
     */
    DataSource saveDataSource(final DataSource DataSource);

    /**
     * Delete data source from the database.
     *
     * @param DataSource the data source
     * @should remove data source from the database
     */
    void purgeDataSource(final DataSource DataSource);

    /**
     * Return the notification data with the given id.
     *
     * @param id the notification data id.
     * @return the notification data with the matching id.
     * @should return notification data with matching id.
     * @should return null when no notification data with matching id.
     */
    NotificationData getNotificationData(final Integer id);

    /**
     * Return the notification data with the given uuid.
     *
     * @param uuid the notification data uuid.
     * @return the notification data with the matching uuid.
     * @should return notification data with matching uuid.
     * @should return null when no notification data with matching uuid.
     */
    NotificationData getNotificationDataByUuid(final String uuid);

    /**
     * Return all saved notification data.
     *
     * @return all saved notification data including voided notification data.
     * @should return empty list when no notification data are saved in the database.
     * @should return all saved notification data.
     */
    List<NotificationData> getAllNotificationData();

    /**
     * Return all saved notification data for a particular person.
     *
     * @return all saved notification data including voided notification data.
     * @should return empty list when no notification data are saved in the database.
     * @should return all saved notification data.
     */
    List<NotificationData> getAllNotificationDataFor(final Person person);

    /**
     * Return all saved notification data from a particular person.
     *
     * @return all saved notification data including voided notification data.
     * @should return empty list when no notification data are saved in the database.
     * @should return all saved notification data.
     */
    List<NotificationData> getAllNotificationDataFrom(final Person person);

    /**
     * Save notification data into the database.
     *
     * @param notificationData the notification data.
     * @return saved notification data.
     * @should save notification data into the database.
     */
    NotificationData saveNotificationData(final NotificationData notificationData);

    /**
     * Delete notification data from the database.
     *
     * @param notificationData the notification data
     * @should remove notification data from the database
     */
    void purgeNotificationData(final NotificationData notificationData);

    /**
     * Void a single notification data.
     *
     * @param notificationData the notification data to be voided.
     * @return the voided notification data.
     */
    NotificationData voidNotificationData(final NotificationData notificationData, final String reason);
}
