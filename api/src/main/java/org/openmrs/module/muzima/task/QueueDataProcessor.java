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
package org.openmrs.module.muzima.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.muzima.api.service.DataService;
import org.openmrs.module.muzima.exception.QueueProcessorException;
import org.openmrs.module.muzima.model.ArchiveData;
import org.openmrs.module.muzima.model.ErrorData;
import org.openmrs.module.muzima.model.QueueData;
import org.openmrs.module.muzima.model.handler.QueueDataHandler;
import org.openmrs.util.HandlerUtil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 */
public class QueueDataProcessor {

    private final Log log = LogFactory.getLog(QueueDataProcessor.class);

    private static Boolean isRunning = false;

    public void processQueueData() {
        if (!isRunning) {
            log.info("Starting up queue data processor ...");
            processAllQueueData();
        } else {
            log.info("Queue data processor aborting (another processor already running)!");
        }
    }

    private void processAllQueueData() {
        try {
            isRunning = true;
            DataService dataService = Context.getService(DataService.class);
            List<QueueData> queueDataList = dataService.getAllQueueData();
            Iterator<QueueData> queueDataIterator = queueDataList.iterator();
            while (queueDataIterator.hasNext()) {
                QueueData queueData = queueDataIterator.next();
                try {
                    QueueDataHandler queueDataHandler = findHandler(queueData);
                    queueDataHandler.process(queueData);
                    // archive them after we're done processing the queue data.
                    createArchiveData(queueData, "Queue data processed successfully!");
                } catch (Exception e) {
                    log.error("Unable to process queue data due to: " + e.getMessage(), e);
                    createErrorData(queueData, "Unable to process queue data due to: " + e.getMessage());
                }
                dataService.purgeQueueData(queueData);
            }
        } finally {
            isRunning = false;
        }
    }

    private QueueDataHandler findHandler(final QueueData queueData) {
        QueueDataHandler queueDataHandler = null;
        List<QueueDataHandler> queueDataHandlers =
                HandlerUtil.getHandlersForType(QueueDataHandler.class, QueueData.class);
        for (int i = 0; i < queueDataHandlers.size(); i++) {
            queueDataHandler = queueDataHandlers.get(i);
            if (queueDataHandler.accept(queueData)) {
                return queueDataHandler;
            }
        }
        return queueDataHandler;
    }

    private void createArchiveData(final QueueData queueData, final String message) {
        ArchiveData archiveData = new ArchiveData(queueData);
        archiveData.setMessage(message);
        archiveData.setDateArchived(new Date());
        Context.getService(DataService.class).saveArchiveData(archiveData);
    }

    private void createErrorData(final QueueData queueData, final String message) {
        ErrorData errorData = new ErrorData(queueData);
        errorData.setMessage(message);
        errorData.setDateProcessed(new Date());
        Context.getService(DataService.class).saveErrorData(errorData);
    }
}
