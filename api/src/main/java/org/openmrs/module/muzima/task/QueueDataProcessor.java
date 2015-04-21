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
import org.openmrs.module.muzima.model.ErrorMessage;
import org.openmrs.module.muzima.model.QueueData;
import org.openmrs.module.muzima.model.handler.QueueDataHandler;
import org.openmrs.util.HandlerUtil;

import java.util.*;

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
            List<QueueDataHandler> queueDataHandlers =
                    HandlerUtil.getHandlersForType(QueueDataHandler.class, QueueData.class);
            for (QueueDataHandler queueDataHandler : queueDataHandlers) {
                Iterator<QueueData> queueDataIterator = queueDataList.iterator();
                while (queueDataIterator.hasNext()) {
                    QueueData queueData = queueDataIterator.next();
                    try {
                        if (queueDataHandler.accept(queueData)) {
                            queueDataHandler.process(queueData);
                            queueDataIterator.remove();
                            // archive them after we're done processing the queue data.
                            createArchiveData(queueData, "Queue data processed successfully!");
                            dataService.purgeQueueData(queueData);
                        }
                    } catch (Exception e) {
                        log.error("Unable to process queue data due to: " + e.getMessage(), e);
                        createErrorData(queueData, (QueueProcessorException)e);
                        dataService.purgeQueueData(queueData);
                    }
                }
            }
        } finally {
            isRunning = false;
        }
    }

    private void createArchiveData(final QueueData queueData, final String message) {
        ArchiveData archiveData = new ArchiveData(queueData);
        archiveData.setMessage(message);
        archiveData.setDateArchived(new Date());
        Context.getService(DataService.class).saveArchiveData(archiveData);
    }

    private void createErrorData(final QueueData queueData, QueueProcessorException exception) {
        ErrorData errorData = new ErrorData(queueData);
        errorData.setDateProcessed(new Date());
        Set errorMessage = new HashSet();
        for(Exception e : exception.getAllException()){
            ErrorMessage error = new ErrorMessage();
            error.setMessage(e.getMessage());
            errorMessage.add(error);
        }
        errorData.setMessage("Unable to process queue data");
        errorData.setErrorMessages(errorMessage);
        Context.getService(DataService.class).saveErrorData(errorData);
    }
}
