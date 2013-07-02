/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.muzima.api.service.impl;

import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.muzima.api.db.ArchiveDataDao;
import org.openmrs.module.muzima.api.db.DataSourceDao;
import org.openmrs.module.muzima.api.db.ErrorDataDao;
import org.openmrs.module.muzima.api.db.NotificationDataDao;
import org.openmrs.module.muzima.api.db.QueueDataDao;
import org.openmrs.module.muzima.api.service.DataService;
import org.openmrs.module.muzima.model.ArchiveData;
import org.openmrs.module.muzima.model.DataSource;
import org.openmrs.module.muzima.model.ErrorData;
import org.openmrs.module.muzima.model.NotificationData;
import org.openmrs.module.muzima.model.QueueData;

import java.util.Date;
import java.util.List;

/**
 */
public class DataServiceImpl extends BaseOpenmrsService implements DataService {

    private ErrorDataDao errorDataDao;

    private QueueDataDao queueDataDao;

    private ArchiveDataDao archiveDataDao;

    private DataSourceDao dataSourceDao;

    private NotificationDataDao notificationDataDao;

    public QueueDataDao getQueueDataDao() {
        return queueDataDao;
    }

    public void setQueueDataDao(final QueueDataDao queueDataDao) {
        this.queueDataDao = queueDataDao;
    }

    public ErrorDataDao getErrorDataDao() {
        return errorDataDao;
    }

    public void setErrorDataDao(final ErrorDataDao errorDataDao) {
        this.errorDataDao = errorDataDao;
    }

    public ArchiveDataDao getArchiveDataDao() {
        return archiveDataDao;
    }

    public void setArchiveDataDao(final ArchiveDataDao archiveDataDao) {
        this.archiveDataDao = archiveDataDao;
    }

    public DataSourceDao getDataSourceDao() {
        return dataSourceDao;
    }

    public void setDataSourceDao(final DataSourceDao dataSourceDao) {
        this.dataSourceDao = dataSourceDao;
    }

    public NotificationDataDao getNotificationDataDao() {
        return notificationDataDao;
    }

    public void setNotificationDataDao(final NotificationDataDao notificationDataDao) {
        this.notificationDataDao = notificationDataDao;
    }

    /**
     * Return the data with the given id.
     *
     * @param id the form data id.
     * @return the form data with the matching id.
     * @should return form data with matching id.
     * @should return null when no form data with matching id.
     */
    @Override
    public QueueData getQueueData(final Integer id) {
        return getQueueDataDao().getData(id);
    }

    /**
     * Return the data with the given uuid.
     *
     * @param uuid the form data uuid.
     * @return the form data with the matching uuid.
     * @should return form data with matching uuid.
     * @should return null when no form data with matching uuid.
     */
    @Override
    public QueueData getQueueDataByUuid(final String uuid) {
        return getQueueDataDao().getDataByUuid(uuid);
    }

    /**
     * Return all saved form data.
     *
     * @return all saved form data including voided form data.
     * @should return empty list when no form data are saved in the database.
     * @should return all saved form data.
     */
    @Override
    public List<QueueData> getAllQueueData() {
        return getQueueDataDao().getAllData();
    }

    /**
     * Save form data into the database.
     *
     * @param formData the form data.
     * @return saved form data.
     * @should save form data into the database.
     */
    @Override
    public QueueData saveQueueData(final QueueData formData) {
        return getQueueDataDao().saveData(formData);
    }

    /**
     * Delete form data from the database.
     *
     * @param formData the form data
     * @should remove form data from the database
     */
    @Override
    public void purgeQueueData(final QueueData formData) {
        getQueueDataDao().purgeData(formData);
    }

    /**
     * Return the error data with the given id.
     *
     * @param id the error data id.
     * @return the error data with the matching id.
     * @should return error data with matching id.
     * @should return null when no error data with matching id.
     */
    @Override
    public ErrorData getErrorData(final Integer id) {
        return getErrorDataDao().getData(id);
    }

    /**
     * Return the error data with the given uuid.
     *
     * @param uuid the error data uuid.
     * @return the error data with the matching uuid.
     * @should return error data with matching uuid.
     * @should return null when no error data with matching uuid.
     */
    @Override
    public ErrorData getErrorDataByUuid(final String uuid) {
        return getErrorDataDao().getDataByUuid(uuid);
    }

    /**
     * Return all saved error data.
     *
     * @return all saved error data including voided error data.
     * @should return empty list when no error data are saved in the database.
     * @should return all saved error data.
     */
    @Override
    public List<ErrorData> getAllErrorData() {
        return getErrorDataDao().getAllData();
    }

    /**
     * Save error data into the database.
     *
     * @param errorData the error data.
     * @return saved error data.
     * @should save error data into the database.
     */
    @Override
    public ErrorData saveErrorData(final ErrorData errorData) {
        return getErrorDataDao().saveData(errorData);
    }

    /**
     * Delete error data from the database.
     *
     * @param errorData the error data
     * @should remove error data from the database
     */
    @Override
    public void purgeErrorData(final ErrorData errorData) {
        getErrorDataDao().purgeData(errorData);
    }

    /**
     * Return the archive data with the given id.
     *
     * @param id the archive data id.
     * @return the archive data with the matching id.
     * @should return archive data with matching id.
     * @should return null when no archive data with matching id.
     */
    @Override
    public ArchiveData getArchiveData(final Integer id) {
        return getArchiveDataDao().getData(id);
    }

    /**
     * Return the archive data with the given uuid.
     *
     * @param uuid the archive data uuid.
     * @return the archive data with the matching uuid.
     * @should return archive data with matching uuid.
     * @should return null when no archive data with matching uuid.
     */
    @Override
    public ArchiveData getArchiveDataByUuid(final String uuid) {
        return getArchiveDataDao().getDataByUuid(uuid);
    }

    /**
     * Return all saved archive data.
     *
     * @return all saved archive data including voided archive data.
     * @should return empty list when no archive data are saved in the database.
     * @should return all saved archive data.
     */
    @Override
    public List<ArchiveData> getAllArchiveData() {
        return getArchiveDataDao().getAllData();
    }

    /**
     * Save archive data into the database.
     *
     * @param archiveData the archive data.
     * @return saved archive data.
     * @should save archive data into the database.
     */
    @Override
    public ArchiveData saveArchiveData(final ArchiveData archiveData) {
        return getArchiveDataDao().saveData(archiveData);
    }

    /**
     * Delete archive data from the database.
     *
     * @param archiveData the archive data
     * @should remove archive data from the database
     */
    @Override
    public void purgeArchiveData(final ArchiveData archiveData) {
        getArchiveDataDao().purgeData(archiveData);
    }

    /**
     * Return the data source with the given id.
     *
     * @param id the data source id.
     * @return the data source with the matching id.
     * @should return data source with matching id.
     * @should return null when no data source with matching id.
     */
    @Override
    public DataSource getDataSource(final Integer id) {
        return getDataSourceDao().getById(id);
    }

    /**
     * Return the data source with the given uuid.
     *
     * @param uuid the data source uuid.
     * @return the data source with the matching uuid.
     * @should return data source with matching uuid.
     * @should return null when no data source with matching uuid.
     */
    @Override
    public DataSource getDataSourceByUuid(final String uuid) {
        return getDataSourceDao().getDataSourceByUuid(uuid);
    }

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
    @Override
    public List<DataSource> getAllDataSource(final String name, final boolean exactMatchOnly, final boolean includeVoided) {
        return getDataSourceDao().getAllDataSources(name, exactMatchOnly, includeVoided);
    }

    /**
     * Save data source into the database.
     *
     * @param dataSource the data source.
     * @return saved data source.
     * @should save data source into the database.
     */
    @Override
    public DataSource saveDataSource(final DataSource dataSource) {
        return getDataSourceDao().saveOrUpdate(dataSource);
    }

    /**
     * Delete data source from the database.
     *
     * @param dataSource the data source
     * @should remove data source from the database
     */
    @Override
    public void purgeDataSource(final DataSource dataSource) {
        getDataSourceDao().delete(dataSource);
    }

    /**
     * Return the notification data with the given id.
     *
     * @param id the notification data id.
     * @return the notification data with the matching id.
     * @should return notification data with matching id.
     * @should return null when no notification data with matching id.
     */
    @Override
    public NotificationData getNotificationData(final Integer id) {
        return getNotificationDataDao().getData(id);
    }

    /**
     * Return the notification data with the given uuid.
     *
     * @param uuid the notification data uuid.
     * @return the notification data with the matching uuid.
     * @should return notification data with matching uuid.
     * @should return null when no notification data with matching uuid.
     */
    @Override
    public NotificationData getNotificationDataByUuid(final String uuid) {
        return getNotificationDataDao().getDataByUuid(uuid);
    }

    /**
     * Return all saved notification data.
     *
     * @return all saved notification data including voided notification data.
     * @should return empty list when no notification data are saved in the database.
     * @should return all saved notification data.
     */
    @Override
    public List<NotificationData> getAllNotificationData() {
        return getNotificationDataDao().getAllData();
    }

    /**
     * Return all saved notification data for a particular person.
     *
     * @return all saved notification data including voided notification data.
     * @should return empty list when no notification data are saved in the database.
     * @should return all saved notification data.
     */
    @Override
    public List<NotificationData> getNotificationDataByReceiver(final Person person) {
        return getNotificationDataDao().getNotificationsByReceiver(person);
    }

    /**
     * Return all saved notification data from a particular person.
     *
     * @return all saved notification data including voided notification data.
     * @should return empty list when no notification data are saved in the database.
     * @should return all saved notification data.
     */
    @Override
    public List<NotificationData> getNotificationDataBySender(final Person person) {
        return getNotificationDataDao().getNotificationsBySender(person);
    }

    /**
     * Save notification data into the database.
     *
     * @param notificationData the notification data.
     * @return saved notification data.
     * @should save notification data into the database.
     */
    @Override
    public NotificationData saveNotificationData(final NotificationData notificationData) {
        return getNotificationDataDao().saveOrUpdate(notificationData);
    }

    /**
     * Delete notification data from the database.
     *
     * @param notificationData the notification data
     * @should remove notification data from the database
     */
    @Override
    public void purgeNotificationData(final NotificationData notificationData) {
        getNotificationDataDao().purgeData(notificationData);
    }

    /**
     * Void a single notification data.
     *
     * @param notificationData the notification data to be voided.
     * @return the voided notification data.
     */
    @Override
    public NotificationData voidNotificationData(final NotificationData notificationData, final String reason) {
        notificationData.setVoided(Boolean.TRUE);
        notificationData.setVoidedBy(Context.getAuthenticatedUser());
        notificationData.setDateVoided(new Date());
        notificationData.setVoidReason(reason);
        return saveNotificationData(notificationData);
    }
}
