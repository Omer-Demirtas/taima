package com.quartz.quartzexample.service.impl;

import com.quartz.quartzexample.dto.QrtzJobStateTrackerDTO;
import com.quartz.quartzexample.model.QrtzJobStateTracker;
import com.quartz.quartzexample.repository.QrtzJobStateTrackerRepository;
import com.quartz.quartzexample.service.QrtzJobStateTrackerService;
import com.quartz.quartzexample.utils.Constants;
import com.quartz.quartzexample.utils.DateFormatConverter;
import org.springframework.stereotype.Service;

@Service
public class QrtzJobStateTrackerServiceImpl implements QrtzJobStateTrackerService
{
    private final QrtzJobStateTrackerRepository qrtzJobStateTrackerRepository;
    private final DateFormatConverter dateFormatConverter;

    public QrtzJobStateTrackerServiceImpl(QrtzJobStateTrackerRepository qrtzJobStateTrackerRepository, DateFormatConverter dateFormatConverter) {
        this.qrtzJobStateTrackerRepository = qrtzJobStateTrackerRepository;
        this.dateFormatConverter = dateFormatConverter;
    }

    @Override
    public QrtzJobStateTracker findFirstByJobName(String jobName) {
        return qrtzJobStateTrackerRepository.findFirstByJobName(jobName);
    }

    @Override
    public QrtzJobStateTracker save(QrtzJobStateTracker qrtzJobStateTracker) {
        return qrtzJobStateTrackerRepository.save(qrtzJobStateTracker);
    }

    @Override
    public QrtzJobStateTrackerDTO getUpdateJobState() {
        QrtzJobStateTracker qrtzJobStateTracker = qrtzJobStateTrackerRepository.findFirstByJobName(Constants.jobNameUpdate);

        if (qrtzJobStateTracker == null)
            throw new IllegalArgumentException("update job can not found");

        formatProcess(qrtzJobStateTracker);
        return new QrtzJobStateTrackerDTO(qrtzJobStateTracker);
    }

    @Override
    public QrtzJobStateTrackerDTO getUpdateJobState(String jobName) {
        QrtzJobStateTracker qrtzJobStateTracker = qrtzJobStateTrackerRepository.findFirstByJobName(jobName);

        if (qrtzJobStateTracker == null)
            throw new IllegalArgumentException("update job can not found with name " + jobName);

        formatProcess(qrtzJobStateTracker);
        return new QrtzJobStateTrackerDTO(qrtzJobStateTracker);
    }

    private void formatProcess(QrtzJobStateTracker qrtzJobStateTracker) {
        String lastUpdate = qrtzJobStateTracker.getLastUpdate();
        String[] values = lastUpdate.split("T");
        lastUpdate = values[0] + " " + values[1].substring(0, 5);

        final String NEW_FORMAT = "dd/MM/yyyy hh:mm";
        final String OLD_FORMAT = "yyyyy-mm-dd hh:mm";
        String oldDateString = lastUpdate;
        String newDateString = dateFormatConverter.formatDate(NEW_FORMAT, OLD_FORMAT, oldDateString);

        qrtzJobStateTracker.setLastUpdate(newDateString);
    }
}
